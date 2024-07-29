package cn.econets.blossom.module.trade.enums;

import cn.econets.blossom.framework.common.exception.ErrorCode;

/**
 * Trade Error code enumeration class
 * trade System，Use 1-011-000-000 Section
 *
 */
public interface ErrorCodeConstants {

    // ========== Order Module 1-011-000-000 ==========
    ErrorCode ORDER_ITEM_NOT_FOUND = new ErrorCode(1_011_000_010, "The transaction order item does not exist");
    ErrorCode ORDER_NOT_FOUND = new ErrorCode(1_011_000_011, "The transaction order does not exist");
    ErrorCode ORDER_ITEM_UPDATE_AFTER_SALE_STATUS_FAIL = new ErrorCode(1_011_000_012, "Failed to update the after-sales status of the transaction order item，Please try again");
    ErrorCode ORDER_UPDATE_PAID_STATUS_NOT_UNPAID = new ErrorCode(1_011_000_013, "Transaction order payment status update failed，The order is not【Not paid】Status");
    ErrorCode ORDER_UPDATE_PAID_FAIL_PAY_ORDER_ID_ERROR = new ErrorCode(1_011_000_014, "Transaction order payment status update failed，Payment order number does not match");
    ErrorCode ORDER_UPDATE_PAID_FAIL_PAY_ORDER_STATUS_NOT_SUCCESS = new ErrorCode(1_011_000_015, "Transaction order payment status update failed，The payment order status is not【Payment successful】Status");
    ErrorCode ORDER_UPDATE_PAID_FAIL_PAY_PRICE_NOT_MATCH = new ErrorCode(1_011_000_016, "Transaction order payment status update failed，The payment amount does not match");
    ErrorCode ORDER_DELIVERY_FAIL_STATUS_NOT_UNDELIVERED = new ErrorCode(1_011_000_017, "Transaction order delivery failed，The order is not【Waiting for delivery】Status");
    ErrorCode ORDER_RECEIVE_FAIL_STATUS_NOT_DELIVERED = new ErrorCode(1_011_000_018, "Transaction order receipt failed，The order is not【Waiting for delivery】Status");
    ErrorCode ORDER_COMMENT_FAIL_STATUS_NOT_COMPLETED = new ErrorCode(1_011_000_019, "Failed to create review for transaction order item，The order is not【Completed】Status");
    ErrorCode ORDER_COMMENT_STATUS_NOT_FALSE = new ErrorCode(1_011_000_020, "Failed to create review for transaction order item，Order has been evaluated");
    ErrorCode ORDER_DELIVERY_FAIL_REFUND_STATUS_NOT_NONE = new ErrorCode(1_011_000_021, "Transaction order delivery failed，The order has been refunded or partially refunded");
    ErrorCode ORDER_DELIVERY_FAIL_COMBINATION_RECORD_STATUS_NOT_SUCCESS = new ErrorCode(1_011_000_022, "Transaction order delivery failed，Group buying failed");
    ErrorCode ORDER_DELIVERY_FAIL_BARGAIN_RECORD_STATUS_NOT_SUCCESS = new ErrorCode(1_011_000_023, "Transaction order delivery failed，Bargaining failed");
    ErrorCode ORDER_DELIVERY_FAIL_DELIVERY_TYPE_NOT_EXPRESS = new ErrorCode(1_011_000_024, "Transaction order delivery failed，Shipping type is not express delivery");
    ErrorCode ORDER_CANCEL_FAIL_STATUS_NOT_UNPAID = new ErrorCode(1_011_000_025, "Trading order cancellation failed，The order is not【Awaiting payment】Status");
    ErrorCode ORDER_UPDATE_PRICE_FAIL_PAID = new ErrorCode(1_011_000_026, "Payment order price adjustment failed，Reason：Payment order has been paid,Cannot adjust price");
    ErrorCode ORDER_UPDATE_PRICE_FAIL_ALREADY = new ErrorCode(1_011_000_027, "Payment order price adjustment failed，Reason：The price has been modified");
    ErrorCode ORDER_UPDATE_PRICE_FAIL_PRICE_ERROR = new ErrorCode(1_011_000_028, "Payment order price adjustment failed，Reason：The adjusted payment price cannot be less than 0.01 Yuan");
    ErrorCode ORDER_DELETE_FAIL_STATUS_NOT_CANCEL = new ErrorCode(1_011_000_029, "Transaction order deletion failed，The order is not【Cancelled】Status");
    ErrorCode ORDER_RECEIVE_FAIL_DELIVERY_TYPE_NOT_PICK_UP = new ErrorCode(1_011_000_030, "Transaction order self-collection failed，The delivery method is not【User pick up】");
    ErrorCode ORDER_UPDATE_ADDRESS_FAIL_STATUS_NOT_DELIVERED = new ErrorCode(1_011_000_031, "Transaction order failed to modify the delivery address，Reason：The order is not【Waiting for delivery】Status");
    ErrorCode ORDER_CREATE_FAIL_EXIST_UNPAID = new ErrorCode(1_011_000_032, "Trading order creation failed，Reason：There are unpaid orders");

