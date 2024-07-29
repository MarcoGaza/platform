package cn.econets.blossom.module.trade.service.delivery;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.ObjectUtil;
import cn.econets.blossom.framework.common.pojo.PageResult;
import cn.econets.blossom.framework.common.util.collection.CollectionUtils;
import cn.econets.blossom.module.trade.controller.admin.delivery.vo.expresstemplate.*;
import cn.econets.blossom.module.trade.dal.dataobject.delivery.DeliveryExpressTemplateChargeDO;
import cn.econets.blossom.module.trade.dal.dataobject.delivery.DeliveryExpressTemplateDO;
import cn.econets.blossom.module.trade.dal.dataobject.delivery.DeliveryExpressTemplateFreeDO;
import cn.econets.blossom.module.trade.dal.mysql.delivery.DeliveryExpressTemplateChargeMapper;
import cn.econets.blossom.module.trade.dal.mysql.delivery.DeliveryExpressTemplateFreeMapper;
import cn.econets.blossom.module.trade.dal.mysql.delivery.DeliveryExpressTemplateMapper;
import cn.econets.blossom.module.trade.service.delivery.bo.DeliveryExpressTemplateRespBO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.util.*;

import static cn.econets.blossom.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.econets.blossom.framework.common.util.collection.CollectionUtils.*;
import static cn.econets.blossom.module.trade.convert.delivery.DeliveryExpressTemplateConvert.INSTANCE;
import static cn.econets.blossom.module.trade.enums.ErrorCodeConstants.EXPRESS_TEMPLATE_NAME_DUPLICATE;
import static cn.econets.blossom.module.trade.enums.ErrorCodeConstants.EXPRESS_TEMPLATE_NOT_EXISTS;

/**
 * Express delivery fee template Service Implementation class
 *
 */
@Service
@Validated
public class DeliveryExpressTemplateServiceImpl implements DeliveryExpressTemplateService {

