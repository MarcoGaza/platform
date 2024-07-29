package cn.econets.blossom.module.system.controller.admin.ip;

import cn.econets.blossom.framework.common.pojo.CommonResult;
import cn.econets.blossom.framework.ip.core.Area;
import cn.econets.blossom.framework.ip.core.utils.AreaUtils;
import cn.econets.blossom.framework.ip.core.utils.IPUtils;
import cn.econets.blossom.module.system.controller.admin.ip.vo.AreaNodeRespVO;
import cn.econets.blossom.module.system.convert.ip.AreaConvert;
import cn.hutool.core.lang.Assert;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static cn.econets.blossom.framework.common.pojo.CommonResult.success;


@Tag(name = "Management Backend - Region")
@RestController
@RequestMapping("/system/area")
@Validated
public class AreaController {

    @GetMapping("/tree")
    @Operation(summary = "Get the region tree")
    public CommonResult<List<AreaNodeRespVO>> getAreaTree() {
        Area area = AreaUtils.getArea(Area.ID_CHINA);
        Assert.notNull(area, "Cannot get China");
        return success(AreaConvert.INSTANCE.convertList(area.getChildren()));
    }

    @GetMapping("/get-by-ip")
    @Operation(summary = "Get IP Corresponding region name")
    @Parameter(name = "ip", description = "IP", required = true)
    public CommonResult<String> getAreaByIp(@RequestParam("ip") String ip) {
        // Get the city
        Area area = IPUtils.getArea(ip);
        if (area == null) {
            return success("Unknown");
        }
        // Format return
        return success(AreaUtils.format(area.getId()));
    }

}
