package cn.econets.blossom.framework.common.validation;


import cn.econets.blossom.framework.common.core.IntArrayValuable;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class InEnumValidator implements ConstraintValidator<InEnum, Integer> {

    private List<Integer> values;

    @Override
    public void initialize(InEnum annotation) {
        IntArrayValuable[] values = annotation.value().getEnumConstants();
        if (values.length == 0) {
            this.values = Collections.emptyList();
        } else {
            this.values = Arrays.stream(values[0].array()).boxed().collect(Collectors.toList());
        }
    }

    @Override
    public boolean isValid(Integer value, ConstraintValidatorContext context) {
        // When empty，No verification by default，It is considered passed
        if (value == null) {
            return true;
        }
        // Verification passed
        if (values.contains(value)) {
            return true;
        }
        // Verification failed，Custom prompt statement（Because，Annotation value is an enumeration class，Unable to obtain the actual value of the enumeration class）
        context.disableDefaultConstraintViolation(); // Disable default message Value of
        context.buildConstraintViolationWithTemplate(context.getDefaultConstraintMessageTemplate()
                .replaceAll("\\{value}", values.toString())).addConstraintViolation(); // Re-add error prompt statement
        return false;
    }

}

