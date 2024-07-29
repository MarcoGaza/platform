package cn.econets.blossom.framework.errorcode.core.loader;

import cn.econets.blossom.framework.common.exception.util.ServiceExceptionUtil;

/**
 * Error code loader
 *
 * Attention，Error code finally loaded into {@link ServiceExceptionUtil} of MESSAGES In variables！
 *
 */
public interface ErrorCodeLoader {

    /**
     * Add error code
     *
     * @param code Error code number
     * @param msg Error code prompt
     */
    default void putErrorCode(Integer code, String msg) {
        ServiceExceptionUtil.put(code, msg);
    }

    /**
     * Refresh error code
     */
    void refreshErrorCodes();

    /**
     * Loading error code
     */
    void loadErrorCodes();

}
