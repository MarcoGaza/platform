package cn.econets.blossom.framework.jackson.core;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

/**
 * LocalDateTimeDeserialization rules
 * <p>
 * Will deserialize millisecond timestamps intoLocalDateTime
 */
public class LocalDateTimeDeserializer extends JsonDeserializer<LocalDateTime> {

    public static final LocalDateTimeDeserializer INSTANCE = new LocalDateTimeDeserializer();

    @Override
    public LocalDateTime deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        return LocalDateTime.ofInstant(Instant.ofEpochMilli(p.getValueAsLong()), ZoneId.systemDefault());
    }
}
