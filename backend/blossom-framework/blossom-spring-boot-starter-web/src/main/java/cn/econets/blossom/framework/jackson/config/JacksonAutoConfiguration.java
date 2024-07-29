package cn.econets.blossom.framework.jackson.config;

import cn.econets.blossom.framework.common.util.json.JsonUtils;
import cn.econets.blossom.framework.jackson.core.LocalDateTimeDeserializer;
import cn.econets.blossom.framework.jackson.core.LocalDateTimeSerializer;
import cn.econets.blossom.framework.jackson.core.NumberSerializer;
import cn.hutool.core.collection.CollUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@AutoConfiguration
@Slf4j
public class JacksonAutoConfiguration {
    @Bean
    @SuppressWarnings("InstantiationOfUtilityClass")
    public JsonUtils jsonUtils(List<ObjectMapper> objectMappers) {
        // 1.1 Create SimpleModule Object
        SimpleModule simpleModule = new SimpleModule();
        simpleModule
                // Newly added Long Type serialization rules，Value exceeds 2^53-1，In JS There will be a loss of precision，Therefore Long Automatically serialize to string type
                .addSerializer(Long.class, NumberSerializer.INSTANCE)
                .addSerializer(Long.TYPE, NumberSerializer.INSTANCE)
                .addSerializer(LocalDate.class, LocalDateSerializer.INSTANCE)
                .addDeserializer(LocalDate.class, LocalDateDeserializer.INSTANCE)
                .addSerializer(LocalTime.class, LocalTimeSerializer.INSTANCE)
                .addDeserializer(LocalTime.class, LocalTimeDeserializer.INSTANCE)
                // Newly added LocalDateTime Serialization、Deserialization rules
                .addSerializer(LocalDateTime.class, LocalDateTimeSerializer.INSTANCE)
                .addDeserializer(LocalDateTime.class, LocalDateTimeDeserializer.INSTANCE);
        // 1.2 Register to objectMapper
        objectMappers.forEach(objectMapper -> objectMapper.registerModule(simpleModule));

        // 2. Settings objectMapper To JsonUtils {
        JsonUtils.init(CollUtil.getFirst(objectMappers));
        log.info("[init][Initialization JsonUtils Success]");
        return new JsonUtils();
    }
}
