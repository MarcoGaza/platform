package cn.econets.blossom.framework.common.validation;

import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.core.util.PhoneUtil;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class TelephoneValidator implements ConstraintValidator<Telephone, String> {

    @Override
    public void initialize(Telephone annotation) {
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        // If the phone number is empty，No verification by default，Verification passed
        if (CharSequenceUtil.isEmpty(value)) {
            return true;
        }
        // Verify mobile phone
        return PhoneUtil.isTel(value) || PhoneUtil.isPhone(value);
    }

}
