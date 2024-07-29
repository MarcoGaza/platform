package cn.econets.blossom.framework.common.validation;

import cn.econets.blossom.framework.common.core.IntArrayValuable;
import cn.hutool.core.collection.CollUtil;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class InEnumCollectionValidator implements ConstraintValidator<InEnum, Collection<Integer>> {

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
    public boolean isValid(Collection<Integer> list, ConstraintValidatorContext context) {
        // Verification passed
        if (CollUtil.containsAll(values, list)) {
            return true;
        }
        // Verification failed，Custom prompt statement（Because，Annotation value is an enumeration class，Unable to obtain the actual value of the enumeration class）
        context.disableDefaultConstraintViolation(); // Disable default message Value of
        context.buildConstraintViolationWithTemplate(context.getDefaultConstraintMessageTemplate()
                .replaceAll("\\{value}", CollUtil.join(list, ","))).addConstraintViolation(); // Re-add error prompt statement
        return false;
    }

}

