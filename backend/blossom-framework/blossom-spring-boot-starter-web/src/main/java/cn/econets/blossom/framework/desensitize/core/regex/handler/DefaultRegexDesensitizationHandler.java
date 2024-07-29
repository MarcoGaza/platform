package cn.econets.blossom.framework.desensitize.core.regex.handler;

import cn.econets.blossom.framework.desensitize.core.regex.annotation.RegexDesensitize;

/**
 * {@link RegexDesensitize} Regular desensitization processor
 *
 */
public class DefaultRegexDesensitizationHandler extends AbstractRegexDesensitizationHandler<RegexDesensitize> {

    @Override
    String getRegex(RegexDesensitize annotation) {
        return annotation.regex();
    }

    @Override
    String getReplacer(RegexDesensitize annotation) {
        return annotation.replacer();
    }
}
