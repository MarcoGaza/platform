package cn.econets.blossom.module.promotion.service.diy;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.ListUtil;
import cn.hutool.core.util.StrUtil;
import cn.econets.blossom.framework.common.pojo.PageResult;
import cn.econets.blossom.module.promotion.controller.admin.diy.vo.page.DiyPageCreateReqVO;
import cn.econets.blossom.module.promotion.controller.admin.diy.vo.page.DiyPagePageReqVO;
import cn.econets.blossom.module.promotion.controller.admin.diy.vo.page.DiyPagePropertyUpdateRequestVO;
import cn.econets.blossom.module.promotion.controller.admin.diy.vo.page.DiyPageUpdateReqVO;
import cn.econets.blossom.module.promotion.convert.diy.DiyPageConvert;
import cn.econets.blossom.module.promotion.dal.dataobject.diy.DiyPageDO;
import cn.econets.blossom.module.promotion.dal.mysql.diy.DiyPageMapper;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.List;

import static cn.econets.blossom.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.econets.blossom.module.promotion.enums.ErrorCodeConstants.DIY_PAGE_NAME_USED;
import static cn.econets.blossom.module.promotion.enums.ErrorCodeConstants.DIY_PAGE_NOT_EXISTS;

/**
 * Decoration page Service Implementation class
 *
 */
@Service
@Validated
public class DiyPageServiceImpl implements DiyPageService {

    @Resource
    private DiyPageMapper diyPageMapper;

    @Override
    public Long createDiyPage(DiyPageCreateReqVO createReqVO) {
        // Verify name uniqueness
        validateNameUnique(null, createReqVO.getTemplateId(), createReqVO.getName());
        // Insert
        DiyPageDO diyPage = DiyPageConvert.INSTANCE.convert(createReqVO);
        diyPage.setProperty("{}");
        diyPageMapper.insert(diyPage);
        return diyPage.getId();
    }

    @Override
    public void updateDiyPage(DiyPageUpdateReqVO updateReqVO) {
        // Check existence
        validateDiyPageExists(updateReqVO.getId());
        // Verify name uniqueness
        validateNameUnique(updateReqVO.getId(), updateReqVO.getTemplateId(), updateReqVO.getName());
        // Update
        DiyPageDO updateObj = DiyPageConvert.INSTANCE.convert(updateReqVO);
        diyPageMapper.updateById(updateObj);
    }

    /**
     * Verification Page Page，In one template The name under the template is unique
     *
     * @param id Page Number
     * @param templateId Template number
     * @param name Page Name
     */
    void validateNameUnique(Long id, Long templateId, String name) {
        if (templateId != null || StrUtil.isBlank(name)) {
            return;
        }
        DiyPageDO page = diyPageMapper.selectByNameAndTemplateIdIsNull(name);
        if (page == null) {
            return;
        }
        // If id Empty，Indicates that there is no need to compare whether they are the same id Page
        if (id == null) {
            throw exception(DIY_PAGE_NAME_USED, name);
        }
        if (!page.getId().equals(id)) {
            throw exception(DIY_PAGE_NAME_USED, name);
        }
    }

    @Override
    public void deleteDiyPage(Long id) {
        // Verify existence
        validateDiyPageExists(id);
        // Delete
        diyPageMapper.deleteById(id);
    }

    private void validateDiyPageExists(Long id) {
        if (diyPageMapper.selectById(id) == null) {
            throw exception(DIY_PAGE_NOT_EXISTS);
        }
    }

    @Override
    public DiyPageDO getDiyPage(Long id) {
        return diyPageMapper.selectById(id);
    }

    @Override
    public List<DiyPageDO> getDiyPageList(Collection<Long> ids) {
        if (CollUtil.isEmpty(ids)) {
            return ListUtil.empty();
        }
        return diyPageMapper.selectBatchIds(ids);
    }

    @Override
    public PageResult<DiyPageDO> getDiyPagePage(DiyPagePageReqVO pageReqVO) {
        return diyPageMapper.selectPage(pageReqVO);
    }

    @Override
    public List<DiyPageDO> getDiyPageByTemplateId(Long templateId) {
        return diyPageMapper.selectListByTemplateId(templateId);
    }

    @Override
    public void updateDiyPageProperty(DiyPagePropertyUpdateRequestVO updateReqVO) {
        // Check existence
        validateDiyPageExists(updateReqVO.getId());
        // Update
        DiyPageDO updateObj = DiyPageConvert.INSTANCE.convert(updateReqVO);
        diyPageMapper.updateById(updateObj);
    }

}
