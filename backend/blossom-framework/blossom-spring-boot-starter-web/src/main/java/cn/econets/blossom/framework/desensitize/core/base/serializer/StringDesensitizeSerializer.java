package cn.econets.blossom.framework.desensitize.core.base.serializer;

import cn.hutool.core.annotation.AnnotationUtil;
import cn.hutool.core.lang.Singleton;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.ReflectUtil;
import cn.hutool.core.util.StrUtil;
import cn.econets.blossom.framework.desensitize.core.base.annotation.DesensitizeBy;
import cn.econets.blossom.framework.desensitize.core.base.handler.DesensitizationHandler;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.ContextualSerializer;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import lombok.Getter;
import lombok.Setter;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

/**
 * Anonymous Serializer
 *
 * Realization JSON When returning data，Use {@link DesensitizationHandler} Fields for desensitizing declaration annotations，Perform desensitization。
 *
 */
@SuppressWarnings("rawtypes")
public class StringDesensitizeSerializer extends StdSerializer<String> implements ContextualSerializer {

    @Getter
    @Setter
    private DesensitizationHandler desensitizationHandler;

    protected StringDesensitizeSerializer() {
        super(String.class);
    }

    @Override
    public JsonSerializer<?> createContextual(SerializerProvider serializerProvider, BeanProperty beanProperty) {
        DesensitizeBy annotation = beanProperty.getAnnotation(DesensitizeBy.class);
        if (annotation == null) {
            return this;
        }
        // Create a StringDesensitizeSerializer Object，Use DesensitizeBy Corresponding processor
        StringDesensitizeSerializer serializer = new StringDesensitizeSerializer();
        serializer.setDesensitizationHandler(Singleton.get(annotation.handler()));
        return serializer;
    }

    @Override
    @SuppressWarnings("unchecked")
    public void serialize(String value, JsonGenerator gen, SerializerProvider serializerProvider) throws IOException {
        if (StrUtil.isBlank(value)) {
            gen.writeNull();
            return;
        }
        // Get serialized fields
        Field field = getField(gen);

        // Custom Processor
        DesensitizeBy[] annotations = AnnotationUtil.getCombinationAnnotations(field, DesensitizeBy.class);
        if (ArrayUtil.isEmpty(annotations)) {
            gen.writeString(value);
            return;
        }
        for (Annotation annotation : field.getAnnotations()) {
            if (AnnotationUtil.hasAnnotation(annotation.annotationType(), DesensitizeBy.class)) {
                value = this.desensitizationHandler.desensitize(value, annotation);
                gen.writeString(value);
                return;
            }
        }
        gen.writeString(value);
    }

    /**
     * Get fields
     *
     * @param generator JsonGenerator
     * @return Field
     */
    private Field getField(JsonGenerator generator) {
        String currentName = generator.getOutputContext().getCurrentName();
        Object currentValue = generator.getCurrentValue();
        Class<?> currentValueClass = currentValue.getClass();
        return ReflectUtil.getField(currentValueClass, currentName);
    }

}
