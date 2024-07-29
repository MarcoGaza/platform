package cn.econets.blossom.module.trade.service.brokerage.bo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * Commission Increase Request BO
 *
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BrokerageAddReqBO {

    /**
     * Business Number
     */
    @NotBlank(message = "Business number cannot be empty")
    private String bizId;
    /**
     * Commission base
     */
    @NotNull(message = "Commission base cannot be empty")
    private Integer basePrice;
    /**
     * First level commission（Fixed）
     */
    @NotNull(message = "First level commission（Fixed）Cannot be empty")
    private Integer firstFixedPrice;
    /**
     * Second level commission（Fixed）
     */
    private Integer secondFixedPrice;

    /**
     * Source user number
     */
    @NotNull(message = "The source user number cannot be empty")
    private Long sourceUserId;

    /**
     * Commission record title
     */
    @NotEmpty(message = "Commission record title cannot be empty")
    private String title;

}
