package cn.econets.blossom.module.pay.enums;

import cn.econets.blossom.framework.common.exception.ErrorCode;

/**
 * Pay Error code Core Enumeration class
 *
 * pay System，Use 1-007-000-000 Section
 */
public interface ErrorCodeConstants {

    // ========== APP Module 1-007-000-000 ==========
    ErrorCode APP_NOT_FOUND = new ErrorCode(1_007_000_000, "App Does not exist");
    ErrorCode APP_IS_DISABLE = new ErrorCode(1_007_000_002, "App has been disabled");
    ErrorCode APP_EXIST_ORDER_CANT_DELETE =  new ErrorCode(1_007_000_003, "There is a payment order in the payment application，Cannot delete");
    ErrorCode APP_EXIST_REFUND_CANT_DELETE =  new ErrorCode(1_007_000_004, "There is a refund order in the payment app，Cannot delete");

    // ========== CHANNEL Module 1-007-001-000 ==========
    ErrorCode CHANNEL_NOT_FOUND = new ErrorCode(1_007_001_000, "The payment channel configuration does not exist");
    ErrorCode CHANNEL_IS_DISABLE = new ErrorCode(1_007_001_001, "The payment channel has been disabled");
    ErrorCode CHANNEL_EXIST_SAME_CHANNEL_ERROR = new ErrorCode(1_007_001_004, "The same channel already exists");

    // ========== ORDER Module 1-007-002-000 ==========
    ErrorCode PAY_ORDER_NOT_FOUND = new ErrorCode(1_007_002_000, "The payment order does not exist");
    ErrorCode PAY_ORDER_STATUS_IS_NOT_WAITING = new ErrorCode(1_007_002_001, "The payment order is not pending payment");
    ErrorCode PAY_ORDER_STATUS_IS_SUCCESS = new ErrorCode(1_007_002_002, "Order has been paid，Please refresh the page");
    ErrorCode PAY_ORDER_IS_EXPIRED = new ErrorCode(1_007_002_003, "The payment order has expired");
    ErrorCode PAY_ORDER_SUBMIT_CHANNEL_ERROR = new ErrorCode(1_007_002_004, "Payment error reported，Error code：{}，Error message：{}");
    ErrorCode PAY_ORDER_REFUND_FAIL_STATUS_ERROR = new ErrorCode(1_007_002_005, "Payment order refund failed，Reason：Status is not paid or refunded");

    // ========== ORDER Module(Expansion order) 1-007-003-000 ==========
    ErrorCode PAY_ORDER_EXTENSION_NOT_FOUND = new ErrorCode(1_007_003_000, "The payment transaction extension form does not exist");
    ErrorCode PAY_ORDER_EXTENSION_STATUS_IS_NOT_WAITING = new ErrorCode(1_007_003_001, "The payment transaction extension order is not pending payment");
    ErrorCode PAY_ORDER_EXTENSION_IS_PAID = new ErrorCode(1_007_003_002, "Order has been paid，Please wait for payment result");

    // ========== Payment Module(Refund) 1-007-006-000 ==========
    ErrorCode REFUND_PRICE_EXCEED = new ErrorCode(1_007_006_000, "The refund amount exceeds the refundable amount of the order");
    ErrorCode REFUND_HAS_REFUNDING = new ErrorCode(1_007_006_002, "A refund is already being processed");
    ErrorCode REFUND_EXISTS = new ErrorCode(1_007_006_003, "A refund order already exists");
    ErrorCode REFUND_NOT_FOUND = new ErrorCode(1_007_006_004, "The payment refund order does not exist");
    ErrorCode REFUND_STATUS_IS_NOT_WAITING = new ErrorCode(1_007_006_005, "Payment refund order is not pending refund");

    // ========== Wallet Module 1-007-007-000 ==========
    ErrorCode WALLET_NOT_FOUND = new ErrorCode(1_007_007_000, "User wallet does not exist");
    ErrorCode WALLET_BALANCE_NOT_ENOUGH = new ErrorCode(1_007_007_001, "Insufficient wallet balance");
    ErrorCode WALLET_TRANSACTION_NOT_FOUND = new ErrorCode(1_007_007_002, "No corresponding wallet transaction found");
    ErrorCode WALLET_REFUND_EXIST = new ErrorCode(1_007_007_003, "Wallet refund already exists");
    ErrorCode WALLET_FREEZE_PRICE_NOT_ENOUGH = new ErrorCode(1_007_007_004, "Insufficient frozen balance in wallet");

