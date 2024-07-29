package cn.econets.blossom.framework.jackson.core;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.JacksonStdImpl;

import java.io.IOException;

/**
 * Long Serialization rules
 *
 * Will be too long long Value converted to string，Solve the front end JavaScript The maximum safe integer is 2^53-1 Problem
 *
 */
@JacksonStdImpl
public class NumberSerializer extends com.fasterxml.jackson.databind.ser.std.NumberSerializer {

    private static final long MAX_SAFE_INTEGER = 9007199254740991L;
    private static final long MIN_SAFE_INTEGER = -9007199254740991L;

    public static final NumberSerializer INSTANCE = new NumberSerializer(Number.class);

    public NumberSerializer(Class<? extends Number> rawType) {
        super(rawType);
    }

    @Override
    public void serialize(Number value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        // Out of range Serialized bit string
        if (value.longValue() > MIN_SAFE_INTEGER && value.longValue() < MAX_SAFE_INTEGER) {
            super.serialize(value, gen, serializers);
        } else {
            gen.writeString(value.toString());
        }
    }
}
