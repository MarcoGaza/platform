package cn.econets.blossom.framework.desensitize.core.slider.handler;

import cn.econets.blossom.framework.desensitize.core.slider.annotation.PasswordDesensitize;

/**
 * {@link PasswordDesensitize} Code desensitization processor
 *
 */
public class PasswordDesensitization extends AbstractSliderDesensitizationHandler<PasswordDesensitize> {
    @Override
    Integer getPrefixKeep(PasswordDesensitize annotation) {
        return annotation.prefixKeep();
    }

    @Override
    Integer getSuffixKeep(PasswordDesensitize annotation) {
        return annotation.suffixKeep();
    }

    @Override
    String getReplacer(PasswordDesensitize annotation) {
        return annotation.replacer();
    }
}
