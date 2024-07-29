package cn.econets.blossom.module.crm.framework.operatelog.core;

import cn.hutool.core.util.StrUtil;
import cn.econets.blossom.module.crm.dal.dataobject.customer.CrmCustomerDO;
import cn.econets.blossom.module.crm.service.customer.CrmCustomerService;
import com.mzt.logapi.service.IParseFunction;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * CRM Customer's {@link IParseFunction} Implementation class
 *
 */
@Component
@Slf4j
public class CrmCustomerParseFunction implements IParseFunction {

    public static final String NAME = "getCustomerById";

    @Resource
    private CrmCustomerService customerService;

    @Override
    public boolean executeBefore() {
        return true; // Convert the value first and then compare
    }

    @Override
    public String functionName() {
        return NAME;
    }

    @Override
    public String apply(Object value) {
        if (StrUtil.isEmptyIfStr(value)) {
            return "";
        }
        CrmCustomerDO crmCustomerDO = customerService.getCustomer(Long.parseLong(value.toString()));
        return crmCustomerDO == null ? "" : crmCustomerDO.getName();
    }

}
