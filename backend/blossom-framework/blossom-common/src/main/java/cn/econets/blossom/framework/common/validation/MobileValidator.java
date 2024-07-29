package cn.econets.blossom.framework.common.validation;

import cn.econets.blossom.framework.common.util.validation.ValidationUtils;
import cn.hutool.core.util.StrUtil;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class MobileValidator implements ConstraintValidator<Mobile, String> {

    @Override
    public void initialize(Mobile annotation) {
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        // If the phone number is empty，No verification by default，Verification passed
        if (StrUtil.isEmpty(value)) {
            return true;
        }
        // Verify mobile phone
        return ValidationUtils.isMobile(value);
    }

}