    @Resource
    private DeliveryExpressTemplateMapper expressTemplateMapper;
    @Resource
    private DeliveryExpressTemplateChargeMapper expressTemplateChargeMapper;
    @Resource
    private DeliveryExpressTemplateFreeMapper expressTemplateFreeMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long createDeliveryExpressTemplate(DeliveryExpressTemplateCreateReqVO createReqVO) {
        // Check if the template name is unique
        validateTemplateNameUnique(createReqVO.getName(), null);

        // Insert
        DeliveryExpressTemplateDO template = INSTANCE.convert(createReqVO);
        expressTemplateMapper.insert(template);
        // Insert freight template billing table
        if (CollUtil.isNotEmpty(createReqVO.getCharges())) {
            expressTemplateChargeMapper.insertBatch(
                    INSTANCE.convertTemplateChargeList(template.getId(), createReqVO.getChargeMode(), createReqVO.getCharges())
            );
        }
        // Insert shipping template free shipping list
        if (CollUtil.isNotEmpty(createReqVO.getFrees())) {
            expressTemplateFreeMapper.insertBatch(
                    INSTANCE.convertTemplateFreeList(template.getId(), createReqVO.getFrees())
            );
        }
        return template.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateDeliveryExpressTemplate(DeliveryExpressTemplateUpdateReqVO updateReqVO) {
        // Check existence
        validateDeliveryExpressTemplateExists(updateReqVO.getId());
        // Check whether the template name is unique
        validateTemplateNameUnique(updateReqVO.getName(), updateReqVO.getId());

        // Update shipping costs from table
        updateExpressTemplateCharge(updateReqVO.getId(), updateReqVO.getChargeMode(), updateReqVO.getCharges());
        // Update the free shipping table
        updateExpressTemplateFree(updateReqVO.getId(), updateReqVO.getFrees());
        // Update template main table
        DeliveryExpressTemplateDO updateObj = INSTANCE.convert(updateReqVO);
        expressTemplateMapper.updateById(updateObj);
    }

    private void updateExpressTemplateFree(Long templateId, List<DeliveryExpressTemplateFreeBaseVO> frees) {
        // First step，Compare new and old data，Get added、Modify、Deleted list
        List<DeliveryExpressTemplateFreeDO> oldList = expressTemplateFreeMapper.selectListByTemplateId(templateId);
        List<DeliveryExpressTemplateFreeDO> newList = INSTANCE.convertTemplateFreeList(templateId, frees);
        List<List<DeliveryExpressTemplateFreeDO>> diffList = CollectionUtils.diffList(oldList, newList,
                (oldVal, newVal) -> ObjectUtil.equal(oldVal.getId(), newVal.getTemplateId()));

        // Step 2，Batch add、Modify、Delete
        if (CollUtil.isNotEmpty(diffList.get(0))) {
            expressTemplateFreeMapper.insertBatch(diffList.get(0));
        }
        if (CollUtil.isNotEmpty(diffList.get(1))) {
            expressTemplateFreeMapper.updateBatch(diffList.get(1));
        }
        if (CollUtil.isNotEmpty(diffList.get(2))) {
            expressTemplateFreeMapper.deleteBatchIds(convertList(diffList.get(2), DeliveryExpressTemplateFreeDO::getId));
        }
    }

    private void updateExpressTemplateCharge(Long templateId, Integer chargeMode, List<DeliveryExpressTemplateChargeBaseVO> charges) {
        // First step，Compare new and old data，Get added、Modify、Deleted list
        List<DeliveryExpressTemplateChargeDO> oldList = expressTemplateChargeMapper.selectListByTemplateId(templateId);
        List<DeliveryExpressTemplateChargeDO> newList = INSTANCE.convertTemplateChargeList(templateId, chargeMode, charges);
        List<List<DeliveryExpressTemplateChargeDO>> diffList = diffList(oldList, newList, (oldVal, newVal) -> {
            boolean same = ObjectUtil.equal(oldVal.getId(), newVal.getId());
            if (same) {
                newVal.setChargeMode(chargeMode); // Update the charging model
            }
            return same;
        });

        // Step 2，Batch add、Modify、Delete
        if (CollUtil.isNotEmpty(diffList.get(0))) {
            expressTemplateChargeMapper.insertBatch(diffList.get(0));
        }
        if (CollUtil.isNotEmpty(diffList.get(1))) {
            expressTemplateChargeMapper.updateBatch(diffList.get(1));
        }
        if (CollUtil.isNotEmpty(diffList.get(2))) {
            expressTemplateChargeMapper.deleteBatchIds(convertList(diffList.get(2), DeliveryExpressTemplateChargeDO::getId));
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteDeliveryExpressTemplate(Long id) {
        // Check existence
        validateDeliveryExpressTemplateExists(id);

        // Delete the main table
        expressTemplateMapper.deleteById(id);
        // Delete shipping costs from the table
        expressTemplateChargeMapper.deleteByTemplateId(id);
        // Delete free shipping from the table
        expressTemplateFreeMapper.deleteByTemplateId(id);
    }

    /**
     * Check whether the freight template name is unique
     *
     * @param name Template name
     * @param id   Freight template number,Can be null
     */
    private void validateTemplateNameUnique(String name, Long id) {
        DeliveryExpressTemplateDO template = expressTemplateMapper.selectByName(name);
        if (template == null) {
            return;
        }
        // If id Empty
        if (id == null) {
            throw exception(EXPRESS_TEMPLATE_NAME_DUPLICATE);
        }
        if (!template.getId().equals(id)) {
            throw exception(EXPRESS_TEMPLATE_NAME_DUPLICATE);
        }
    }

    private void validateDeliveryExpressTemplateExists(Long id) {
        if (expressTemplateMapper.selectById(id) == null) {
            throw exception(EXPRESS_TEMPLATE_NOT_EXISTS);
        }
    }

    @Override
    public DeliveryExpressTemplateDetailRespVO getDeliveryExpressTemplate(Long id) {
        List<DeliveryExpressTemplateChargeDO> chargeList = expressTemplateChargeMapper.selectListByTemplateId(id);
        List<DeliveryExpressTemplateFreeDO> freeList = expressTemplateFreeMapper.selectListByTemplateId(id);
        DeliveryExpressTemplateDO template = expressTemplateMapper.selectById(id);
        return INSTANCE.convert(template, chargeList, freeList);
    }

    @Override
    public List<DeliveryExpressTemplateDO> getDeliveryExpressTemplateList(Collection<Long> ids) {
        return expressTemplateMapper.selectBatchIds(ids);
    }

    @Override
    public List<DeliveryExpressTemplateDO> getDeliveryExpressTemplateList() {
        return expressTemplateMapper.selectList();
    }

    @Override
    public PageResult<DeliveryExpressTemplateDO> getDeliveryExpressTemplatePage(DeliveryExpressTemplatePageReqVO pageReqVO) {
        return expressTemplateMapper.selectPage(pageReqVO);
    }

    @Override
    public DeliveryExpressTemplateDO validateDeliveryExpressTemplate(Long templateId) {
        DeliveryExpressTemplateDO template = expressTemplateMapper.selectById(templateId);
        if (template == null) {
            throw exception(EXPRESS_TEMPLATE_NOT_EXISTS);
        }
        return template;
    }

    @Override
    public Map<Long, DeliveryExpressTemplateRespBO> getExpressTemplateMapByIdsAndArea(Collection<Long> ids, Integer areaId) {
        Assert.notNull(areaId, "Region number {} Cannot be empty", areaId);
        // Query template Array
        if (CollUtil.isEmpty(ids)) {
            return Collections.emptyMap();
        }
        List<DeliveryExpressTemplateDO> templateList = expressTemplateMapper.selectBatchIds(ids);
        // Query templateCharge Array
        List<DeliveryExpressTemplateChargeDO> chargeList = expressTemplateChargeMapper.selectByTemplateIds(ids);
        // Query templateFree Array
        List<DeliveryExpressTemplateFreeDO> freeList = expressTemplateFreeMapper.selectListByTemplateIds(ids);

        // Combined shipping template configuration RespBO
        return INSTANCE.convertMap(areaId, templateList, chargeList, freeList);
    }

}
