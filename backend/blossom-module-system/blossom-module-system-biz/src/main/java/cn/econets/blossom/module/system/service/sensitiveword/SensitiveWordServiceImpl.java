package cn.econets.blossom.module.system.service.sensitiveword;

import cn.econets.blossom.framework.common.enums.CommonStatusEnum;
import cn.econets.blossom.framework.common.pojo.PageResult;
import cn.econets.blossom.framework.common.util.collection.CollectionUtils;
import cn.econets.blossom.framework.common.util.object.BeanUtils;
import cn.econets.blossom.module.system.controller.admin.sensitiveword.vo.SensitiveWordPageReqVO;
import cn.econets.blossom.module.system.controller.admin.sensitiveword.vo.SensitiveWordSaveVO;
import cn.econets.blossom.module.system.dal.dataobject.sensitiveword.SensitiveWordDO;
import cn.econets.blossom.module.system.dal.mysql.sensitiveword.SensitiveWordMapper;
import cn.econets.blossom.module.system.enums.ErrorCodeConstants;
import cn.econets.blossom.module.system.util.collection.SimpleTrie;
import cn.hutool.core.collection.CollUtil;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.validation.annotation.Validated;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.TimeUnit;

import static cn.econets.blossom.framework.common.exception.util.ServiceExceptionUtil.exception;

/**
 * Sensitive words Service Implementation class
 *
 */
@Service
@Slf4j
@Validated
public class SensitiveWordServiceImpl implements SensitiveWordService {

    /**
     * Whether to enable the sensitive word function
     */
    public static Boolean ENABLED = false;

    /**
     * Sensitive word list cache
     */
    @Getter
    private volatile List<SensitiveWordDO> sensitiveWordCache = Collections.emptyList();
    /**
     * Sensitive word label cache
     * key：Sensitive word number {@link SensitiveWordDO#getId()}
     * <p>
     * Here is the statement volatile The reason for modification is，Every time you refresh，Directly modify the point
     */
    @Getter
    private volatile Set<String> sensitiveWordTagsCache = Collections.emptySet();

    @Resource
    private SensitiveWordMapper sensitiveWordMapper;

    /**
     * The default dictionary tree of sensitive words，Include all sensitive words
     */
    @Getter
    private volatile SimpleTrie defaultSensitiveWordTrie = new SimpleTrie(Collections.emptySet());
    /**
     * Mapping of labels and sensitive word fields
     */
    @Getter
    private volatile Map<String, SimpleTrie> tagSensitiveWordTries = Collections.emptyMap();

    /**
     * Initialize cache
     */
    @PostConstruct
    public void initLocalCache() {
        if (!ENABLED) {
            return;
        }

        // First step：Query data
        List<SensitiveWordDO> sensitiveWords = sensitiveWordMapper.selectList();
        log.info("[initLocalCache][Cache sensitive words，The quantity is:{}]", sensitiveWords.size());

        // Step 2：Build Cache
        // Write sensitiveWordTagsCache Cache
        Set<String> tags = new HashSet<>();
        sensitiveWords.forEach(word -> tags.addAll(word.getTags()));
        sensitiveWordTagsCache = tags;
        sensitiveWordCache = sensitiveWords;
        // Write defaultSensitiveWordTrie、tagSensitiveWordTries Cache
        initSensitiveWordTrie(sensitiveWords);
    }

    private void initSensitiveWordTrie(List<SensitiveWordDO> wordDOs) {
        // Filter banned sensitive words
        wordDOs = CollectionUtils.filterList(wordDOs, word -> word.getStatus().equals(CommonStatusEnum.ENABLE.getStatus()));

        // Initialize default defaultSensitiveWordTrie
        this.defaultSensitiveWordTrie = new SimpleTrie(CollectionUtils.convertList(wordDOs, SensitiveWordDO::getName));

        // Initialization tagSensitiveWordTries
        Multimap<String, String> tagWords = HashMultimap.create();
        for (SensitiveWordDO word : wordDOs) {
            if (CollUtil.isEmpty(word.getTags())) {
                continue;
            }
            word.getTags().forEach(tag -> tagWords.put(tag, word.getName()));
        }
        // Add to tagSensitiveWordTries Medium
        Map<String, SimpleTrie> tagSensitiveWordTries = new HashMap<>();
        tagWords.asMap().forEach((tag, words) -> tagSensitiveWordTries.put(tag, new SimpleTrie(words)));
        this.tagSensitiveWordTries = tagSensitiveWordTries;
    }

