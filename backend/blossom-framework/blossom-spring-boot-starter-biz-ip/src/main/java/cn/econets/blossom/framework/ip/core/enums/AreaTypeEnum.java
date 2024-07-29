package cn.econets.blossom.framework.ip.core.enums;

import cn.econets.blossom.framework.common.core.IntArrayValuable;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

/**
 * Region type enumeration
 *
 */
@AllArgsConstructor
@Getter
public enum AreaTypeEnum implements IntArrayValuable {

    COUNTRY(1, "Country"),
    PROVINCE(2, "Province"),
    CITY(3, "City"),
    DISTRICT(4, "Region"), // County、Town、District, etc.
    ;

    public static final int[] ARRAYS = Arrays.stream(values()).mapToInt(AreaTypeEnum::getType).toArray();

    /**
     * Type
     */
    private final Integer type;
    /**
     * Name
     */
    private final String name;

    @Override
    public int[] array() {
        return ARRAYS;
    }
}
