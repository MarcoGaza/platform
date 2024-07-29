package cn.econets.blossom.framework.desensitize.core.regex.handler;

import cn.econets.blossom.framework.desensitize.core.base.handler.DesensitizationHandler;

import java.lang.annotation.Annotation;

/**
 * Regular expression desensitization processor abstract class，Common methods have been implemented
 *
 */
public abstract class AbstractRegexDesensitizationHandler<T extends Annotation>
        implements DesensitizationHandler<T> {

    @Override
    public String desensitize(String origin, T annotation) {
        String regex = getRegex(annotation);
        String replacer = getReplacer(annotation);
        return origin.replaceAll(regex, replacer);
    }

    /**
     * Get the annotation regex Parameters
     *
     * @param annotation Annotation information
     * @return Regular expression
     */
    abstract String getRegex(T annotation);

    /**
     * Get the annotation replacer Parameters
     *
     * @param annotation Annotation information
     * @return The string to be replaced
     */
    abstract String getReplacer(T annotation);

}
