package cn.econets.blossom.module.system.controller.app.ip;

import cn.econets.blossom.framework.common.pojo.CommonResult;
import cn.econets.blossom.framework.ip.core.Area;
import cn.econets.blossom.framework.ip.core.utils.AreaUtils;
import cn.econets.blossom.module.system.controller.app.ip.vo.AppAreaNodeRespVO;
import cn.econets.blossom.module.system.convert.ip.AreaConvert;
import cn.hutool.core.lang.Assert;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static cn.econets.blossom.framework.common.pojo.CommonResult.success;


@Tag(name = "User App - Region")
@RestController
@RequestMapping("/system/area")
@Validated
public class AppAreaController {

    @GetMapping("/tree")
    @Operation(summary = "Get the region tree")
    public CommonResult<List<AppAreaNodeRespVO>> getAreaTree() {
        Area area = AreaUtils.getArea(Area.ID_CHINA);
        Assert.notNull(area, "Cannot get China");
        return success(AreaConvert.INSTANCE.convertList3(area.getChildren()));
    }

}
