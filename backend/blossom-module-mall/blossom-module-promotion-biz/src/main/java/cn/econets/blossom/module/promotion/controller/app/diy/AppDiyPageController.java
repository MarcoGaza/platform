package cn.econets.blossom.module.promotion.controller.app.diy;

import cn.econets.blossom.framework.common.pojo.CommonResult;
import cn.econets.blossom.framework.common.util.object.BeanUtils;
import cn.econets.blossom.module.promotion.controller.app.diy.vo.AppDiyPagePropertyRespVO;
import cn.econets.blossom.module.promotion.dal.dataobject.diy.DiyPageDO;
import cn.econets.blossom.module.promotion.service.diy.DiyPageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

import static cn.econets.blossom.framework.common.pojo.CommonResult.success;

@Tag(name = "User APP - Decoration page")
@RestController
@RequestMapping("/promotion/diy-page")
@Validated
public class AppDiyPageController {

    @Resource
    private DiyPageService diyPageService;

    @GetMapping("/get")
    @Operation(summary = "Get the decoration page")
    @Parameter(name = "id", description = "Number", required = true, example = "1024")
    public CommonResult<AppDiyPagePropertyRespVO> getDiyPage(@RequestParam("id") Long id) {
        DiyPageDO diyPage = diyPageService.getDiyPage(id);
        return success(BeanUtils.toBean(diyPage, AppDiyPagePropertyRespVO.class));
    }

}
