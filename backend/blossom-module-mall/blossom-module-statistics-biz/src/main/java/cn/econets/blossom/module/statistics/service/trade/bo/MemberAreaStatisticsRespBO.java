package cn.econets.blossom.module.statistics.service.trade.bo;

import lombok.Data;

/**
 * Members' Regional Statistics Response BO
 *
 */
@Data
public class MemberAreaStatisticsRespBO {

    /**
     * Province number
     */
    private Integer areaId;
    /**
     * Province Name
     */
    private String areaName;

    /**
     * Number of members
     */
    private Integer userCount;

    /**
     * Number of members who placed orders
     */
    private Integer orderCreateUserCount;
    /**
     * Number of members who paid for the order
     */
    private Integer orderPayUserCount;

    /**
     * Order payment amount，Unit：Points
     */
    private Integer orderPayPrice;

}
