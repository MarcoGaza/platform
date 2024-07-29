package cn.econets.blossom.framework.desensitize.core.slider.handler;

import cn.econets.blossom.framework.desensitize.core.base.handler.DesensitizationHandler;

import java.lang.annotation.Annotation;

/**
 * Sliding desensitization processor abstract class，Common methods have been implemented
 *
 */
public abstract class AbstractSliderDesensitizationHandler<T extends Annotation>
        implements DesensitizationHandler<T> {

    @Override
    public String desensitize(String origin, T annotation) {
        int prefixKeep = getPrefixKeep(annotation);
        int suffixKeep = getSuffixKeep(annotation);
        String replacer = getReplacer(annotation);
        int length = origin.length();

        // Situation 1：The original string length is less than or equal to the reserved length，The original string is completely replaced
        if (prefixKeep >= length || suffixKeep >= length) {
            return buildReplacerByLength(replacer, length);
        }

        // Case 2：The original string length is less than or equal to the length of the prefix and suffix retained string，The original string is completely replaced
        if ((prefixKeep + suffixKeep) >= length) {
            return buildReplacerByLength(replacer, length);
        }

        // Situation 3：The original string length is greater than the prefix and suffix reserved string length，Replace the middle string
        int interval = length - prefixKeep - suffixKeep;
        return origin.substring(0, prefixKeep) +
                buildReplacerByLength(replacer, interval) +
                origin.substring(prefixKeep + interval);
    }

    /**
     * Build replacement symbols based on length loop
     *
     * @param replacer Replacement symbol
     * @param length   Length
     * @return Replacement symbol after construction
     */
    private String buildReplacerByLength(String replacer, int length) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < length; i++) {
            builder.append(replacer);
        }
        return builder.toString();
    }

    /**
     * Prefix reserved length
     *
     * @param annotation Annotation information
     * @return Prefix reserved length
     */
    abstract Integer getPrefixKeep(T annotation);

    /**
     * Suffix retention length
     *
     * @param annotation Annotation information
     * @return Suffix retention length
     */
    abstract Integer getSuffixKeep(T annotation);

    /**
     * Replacement symbol
     *
     * @param annotation Annotation information
     * @return Replacement symbol
     */
    abstract String getReplacer(T annotation);

}