    /**
     * Polling through scheduled tasks，Refresh cache
     *
     * Purpose：Multi-node deployment，Through polling”Notification“All nodes，Refresh
     */
    @Scheduled(initialDelay = 60, fixedRate = 60, timeUnit = TimeUnit.SECONDS)
    public void refreshLocalCache() {
        // Situation 1：If there is no data in the cache，Refresh the cache directly
        if (CollUtil.isEmpty(sensitiveWordCache)) {
            initLocalCache();
            return;
        }

        // Situation 2，If the data in the cache，Passed updateTime Judge whether there is data change，Refresh cache if there is a change
        LocalDateTime maxTime = CollectionUtils.getMaxValue(sensitiveWordCache, SensitiveWordDO::getUpdateTime);
        if (sensitiveWordMapper.selectCountByUpdateTimeGt(maxTime) > 0) {
            initLocalCache();
        }
    }

    @Override
    public Long createSensitiveWord(SensitiveWordSaveVO createReqVO) {
        // Verify uniqueness
        validateSensitiveWordNameUnique(null, createReqVO.getName());

        // Insert
        SensitiveWordDO sensitiveWord = BeanUtils.toBean(createReqVO, SensitiveWordDO.class);
        sensitiveWordMapper.insert(sensitiveWord);

        // Refresh cache
        initLocalCache();
        return sensitiveWord.getId();
    }

    @Override
    public void updateSensitiveWord(SensitiveWordSaveVO updateReqVO) {
        // Verify uniqueness
        validateSensitiveWordExists(updateReqVO.getId());
        validateSensitiveWordNameUnique(updateReqVO.getId(), updateReqVO.getName());

        // Update
        SensitiveWordDO updateObj = BeanUtils.toBean(updateReqVO, SensitiveWordDO.class);
        sensitiveWordMapper.updateById(updateObj);

        // Refresh cache
        initLocalCache();
    }

    @Override
    public void deleteSensitiveWord(Long id) {
        // Check existence
        validateSensitiveWordExists(id);
        // Delete
        sensitiveWordMapper.deleteById(id);

        // Refresh cache
        initLocalCache();
    }

    private void validateSensitiveWordNameUnique(Long id, String name) {
        SensitiveWordDO word = sensitiveWordMapper.selectByName(name);
        if (word == null) {
            return;
        }
        // If id Empty，Indicates that there is no need to compare whether they are the same id sensitive words
        if (id == null) {
            throw exception(ErrorCodeConstants.SENSITIVE_WORD_EXISTS);
        }
        if (!word.getId().equals(id)) {
            throw exception(ErrorCodeConstants.SENSITIVE_WORD_EXISTS);
        }
    }

    private void validateSensitiveWordExists(Long id) {
        if (sensitiveWordMapper.selectById(id) == null) {
            throw exception(ErrorCodeConstants.SENSITIVE_WORD_NOT_EXISTS);
        }
    }

    @Override
    public SensitiveWordDO getSensitiveWord(Long id) {
        return sensitiveWordMapper.selectById(id);
    }

    @Override
    public List<SensitiveWordDO> getSensitiveWordList() {
        return sensitiveWordMapper.selectList();
    }

    @Override
    public PageResult<SensitiveWordDO> getSensitiveWordPage(SensitiveWordPageReqVO pageReqVO) {
        return sensitiveWordMapper.selectPage(pageReqVO);
    }

    @Override
    public Set<String> getSensitiveWordTagSet() {
        return sensitiveWordTagsCache;
    }

    @Override
    public List<String> validateText(String text, List<String> tags) {
        Assert.isTrue(ENABLED, "Sensitive word function is not enabled，Please ENABLED Set to true");

        // When there is no label，Default all
        if (CollUtil.isEmpty(tags)) {
            return defaultSensitiveWordTrie.validate(text);
        }
        // With label
        Set<String> result = new HashSet<>();
        tags.forEach(tag -> {
            SimpleTrie trie = tagSensitiveWordTries.get(tag);
            if (trie == null) {
                return;
            }
            result.addAll(trie.validate(text));
        });
        return new ArrayList<>(result);
    }

    @Override
    public boolean isTextValid(String text, List<String> tags) {
        Assert.isTrue(ENABLED, "Sensitive word function is not enabled，Please ENABLED Set to true");

        // When there is no label，Default all
        if (CollUtil.isEmpty(tags)) {
            return defaultSensitiveWordTrie.isValid(text);
        }
        // With label
        for (String tag : tags) {
            SimpleTrie trie = tagSensitiveWordTries.get(tag);
            if (trie == null) {
                continue;
            }
            // If a tag is invalid，Then return false Illegal
            if (!trie.isValid(text)) {
                return false;
            }
        }
        return true;
    }

}
