package cn.econets.blossom.module.trade.controller.app.brokerage;

import cn.econets.blossom.framework.common.pojo.CommonResult;
import cn.econets.blossom.framework.common.pojo.PageResult;
import cn.econets.blossom.framework.security.core.annotations.PreAuthenticated;
import cn.econets.blossom.module.trade.controller.app.brokerage.vo.withdraw.AppBrokerageWithdrawCreateReqVO;
import cn.econets.blossom.module.trade.controller.app.brokerage.vo.withdraw.AppBrokerageWithdrawPageReqVO;
import cn.econets.blossom.module.trade.controller.app.brokerage.vo.withdraw.AppBrokerageWithdrawRespVO;
import cn.econets.blossom.module.trade.convert.brokerage.BrokerageWithdrawConvert;
import cn.econets.blossom.module.trade.dal.dataobject.brokerage.BrokerageWithdrawDO;
import cn.econets.blossom.module.trade.service.brokerage.BrokerageWithdrawService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

import static cn.econets.blossom.framework.common.pojo.CommonResult.success;
import static cn.econets.blossom.framework.web.core.util.WebFrameworkUtils.getLoginUserId;

@Tag(name = "User APP - Distribution and withdrawal")
@RestController
@RequestMapping("/trade/brokerage-withdraw")
@Validated
@Slf4j
public class AppBrokerageWithdrawController {

    @Resource
    private BrokerageWithdrawService brokerageWithdrawService;

    @GetMapping("/page")
    @Operation(summary = "Get the distribution withdrawal page")
    @PreAuthenticated
    public CommonResult<PageResult<AppBrokerageWithdrawRespVO>> getBrokerageWithdrawPage(AppBrokerageWithdrawPageReqVO pageReqVO) {
        PageResult<BrokerageWithdrawDO> pageResult = brokerageWithdrawService.getBrokerageWithdrawPage(
                BrokerageWithdrawConvert.INSTANCE.convert(pageReqVO, getLoginUserId()));
        return success(BrokerageWithdrawConvert.INSTANCE.convertPage03(pageResult));
    }

    @PostMapping("/create")
    @Operation(summary = "Create distribution withdrawal")
    @PreAuthenticated
    public CommonResult<Long> createBrokerageWithdraw(@RequestBody @Valid AppBrokerageWithdrawCreateReqVO createReqVO) {
        return success(brokerageWithdrawService.createBrokerageWithdraw(getLoginUserId(), createReqVO));
    }

}
