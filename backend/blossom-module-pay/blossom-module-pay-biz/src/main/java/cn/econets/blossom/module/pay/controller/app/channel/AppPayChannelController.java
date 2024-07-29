package cn.econets.blossom.module.pay.controller.app.channel;

import cn.econets.blossom.framework.common.pojo.CommonResult;
import cn.econets.blossom.module.pay.dal.dataobject.channel.PayChannelDO;
import cn.econets.blossom.module.pay.service.channel.PayChannelService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;

import static cn.econets.blossom.framework.common.pojo.CommonResult.success;
import static cn.econets.blossom.framework.common.util.collection.CollectionUtils.convertSet;

@Tag(name = "User App - Payment channel")
@RestController
@RequestMapping("/pay/channel")
@Validated
public class AppPayChannelController {

    @Resource
    private PayChannelService channelService;

    @GetMapping("/get-enable-code-list")
    @Operation(summary = "Get the list of payment channel codes enabled for the specified application")
    @Parameter(name = "appId", description = "Application Number", required = true, example = "1")
    public CommonResult<Set<String>> getEnableChannelCodeList(@RequestParam("appId") Long appId) {
        List<PayChannelDO> channels = channelService.getEnableChannelList(appId);
        return success(convertSet(channels, PayChannelDO::getCode));
    }

}
