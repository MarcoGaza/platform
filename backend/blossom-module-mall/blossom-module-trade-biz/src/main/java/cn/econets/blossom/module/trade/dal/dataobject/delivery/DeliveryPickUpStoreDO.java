package cn.econets.blossom.module.trade.dal.dataobject.delivery;

import cn.econets.blossom.framework.common.enums.CommonStatusEnum;
import cn.econets.blossom.framework.mybatis.core.dataobject.BaseDO;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalTime;

/**
 * Self-pickup store DO
 *
 */
@TableName(value ="trade_delivery_pick_up_store")
@KeySequence("trade_delivery_pick_up_store_seq") // Used for Oracle、PostgreSQL、Kingbase、DB2、H2 The primary key of the database is automatically increased。If yes MySQL Waiting for database，Optional。
@Data
public class DeliveryPickUpStoreDO extends BaseDO {

    /**
     * Number
     */
    @TableId
    private Long id;

    /**
     * Store Name
     */
    private String name;

    /**
     * Store Introduction
     */
    private String introduction;

    /**
     * Store Mobile Phone
     */
    private String phone;

    /**
     * Area Number
     */
    private Integer areaId;

    /**
     * Detailed store address
     */
    private String detailAddress;

    /**
     * Store logo
     */
    private String logo;

    /**
     * Business start time
     */
    private LocalTime openingTime;

    /**
     * Business end time
     */
    private LocalTime closingTime;

    /**
     * Latitude
     */
    private Double latitude;
    /**
     * Longitude
     */
    private Double longitude;

    /**
     * Store Status
     *
     * Enumeration {@link CommonStatusEnum}
     */
    private Integer status;

}
