package cn.econets.blossom.module.promotion.controller.app.bargain;

import cn.hutool.core.collection.CollUtil;
import cn.econets.blossom.framework.common.pojo.CommonResult;
import cn.econets.blossom.module.member.api.user.MemberUserApi;
import cn.econets.blossom.module.member.api.user.dto.MemberUserRespDTO;
import cn.econets.blossom.module.promotion.controller.app.bargain.vo.help.AppBargainHelpCreateReqVO;
import cn.econets.blossom.module.promotion.controller.app.bargain.vo.help.AppBargainHelpRespVO;
import cn.econets.blossom.module.promotion.convert.bargain.BargainHelpConvert;
import cn.econets.blossom.module.promotion.dal.dataobject.bargain.BargainHelpDO;
import cn.econets.blossom.module.promotion.service.bargain.BargainHelpService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static cn.econets.blossom.framework.common.pojo.CommonResult.success;
import static cn.econets.blossom.framework.common.util.collection.CollectionUtils.convertSet;
import static cn.econets.blossom.framework.security.core.util.SecurityFrameworkUtils.getLoginUserId;

@Tag(name = "User App - Bargaining assistance")
@RestController
@RequestMapping("/promotion/bargain-help")
@Validated
public class AppBargainHelpController {

    @Resource
    private BargainHelpService bargainHelpService;

    @Resource
    private MemberUserApi memberUserApi;

    @PostMapping("/create")
    @Operation(summary = "Create bargaining assistance", description = "Cut the group purchase record") // The result returned is the bargaining amount，Unit：Points
    public CommonResult<Integer> createBargainHelp(@RequestBody AppBargainHelpCreateReqVO reqVO) {
        BargainHelpDO help = bargainHelpService.createBargainHelp(getLoginUserId(), reqVO);
        return success(help.getReducePrice());
    }

    @GetMapping("/list")
    @Operation(summary = "Get the bargaining assistance list")
    @Parameter(name = "recordId", description = "Bargaining record number", required = true, example = "111")
    public CommonResult<List<AppBargainHelpRespVO>> getBargainHelpList(@RequestParam("recordId") Long recordId) {
        List<BargainHelpDO> helps = bargainHelpService.getBargainHelpListByRecordId(recordId);
        if (CollUtil.isEmpty(helps)) {
            return success(Collections.emptyList());
        }
        helps.sort((o1, o2) -> o2.getCreateTime().compareTo(o1.getCreateTime())); // Display in reverse order

        // Splicing data
        Map<Long, MemberUserRespDTO> userMap = memberUserApi.getUserMap(
                convertSet(helps, BargainHelpDO::getUserId));
        return success(BargainHelpConvert.INSTANCE.convertList(helps, userMap));
    }

}
