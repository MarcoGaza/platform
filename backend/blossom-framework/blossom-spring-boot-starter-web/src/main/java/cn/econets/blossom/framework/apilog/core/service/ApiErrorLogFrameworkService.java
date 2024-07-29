package cn.econets.blossom.framework.apilog.core.service;

/**
 * API Error log Framework Service Interface
 *
 */
public interface ApiErrorLogFrameworkService {

    /**
     * Create API Error log
     *
     * @param apiErrorLog API Error log
     */
    void createApiErrorLog(ApiErrorLog apiErrorLog);
}
