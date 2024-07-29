package cn.econets.blossom.module.product.enums.comment;

import cn.econets.blossom.framework.common.core.IntArrayValuable;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

/**
 * Star enumeration of product reviews
 *
 */
@Getter
@AllArgsConstructor
public enum ProductCommentScoresEnum implements IntArrayValuable {

    ONE(1, "1Star"),
    TWO(2, "2Star"),
    THREE(3, "3Star"),
    FOUR(4, "4Star"),
    FIVE(5, "5Star");

    public static final int[] ARRAYS = Arrays.stream(values()).mapToInt(ProductCommentScoresEnum::getScores).toArray();

    /**
     * Stars
     */
    private final Integer scores;

    /**
     * Star Name
     */
    private final String name;

    @Override
    public int[] array() {
        return ARRAYS;
    }

}
