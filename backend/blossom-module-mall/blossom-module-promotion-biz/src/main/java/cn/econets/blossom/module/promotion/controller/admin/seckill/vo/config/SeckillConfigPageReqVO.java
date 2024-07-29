package cn.econets.blossom.module.promotion.controller.admin.seckill.vo.config;

import cn.econets.blossom.framework.common.pojo.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Schema(description = "Management Backend - Second sale period paging Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class SeckillConfigPageReqVO extends PageParam {

    @Schema(description = "Name of flash sale period", example = "Morning session")
    private String name;

    @Schema(description = "Status", example = "0")
    private Integer status;

}
