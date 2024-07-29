package cn.econets.blossom.framework.common.pojo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Schema(description="Paging parameters")
@Data
public class PageParam implements Serializable {

    private static final Integer PAGE_NO = 1;
    private static final Integer PAGE_SIZE = 10;

    /**
     * Number of entries per page - No paging
     *
     * For example，Export interface，Can be set {@link #pageSize} for -1 No paging，Query all data。
     */
    public static final Integer PAGE_SIZE_NONE = -1;

    @Schema(description = "Page number，From 1 Start", requiredMode = Schema.RequiredMode.REQUIRED,example = "1")
    @NotNull(message = "Page number cannot be empty")
    @Min(value = 1, message = "The minimum page number is 1")
    private Integer pageNo = PAGE_NO;

    @Schema(description = "Number of entries per page，The maximum value is 100", requiredMode = Schema.RequiredMode.REQUIRED, example = "10")
    @NotNull(message = "The number of entries per page cannot be empty")
    @Min(value = 1, message = "The minimum number of entries per page is 1")
    @Max(value = 100, message = "The maximum number of entries per page is 100")
    private Integer pageSize = PAGE_SIZE;

}