    // ========== Wallet recharge module 1-007-008-000 ==========
    ErrorCode WALLET_RECHARGE_NOT_FOUND = new ErrorCode(1_007_008_000, "Wallet recharge record does not exist");
    ErrorCode WALLET_RECHARGE_UPDATE_PAID_STATUS_NOT_UNPAID = new ErrorCode(1_007_008_001, "Wallet recharge update payment status failed，Wallet recharge record is not【Not paid】Status");
    ErrorCode WALLET_RECHARGE_UPDATE_PAID_PAY_ORDER_ID_ERROR = new ErrorCode(1_007_008_002, "Wallet recharge update payment status failed，Payment order number does not match");
    ErrorCode WALLET_RECHARGE_UPDATE_PAID_PAY_ORDER_STATUS_NOT_SUCCESS = new ErrorCode(1_007_008_003, "Wallet recharge update payment status failed，The payment order status is not【Payment successful】Status");
    ErrorCode WALLET_RECHARGE_UPDATE_PAID_PAY_PRICE_NOT_MATCH = new ErrorCode(1_007_008_004, "Wallet recharge update payment status failed，The payment amount does not match");
    ErrorCode WALLET_RECHARGE_REFUND_FAIL_NOT_PAID = new ErrorCode(1_007_008_005, "Wallet initiated refund failed，Wallet recharge order not paid");
    ErrorCode WALLET_RECHARGE_REFUND_FAIL_REFUNDED = new ErrorCode(1_007_008_006, "Wallet initiated refund failed，Wallet recharge order has been refunded");
    ErrorCode WALLET_RECHARGE_REFUND_BALANCE_NOT_ENOUGH = new ErrorCode(1_007_008_007, "Wallet initiated refund failed，Insufficient wallet balance");
    ErrorCode WALLET_RECHARGE_REFUND_FAIL_REFUND_ORDER_ID_ERROR = new ErrorCode(1_007_008_008, "Wallet refund update failed，Wallet refund order number does not match");
    ErrorCode WALLET_RECHARGE_REFUND_FAIL_REFUND_NOT_FOUND = new ErrorCode(1_007_008_009, "Wallet refund update failed，Refund order does not exist");
    ErrorCode WALLET_RECHARGE_REFUND_FAIL_REFUND_PRICE_NOT_MATCH = new ErrorCode(1_007_008_010, "Wallet refund update failed，Refund order amount does not match");
    ErrorCode WALLET_RECHARGE_PACKAGE_NOT_FOUND = new ErrorCode(1_007_008_011, "Wallet recharge package does not exist");
    ErrorCode WALLET_RECHARGE_PACKAGE_IS_DISABLE = new ErrorCode(1_007_008_012, "Wallet recharge package is disabled");
    ErrorCode WALLET_RECHARGE_PACKAGE_NAME_EXISTS = new ErrorCode(1_007_008_013, "The wallet recharge package name already exists");

    // ========== Transfer module 1-007-009-000 ==========
    ErrorCode PAY_TRANSFER_SUBMIT_CHANNEL_ERROR = new ErrorCode(1_007_009_000, "Error initiating transfer，Error code：{}，Error message：{}");
    ErrorCode PAY_TRANSFER_NOT_FOUND = new ErrorCode(1_007_009_001, "The transfer order does not exist");
    ErrorCode PAY_SAME_MERCHANT_TRANSFER_TYPE_NOT_MATCH = new ErrorCode(1_007_009_002, "The types of the two same transfer requests do not match");
    ErrorCode PAY_SAME_MERCHANT_TRANSFER_PRICE_NOT_MATCH = new ErrorCode(1_007_009_003, "The amounts of the two identical transfer requests do not match");
    ErrorCode PAY_MERCHANT_TRANSFER_EXISTS = new ErrorCode(1_007_009_004, "The transfer of this business has been initiated,Please check the status of the transfer order");
    ErrorCode PAY_TRANSFER_STATUS_IS_NOT_WAITING = new ErrorCode(1_007_009_005, "The transfer order is not pending transfer");
    ErrorCode PAY_TRANSFER_STATUS_IS_NOT_PENDING = new ErrorCode(1_007_009_006, "The transfer order is not pending or in the process of transfer");

    // ========== Sample Order 1-007-900-000 ==========
    ErrorCode DEMO_ORDER_NOT_FOUND = new ErrorCode(1_007_900_000, "The sample order does not exist");
    ErrorCode DEMO_ORDER_UPDATE_PAID_STATUS_NOT_UNPAID = new ErrorCode(1_007_900_001, "Failed to update payment status of sample order，The order is not【Not paid】Status");
    ErrorCode DEMO_ORDER_UPDATE_PAID_FAIL_PAY_ORDER_ID_ERROR = new ErrorCode(1_007_900_002, "Failed to update payment status of sample order，Payment order number does not match");
    ErrorCode DEMO_ORDER_UPDATE_PAID_FAIL_PAY_ORDER_STATUS_NOT_SUCCESS = new ErrorCode(1_007_900_003, "Failed to update payment status of sample order，The payment order status is not【Payment successful】Status");
    ErrorCode DEMO_ORDER_UPDATE_PAID_FAIL_PAY_PRICE_NOT_MATCH = new ErrorCode(1_007_900_004, "Failed to update payment status of sample order，The payment amount does not match");
    ErrorCode DEMO_ORDER_REFUND_FAIL_NOT_PAID = new ErrorCode(1_007_900_005, "Failed to initiate refund，Sample order not paid");
    ErrorCode DEMO_ORDER_REFUND_FAIL_REFUNDED = new ErrorCode(1_007_900_006, "Failed to initiate refund，The example order has been refunded");
    ErrorCode DEMO_ORDER_REFUND_FAIL_REFUND_NOT_FOUND = new ErrorCode(1_007_900_007, "Failed to initiate refund，Refund order does not exist");
    ErrorCode DEMO_ORDER_REFUND_FAIL_REFUND_NOT_SUCCESS = new ErrorCode(1_007_900_008, "Failed to initiate refund，Refund order was not successfully refunded");
    ErrorCode DEMO_ORDER_REFUND_FAIL_REFUND_ORDER_ID_ERROR = new ErrorCode(1_007_900_009, "Failed to initiate refund，Refund order number does not match");
    ErrorCode DEMO_ORDER_REFUND_FAIL_REFUND_PRICE_NOT_MATCH = new ErrorCode(1_007_900_010, "Failed to initiate refund，Refund order amount does not match");

    // ========== Sample transfer order 1-007-901-001 ==========
    ErrorCode DEMO_TRANSFER_NOT_FOUND = new ErrorCode(1_007_901_001, "The sample transfer order does not exist");
    ErrorCode DEMO_TRANSFER_FAIL_TRANSFER_ID_ERROR = new ErrorCode(1_007_901_002, "Transfer failed，The transfer order number does not match");
    ErrorCode DEMO_TRANSFER_FAIL_PRICE_NOT_MATCH = new ErrorCode(1_007_901_003, "Transfer failed，The amount of the transfer order does not match");
}
