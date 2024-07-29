package cn.econets.blossom.module.promotion.controller.admin.combination.vo.recrod;

import cn.econets.blossom.module.promotion.controller.admin.combination.vo.activity.CombinationActivityRespVO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Schema(description = "Management Backend - Pagination items of group purchase records Response VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class CombinationRecordPageItemRespVO extends CombinationRecordBaseVO {

    // ========== Activity related ==========

    private CombinationActivityRespVO activity;

}
