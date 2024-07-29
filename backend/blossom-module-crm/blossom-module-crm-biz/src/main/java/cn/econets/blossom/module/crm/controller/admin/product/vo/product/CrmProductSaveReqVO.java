package cn.econets.blossom.module.crm.controller.admin.product.vo.product;

import cn.econets.blossom.module.crm.framework.operatelog.core.CrmProductStatusParseFunction;
import cn.econets.blossom.module.crm.framework.operatelog.core.CrmProductUnitParseFunction;
import com.mzt.logapi.starter.annotation.DiffLogField;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Schema(description = "Management Backend - CRM Product Creation/Modify Request VO")
@Data
public class CrmProductSaveReqVO {

    @Schema(description = "Product Number", example = "20529")
    private Long id;

    @Schema(description = "Product Name", requiredMode = Schema.RequiredMode.REQUIRED, example = "Good product")
    @NotNull(message = "Product name cannot be empty")
    @DiffLogField(name = "Product Name")
    private String name;

    @Schema(description = "Product Code", requiredMode = Schema.RequiredMode.REQUIRED, example = "12306")
    @NotNull(message = "Product code cannot be empty")
    @DiffLogField(name = "Product Code")
    private String no;

    @Schema(description = "Unit", example = "2")
    @DiffLogField(name = "Unit", function = CrmProductUnitParseFunction.NAME)
    private Integer unit;

    @Schema(description = "Price, Unit：Points", requiredMode = Schema.RequiredMode.REQUIRED, example = "8911")
    @NotNull(message = "Price cannot be empty")
    @DiffLogField(name = "Price")
    private Long price;

    @Schema(description = "Status", requiredMode = Schema.RequiredMode.REQUIRED, example = "On the shelf")
    @NotNull(message = "Status cannot be empty")
    @DiffLogField(name = "Status", function = CrmProductStatusParseFunction.NAME)
    private Integer status;

    @Schema(description = "Product Category Number", requiredMode = Schema.RequiredMode.REQUIRED, example = "2")
    @NotNull(message = "Product category number cannot be empty")
    @DiffLogField(name = "Product Category Number")
    private Long categoryId;

    @Schema(description = "Product Description", example = "You are right")
    @DiffLogField(name = "Product Description")
    private String description;

    @Schema(description = "User ID of the person in charge", requiredMode = Schema.RequiredMode.REQUIRED, example = "31926")
    @NotNull(message = "The user ID of the person in charge cannot be empty")
    private Long ownerUserId;

}
