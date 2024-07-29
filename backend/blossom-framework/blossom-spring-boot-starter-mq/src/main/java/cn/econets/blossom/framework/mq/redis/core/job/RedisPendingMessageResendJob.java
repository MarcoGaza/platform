package cn.econets.blossom.framework.mq.redis.core.job;

import cn.econets.blossom.framework.mq.redis.core.RedisMQTemplate;
import cn.econets.blossom.framework.mq.redis.core.stream.AbstractRedisStreamMessageListener;
import cn.hutool.core.collection.CollUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.data.domain.Range;
import org.springframework.data.redis.connection.stream.*;
import org.springframework.data.redis.core.StreamOperations;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * This task is used to process，crash The messages that the subsequent consumers have not consumed
 */
@Slf4j
@AllArgsConstructor
public class RedisPendingMessageResendJob {

    private static final String LOCK_KEY = "redis:pending:msg:lock";

    /**
     * Message timeout，Default 5 minutes
     *
     * 1. Timed out messages will be redelivered
     * 2. Due to scheduled tasks 1 Once per minute，The message will not be resubmitted immediately after timeout，Message in extreme cases5After the minutes have expired，Wait a little longer 1 It will take minutes to be scanned
     */
    private static final int EXPIRE_TIME = 5 * 60;

    private final List<AbstractRedisStreamMessageListener<?>> listeners;
    private final RedisMQTemplate redisTemplate;
    private final String groupName;
    private final RedissonClient redissonClient;

    /**
     * Execute once a minute,Select every minute here35Execute in seconds，This is to avoid the problem of too many tasks at the hour
     */
    @Scheduled(cron = "35 * * * * ?")
    public void messageResend() {
        RLock lock = redissonClient.getLock(LOCK_KEY);
        // Try to lock
        if (lock.tryLock()) {
            try {
                execute();
            } catch (Exception ex) {
                log.error("[messageResend][Execution exception]", ex);
            } finally {
                lock.unlock();
            }
        }
    }

    /**
     * Execute cleanup logic
     *
     * @see <a href="https://gitee.com/zhijiantianya/econets-vue/pulls/480/files">Discussion</a>
     */
    private void execute() {
        StreamOperations<String, Object, Object> ops = redisTemplate.getRedisTemplate().opsForStream();
        listeners.forEach(listener -> {
            PendingMessagesSummary pendingMessagesSummary = Objects.requireNonNull(ops.pending(listener.getStreamKey(), groupName));
            // Each consumer pending Number of queue messages
            Map<String, Long> pendingMessagesPerConsumer = pendingMessagesSummary.getPendingMessagesPerConsumer();
            pendingMessagesPerConsumer.forEach((consumerName, pendingMessageCount) -> {
                log.info("[processPendingMessage][Consumer({}) Number of messages({})]", consumerName, pendingMessageCount);
                // Each consumer pendingDetails of the message
                PendingMessages pendingMessages = ops.pending(listener.getStreamKey(), Consumer.from(groupName, consumerName), Range.unbounded(), pendingMessageCount);
                if (pendingMessages.isEmpty()) {
                    return;
                }
                pendingMessages.forEach(pendingMessage -> {
                    // Get the message last delivered to consumer Time,
                    long lastDelivery = pendingMessage.getElapsedTimeSinceLastDelivery().getSeconds();
                    if (lastDelivery < EXPIRE_TIME){
                        return;
                    }
                    // Get the specified value id Message body
                    List<MapRecord<String, Object, Object>> records = ops.range(listener.getStreamKey(),
                            Range.of(Range.Bound.inclusive(pendingMessage.getIdAsString()), Range.Bound.inclusive(pendingMessage.getIdAsString())));
                    if (CollUtil.isEmpty(records)) {
                        return;
                    }
                    // Redeliver message
                    redisTemplate.getRedisTemplate().opsForStream().add(StreamRecords.newRecord()
                            .ofObject(records.get(0).getValue()) // Setting content
                            .withStreamKey(listener.getStreamKey()));
                    // ack Message consumption completed
                    redisTemplate.getRedisTemplate().opsForStream().acknowledge(groupName, records.get(0));
                    log.info("[processPendingMessage][Message({})Re-delivery successful]", records.get(0).getId());
                });
            });
        });
    }
}
