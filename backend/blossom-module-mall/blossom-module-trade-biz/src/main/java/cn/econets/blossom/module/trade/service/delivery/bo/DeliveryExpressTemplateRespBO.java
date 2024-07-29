package cn.econets.blossom.module.trade.service.delivery.bo;

import cn.econets.blossom.module.trade.enums.delivery.DeliveryExpressChargeModeEnum;
import lombok.Data;

/**
 * Freight template configuration Resp BO
 *
 */
@Data
public class DeliveryExpressTemplateRespBO {

    /**
     * Delivery billing method
     *
     * Enumeration {@link DeliveryExpressChargeModeEnum}
     */
    private Integer chargeMode;

    /**
     * Freight template express freight settings
     */
    private Charge charge;

    /**
     * Free shipping template settings
     */
    private Free free;

    /**
     * Express freight template fee configuration BO
     *
     */
    @Data
    public static class Charge {

        /**
         * First Quantity(Number of items,Weight，or volume)
         */
        private Double startCount;
        /**
         * Starting price，Unit：Points
         */
        private Integer startPrice;
        /**
         * Number of follow-up items(Item, Weight，or volume)
         */
        private Double extraCount;
        /**
         * Additional price，Unit：Points
         */
        private Integer extraPrice;
    }

    /**
     * Express delivery template free shipping configuration BO
     *
     */
    @Data
    public static class Free {

        /**
         * Free shipping amount，Unit：Points
         *
         * Total order amount > Free shipping amount，Free shipping
         */
        private Integer freePrice;

        /**
         * Number of mails in the package
         *
         * Total number of orders > Number of mails，Free shipping
         */
        private Integer freeCount;
    }

}
