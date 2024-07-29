package cn.econets.blossom.framework.desensitize.core.slider.handler;

import cn.econets.blossom.framework.desensitize.core.slider.annotation.BankCardDesensitize;

/**
 * {@link BankCardDesensitize} Desensitization processor
 *
 */
public class BankCardDesensitization extends AbstractSliderDesensitizationHandler<BankCardDesensitize> {

    @Override
    Integer getPrefixKeep(BankCardDesensitize annotation) {
        return annotation.prefixKeep();
    }

    @Override
    Integer getSuffixKeep(BankCardDesensitize annotation) {
        return annotation.suffixKeep();
    }

    @Override
    String getReplacer(BankCardDesensitize annotation) {
        return annotation.replacer();
    }

}
