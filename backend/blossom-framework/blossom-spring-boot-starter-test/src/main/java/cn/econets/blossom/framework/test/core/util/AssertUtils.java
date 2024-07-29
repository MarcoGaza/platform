package cn.econets.blossom.framework.test.core.util;

import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.ReflectUtil;
import cn.econets.blossom.framework.common.exception.ErrorCode;
import cn.econets.blossom.framework.common.exception.ServiceException;
import cn.econets.blossom.framework.common.exception.util.ServiceExceptionUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.function.Executable;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Unit Test，assert Assertion Tool Class
 *
 */
public class AssertUtils {

    /**
     * Compare the properties of two objects to see if they are consistent
     *
     * Attention，If expected Properties of existence，actual When it does not exist，Will be ignored
     *
     * @param expected Expected object
     * @param actual Actual object
     * @param ignoreFields Array of ignored properties
     */
    public static void assertPojoEquals(Object expected, Object actual, String... ignoreFields) {
        Field[] expectedFields = ReflectUtil.getFields(expected.getClass());
        Arrays.stream(expectedFields).forEach(expectedField -> {
            // Ignore jacoco Automatically generated $jacocoData Attributes
            if (expectedField.isSynthetic()) {
                return;
            }
            // If it is an ignored attribute，No comparison is performed
            if (ArrayUtil.contains(ignoreFields, expectedField.getName())) {
                return;
            }
            // Ignore non-existent properties
            Field actualField = ReflectUtil.getField(actual.getClass(), expectedField.getName());
            if (actualField == null) {
                return;
            }
            // Comparison
            Assertions.assertEquals(
                    ReflectUtil.getFieldValue(expected, expectedField),
                    ReflectUtil.getFieldValue(actual, actualField),
                    String.format("Field(%s) No match", expectedField.getName())
            );
        });
    }

    /**
     * Compare the properties of two objects to see if they are consistent
     *
     * Attention，If expected Properties of existence，actual When it does not exist，Will be ignored
     *
     * @param expected Expected object
     * @param actual Actual object
     * @param ignoreFields Array of ignored properties
     * @return Are they consistent?
     */
    public static boolean isPojoEquals(Object expected, Object actual, String... ignoreFields) {
        Field[] expectedFields = ReflectUtil.getFields(expected.getClass());
        return Arrays.stream(expectedFields).allMatch(expectedField -> {
            // If it is an ignored attribute，No comparison is performed
            if (ArrayUtil.contains(ignoreFields, expectedField.getName())) {
                return true;
            }
            // Ignore non-existent properties
            Field actualField = ReflectUtil.getField(actual.getClass(), expectedField.getName());
            if (actualField == null) {
                return true;
            }
            return Objects.equals(ReflectUtil.getFieldValue(expected, expectedField),
                    ReflectUtil.getFieldValue(actual, actualField));
        });
    }

    /**
     * Execution method，Check thrown Service Whether it meets the conditions
     *
     * @param executable Business exception
     * @param errorCode Error code object
     * @param messageParams Message parameters
     */
    public static void assertServiceException(Executable executable, ErrorCode errorCode, Object... messageParams) {
        // Calling method
        ServiceException serviceException = assertThrows(ServiceException.class, executable);
        // Verification error code
        Assertions.assertEquals(errorCode.getCode(), serviceException.getCode(), "Error code does not match");
        String message = ServiceExceptionUtil.doFormat(errorCode.getCode(), errorCode.getMsg(), messageParams);
        Assertions.assertEquals(message, serviceException.getMessage(), "Error message does not match");
    }

}
