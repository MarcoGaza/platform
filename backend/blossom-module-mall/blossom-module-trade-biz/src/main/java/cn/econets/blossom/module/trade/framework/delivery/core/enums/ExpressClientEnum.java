package cn.econets.blossom.module.trade.framework.delivery.core.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Express client enumeration
 *
 */
@Getter
@AllArgsConstructor
public enum ExpressClientEnum {

    NOT_PROVIDE("not-provide","Not provided"),
    KD_NIAO("kd-niao", "ExpressBird"),
    KD_100("kd-100", "Express Delivery100");

    /**
     * Express delivery service provider unique code
     */
    private final String code;
    /**
     * Name of the courier service provider
     */
    private final String name;

}