    // ========== After Sale Module 1-011-000-100 ==========
    ErrorCode AFTER_SALE_NOT_FOUND = new ErrorCode(1_011_000_100, "After-sales order does not exist");
    ErrorCode AFTER_SALE_CREATE_FAIL_REFUND_PRICE_ERROR = new ErrorCode(1_011_000_101, "Wrong refund amount applied");
    ErrorCode AFTER_SALE_CREATE_FAIL_ORDER_STATUS_CANCELED = new ErrorCode(1_011_000_102, "Order closed，Unable to apply for after-sales service");
    ErrorCode AFTER_SALE_CREATE_FAIL_ORDER_STATUS_NO_PAID = new ErrorCode(1_011_000_103, "Order not paid，Unable to apply for after-sales service");
    ErrorCode AFTER_SALE_CREATE_FAIL_ORDER_STATUS_NO_DELIVERED = new ErrorCode(1_011_000_104, "Order not shipped，Unable to apply【Return and Refund】After-sales");
    ErrorCode AFTER_SALE_CREATE_FAIL_ORDER_ITEM_APPLIED = new ErrorCode(1_011_000_105, "After-sales service has been applied for the order item，Cannot apply repeatedly");
    ErrorCode AFTER_SALE_AUDIT_FAIL_STATUS_NOT_APPLY = new ErrorCode(1_011_000_106, "Approval failed，After-sales status is not under review");
    ErrorCode AFTER_SALE_UPDATE_STATUS_FAIL = new ErrorCode(1_011_000_107, "After-sales order operation failed，Please refresh and try again");
    ErrorCode AFTER_SALE_DELIVERY_FAIL_STATUS_NOT_SELLER_AGREE = new ErrorCode(1_011_000_108, "Return failed，After-sales order status is not in【Waiting for buyer's return】");
    ErrorCode AFTER_SALE_CONFIRM_FAIL_STATUS_NOT_BUYER_DELIVERY = new ErrorCode(1_011_000_109, "Confirmation of receipt failed，After-sales order status is not in【Waiting for confirmation of receipt】");
    ErrorCode AFTER_SALE_REFUND_FAIL_STATUS_NOT_WAIT_REFUND = new ErrorCode(1_011_000_110, "Refund failed，After-sales order status is not【Waiting for refund】");
    ErrorCode AFTER_SALE_CANCEL_FAIL_STATUS_NOT_APPLY_OR_AGREE_OR_BUYER_DELIVERY =
            new ErrorCode(1_011_000_111, "Failed to cancel the after-sales order，After-sales order status is not【Awaiting review】or【Seller agrees】or【Merchant waiting to receive goods】");

    // ========== Cart Module 1-011-002-000 ==========
    ErrorCode CARD_ITEM_NOT_FOUND = new ErrorCode(1_011_002_000, "The shopping cart item does not exist");

    // ========== Price Related 1-011-003-000 ============
    ErrorCode PRICE_CALCULATE_PAY_PRICE_ILLEGAL = new ErrorCode(1_011_003_000, "Payment price calculation exception，Reason：Price is less than or equal to 0");
    ErrorCode PRICE_CALCULATE_DELIVERY_PRICE_TEMPLATE_NOT_FOUND = new ErrorCode(1_011_003_002, "Anomaly in calculating express delivery charges，Cannot find the corresponding shipping template");
    ErrorCode PRICE_CALCULATE_COUPON_NOT_MATCH_NORMAL_ORDER = new ErrorCode(1_011_003_004, "Participate in flash sales、Group buy、Marketing products for bargaining，Coupon cannot be used");
    ErrorCode PRICE_CALCULATE_SECKILL_TOTAL_LIMIT_COUNT = new ErrorCode(1_011_003_005, "Products participating in flash sales，Exceeded the total purchase limit for flash sales");

