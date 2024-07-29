package cn.econets.blossom.module.trade.controller.admin.brokerage;

import cn.econets.blossom.framework.common.pojo.CommonResult;
import cn.econets.blossom.framework.common.pojo.PageResult;
import cn.econets.blossom.module.member.api.user.MemberUserApi;
import cn.econets.blossom.module.member.api.user.dto.MemberUserRespDTO;
import cn.econets.blossom.module.trade.controller.admin.brokerage.vo.user.*;
import cn.econets.blossom.module.trade.convert.brokerage.BrokerageUserConvert;
import cn.econets.blossom.module.trade.dal.dataobject.brokerage.BrokerageUserDO;
import cn.econets.blossom.module.trade.enums.brokerage.BrokerageRecordBizTypeEnum;
import cn.econets.blossom.module.trade.enums.brokerage.BrokerageRecordStatusEnum;
import cn.econets.blossom.module.trade.enums.brokerage.BrokerageWithdrawStatusEnum;
import cn.econets.blossom.module.trade.service.brokerage.BrokerageRecordService;
import cn.econets.blossom.module.trade.service.brokerage.BrokerageUserService;
import cn.econets.blossom.module.trade.service.brokerage.BrokerageWithdrawService;
import cn.econets.blossom.module.trade.service.brokerage.bo.UserBrokerageSummaryRespBO;
import cn.econets.blossom.module.trade.service.brokerage.bo.BrokerageWithdrawSummaryRespBO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.Map;
import java.util.Set;

import static cn.econets.blossom.framework.common.pojo.CommonResult.success;
import static cn.econets.blossom.framework.common.util.collection.CollectionUtils.convertMap;
import static cn.econets.blossom.framework.common.util.collection.CollectionUtils.convertSet;

@Tag(name = "Management Backend - Distribution User")
@RestController
@RequestMapping("/trade/brokerage-user")
@Validated
public class BrokerageUserController {

    @Resource
    private BrokerageUserService brokerageUserService;
    @Resource
    private BrokerageRecordService brokerageRecordService;
    @Resource
    private BrokerageWithdrawService brokerageWithdrawService;

    @Resource
    private MemberUserApi memberUserApi;

    @PutMapping("/update-bind-user")
    @Operation(summary = "Modify promoter")
    @PreAuthorize("@ss.hasPermission('trade:brokerage-user:update-bind-user')")
    public CommonResult<Boolean> updateBindUser(@Valid @RequestBody BrokerageUserUpdateBrokerageUserReqVO updateReqVO) {
        brokerageUserService.updateBrokerageUserId(updateReqVO.getId(), updateReqVO.getBindUserId());
        return success(true);
    }

    @PutMapping("/clear-bind-user")
    @Operation(summary = "Clear promoters")
    @PreAuthorize("@ss.hasPermission('trade:brokerage-user:clear-bind-user')")
    public CommonResult<Boolean> clearBindUser(@Valid @RequestBody BrokerageUserClearBrokerageUserReqVO updateReqVO) {
        brokerageUserService.updateBrokerageUserId(updateReqVO.getId(), null);
        return success(true);
    }

    @PutMapping("/update-brokerage-enable")
    @Operation(summary = "Modify promotion qualifications")
    @PreAuthorize("@ss.hasPermission('trade:brokerage-user:update-brokerage-enable')")
    public CommonResult<Boolean> updateBrokerageEnabled(@Valid @RequestBody BrokerageUserUpdateBrokerageEnabledReqVO updateReqVO) {
        brokerageUserService.updateBrokerageUserEnabled(updateReqVO.getId(), updateReqVO.getEnabled());
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "Get distribution users")
    @Parameter(name = "id", description = "Number", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('trade:brokerage-user:query')")
    public CommonResult<BrokerageUserRespVO> getBrokerageUser(@RequestParam("id") Long id) {
        BrokerageUserDO brokerageUser = brokerageUserService.getBrokerageUser(id);
        // TODO Should we make it a unified one? convert？
        BrokerageUserRespVO respVO = BrokerageUserConvert.INSTANCE.convert(brokerageUser);
        return success(BrokerageUserConvert.INSTANCE.copyTo(memberUserApi.getUser(id), respVO));
    }

    @GetMapping("/page")
    @Operation(summary = "Get the distribution user paging")
    @PreAuthorize("@ss.hasPermission('trade:brokerage-user:query')")
    public CommonResult<PageResult<BrokerageUserRespVO>> getBrokerageUserPage(@Valid BrokerageUserPageReqVO pageVO) {
        // Paged query
        PageResult<BrokerageUserDO> pageResult = brokerageUserService.getBrokerageUserPage(pageVO);

        // Query user information
        Set<Long> userIds = convertSet(pageResult.getList(), BrokerageUserDO::getId);
        Map<Long, MemberUserRespDTO> userMap = memberUserApi.getUserMap(userIds);
        // Total promotion orders for commission sharing
        Map<Long, UserBrokerageSummaryRespBO> brokerageOrderSummaryMap = brokerageRecordService.getUserBrokerageSummaryMapByUserId(
                userIds, BrokerageRecordBizTypeEnum.ORDER.getType(), BrokerageRecordStatusEnum.SETTLEMENT.getStatus());
        // Total commission for promotion users
        // TODO Convert to map Batch read
        Map<Long, Long> brokerageUserCountMap = convertMap(userIds,
                userId -> userId,
                userId -> brokerageUserService.getBrokerageUserCountByBindUserId(userId, null));
        // Total commission withdrawal
        // TODO If payment is supported in the future，Maybe status It won't be right；
        Map<Long, BrokerageWithdrawSummaryRespBO> withdrawMap = brokerageWithdrawService.getWithdrawSummaryMapByUserId(
                userIds, BrokerageWithdrawStatusEnum.AUDIT_SUCCESS);
        // Splicing returns
        return success(BrokerageUserConvert.INSTANCE.convertPage(pageResult, userMap, brokerageUserCountMap,
                brokerageOrderSummaryMap, withdrawMap));
    }

}
