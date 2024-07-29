package cn.econets.blossom.module.trade.enums.aftersale;

import cn.econets.blossom.framework.common.core.IntArrayValuable;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.Collection;

import static cn.hutool.core.util.ArrayUtil.firstMatch;

/**
 * After-sales status enumeration
 *
 * <a href="https://www.processon.com/view/link/63731a270e3e742ce7b7c194">State Transfer</a>
 *
 */
@AllArgsConstructor
@Getter
public enum AfterSaleStatusEnum implements IntArrayValuable {

    /**
     * 【Apply for after-sales service】
     */
    APPLY(10,"Applying", "Member applies for refund"), // Youzan status reminder：Refund application pending merchant processing
    /**
     * The seller passes after-sales service；【Product awaiting return】
     */
    SELLER_AGREE(20, "The seller passes", "The merchant agreed to refund"), // Youzan status reminder：Please return the goods and fill in the logistics information
    /**
     * The buyer has returned the product，Waiting for the seller to receive the goods；【Merchant waiting to receive goods】
     */
    BUYER_DELIVERY(30,"Waiting for seller to receive goods", "Members fill in the return logistics information"), // Youzan status reminder：Return and refund application is pending processing by the merchant
    /**
     * The seller has received the goods，Waiting for platform refund；Waiting for refund【Waiting for refund】
     */
    WAIT_REFUND(40, "Waiting for platform refund", "Merchant receives goods"), // Youzan status reminder：None（There is a like but no status）
    /**
     * Refund completed【Refund successful】
     */
    COMPLETE(50, "Completed", "Merchant confirms refund"), // Youzan status reminder：Refund successful
    /**
     * 【Buyer canceled】
     */
    BUYER_CANCEL(61, "Buyer cancels after-sales service", "Member cancellation refund"), // Youzan status reminder：Refund closed
    /**
     * The seller refuses to provide after-sales service；Merchant refused【Merchant refused】
     */
    SELLER_DISAGREE(62,"Seller refused", "The merchant refused to refund"), // Youzan status reminder：The merchant does not agree to the refund application
    /**
     * The seller refused to accept the goods，Termination of after-sales service；【Merchant refused to accept goods】
     */
    SELLER_REFUSE(63,"The seller refused to accept the goods", "The merchant refused to accept the goods"), // Youzan status reminder：The merchant refused to accept the goods，Do not agree to refund
    ;

    public static final int[] ARRAYS = Arrays.stream(values()).mapToInt(AfterSaleStatusEnum::getStatus).toArray();

    /**
     * After-sales status in progress
     *
     * Does not include ended states
     */
    public static final Collection<Integer> APPLYING_STATUSES = Arrays.asList(
            APPLY.getStatus(),
            SELLER_AGREE.getStatus(),
            BUYER_DELIVERY.getStatus(),
            WAIT_REFUND.getStatus()
    );

    /**
     * Status
     */
    private final Integer status;
    /**
     * Status name
     */
    private final String name;
    /**
     * Operation content
     *
     * Purpose：Record the contents of the after-sales log
     */
    private final String content;

    @Override
    public int[] array() {
        return ARRAYS;
    }

    public static AfterSaleStatusEnum valueOf(Integer status) {
        return firstMatch(value -> value.getStatus().equals(status), values());
    }

}
