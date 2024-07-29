package cn.econets.blossom.framework.common.pojo;

import cn.econets.blossom.framework.common.exception.ErrorCode;
import cn.econets.blossom.framework.common.exception.ServiceException;
import cn.econets.blossom.framework.common.exception.enums.GlobalErrorCodeConstants;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.util.Assert;

import java.io.Serializable;
import java.util.Objects;

/**
 * General return
 *
 * @param <T> Data Generics
 */
@Data
public class CommonResult<T> implements Serializable {

    /**
     * Error code
     *
     * @see ErrorCode#getCode()
     */
    private Integer code;
    /**
     * Return data
     */
    private T data;
    /**
     * Error message，Users can read
     *
     * @see ErrorCode#getMsg()
     */
    private String msg;

    /**
     * The incoming result Object，Converted into another generic result object
     *
     * Because A Method returns CommonResult Object，Does not satisfy the requirement to call it B Method return，So conversion is needed。
     *
     * @param result Incoming result Object
     * @param <T>    Returned generic type
     * @return New CommonResult Object
     */
    public static <T> CommonResult<T> error(CommonResult<?> result) {
        return error(result.getCode(), result.getMsg());
    }

    public static <T> CommonResult<T> error(Integer code, String message) {
        Assert.isTrue(!GlobalErrorCodeConstants.SUCCESS.getCode().equals(code), "code Must be wrong！");
        CommonResult<T> result = new CommonResult<>();
        result.code = code;
        result.msg = message;
        return result;
    }

    public static <T> CommonResult<T> error(ErrorCode errorCode) {
        return error(errorCode.getCode(), errorCode.getMsg());
    }

    public static <T> CommonResult<T> success(T data) {
        CommonResult<T> result = new CommonResult<>();
        result.code = GlobalErrorCodeConstants.SUCCESS.getCode();
        result.data = data;
        result.msg = "";
        return result;
    }

    public static boolean isSuccess(Integer code) {
        return Objects.equals(code, GlobalErrorCodeConstants.SUCCESS.getCode());
    }

    @JsonIgnore // Avoid jackson Serialization
    public boolean isSuccess() {
        return isSuccess(code);
    }

    @JsonIgnore // Avoid jackson Serialization
    public boolean isError() {
        return !isSuccess();
    }

    // ========= Maki Exception Abnormal system integration =========

    /**
     * Judge whether there is an abnormality。If there is one，Throw {@link ServiceException} Abnormal
     */
    public void checkError() throws ServiceException {
        if (isSuccess()) {
            return;
        }
        // Business exception
        throw new ServiceException(code, msg);
    }

    /**
     * Judge whether there is an abnormality。If there is one，Throw {@link ServiceException} Abnormal
     * If not，Then return {@link #data} Data
     */
    @JsonIgnore // Avoid jackson Serialization
    public T getCheckedData() {
        checkError();
        return data;
    }

    public static <T> CommonResult<T> error(ServiceException serviceException) {
        return error(serviceException.getCode(), serviceException.getMessage());
    }

}
