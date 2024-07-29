package cn.econets.blossom.framework.apilog.core.service;

/**
 * API Access log Framework Service Interface
 *
 */
public interface ApiAccessLogFrameworkService {

    /**
     * Create API Access log
     *
     * @param apiAccessLog API Access log
     */
    void createApiAccessLog(ApiAccessLog apiAccessLog);
}
