package cn.econets.blossom.framework.desensitize.core.slider.handler;

import cn.econets.blossom.framework.desensitize.core.slider.annotation.CarLicenseDesensitize;

/**
 * {@link CarLicenseDesensitize} Desensitization processor
 *
 */
public class CarLicenseDesensitization extends AbstractSliderDesensitizationHandler<CarLicenseDesensitize> {
    @Override
    Integer getPrefixKeep(CarLicenseDesensitize annotation) {
        return annotation.prefixKeep();
    }

    @Override
    Integer getSuffixKeep(CarLicenseDesensitize annotation) {
        return annotation.suffixKeep();
    }

    @Override
    String getReplacer(CarLicenseDesensitize annotation) {
        return annotation.replacer();
    }
}
