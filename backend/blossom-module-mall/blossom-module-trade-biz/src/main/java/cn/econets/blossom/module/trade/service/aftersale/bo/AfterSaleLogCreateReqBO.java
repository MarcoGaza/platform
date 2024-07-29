package cn.econets.blossom.module.trade.service.aftersale.bo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * Creation of after-sales log Request BO
 *
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AfterSaleLogCreateReqBO {

    /**
     * User Number
     */
    @NotNull(message = "User ID cannot be empty")
    private Long userId;
    /**
     * User Type
     */
    @NotNull(message = "User type cannot be empty")
    private Integer userType;

    /**
     * After-sales number
     */
    @NotNull(message = "After-sales number cannot be empty")
    private Long afterSaleId;
    /**
     * Status before operation
     */
    private Integer beforeStatus;
    /**
     * Status after operation
     */
    @NotNull(message = "The status after the operation cannot be empty")
    private Integer afterStatus;

    /**
     * Operation type
     */
    @NotNull(message = "Operation type cannot be empty")
    private Integer operateType;
    /**
     * Operation details
     */
    @NotEmpty(message = "Operation details cannot be empty")
    private String content;
}
