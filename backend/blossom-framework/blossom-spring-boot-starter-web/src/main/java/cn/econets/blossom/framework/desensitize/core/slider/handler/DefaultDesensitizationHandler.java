package cn.econets.blossom.framework.desensitize.core.slider.handler;

import cn.econets.blossom.framework.desensitize.core.slider.annotation.SliderDesensitize;

/**
 * {@link SliderDesensitize} Desensitization processor
 *
 */
public class DefaultDesensitizationHandler extends AbstractSliderDesensitizationHandler<SliderDesensitize> {
    @Override
    Integer getPrefixKeep(SliderDesensitize annotation) {
        return annotation.prefixKeep();
    }

    @Override
    Integer getSuffixKeep(SliderDesensitize annotation) {
        return annotation.suffixKeep();
    }

    @Override
    String getReplacer(SliderDesensitize annotation) {
        return annotation.replacer();
    }
}
