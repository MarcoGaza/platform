package cn.econets.blossom.module.statistics.service.member.bo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(description = "Management Backend - Members' Regional Statistics Response BO")
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
