package cn.econets.blossom.module.crm.controller.admin.bi.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;


@Schema(description = "Management Backend - CRM BI Rankings Response VO")
@Data
public class CrmBiRanKRespVO {

    @Schema(description = "Person in charge number", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private Long ownerUserId;

    @Schema(description = "Name", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private String nickname;

    @Schema(description = "Department Name", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private String deptName;

    /**
     * Quantity is special“Abstract”Concept of，Under different rankings，Represents different meanings
     *
     * 1. Amount：Contract Amount Ranking、Ranking of Refund Amount
     * 2. Number：Contract Signing Ranking、Product Sales Ranking、Product Sales Ranking、Ranking of number of new customers、Newly added contact ranking、Ranking of follow-up times、Ranking of number of follow-up customers
     */
    @Schema(description = "Quantity", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private Integer count;

}
