package cn.econets.blossom.module.trade.controller.app.delivery.vo.pickup;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(description = "User App - Self-pickup store Response VO")
@Data
public class AppDeliveryPickUpStoreRespVO {

    @Schema(description = "Number", requiredMode = Schema.RequiredMode.REQUIRED, example = "23128")
    private Long id;

    @Schema(description = "Store Name", requiredMode = Schema.RequiredMode.REQUIRED, example = "Li Si")
    private String name;

    @Schema(description = "Store logo", requiredMode = Schema.RequiredMode.REQUIRED, example = "https://www.econets.cn/1.png")
    private String logo;

    @Schema(description = "Store Mobile Phone", requiredMode = Schema.RequiredMode.REQUIRED, example = "15601892312")
    private String phone;

    @Schema(description = "Region Number", requiredMode = Schema.RequiredMode.REQUIRED, example = "18733")
    private Integer areaId;

    @Schema(description = "Region name", requiredMode = Schema.RequiredMode.REQUIRED, example = "Putuo District, Shanghai")
    private String areaName;

    @Schema(description = "Detailed store address", requiredMode = Schema.RequiredMode.REQUIRED, example = "Fudan University Road 188 Number")
    private String detailAddress;

    @Schema(description = "Latitude", requiredMode = Schema.RequiredMode.REQUIRED, example = "5.88")
    private Double latitude;

    @Schema(description = "Longitude", requiredMode = Schema.RequiredMode.REQUIRED, example = "6.99")
    private Double longitude;

    @Schema(description = "Distance，Unit：kilometers", example = "100") // Only when the user passes the latitude and longitude，Calculation will be performed
    private Double distance;

}
