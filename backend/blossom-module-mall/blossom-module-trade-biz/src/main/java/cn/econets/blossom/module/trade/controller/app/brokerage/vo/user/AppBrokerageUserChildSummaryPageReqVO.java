package cn.econets.blossom.module.trade.controller.app.brokerage.vo.user;

import cn.econets.blossom.framework.common.pojo.PageParam;
import cn.econets.blossom.framework.common.pojo.SortingField;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;

@Schema(description = "User App - Subordinate distribution statistics paging Request VO")
@Data
public class AppBrokerageUserChildSummaryPageReqVO extends PageParam {

    public static final String SORT_FIELD_USER_COUNT = "userCount";
    public static final String SORT_FIELD_ORDER_COUNT = "orderCount";
    public static final String SORT_FIELD_PRICE = "price";

    @Schema(description = "User Nickname", example = "Li") // Fuzzy matching
    private String nickname;

    @Schema(description = "Sort Field", example = "userCount")
    private SortingField sortingField;

    @Schema(description = "Subordinate's level", requiredMode = Schema.RequiredMode.REQUIRED, example = "1") // 1 - Direct subordinates；2 - Indirect subordinates
    @NotNull(message = "The level of the subordinate cannot be empty")
    @Range(min = 1, max = 2, message = "The subordinate's level can only be {min} Or {max}")
    private Integer level;

}
