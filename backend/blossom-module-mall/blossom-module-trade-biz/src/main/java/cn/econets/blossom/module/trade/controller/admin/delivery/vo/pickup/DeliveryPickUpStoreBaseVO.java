package cn.econets.blossom.module.trade.controller.admin.delivery.vo.pickup;

import cn.econets.blossom.framework.common.enums.CommonStatusEnum;
import cn.econets.blossom.framework.common.validation.InEnum;
import cn.econets.blossom.framework.common.validation.Mobile;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalTime;

/**
* Self-pickup store Base VO，Provide for adding、Modify、Detailed sub VO Use
* If VO Fields with differences，Please do not add here，Influence Swagger Document Generation
*/
@Data
public class DeliveryPickUpStoreBaseVO {

    @Schema(description = "Store Name", requiredMode = Schema.RequiredMode.REQUIRED, example = "Li Si")
    @NotBlank(message = "Store name cannot be empty")
    private String name;

    @Schema(description = "Store Introduction", example = "I am the store profile")
    private String introduction;

    @Schema(description = "Store Mobile Phone", requiredMode = Schema.RequiredMode.REQUIRED, example = "15601892312")
    @NotBlank(message = "The store phone number cannot be empty")
    @Mobile
    private String phone;

    @Schema(description = "Area Number", requiredMode = Schema.RequiredMode.REQUIRED, example = "18733")
    @NotNull(message = "Area number cannot be empty")
    private Integer areaId;

    @Schema(description = "Detailed store address", requiredMode = Schema.RequiredMode.REQUIRED, example = "Fudan University Road 188 Number")
    @NotBlank(message = "The detailed store address cannot be empty")
    private String detailAddress;

    @Schema(description = "Store logo", requiredMode = Schema.RequiredMode.REQUIRED, example = "https://www.econets.cn/1.png")
    @NotBlank(message = "Store logo Cannot be empty")
    private String logo;

    @Schema(description = "Business start time", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "Business start time cannot be empty")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm")
    private LocalTime openingTime;

    @Schema(description = "Business end time", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "Business end time cannot be empty")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm")
    private LocalTime closingTime;

    @Schema(description = "Latitude", requiredMode = Schema.RequiredMode.REQUIRED, example = "5.88")
    @NotNull(message = "Latitude cannot be empty")
    private Double latitude;

    @Schema(description = "Longitude", requiredMode = Schema.RequiredMode.REQUIRED, example = "6.99")
    @NotNull(message = "Longitude cannot be empty")
    private Double longitude;

    @Schema(description = "Store Status", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "Store status cannot be empty")
    @InEnum(CommonStatusEnum.class)
    private Integer status;

}
