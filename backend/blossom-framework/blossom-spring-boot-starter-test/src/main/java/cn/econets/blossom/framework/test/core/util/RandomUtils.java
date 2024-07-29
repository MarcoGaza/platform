package cn.econets.blossom.framework.test.core.util;

import cn.hutool.core.date.LocalDateTimeUtil;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import cn.econets.blossom.framework.common.enums.CommonStatusEnum;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Random Tools
 *
 */
public class RandomUtils {

    private static final int RANDOM_STRING_LENGTH = 10;

    private static final int TINYINT_MAX = 127;

    private static final int RANDOM_DATE_MAX = 30;

    private static final int RANDOM_COLLECTION_LENGTH = 5;

    private static final PodamFactory PODAM_FACTORY = new PodamFactoryImpl();

    static {
        // String
        PODAM_FACTORY.getStrategy().addOrReplaceTypeManufacturer(String.class,
                (dataProviderStrategy, attributeMetadata, map) -> randomString());
        // Integer
        PODAM_FACTORY.getStrategy().addOrReplaceTypeManufacturer(Integer.class, (dataProviderStrategy, attributeMetadata, map) -> {
            // If yes status Fields，Return 0 or 1
            if ("status".equals(attributeMetadata.getAttributeName())) {
                return RandomUtil.randomEle(CommonStatusEnum.values()).getStatus();
            }
            // If yes type、status The end field，Return tinyint Range
            if (StrUtil.endWithAnyIgnoreCase(attributeMetadata.getAttributeName(),
                    "type", "status", "category", "scope", "result")) {
                return RandomUtil.randomInt(0, TINYINT_MAX + 1);
            }
            return RandomUtil.randomInt();
        });
        // LocalDateTime
        PODAM_FACTORY.getStrategy().addOrReplaceTypeManufacturer(LocalDateTime.class,
                (dataProviderStrategy, attributeMetadata, map) -> randomLocalDateTime());
        // Boolean
        PODAM_FACTORY.getStrategy().addOrReplaceTypeManufacturer(Boolean.class, (dataProviderStrategy, attributeMetadata, map) -> {
            // If yes deleted Fields，Return non-delete
            if ("deleted".equals(attributeMetadata.getAttributeName())) {
                return false;
            }
            return RandomUtil.randomBoolean();
        });
    }

    public static String randomString() {
        return RandomUtil.randomString(RANDOM_STRING_LENGTH);
    }

    public static Long randomLongId() {
        return RandomUtil.randomLong(0, Long.MAX_VALUE);
    }

    public static Integer randomInteger() {
        return RandomUtil.randomInt(0, Integer.MAX_VALUE);
    }

    public static Date randomDate() {
        return RandomUtil.randomDay(0, RANDOM_DATE_MAX);
    }

    public static LocalDateTime randomLocalDateTime() {
        // Settings Nano Reason for zero，Avoid MySQL、H2 Timestamp cannot be stored
        return LocalDateTimeUtil.of(randomDate()).withNano(0);
    }

    public static Short randomShort() {
        return (short) RandomUtil.randomInt(0, Short.MAX_VALUE);
    }

    public static <T> Set<T> randomSet(Class<T> clazz) {
        return Stream.iterate(0, i -> i).limit(RandomUtil.randomInt(1, RANDOM_COLLECTION_LENGTH))
                .map(i -> randomPojo(clazz)).collect(Collectors.toSet());
    }

    public static Integer randomCommonStatus() {
        return RandomUtil.randomEle(CommonStatusEnum.values()).getStatus();
    }

    public static String randomEmail() {
        return randomString() + "@qq.com";
    }

    public static String randomURL() {
        return "https://www.econets.cn/" + randomString();
    }

    @SafeVarargs
    public static <T> T randomPojo(Class<T> clazz, Consumer<T>... consumers) {
        T pojo = PODAM_FACTORY.manufacturePojo(clazz);
        // When not empty，Callback logic。Through it，Can be achieved Pojo Further processing
        if (ArrayUtil.isNotEmpty(consumers)) {
            Arrays.stream(consumers).forEach(consumer -> consumer.accept(pojo));
        }
        return pojo;
    }

    @SafeVarargs
    public static <T> T randomPojo(Class<T> clazz, Type type, Consumer<T>... consumers) {
        T pojo = PODAM_FACTORY.manufacturePojo(clazz, type);
        // When not empty，Callback logic。Through it，Can be achieved Pojo Further processing
        if (ArrayUtil.isNotEmpty(consumers)) {
            Arrays.stream(consumers).forEach(consumer -> consumer.accept(pojo));
        }
        return pojo;
    }

    @SafeVarargs
    public static <T> List<T> randomPojoList(Class<T> clazz, Consumer<T>... consumers) {
        int size = RandomUtil.randomInt(1, RANDOM_COLLECTION_LENGTH);
        return Stream.iterate(0, i -> i).limit(size).map(o -> randomPojo(clazz, consumers))
                .collect(Collectors.toList());
    }

}
