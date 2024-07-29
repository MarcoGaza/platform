package cn.econets.blossom.module.pay.controller.admin.app;

import cn.hutool.core.collection.CollUtil;
import cn.econets.blossom.framework.common.pojo.CommonResult;
import cn.econets.blossom.framework.common.pojo.PageResult;
import cn.econets.blossom.framework.common.util.collection.CollectionUtils;
import cn.econets.blossom.framework.pay.core.enums.channel.PayChannelEnum;
import cn.econets.blossom.module.pay.controller.admin.app.vo.*;
import cn.econets.blossom.module.pay.convert.app.PayAppConvert;
import cn.econets.blossom.module.pay.dal.dataobject.app.PayAppDO;
import cn.econets.blossom.module.pay.dal.dataobject.channel.PayChannelDO;
import cn.econets.blossom.module.pay.service.app.PayAppService;
import cn.econets.blossom.module.pay.service.channel.PayChannelService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.*;

import static cn.econets.blossom.framework.common.pojo.CommonResult.success;
import static cn.econets.blossom.framework.common.util.collection.CollectionUtils.convertList;

@Slf4j
@Tag(name = "Management Backend - Payment application information")
@RestController
@RequestMapping("/pay/app")
@Validated
public class PayAppController {

    @Resource
    private PayAppService appService;
    @Resource
    private PayChannelService channelService;

    @PostMapping("/create")
    @Operation(summary = "Create payment application information")
    @PreAuthorize("@ss.hasPermission('pay:app:create')")
    public CommonResult<Long> createApp(@Valid @RequestBody PayAppCreateReqVO createReqVO) {
        return success(appService.createApp(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "Update payment application information")
    @PreAuthorize("@ss.hasPermission('pay:app:update')")
    public CommonResult<Boolean> updateApp(@Valid @RequestBody PayAppUpdateReqVO updateReqVO) {
        appService.updateApp(updateReqVO);
        return success(true);
    }

    @PutMapping("/update-status")
    @Operation(summary = "Update payment application status")
    @PreAuthorize("@ss.hasPermission('pay:app:update')")
    public CommonResult<Boolean> updateAppStatus(@Valid @RequestBody PayAppUpdateStatusReqVO updateReqVO) {
        appService.updateAppStatus(updateReqVO.getId(), updateReqVO.getStatus());
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "Delete payment application information")
    @Parameter(name = "id", description = "Number", required = true)
    @PreAuthorize("@ss.hasPermission('pay:app:delete')")
    public CommonResult<Boolean> deleteApp(@RequestParam("id") Long id) {
        appService.deleteApp(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "Get payment application information")
    @Parameter(name = "id", description = "Number", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('pay:app:query')")
    public CommonResult<PayAppRespVO> getApp(@RequestParam("id") Long id) {
        PayAppDO app = appService.getApp(id);
        return success(PayAppConvert.INSTANCE.convert(app));
    }

    @GetMapping("/page")
    @Operation(summary = "Get payment application information page")
    @PreAuthorize("@ss.hasPermission('pay:app:query')")
    public CommonResult<PageResult<PayAppPageItemRespVO>> getAppPage(@Valid PayAppPageReqVO pageVO) {
        // Get the application paging list
        PageResult<PayAppDO> pageResult = appService.getAppPage(pageVO);
        if (CollUtil.isEmpty(pageResult.getList())) {
            return success(PageResult.empty());
        }

        // Get all application numbers，Check all channels
        Collection<Long> appIds = convertList(pageResult.getList(), PayAppDO::getId);
        List<PayChannelDO> channels = channelService.getChannelListByAppIds(appIds);

        // Return after splicing
        return success(PayAppConvert.INSTANCE.convertPage(pageResult, channels));
    }

    @GetMapping("/list")
    @Operation(summary = "Get the application list")
    @PreAuthorize("@ss.hasPermission('pay:merchant:query')")
    public CommonResult<List<PayAppRespVO>> getAppList() {
        List<PayAppDO> appListDO = appService.getAppList();
        return success(PayAppConvert.INSTANCE.convertList(appListDO));
    }

}
