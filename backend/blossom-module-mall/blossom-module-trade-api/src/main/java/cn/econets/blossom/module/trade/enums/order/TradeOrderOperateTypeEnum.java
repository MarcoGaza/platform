package cn.econets.blossom.module.trade.enums.order;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Enumeration of order operation types
 */
@RequiredArgsConstructor
@Getter
public enum TradeOrderOperateTypeEnum {

    MEMBER_CREATE(1, "User places an order"),
    ADMIN_UPDATE_PRICE(2, "Order Price {oldPayPrice} Modify，The actual payment amount is {newPayPrice} Yuan"),
    MEMBER_PAY(10, "User payment successful"),
    ADMIN_UPDATE_ADDRESS(11, "Modify delivery address"),
    ADMIN_DELIVERY(20, "Shipment，Express Delivery Company：{deliveryName}，Express delivery number：{logisticsNo}"),
    MEMBER_RECEIVE(30, "The user has received the goods"),
    SYSTEM_RECEIVE(31, "Not received due date，The system automatically confirms receipt"),
    ADMIN_PICK_UP_RECEIVE(32, "Administrator picks up the goods by himself"),
    MEMBER_COMMENT(33, "User Reviews"),
    SYSTEM_COMMENT(34, "Expired but not evaluated，System automatic evaluation"),
    MEMBER_CANCEL(40, "Cancel order"),
    SYSTEM_CANCEL(41, "Not paid when due，The system automatically cancels the order"),
    // 42 Reserved：Administrator cancels order
    ADMIN_CANCEL_AFTER_SALE(43, "All orders are after-sales，Administrator automatically cancels order"),
    MEMBER_DELETE(49, "Delete order"),
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
