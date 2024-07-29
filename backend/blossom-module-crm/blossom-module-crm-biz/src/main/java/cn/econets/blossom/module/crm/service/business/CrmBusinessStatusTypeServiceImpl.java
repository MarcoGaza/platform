package cn.econets.blossom.module.crm.service.business;

import cn.hutool.core.collection.CollUtil;
import cn.econets.blossom.framework.common.pojo.PageResult;
import cn.econets.blossom.framework.common.util.object.BeanUtils;
import cn.econets.blossom.module.crm.controller.admin.business.vo.type.CrmBusinessStatusTypePageReqVO;
import cn.econets.blossom.module.crm.controller.admin.business.vo.type.CrmBusinessStatusTypeQueryVO;
import cn.econets.blossom.module.crm.controller.admin.business.vo.type.CrmBusinessStatusTypeSaveReqVO;
import cn.econets.blossom.module.crm.dal.dataobject.business.CrmBusinessStatusDO;
import cn.econets.blossom.module.crm.dal.dataobject.business.CrmBusinessStatusTypeDO;
import cn.econets.blossom.module.crm.dal.mysql.business.CrmBusinessStatusMapper;
import cn.econets.blossom.module.crm.dal.mysql.business.CrmBusinessStatusTypeMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.List;

import static cn.econets.blossom.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.econets.blossom.module.crm.enums.ErrorCodeConstants.BUSINESS_STATUS_TYPE_NAME_EXISTS;
import static cn.econets.blossom.module.crm.enums.ErrorCodeConstants.BUSINESS_STATUS_TYPE_NOT_EXISTS;

/**
 * Opportunity status type Service Implementation class
 *
 */
@Service
@Validated
public class CrmBusinessStatusTypeServiceImpl implements CrmBusinessStatusTypeService {

    @Resource
    private CrmBusinessStatusTypeMapper businessStatusTypeMapper;

    @Resource
    private CrmBusinessStatusMapper businessStatusMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long createBusinessStatusType(CrmBusinessStatusTypeSaveReqVO createReqVO) {
        //Check if the name exists
        validateBusinessStatusTypeExists(createReqVO.getName(), null);
        // Insert type
        CrmBusinessStatusTypeDO businessStatusType = BeanUtils.toBean(createReqVO, CrmBusinessStatusTypeDO.class);
        businessStatusTypeMapper.insert(businessStatusType);
        // Insert status
        if (CollUtil.isNotEmpty(createReqVO.getStatusList())) {
            createReqVO.getStatusList().forEach(status -> status.setTypeId(businessStatusType.getId()));
            businessStatusMapper.insertBatch(BeanUtils.toBean(createReqVO.getStatusList(), CrmBusinessStatusDO.class));
        }
        return businessStatusType.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateBusinessStatusType(CrmBusinessStatusTypeSaveReqVO updateReqVO) {
        // Check existence
        validateBusinessStatusTypeExists(updateReqVO.getId());
        // Check if the name exists
        validateBusinessStatusTypeExists(updateReqVO.getName(), updateReqVO.getId());
        // Update type
        CrmBusinessStatusTypeDO updateObj = BeanUtils.toBean(updateReqVO, CrmBusinessStatusTypeDO.class);
        businessStatusTypeMapper.updateById(updateObj);
        // Update status（Delete + Update）
        // TODO @ljlleo For reference DeliveryExpressTemplateServiceImpl of updateExpressTemplateFree Method；Mainly no changes，I still won't delete it。
        businessStatusMapper.delete(updateReqVO.getId());
        updateReqVO.getStatusList().forEach(status -> status.setTypeId(updateReqVO.getId()));
        businessStatusMapper.insertBatch(BeanUtils.toBean(updateReqVO.getStatusList(), CrmBusinessStatusDO.class));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteBusinessStatusType(Long id) {
        // TODO To be added referenced verification
        //...

        // Check existence
        validateBusinessStatusTypeExists(id);
        // Delete type
        businessStatusTypeMapper.deleteById(id);
        // Delete status
        businessStatusMapper.delete(id);
    }

    private void validateBusinessStatusTypeExists(Long id) {
        if (businessStatusTypeMapper.selectById(id) == null) {
            throw exception(BUSINESS_STATUS_TYPE_NOT_EXISTS);
        }
    }

    // TODO @ljlleo This method，This reference validateDeptNameUnique Realization。
    private void validateBusinessStatusTypeExists(String name, Long id) {
        CrmBusinessStatusTypeDO businessStatusTypeDO = businessStatusTypeMapper.selectByIdAndName(id, name);
        if (businessStatusTypeDO != null) {
            throw exception(BUSINESS_STATUS_TYPE_NAME_EXISTS);
        }
//        LambdaQueryWrapper<CrmBusinessStatusTypeDO> wrapper = new LambdaQueryWrapperX<>();
//        if(null != id) {
//            wrapper.ne(CrmBusinessStatusTypeDO::getId, id);
//        }
//        long cnt = businessStatusTypeMapper.selectCount(wrapper.eq(CrmBusinessStatusTypeDO::getName, name));
//        if (cnt > 0) {
//            throw exception(BUSINESS_STATUS_TYPE_NAME_EXISTS);
//        }
    }

    @Override
    public CrmBusinessStatusTypeDO getBusinessStatusType(Long id) {
        return businessStatusTypeMapper.selectById(id);
    }

    @Override
    public PageResult<CrmBusinessStatusTypeDO> getBusinessStatusTypePage(CrmBusinessStatusTypePageReqVO pageReqVO) {
        return businessStatusTypeMapper.selectPage(pageReqVO);
    }

    @Override
    public List<CrmBusinessStatusTypeDO> selectList(CrmBusinessStatusTypeQueryVO queryVO) {
        return businessStatusTypeMapper.selectList(queryVO);
    }

    @Override
    public List<CrmBusinessStatusTypeDO> getBusinessStatusTypeList(Collection<Long> ids) {
        return businessStatusTypeMapper.selectBatchIds(ids);
    }

}
