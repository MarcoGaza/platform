package cn.econets.blossom.module.promotion.service.diy;

import cn.hutool.core.util.BooleanUtil;
import cn.hutool.core.util.StrUtil;
import cn.econets.blossom.framework.common.pojo.PageResult;
import cn.econets.blossom.module.promotion.controller.admin.diy.vo.template.DiyTemplateCreateReqVO;
import cn.econets.blossom.module.promotion.controller.admin.diy.vo.template.DiyTemplatePageReqVO;
import cn.econets.blossom.module.promotion.controller.admin.diy.vo.template.DiyTemplatePropertyUpdateRequestVO;
import cn.econets.blossom.module.promotion.controller.admin.diy.vo.template.DiyTemplateUpdateReqVO;
import cn.econets.blossom.module.promotion.convert.diy.DiyPageConvert;
import cn.econets.blossom.module.promotion.convert.diy.DiyTemplateConvert;
import cn.econets.blossom.module.promotion.dal.dataobject.diy.DiyTemplateDO;
import cn.econets.blossom.module.promotion.dal.mysql.diy.DiyTemplateMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.time.LocalDateTime;

import static cn.econets.blossom.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.econets.blossom.module.promotion.enums.ErrorCodeConstants.*;

/**
 * Decoration template Service Implementation class
 *
 */
@Service
@Validated
public class DiyTemplateServiceImpl implements DiyTemplateService {

    @Resource
    private DiyTemplateMapper diyTemplateMapper;

    @Resource
    private DiyPageService diyPageService;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Long createDiyTemplate(DiyTemplateCreateReqVO createReqVO) {
        // Verify name uniqueness
        validateNameUnique(null, createReqVO.getName());
        // Insert
        DiyTemplateDO diyTemplate = DiyTemplateConvert.INSTANCE.convert(createReqVO);
        diyTemplate.setProperty("{}");
        diyTemplateMapper.insert(diyTemplate);
        // Create a default page
        createDefaultPage(diyTemplate);
        // Return
        return diyTemplate.getId();
    }

    /**
     * Create the default page below the template
     * Create two pages by default：Home、My
     *
     * @param diyTemplate Template object
     */
    private void createDefaultPage(DiyTemplateDO diyTemplate) {
        String remark = String.format("Template【%s】Automatically create", diyTemplate.getName());
        diyPageService.createDiyPage(DiyPageConvert.INSTANCE.convertCreateVo(diyTemplate.getId(), "Home", remark));
        diyPageService.createDiyPage(DiyPageConvert.INSTANCE.convertCreateVo(diyTemplate.getId(), "My", remark));
    }

    @Override
    public void updateDiyTemplate(DiyTemplateUpdateReqVO updateReqVO) {
        // Check existence
        validateDiyTemplateExists(updateReqVO.getId());
        // Verify name uniqueness
        validateNameUnique(updateReqVO.getId(), updateReqVO.getName());
        // Update
        DiyTemplateDO updateObj = DiyTemplateConvert.INSTANCE.convert(updateReqVO);
        diyTemplateMapper.updateById(updateObj);
    }

    void validateNameUnique(Long id, String name) {
        if (StrUtil.isBlank(name)) {
            return;
        }
        DiyTemplateDO template = diyTemplateMapper.selectByName(name);
        if (template == null) {
            return;
        }
        // If id Empty，Indicates that there is no need to compare whether they are the same id Template
        if (id == null) {
            throw exception(DIY_TEMPLATE_NAME_USED, name);
        }
        if (!template.getId().equals(id)) {
            throw exception(DIY_TEMPLATE_NAME_USED, name);
        }
    }

    @Override
    public void deleteDiyTemplate(Long id) {
        // Check existence
        DiyTemplateDO diyTemplateDO = validateDiyTemplateExists(id);
        // Verification in use
        if (BooleanUtil.isTrue(diyTemplateDO.getUsed())) {
            throw exception(DIY_TEMPLATE_USED_CANNOT_DELETE);
        }
        // Delete
        diyTemplateMapper.deleteById(id);
    }

    private DiyTemplateDO validateDiyTemplateExists(Long id) {
        DiyTemplateDO diyTemplateDO = diyTemplateMapper.selectById(id);
        if (diyTemplateDO == null) {
            throw exception(DIY_TEMPLATE_NOT_EXISTS);
        }
        return diyTemplateDO;
    }

    @Override
    public DiyTemplateDO getDiyTemplate(Long id) {
        return diyTemplateMapper.selectById(id);
    }

    @Override
    public PageResult<DiyTemplateDO> getDiyTemplatePage(DiyTemplatePageReqVO pageReqVO) {
        return diyTemplateMapper.selectPage(pageReqVO);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void useDiyTemplate(Long id) {
        // Verify existence
        validateDiyTemplateExists(id);
        // TODO If you don't want to use it，Throw a business exception？
        // Used updates are unused
        DiyTemplateDO used = diyTemplateMapper.selectByUsed(true);
        if (used != null) {
            // If id Same，The description has not changed
            if (used.getId().equals(id)) {
                return;
            }
            this.updateTemplateUsed(used.getId(), false, null);
        }
        // Update to used
        this.updateTemplateUsed(id, true, LocalDateTime.now());
    }

    /**
     * Whether to use the updated template
     *
     * @param id       Template number
     * @param used     Whether to use
     * @param usedTime Usage time
     */
    private void updateTemplateUsed(Long id, Boolean used, LocalDateTime usedTime) {
        DiyTemplateDO updateObj = new DiyTemplateDO().setId(id)
                .setUsed(used).setUsedTime(usedTime);
        diyTemplateMapper.updateById(updateObj);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateDiyTemplateProperty(DiyTemplatePropertyUpdateRequestVO updateReqVO) {
        // Check existence
        validateDiyTemplateExists(updateReqVO.getId());
        // Update template properties
        DiyTemplateDO updateObj = DiyTemplateConvert.INSTANCE.convert(updateReqVO);
        diyTemplateMapper.updateById(updateObj);
    }

    @Override
    public DiyTemplateDO getUsedDiyTemplate() {
        return diyTemplateMapper.selectByUsed(true);
    }

}
