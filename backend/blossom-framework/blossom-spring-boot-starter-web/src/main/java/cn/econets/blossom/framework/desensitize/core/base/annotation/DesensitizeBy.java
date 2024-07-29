package cn.econets.blossom.framework.desensitize.core.base.annotation;

import cn.econets.blossom.framework.desensitize.core.base.handler.DesensitizationHandler;
import cn.econets.blossom.framework.desensitize.core.base.serializer.StringDesensitizeSerializer;
import com.fasterxml.jackson.annotation.JacksonAnnotationsInside;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.lang.annotation.*;

/**
 * Top level desensitization annotation，Custom annotations need to use this annotation
 *
 */
@Documented
@Target(ElementType.ANNOTATION_TYPE)
@Retention(RetentionPolicy.RUNTIME)
@JacksonAnnotationsInside // This annotation is all others jackson Meta-annotation of annotation，The annotation marked with this annotation indicates that jackson Part of the annotation
@JsonSerialize(using = StringDesensitizeSerializer.class) // Specify serializer
public @interface DesensitizeBy {

    /**
     * Desensitization Processor
     */
    @SuppressWarnings("rawtypes")
    Class<? extends DesensitizationHandler> handler();

}
