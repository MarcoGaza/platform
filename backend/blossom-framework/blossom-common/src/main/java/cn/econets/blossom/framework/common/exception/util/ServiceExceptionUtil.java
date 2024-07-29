package cn.econets.blossom.framework.common.exception.util;

import cn.econets.blossom.framework.common.exception.ErrorCode;
import cn.econets.blossom.framework.common.exception.ServiceException;
import cn.econets.blossom.framework.common.exception.enums.GlobalErrorCodeConstants;
import com.google.common.annotations.VisibleForTesting;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * {@link ServiceException} Tools
 *
 * The purpose is，Formatting exception information prompt。
 * Taking into account String.format An error will be reported if the parameters are incorrect，Therefore use {} As a placeholder，and use {@link #doFormat(int, String, Object...)} Method to format
 *
 * Because {@link #MESSAGES} By default, there is no template with exception information prompt，So the user needs to initialize it himself。There are several ways I can think of at the moment：
 *
 * 1. Abnormal prompt information，Written in the enumeration class，For example，cn.econets.oceans.user.api.constants.ErrorCodeEnum Class + ServiceExceptionConfiguration
 * 2. Abnormal prompt information，Written in .properties etc. configuration files
 * 3. Abnormal prompt information，Written in Apollo In the configuration center，So as to achieve dynamic refresh
 * 4. Abnormal prompt information，Stored in db In the database，So as to achieve dynamic refresh
 */
@Slf4j
public class ServiceExceptionUtil {

    /**
     * Error code prompt template
     */
    private static final ConcurrentMap<Integer, String> MESSAGES = new ConcurrentHashMap<>();

    public static void putAll(Map<Integer, String> messages) {
        ServiceExceptionUtil.MESSAGES.putAll(messages);
    }

    public static void put(Integer code, String message) {
        ServiceExceptionUtil.MESSAGES.put(code, message);
    }

    public static void delete(Integer code, String message) {
        ServiceExceptionUtil.MESSAGES.remove(code, message);
    }

    // ========== Maki ServiceException Integration ==========

    public static ServiceException exception(ErrorCode errorCode) {
        String messagePattern = MESSAGES.getOrDefault(errorCode.getCode(), errorCode.getMsg());
        return exception0(errorCode.getCode(), messagePattern);
    }

    public static ServiceException exception(ErrorCode errorCode, Object... params) {
        String messagePattern = MESSAGES.getOrDefault(errorCode.getCode(), errorCode.getMsg());
        return exception0(errorCode.getCode(), messagePattern, params);
    }

    /**
     * Create the specified number ServiceException Abnormality
     *
     * @param code Number
     * @return Abnormal
     */
    public static ServiceException exception(Integer code) {
        return exception0(code, MESSAGES.get(code));
    }

    /**
     * Create the specified number ServiceException Abnormality
     *
     * @param code Number
     * @param params Parameters corresponding to the placeholders of message prompts
     * @return Abnormal
     */
    public static ServiceException exception(Integer code, Object... params) {
        return exception0(code, MESSAGES.get(code), params);
    }

    public static ServiceException exception0(Integer code, String messagePattern, Object... params) {
        String message = doFormat(code, messagePattern, params);
        return new ServiceException(code, message);
    }

    public static ServiceException invalidParamException(String messagePattern, Object... params) {
        return exception0(GlobalErrorCodeConstants.BAD_REQUEST.getCode(), messagePattern, params);
    }

    // ========== Formatting method ==========

    /**
     * Use the message corresponding to the error number params Format。
     *
     * @param code           Error number
     * @param messagePattern Message template
     * @param params         Parameters
     * @return Formatted prompt
     */
    @VisibleForTesting
    public static String doFormat(int code, String messagePattern, Object... params) {
        StringBuilder sbuf = new StringBuilder(messagePattern.length() + 50);
        int i = 0;
        int j;
        int l;
        for (l = 0; l < params.length; l++) {
            j = messagePattern.indexOf("{}", i);
            if (j == -1) {
                log.error("[doFormat][Too many parameters：Error code({})|Error content({})|Parameters({})", code, messagePattern, params);
                if (i == 0) {
                    return messagePattern;
                } else {
                    sbuf.append(messagePattern.substring(i));
                    return sbuf.toString();
                }
            } else {
                sbuf.append(messagePattern, i, j);
                sbuf.append(params[l]);
                i = j + 2;
            }
        }
        if (messagePattern.indexOf("{}", i) != -1) {
            log.error("[doFormat][Too few parameters：Error code({})|Error content({})|Parameters({})", code, messagePattern, params);
        }
        sbuf.append(messagePattern.substring(i));
        return sbuf.toString();
    }

}
