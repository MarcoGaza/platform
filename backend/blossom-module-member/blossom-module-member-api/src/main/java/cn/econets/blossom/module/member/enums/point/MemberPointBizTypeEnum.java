package cn.econets.blossom.module.member.enums.point;

import cn.hutool.core.util.EnumUtil;
import cn.econets.blossom.framework.common.core.IntArrayValuable;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Objects;

/**
 * Business type enumeration of member points
 *
 * 
 */
@AllArgsConstructor
@Getter
public enum MemberPointBizTypeEnum implements IntArrayValuable {

    SIGN(1, "Sign in", "Sign in to get {} Points", true),
    ADMIN(2, "Administrator modification", "Administrator modification {} Points", true),

    ORDER_USE(11, "Order Points Deduction", "Order and use {} Points", false), // When placing an order，Deduct points
    ORDER_USE_CANCEL(12, "Order Points Deduction（Cancel the entire order）", "Order Cancelled，Refund {} Points", true), // ORDER_USE Cancellation
    ORDER_USE_CANCEL_ITEM(13, "Order points deduction（Single Refund）", "Order Refund，Refund {} Points", true), // ORDER_USE Cancellation

    ORDER_GIVE(21, "Order Points Reward", "Order to get {} Points", true), // When paying for an order，Send points
    ORDER_GIVE_CANCEL(22, "Order Points Reward（Cancel the entire order）", "Order Cancelled，Refund {} Points", false), // ORDER_GIVE Cancellation
    ORDER_GIVE_CANCEL_ITEM(23, "Order Points Reward（Single Refund）", "Order Refund，Deduct the gift {} Points", false) // ORDER_GIVE Cancellation
    ;

    /**
     * Type
     */
    private final Integer type;
    /**
     * Name
     */
    private final String name;
    /**
     * Description
     */
    private final String description;
    /**
     * Whether to deduct points
     */
    private final boolean add;

    @Override
    public int[] array() {
        return new int[0];
    }

    public static MemberPointBizTypeEnum getByType(Integer type) {
        return EnumUtil.getBy(MemberPointBizTypeEnum.class,
                e -> Objects.equals(type, e.getType()));
    }

}
