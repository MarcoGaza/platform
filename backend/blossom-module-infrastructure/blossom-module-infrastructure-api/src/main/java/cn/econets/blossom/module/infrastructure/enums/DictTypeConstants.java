package cn.econets.blossom.module.infrastructure.enums;

/**
 * Infra Dictionary type enumeration class
 *
 */
public interface DictTypeConstants {

    String REDIS_TIMEOUT_TYPE = "infra_redis_timeout_type"; // Redis Timeout type

    String JOB_STATUS = "infra_job_status"; // Enumeration of scheduled task status
    String JOB_LOG_STATUS = "infra_job_log_status"; // Enumeration of scheduled task log status

    String API_ERROR_LOG_PROCESS_STATUS = "infra_api_error_log_process_status"; // API Enumeration of error log processing status

    String CONFIG_TYPE = "infra_config_type"; // Parameter configuration type
    String BOOLEAN_STRING = "infra_boolean_string"; // Boolean Whether type

}
