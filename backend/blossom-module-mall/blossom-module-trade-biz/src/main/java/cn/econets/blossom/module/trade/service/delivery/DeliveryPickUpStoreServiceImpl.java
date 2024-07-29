package cn.econets.blossom.module.trade.service.delivery;

import cn.econets.blossom.framework.common.pojo.PageResult;
import cn.econets.blossom.module.trade.controller.admin.delivery.vo.pickup.DeliveryPickUpStoreCreateReqVO;
import cn.econets.blossom.module.trade.controller.admin.delivery.vo.pickup.DeliveryPickUpStorePageReqVO;
import cn.econets.blossom.module.trade.controller.admin.delivery.vo.pickup.DeliveryPickUpStoreUpdateReqVO;
import cn.econets.blossom.module.trade.convert.delivery.DeliveryPickUpStoreConvert;
import cn.econets.blossom.module.trade.dal.dataobject.delivery.DeliveryPickUpStoreDO;
import cn.econets.blossom.module.trade.dal.mysql.delivery.DeliveryPickUpStoreMapper;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.List;

import static cn.econets.blossom.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.econets.blossom.module.trade.enums.ErrorCodeConstants.PICK_UP_STORE_NOT_EXISTS;

/**
 * Self-pickup store Service Implementation class
 *
 */
@Service
@Validated
public class DeliveryPickUpStoreServiceImpl implements DeliveryPickUpStoreService {

    @Resource
    private DeliveryPickUpStoreMapper deliveryPickUpStoreMapper;

    @Override
    public Long createDeliveryPickUpStore(DeliveryPickUpStoreCreateReqVO createReqVO) {
        // Insert
        DeliveryPickUpStoreDO deliveryPickUpStore = DeliveryPickUpStoreConvert.INSTANCE.convert(createReqVO);
        deliveryPickUpStoreMapper.insert(deliveryPickUpStore);
        // Return
        return deliveryPickUpStore.getId();
    }

    @Override
    public void updateDeliveryPickUpStore(DeliveryPickUpStoreUpdateReqVO updateReqVO) {
        // Check existence
        validateDeliveryPickUpStoreExists(updateReqVO.getId());
        // Update
        DeliveryPickUpStoreDO updateObj = DeliveryPickUpStoreConvert.INSTANCE.convert(updateReqVO);
        deliveryPickUpStoreMapper.updateById(updateObj);
    }

    @Override
    public void deleteDeliveryPickUpStore(Long id) {
        // Check existence
        validateDeliveryPickUpStoreExists(id);
        // Delete
        deliveryPickUpStoreMapper.deleteById(id);
    }

    private void validateDeliveryPickUpStoreExists(Long id) {
        if (deliveryPickUpStoreMapper.selectById(id) == null) {
            throw exception(PICK_UP_STORE_NOT_EXISTS);
        }
    }

    @Override
    public DeliveryPickUpStoreDO getDeliveryPickUpStore(Long id) {
        return deliveryPickUpStoreMapper.selectById(id);
    }

    @Override
    public List<DeliveryPickUpStoreDO> getDeliveryPickUpStoreList(Collection<Long> ids) {
        return deliveryPickUpStoreMapper.selectBatchIds(ids);
    }

    @Override
    public PageResult<DeliveryPickUpStoreDO> getDeliveryPickUpStorePage(DeliveryPickUpStorePageReqVO pageReqVO) {
        return deliveryPickUpStoreMapper.selectPage(pageReqVO);
    }

    @Override
    public List<DeliveryPickUpStoreDO> getDeliveryPickUpStoreListByStatus(Integer status) {
        return deliveryPickUpStoreMapper.selectListByStatus(status);
    }

}
