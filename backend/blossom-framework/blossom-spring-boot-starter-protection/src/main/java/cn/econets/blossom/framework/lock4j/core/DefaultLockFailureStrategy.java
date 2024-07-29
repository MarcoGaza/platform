package cn.econets.blossom.framework.lock4j.core;

import cn.econets.blossom.framework.common.exception.ServiceException;
import cn.econets.blossom.framework.common.exception.enums.GlobalErrorCodeConstants;
import com.baomidou.lock.LockFailureStrategy;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Method;

/**
 * Customize the lock acquisition failure strategy，Throw {@link ServiceException} Abnormal
 */
@Slf4j
public class DefaultLockFailureStrategy implements LockFailureStrategy {

    @Override
    public void onLockFailure(String key, Method method, Object[] arguments) {
        log.debug("[onLockFailure][Thread:{} Failed to obtain lock，key:{} Failed to obtain:{} ]", Thread.currentThread().getName(), key, arguments);
        throw new ServiceException(GlobalErrorCodeConstants.LOCKED);
    }
}
