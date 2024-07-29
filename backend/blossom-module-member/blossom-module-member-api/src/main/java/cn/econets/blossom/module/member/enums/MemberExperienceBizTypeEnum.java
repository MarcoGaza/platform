package cn.econets.blossom.module.member.enums;

import cn.hutool.core.util.EnumUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Objects;

/**
 * Membership Experience - Business Type
 *
 */
@Getter
@AllArgsConstructor
public enum MemberExperienceBizTypeEnum {

    /**
     * Administrator Adjustment、Invite new users、Place an order、Refund、Sign in、Lottery
     */
    ADMIN(0, "Administrator Adjustment", "Adjusted by the administrator {} Experience", true),
    INVITE_REGISTER(1, "New Invitation Reward", "Invite friends to get {} Experience", true),
    SIGN_IN(4, "Sign-in Reward", "Sign in to get {} Experience", true),
    LOTTERY(5, "Lottery Rewards", "Win by lottery {} Experience", true),
    ORDER_GIVE(11, "Order Rewards", "Order to get {} Experience", true),
    ORDER_GIVE_CANCEL(12, "Order Rewards（Cancel the entire order）", "Cancel order to obtain {} Experience", false), // ORDER_GIVE Cancellation
    ORDER_GIVE_CANCEL_ITEM(13, "Order Rewards（Single Refund）", "Refund order received {} Experience", false), // ORDER_GIVE Cancellation
    ;

    /**
     * Business Type
     */
    private final int type;
    /**
     * Title
     */
    private final String title;
    /**
     * Description
     */
    private final String description;
    /**
     * Whether to deduct points
     */
    private final boolean add;

    public static MemberExperienceBizTypeEnum getByType(Integer type) {
        return EnumUtil.getBy(MemberExperienceBizTypeEnum.class,
                e -> Objects.equals(type, e.getType()));
    }
}
