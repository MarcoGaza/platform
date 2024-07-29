package cn.econets.blossom.framework.desensitize.core.slider.handler;

import cn.econets.blossom.framework.desensitize.core.slider.annotation.IdCardDesensitize;

/**
 * {@link IdCardDesensitize} Desensitization processor
 *
 */
public class IdCardDesensitization extends AbstractSliderDesensitizationHandler<IdCardDesensitize> {
    @Override
    Integer getPrefixKeep(IdCardDesensitize annotation) {
        return annotation.prefixKeep();
    }

    @Override
    Integer getSuffixKeep(IdCardDesensitize annotation) {
        return annotation.suffixKeep();
    }

    @Override
    String getReplacer(IdCardDesensitize annotation) {
        return annotation.replacer();
    }
}
