package cn.econets.blossom.module.trade.enums.aftersale;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Enumeration of after-sales operation types
 *
 */
@RequiredArgsConstructor
@Getter
public enum AfterSaleOperateTypeEnum {

    MEMBER_CREATE(10, "Member applies for refund"),
    ADMIN_AGREE_APPLY(11, "The merchant agreed to refund"),
    ADMIN_DISAGREE_APPLY(12, "The merchant refused to refund"),
    MEMBER_DELIVERY(20, "Members fill in return logistics information，Express Delivery Company：{deliveryName}，Express delivery number：{logisticsNo}"),
    ADMIN_AGREE_RECEIVE(21, "Merchant receives goods"),
    ADMIN_DISAGREE_RECEIVE(22, "The merchant refused to accept the goods，Reason：{reason}"),
    ADMIN_REFUND(30, "Merchant refund"),
    MEMBER_CANCEL(40, "Member cancellation refund"),
    ;

    /**
     * Operation type
     */
    private final Integer type;
    /**
     * Operation description
     */
    private final String content;

}
