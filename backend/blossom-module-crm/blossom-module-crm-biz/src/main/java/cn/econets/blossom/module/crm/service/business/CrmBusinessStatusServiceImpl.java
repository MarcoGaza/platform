package cn.econets.blossom.module.crm.service.business;

import cn.econets.blossom.framework.common.pojo.PageResult;
import cn.econets.blossom.framework.common.util.object.BeanUtils;
import cn.econets.blossom.module.crm.controller.admin.business.vo.status.CrmBusinessStatusPageReqVO;
import cn.econets.blossom.module.crm.controller.admin.business.vo.status.CrmBusinessStatusQueryVO;
import cn.econets.blossom.module.crm.controller.admin.business.vo.status.CrmBusinessStatusSaveReqVO;
import cn.econets.blossom.module.crm.dal.dataobject.business.CrmBusinessStatusDO;
import cn.econets.blossom.module.crm.dal.mysql.business.CrmBusinessStatusMapper;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;

import java.util.Collection;
import java.util.List;

import static cn.econets.blossom.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.econets.blossom.module.crm.enums.ErrorCodeConstants.BUSINESS_STATUS_NOT_EXISTS;

/**
 * Opportunity Status Service Implementation class
 *
 */
@Service
@Validated
public class CrmBusinessStatusServiceImpl implements CrmBusinessStatusService {

    @Resource
    private CrmBusinessStatusMapper businessStatusMapper;

    @Override
    public Long createBusinessStatus(CrmBusinessStatusSaveReqVO createReqVO) {
        // Insert
        CrmBusinessStatusDO businessStatus = BeanUtils.toBean(createReqVO, CrmBusinessStatusDO.class);
        businessStatusMapper.insert(businessStatus);
        // Return
        return businessStatus.getId();
    }

    @Override
    public void updateBusinessStatus(CrmBusinessStatusSaveReqVO updateReqVO) {
        // Check existence
        validateBusinessStatusExists(updateReqVO.getId());
        // Update
        CrmBusinessStatusDO updateObj = BeanUtils.toBean(updateReqVO, CrmBusinessStatusDO.class);
        businessStatusMapper.updateById(updateObj);
    }

    @Override
    public void deleteBusinessStatus(Long id) {
        // Check existence
        validateBusinessStatusExists(id);
        // TODO @ljlleo This can be considered，If there is a business opportunity in use，Deletion is not allowed
        // Delete
        businessStatusMapper.deleteById(id);
    }

    private void validateBusinessStatusExists(Long id) {
        if (businessStatusMapper.selectById(id) == null) {
            throw exception(BUSINESS_STATUS_NOT_EXISTS);
        }
    }

    @Override
    public CrmBusinessStatusDO getBusinessStatus(Long id) {
        return businessStatusMapper.selectById(id);
    }

    @Override
    public PageResult<CrmBusinessStatusDO> getBusinessStatusPage(CrmBusinessStatusPageReqVO pageReqVO) {
        return businessStatusMapper.selectPage(pageReqVO);
    }

    @Override
    public List<CrmBusinessStatusDO> selectList(CrmBusinessStatusQueryVO queryVO) {
        return businessStatusMapper.selectList(queryVO);
    }

    @Override
    public List<CrmBusinessStatusDO> getBusinessStatusList(Collection<Long> ids) {
        return businessStatusMapper.selectBatchIds(ids);
    }

}
