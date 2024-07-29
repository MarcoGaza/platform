package cn.econets.blossom.framework.excel.core.annotations;

import java.lang.annotation.*;

/**
 * Dictionary formatting
 *
 * Realize the value of dictionary data，Tags formatted as dictionary data
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface DictFormat {

    /**
     * For example，SysDictTypeConstants、InfDictTypeConstants
     *
     * @return Dictionary type
     */
    String value();

}
