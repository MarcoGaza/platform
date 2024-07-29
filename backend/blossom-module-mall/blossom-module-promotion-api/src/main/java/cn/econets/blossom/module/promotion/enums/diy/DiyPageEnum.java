package cn.econets.blossom.module.promotion.enums.diy;

import cn.econets.blossom.framework.common.core.IntArrayValuable;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

/**
 * Decoration page enumeration
 *
 */
@AllArgsConstructor
@Getter
public enum DiyPageEnum implements IntArrayValuable {

    INDEX(1, "Home"),
    MY(2, "My"),
    ;

    private static final int[] ARRAYS = Arrays.stream(values()).mapToInt(DiyPageEnum::getPage).toArray();

    /**
     * Page number
     */
    private final Integer page;

    /**
     * Page Name
     */
    private final String name;

    @Override
    public int[] array() {
        return ARRAYS;
    }

}
