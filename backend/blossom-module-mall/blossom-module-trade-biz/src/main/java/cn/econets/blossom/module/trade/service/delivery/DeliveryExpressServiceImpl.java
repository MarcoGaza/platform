package cn.econets.blossom.module.trade.service.delivery;

import cn.econets.blossom.framework.common.enums.CommonStatusEnum;
import cn.econets.blossom.framework.common.pojo.PageResult;
import cn.econets.blossom.module.trade.controller.admin.delivery.vo.express.DeliveryExpressCreateReqVO;
import cn.econets.blossom.module.trade.controller.admin.delivery.vo.express.DeliveryExpressExportReqVO;
import cn.econets.blossom.module.trade.controller.admin.delivery.vo.express.DeliveryExpressPageReqVO;
import cn.econets.blossom.module.trade.controller.admin.delivery.vo.express.DeliveryExpressUpdateReqVO;
import cn.econets.blossom.module.trade.convert.delivery.DeliveryExpressConvert;
import cn.econets.blossom.module.trade.dal.dataobject.delivery.DeliveryExpressDO;
import cn.econets.blossom.module.trade.dal.mysql.delivery.DeliveryExpressMapper;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.util.List;

import static cn.econets.blossom.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.econets.blossom.module.trade.enums.ErrorCodeConstants.*;

/**
 * Express Delivery Company Service Implementation class
 *
 */
@Service
@Validated
public class DeliveryExpressServiceImpl implements DeliveryExpressService {

    @Resource
    private DeliveryExpressMapper deliveryExpressMapper;

    @Override
    public Long createDeliveryExpress(DeliveryExpressCreateReqVO createReqVO) {
        //Check whether the code is unique
        validateExpressCodeUnique(createReqVO.getCode(), null);
        // Insert
        DeliveryExpressDO deliveryExpress = DeliveryExpressConvert.INSTANCE.convert(createReqVO);
        deliveryExpressMapper.insert(deliveryExpress);
        // Return
        return deliveryExpress.getId();
    }

    @Override
    public void updateDeliveryExpress(DeliveryExpressUpdateReqVO updateReqVO) {
        // Check existence
        validateDeliveryExpressExists(updateReqVO.getId());
        //Check whether the code is unique
        validateExpressCodeUnique(updateReqVO.getCode(), updateReqVO.getId());
        // Update
        DeliveryExpressDO updateObj = DeliveryExpressConvert.INSTANCE.convert(updateReqVO);
        deliveryExpressMapper.updateById(updateObj);
    }

    @Override
    public void deleteDeliveryExpress(Long id) {
        // Check existence
        validateDeliveryExpressExists(id);
        // Delete
        deliveryExpressMapper.deleteById(id);
    }

    private void validateExpressCodeUnique(String code, Long id) {
        DeliveryExpressDO express = deliveryExpressMapper.selectByCode(code);
        if (express == null) {
            return;
        }
        // If id Empty，Indicates that there is no need to compare whether they are the same id Express delivery company
        if (id == null) {
            throw exception(EXPRESS_CODE_DUPLICATE);
        }
        if (!express.getId().equals(id)) {
            throw exception(EXPRESS_CODE_DUPLICATE);
        }
    }
    private void validateDeliveryExpressExists(Long id) {
        if (deliveryExpressMapper.selectById(id) == null) {
            throw exception(EXPRESS_NOT_EXISTS);
        }
    }

    @Override
    public DeliveryExpressDO getDeliveryExpress(Long id) {
        return deliveryExpressMapper.selectById(id);
    }

    @Override
    public DeliveryExpressDO validateDeliveryExpress(Long id) {
        DeliveryExpressDO deliveryExpress = deliveryExpressMapper.selectById(id);
        if (deliveryExpress == null) {
            throw exception(EXPRESS_NOT_EXISTS);
        }
        if (deliveryExpress.getStatus().equals(CommonStatusEnum.DISABLE.getStatus())) {
            throw exception(EXPRESS_STATUS_NOT_ENABLE);
        }
        return deliveryExpress;
    }

    @Override
    public PageResult<DeliveryExpressDO> getDeliveryExpressPage(DeliveryExpressPageReqVO pageReqVO) {
        return deliveryExpressMapper.selectPage(pageReqVO);
    }

    @Override
    public List<DeliveryExpressDO> getDeliveryExpressList(DeliveryExpressExportReqVO exportReqVO) {
        return deliveryExpressMapper.selectList(exportReqVO);
    }

    @Override
    public List<DeliveryExpressDO> getDeliveryExpressListByStatus(Integer status) {
        return deliveryExpressMapper.selectListByStatus(status);
    }

}
