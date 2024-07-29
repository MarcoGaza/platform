package cn.econets.blossom.module.promotion.controller.app.diy;

import cn.econets.blossom.framework.common.pojo.CommonResult;
import cn.econets.blossom.module.promotion.controller.app.diy.vo.AppDiyTemplatePropertyRespVO;
import cn.econets.blossom.module.promotion.convert.diy.DiyTemplateConvert;
import cn.econets.blossom.module.promotion.dal.dataobject.diy.DiyPageDO;
import cn.econets.blossom.module.promotion.dal.dataobject.diy.DiyTemplateDO;
import cn.econets.blossom.module.promotion.enums.diy.DiyPageEnum;
import cn.econets.blossom.module.promotion.service.diy.DiyPageService;
import cn.econets.blossom.module.promotion.service.diy.DiyTemplateService;
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

import static cn.econets.blossom.framework.common.pojo.CommonResult.success;
import static cn.econets.blossom.framework.common.util.collection.CollectionUtils.findFirst;

@Tag(name = "User APP - Decoration template")
@RestController
@RequestMapping("/promotion/diy-template")
@Validated
public class AppDiyTemplateController {

    @Resource
    private DiyTemplateService diyTemplateService;
    @Resource
    private DiyPageService diyPageService;

    // TODO Do you want to used Japanese get Interface merging；Not passed id，Just take the default；
    @GetMapping("/used")
    @Operation(summary = "Decoration template in use")
    public CommonResult<AppDiyTemplatePropertyRespVO> getUsedDiyTemplate() {
        DiyTemplateDO diyTemplate = diyTemplateService.getUsedDiyTemplate();
        return success(buildVo(diyTemplate));
    }

    @GetMapping("/get")
    @Operation(summary = "Get decoration template")
    @Parameter(name = "id", description = "Number", required = true, example = "1024")
    public CommonResult<AppDiyTemplatePropertyRespVO> getDiyTemplate(@RequestParam("id") Long id) {
        DiyTemplateDO diyTemplate = diyTemplateService.getDiyTemplate(id);
        return success(buildVo(diyTemplate));
    }

    private AppDiyTemplatePropertyRespVO buildVo(DiyTemplateDO diyTemplate) {
        if (diyTemplate == null) {
            return null;
        }
        // Query the pages under the template
        List<DiyPageDO> pages = diyPageService.getDiyPageByTemplateId(diyTemplate.getId());
        String home = findFirst(pages, page -> DiyPageEnum.INDEX.getName().equals(page.getName()), DiyPageDO::getProperty);
        String user = findFirst(pages, page -> DiyPageEnum.MY.getName().equals(page.getName()), DiyPageDO::getProperty);
        // Splicing returns
        return DiyTemplateConvert.INSTANCE.convertPropertyVo2(diyTemplate, home, user);
    }

}
