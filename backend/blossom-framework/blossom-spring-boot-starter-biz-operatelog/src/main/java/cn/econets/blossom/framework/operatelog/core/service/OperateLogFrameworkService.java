package cn.econets.blossom.framework.operatelog.core.service;

/**
 * Operation log Framework Service Interface
 *
 */
public interface OperateLogFrameworkService {

    /**
     * Record operation log
     *
     * @param operateLog Operation log request
     */
    void createOperateLog(OperateLog operateLog);

}
