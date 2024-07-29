package cn.econets.blossom.module.pay.service.wallet.bo;

import cn.econets.blossom.framework.common.validation.InEnum;
import cn.econets.blossom.module.pay.enums.wallet.PayWalletBizTypeEnum;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * Create wallet transaction BO
 *
 *
 */
@Data
public class WalletTransactionCreateReqBO {

    /**
     * Wallet Number
     *
     */
    @NotNull(message = "Wallet number cannot be empty")
    private Long walletId;

    /**
     * Transaction amount，Unit points
     *
     * A positive value indicates an increase in balance，Negative value means the balance decreases
     */
    @NotNull(message = "Transaction amount cannot be empty")
    private Integer price;

    /**
     * Balance after transaction，Unit points
     */
    @NotNull(message = "The balance after transaction cannot be empty")
    private Integer balance;

    /**
     * Related business categories
     *
     * Enumeration {@link PayWalletBizTypeEnum#getType()}
     */
    @NotNull(message = "The associated business category cannot be empty")
    @InEnum(PayWalletBizTypeEnum.class)
    private Integer bizType;

    /**
     * Related business number
     */
    @NotEmpty(message = "The associated business number cannot be empty")
    private String bizId;

    /**
     * Flow Description
     */
    @NotEmpty(message = "The transaction description cannot be empty")
    private String title;
}
