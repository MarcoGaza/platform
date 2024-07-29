package cn.econets.blossom.module.crm.service.customer;

import cn.econets.blossom.framework.common.util.object.BeanUtils;
import cn.econets.blossom.module.crm.controller.admin.customer.vo.poolconfig.CrmCustomerPoolConfigSaveReqVO;
import cn.econets.blossom.module.crm.dal.dataobject.customer.CrmCustomerPoolConfigDO;
import cn.econets.blossom.module.crm.dal.mysql.customer.CrmCustomerPoolConfigMapper;
import com.mzt.logapi.context.LogRecordContext;
import com.mzt.logapi.starter.annotation.LogRecord;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.util.Objects;

import static cn.econets.blossom.module.crm.enums.LogRecordConstants.*;

/**
 * Customer high seas configuration Service Implementation class
 *
 */
@Service
@Validated
public class CrmCustomerPoolConfigServiceImpl implements CrmCustomerPoolConfigService {

    @Resource
    private CrmCustomerPoolConfigMapper customerPoolConfigMapper;

    /**
     * Get the customer's high seas configuration
     *
     * @return Customer high seas configuration
     */
    @Override
    public CrmCustomerPoolConfigDO getCustomerPoolConfig() {
        return customerPoolConfigMapper.selectOne();
    }

    /**
     * Save client high seas configuration
     *
     * @param saveReqVO Update information
     */
    @Override
    @LogRecord(type = CRM_CUSTOMER_POOL_CONFIG_TYPE, subType = CRM_CUSTOMER_POOL_CONFIG_SUB_TYPE, bizNo = "{{#poolConfigId}}",
            success = CRM_CUSTOMER_POOL_CONFIG_SUCCESS)
    public void saveCustomerPoolConfig(CrmCustomerPoolConfigSaveReqVO saveReqVO) {
        // Exists，Then update
        CrmCustomerPoolConfigDO dbConfig = getCustomerPoolConfig();
        CrmCustomerPoolConfigDO poolConfig = BeanUtils.toBean(saveReqVO, CrmCustomerPoolConfigDO.class);
        if (Objects.nonNull(dbConfig)) {
            customerPoolConfigMapper.updateById(poolConfig.setId(dbConfig.getId()));
            // Record operation log context
            LogRecordContext.putVariable("isPoolConfigUpdate", Boolean.TRUE);
            LogRecordContext.putVariable("poolConfigId", poolConfig.getId());
            return;
        }
        // Does not exist，Insert
        customerPoolConfigMapper.insert(poolConfig);
        // Record operation log context
        LogRecordContext.putVariable("isPoolConfigUpdate", Boolean.FALSE);
        LogRecordContext.putVariable("poolConfigId", poolConfig.getId());
    }

}
