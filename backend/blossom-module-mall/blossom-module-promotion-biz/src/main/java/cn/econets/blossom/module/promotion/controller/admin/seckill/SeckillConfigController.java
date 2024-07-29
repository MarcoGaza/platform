package cn.econets.blossom.module.promotion.controller.admin.seckill;

import cn.econets.blossom.framework.common.enums.CommonStatusEnum;
import cn.econets.blossom.framework.common.pojo.CommonResult;
import cn.econets.blossom.framework.common.pojo.PageResult;
import cn.econets.blossom.module.promotion.controller.admin.seckill.vo.config.*;
import cn.econets.blossom.module.promotion.convert.seckill.seckillconfig.SeckillConfigConvert;
import cn.econets.blossom.module.promotion.dal.dataobject.seckill.SeckillConfigDO;
import cn.econets.blossom.module.promotion.service.seckill.SeckillConfigService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

import static cn.econets.blossom.framework.common.pojo.CommonResult.success;

@Tag(name = "Management Backend - Second sale period")
@RestController
@RequestMapping("/promotion/seckill-config")
@Validated
public class SeckillConfigController {

    @Resource
    private SeckillConfigService seckillConfigService;

    @PostMapping("/create")
    @Operation(summary = "Create a flash sale period")
    @PreAuthorize("@ss.hasPermission('promotion:seckill-config:create')")
    public CommonResult<Long> createSeckillConfig(@Valid @RequestBody SeckillConfigCreateReqVO createReqVO) {
        return success(seckillConfigService.createSeckillConfig(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "Update flash sale period")
    @PreAuthorize("@ss.hasPermission('promotion:seckill-config:update')")
    public CommonResult<Boolean> updateSeckillConfig(@Valid @RequestBody SeckillConfigUpdateReqVO updateReqVO) {
        seckillConfigService.updateSeckillConfig(updateReqVO);
        return success(true);
    }

    @PutMapping("/update-status")
    @Operation(summary = "Modify time period configuration status")
    @PreAuthorize("@ss.hasPermission('system:seckill-config:update')")
    public CommonResult<Boolean> updateSeckillConfigStatus(@Valid @RequestBody SeckillConfigUpdateStatusReqVo reqVO) {
        seckillConfigService.updateSeckillConfigStatus(reqVO.getId(), reqVO.getStatus());
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "Delete flash sale period")
    @Parameter(name = "id", description = "Number", required = true)
    @PreAuthorize("@ss.hasPermission('promotion:seckill-config:delete')")
    public CommonResult<Boolean> deleteSeckillConfig(@RequestParam("id") Long id) {
        seckillConfigService.deleteSeckillConfig(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "Get the flash sale time")
    @Parameter(name = "id", description = "Number", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('promotion:seckill-config:query')")
    public CommonResult<SeckillConfigRespVO> getSeckillConfig(@RequestParam("id") Long id) {
        SeckillConfigDO seckillConfig = seckillConfigService.getSeckillConfig(id);
        return success(SeckillConfigConvert.INSTANCE.convert(seckillConfig));
    }

    @GetMapping("/list")
    @Operation(summary = "Get a list of all flash sale periods")
    @PreAuthorize("@ss.hasPermission('promotion:seckill-config:query')")
    public CommonResult<List<SeckillConfigRespVO>> getSeckillConfigList() {
        List<SeckillConfigDO> list = seckillConfigService.getSeckillConfigList();
        return success(SeckillConfigConvert.INSTANCE.convertList(list));
    }

    @GetMapping("/list-all-simple")
    @Operation(summary = "Get a simplified list of all enabled flash sale periods", description = "Mainly used for front-end drop-down options")
    public CommonResult<List<SeckillConfigSimpleRespVO>> getListAllSimple() {
        List<SeckillConfigDO> list = seckillConfigService.getSeckillConfigListByStatus(
                CommonStatusEnum.ENABLE.getStatus());
        return success(SeckillConfigConvert.INSTANCE.convertList1(list));
    }

    @GetMapping("/page")
    @Operation(summary = "Get the flash sale time period page")
    @PreAuthorize("@ss.hasPermission('promotion:seckill-config:query')")
    public CommonResult<PageResult<SeckillConfigRespVO>> getSeckillActivityPage(@Valid SeckillConfigPageReqVO pageVO) {
        PageResult<SeckillConfigDO> pageResult = seckillConfigService.getSeckillConfigPage(pageVO);
        return success(SeckillConfigConvert.INSTANCE.convertPage(pageResult));
    }

}
