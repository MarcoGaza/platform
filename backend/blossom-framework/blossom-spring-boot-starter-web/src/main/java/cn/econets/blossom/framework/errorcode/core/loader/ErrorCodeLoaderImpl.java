package cn.econets.blossom.framework.errorcode.core.loader;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.exceptions.ExceptionUtil;
import cn.econets.blossom.framework.common.util.date.DateUtils;
import cn.econets.blossom.module.system.api.errorcode.ErrorCodeApi;
import cn.econets.blossom.module.system.api.errorcode.dto.ErrorCodeRespDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.LocalDateTime;
import java.util.List;

/**
 * ErrorCodeLoader Implementation class of，From infra in the database，Loading error code。
 *
 * Taking into account the error code will be refreshed，So follow {@link #REFRESH_ERROR_CODE_PERIOD} Frequency，Incremental loading error code。
 *
 */
@RequiredArgsConstructor
@Slf4j
public class ErrorCodeLoaderImpl implements ErrorCodeLoader {

    /**
     * Frequency of refreshing error codes，Unit：milliseconds
     */
    private static final int REFRESH_ERROR_CODE_PERIOD = 60 * 1000;

    /**
     * Application Grouping
     */
    private final String applicationName;
    /**
     * Error code Api
     */
    private final ErrorCodeApi errorCodeApi;

    /**
     * Maximum update time of cached error code，Used for subsequent incremental polling，Judge whether there is an update
     */
    private LocalDateTime maxUpdateTime;

    @Override
    @EventListener(ApplicationReadyEvent.class)
    @Async // Asynchronous，Ensure the project startup process，It is not a critical process after all
    public void loadErrorCodes() {
        loadErrorCodes0();
    }

    @Override
    @Scheduled(fixedDelay = REFRESH_ERROR_CODE_PERIOD, initialDelay = REFRESH_ERROR_CODE_PERIOD)
    public void refreshErrorCodes() {
        loadErrorCodes0();
    }

    private void loadErrorCodes0() {
        try {
            // Loading error code
            List<ErrorCodeRespDTO> errorCodeRespDTOs = errorCodeApi.getErrorCodeList(applicationName, maxUpdateTime);
            if (CollUtil.isEmpty(errorCodeRespDTOs)) {
                return;
            }
            log.info("[loadErrorCodes0][Load to ({}) Error code]", errorCodeRespDTOs.size());

            // Refresh error code cache
            errorCodeRespDTOs.forEach(errorCodeRespDTO -> {
                // Write to the error code cache
                putErrorCode(errorCodeRespDTO.getCode(), errorCodeRespDTO.getMessage());
                // Record the update time，Convenient incremental update
                maxUpdateTime = DateUtils.max(maxUpdateTime, errorCodeRespDTO.getUpdateTime());
            });
        } catch (Exception ex) {
            log.error("[loadErrorCodes0][Load error code failed({})]", ExceptionUtil.getRootCauseMessage(ex));
        }
    }

}
