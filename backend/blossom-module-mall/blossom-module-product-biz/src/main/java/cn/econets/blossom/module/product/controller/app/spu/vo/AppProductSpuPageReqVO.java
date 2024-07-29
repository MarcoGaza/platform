package cn.econets.blossom.module.product.controller.app.spu.vo;

import cn.hutool.core.util.StrUtil;
import cn.econets.blossom.framework.common.pojo.PageParam;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.validation.constraints.AssertTrue;
import java.util.List;

@Schema(description = "User App - Products SPU Pagination Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class AppProductSpuPageReqVO extends PageParam {

    public static final String SORT_FIELD_PRICE = "price";
    public static final String SORT_FIELD_SALES_COUNT = "salesCount";
    public static final String SORT_FIELD_CREATE_TIME = "createTime";

    @Schema(description = "Products SPU Number array", example = "1,3,5")
    private List<Long> ids;

    @Schema(description = "Classification number", example = "1")
    private Long categoryId;

    @Schema(description = "Classification number array", example = "1,2,3")
    private List<Long> categoryIds;

    @Schema(description = "Keywords", example = "Looks good")
    private String keyword;

    @Schema(description = "Sort Field", example = "price") // See also AppProductSpuPageReqVO.SORT_FIELD_XXX Constant
    private String sortField;

    @Schema(description = "Sorting method", example = "true")
    private Boolean sortAsc;

    @AssertTrue(message = "The sorting field is illegal")
    @JsonIgnore
    public boolean isSortFieldValid() {
        if (StrUtil.isEmpty(sortField)) {
            return true;
        }
        return StrUtil.equalsAny(sortField, SORT_FIELD_PRICE, SORT_FIELD_SALES_COUNT);
    }

}
