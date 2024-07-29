package cn.econets.blossom.framework.desensitize.core.base.handler;

import java.lang.annotation.Annotation;

/**
 * Desensitization Processor Interface
 *
 */
public interface DesensitizationHandler<T extends Annotation> {

    /**
     * Desensitization
     *
     * @param origin     Original string
     * @param annotation Annotation information
     * @return Anonymous string
     */
    String desensitize(String origin, T annotation);

}
