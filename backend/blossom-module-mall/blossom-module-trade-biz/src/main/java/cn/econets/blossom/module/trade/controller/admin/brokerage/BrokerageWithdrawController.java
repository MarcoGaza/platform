package cn.econets.blossom.module.trade.controller.admin.brokerage;

import cn.econets.blossom.framework.common.pojo.CommonResult;
import cn.econets.blossom.framework.common.pojo.PageResult;
import cn.econets.blossom.module.member.api.user.MemberUserApi;
import cn.econets.blossom.module.member.api.user.dto.MemberUserRespDTO;
import cn.econets.blossom.module.trade.controller.admin.brokerage.vo.withdraw.BrokerageWithdrawRejectReqVO;
import cn.econets.blossom.module.trade.controller.admin.brokerage.vo.withdraw.BrokerageWithdrawPageReqVO;
import cn.econets.blossom.module.trade.controller.admin.brokerage.vo.withdraw.BrokerageWithdrawRespVO;
import cn.econets.blossom.module.trade.convert.brokerage.BrokerageWithdrawConvert;
import cn.econets.blossom.module.trade.dal.dataobject.brokerage.BrokerageWithdrawDO;
import cn.econets.blossom.module.trade.enums.brokerage.BrokerageWithdrawStatusEnum;
import cn.econets.blossom.module.trade.service.brokerage.BrokerageWithdrawService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.Map;

import static cn.econets.blossom.framework.common.pojo.CommonResult.success;
import static cn.econets.blossom.framework.common.util.collection.CollectionUtils.convertSet;

@Tag(name = "Management Backend - Commission withdrawal")
@RestController
@RequestMapping("/trade/brokerage-withdraw")
@Validated
public class BrokerageWithdrawController {

    @Resource
    private BrokerageWithdrawService brokerageWithdrawService;

    @Resource
    private MemberUserApi memberUserApi;

    @PutMapping("/approve")
    @Operation(summary = "Approved")
    @PreAuthorize("@ss.hasPermission('trade:brokerage-withdraw:audit')")
    public CommonResult<Boolean> approveBrokerageWithdraw(@RequestParam("id") Integer id) {
        brokerageWithdrawService.auditBrokerageWithdraw(id, BrokerageWithdrawStatusEnum.AUDIT_SUCCESS, "");
        return success(true);
    }

    @PutMapping("/reject")
    @Operation(summary = "Reject application")
    @PreAuthorize("@ss.hasPermission('trade:brokerage-withdraw:audit')")
    public CommonResult<Boolean> rejectBrokerageWithdraw(@Valid @RequestBody BrokerageWithdrawRejectReqVO reqVO) {
        brokerageWithdrawService.auditBrokerageWithdraw(reqVO.getId(), BrokerageWithdrawStatusEnum.AUDIT_FAIL, reqVO.getAuditReason());
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "Get commission withdrawal")
    @Parameter(name = "id", description = "Number", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('trade:brokerage-withdraw:query')")
    public CommonResult<BrokerageWithdrawRespVO> getBrokerageWithdraw(@RequestParam("id") Integer id) {
        BrokerageWithdrawDO brokerageWithdraw = brokerageWithdrawService.getBrokerageWithdraw(id);
        return success(BrokerageWithdrawConvert.INSTANCE.convert(brokerageWithdraw));
    }

    @GetMapping("/page")
    @Operation(summary = "Get the commission withdrawal page")
    @PreAuthorize("@ss.hasPermission('trade:brokerage-withdraw:query')")
    public CommonResult<PageResult<BrokerageWithdrawRespVO>> getBrokerageWithdrawPage(@Valid BrokerageWithdrawPageReqVO pageVO) {
        // Paged query
        PageResult<BrokerageWithdrawDO> pageResult = brokerageWithdrawService.getBrokerageWithdrawPage(pageVO);

        // Splicing information
        Map<Long, MemberUserRespDTO> userMap = memberUserApi.getUserMap(
                convertSet(pageResult.getList(), BrokerageWithdrawDO::getUserId));
        return success(BrokerageWithdrawConvert.INSTANCE.convertPage(pageResult, userMap));
    }

}