    // ========== Logistics Express Module 1-011-004-000 ==========
    ErrorCode EXPRESS_NOT_EXISTS = new ErrorCode(1_011_004_000, "The express company does not exist");
    ErrorCode EXPRESS_CODE_DUPLICATE = new ErrorCode(1_011_004_001, "A courier company with this code already exists");
    ErrorCode EXPRESS_CLIENT_NOT_PROVIDE = new ErrorCode(1_011_004_002, "Need to access courier service provider，For example【Express Delivery100】");
    ErrorCode EXPRESS_STATUS_NOT_ENABLE = new ErrorCode(1_011_004_003, "The express company is not enabled");

    ErrorCode EXPRESS_API_QUERY_ERROR = new ErrorCode(1_011_004_101, "Express query interface exception");
    ErrorCode EXPRESS_API_QUERY_FAILED = new ErrorCode(1_011_004_102, "Express tracking returned failed，Reason：{}");

    // ========== Logistics Template Module 1-011-005-000 ==========
    ErrorCode EXPRESS_TEMPLATE_NAME_DUPLICATE = new ErrorCode(1_011_005_000, "The shipping fee template name already exists");
    ErrorCode EXPRESS_TEMPLATE_NOT_EXISTS = new ErrorCode(1_011_005_001, "Shipping template does not exist");

    // ==========  Logistics PICK_UP Module 1-011-006-000 ==========
    ErrorCode PICK_UP_STORE_NOT_EXISTS = new ErrorCode(1_011_006_000, "The pick-up store does not exist");

    // ========== Distribution User Module 1-011-007-000 ==========
    ErrorCode BROKERAGE_USER_NOT_EXISTS = new ErrorCode(1_011_007_000, "The distribution user does not exist");
    ErrorCode BROKERAGE_USER_FROZEN_PRICE_NOT_ENOUGH = new ErrorCode(1_011_007_001, "User freeze commission({})Insufficient quantity");
    ErrorCode BROKERAGE_BIND_SELF = new ErrorCode(1_011_007_002, "Cannot bind itself");
    ErrorCode BROKERAGE_BIND_USER_NOT_ENABLED = new ErrorCode(1_011_007_003, "Bound users are not eligible for promotion");
    ErrorCode BROKERAGE_BIND_CONDITION_ADMIN = new ErrorCode(1_011_007_004, "Can only bind promoters in the background");
    ErrorCode BROKERAGE_BIND_MODE_REGISTER = new ErrorCode(1_011_007_005, "Can only be bound when registering");
    ErrorCode BROKERAGE_BIND_OVERRIDE = new ErrorCode(1_011_007_006, "Already bound to promoter");
    ErrorCode BROKERAGE_BIND_LOOP = new ErrorCode(1_011_007_007, "Subordinates cannot bind to their superiors");
    ErrorCode BROKERAGE_USER_LEVEL_NOT_SUPPORT = new ErrorCode(1_011_007_008, "Currently only supports level Less than or equal to 2");

    // ========== Distribution and withdrawal Module 1-011-008-000 ==========
    ErrorCode BROKERAGE_WITHDRAW_NOT_EXISTS = new ErrorCode(1_011_008_000, "Commission withdrawal record does not exist");
    ErrorCode BROKERAGE_WITHDRAW_STATUS_NOT_AUDITING = new ErrorCode(1_011_008_001, "Commission withdrawal record status is not under review");
    ErrorCode BROKERAGE_WITHDRAW_MIN_PRICE = new ErrorCode(1_011_008_002, "Withdrawal amount cannot be less than {} Yuan");
    ErrorCode BROKERAGE_WITHDRAW_USER_BALANCE_NOT_ENOUGH = new ErrorCode(1_011_008_003, "You can currently withdraw up to {} Yuan");

}
