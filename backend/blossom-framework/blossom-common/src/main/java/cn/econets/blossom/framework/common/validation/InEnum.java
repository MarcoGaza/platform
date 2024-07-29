package cn.econets.blossom.framework.common.validation;


import cn.econets.blossom.framework.common.core.IntArrayValuable;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target({
        ElementType.METHOD,
        ElementType.FIELD,
        ElementType.ANNOTATION_TYPE,
        ElementType.CONSTRUCTOR,
        ElementType.PARAMETER,
        ElementType.TYPE_USE
})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(
        validatedBy = {InEnumValidator.class, InEnumCollectionValidator.class}
)
public @interface InEnum {

    /**
     * @return Realization EnumValuable Interface
     */
    Class<? extends IntArrayValuable> value();

    String message() default "Must be within the specified range {value}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
