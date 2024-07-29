package cn.econets.blossom.module.trade.controller.admin.delivery.vo.pickup;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(description = "Management Backend - Self-pickup store simplified information Response VO")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeliveryPickUpStoreSimpleRespVO {

    @Schema(description = "Number", requiredMode = Schema.RequiredMode.REQUIRED, example = "23128")
    private Long id;

    @Schema(description = "Store Name", requiredMode = Schema.RequiredMode.REQUIRED, example = "Li Si")
    private String name;

    @Schema(description = "Store Mobile Phone", requiredMode = Schema.RequiredMode.REQUIRED, example = "15601892312")
    private String phone;

    @Schema(description = "Area Number", requiredMode = Schema.RequiredMode.REQUIRED, example = "18733")
    private Integer areaId;

    @Schema(description = "Region Name", requiredMode = Schema.RequiredMode.REQUIRED, example = "xxCity")
    private String areaName;

    @Schema(description = "Detailed store address", requiredMode = Schema.RequiredMode.REQUIRED, example = "Fudan University Road 188 Number")
    private String detailAddress;

}
