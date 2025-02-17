package cn.econets.blossom.module.crm.framework.operatelog.core;

import cn.hutool.core.util.StrUtil;
import cn.econets.blossom.module.crm.dal.dataobject.business.CrmBusinessDO;
import cn.econets.blossom.module.crm.service.business.CrmBusinessService;
import com.mzt.logapi.service.IParseFunction;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * CRM Business Opportunities {@link IParseFunction} Implementation class
 *
 */
@Component
@Slf4j
public class CrmBusinessParseFunction implements IParseFunction {

    public static final String NAME = "getBusinessById";

    @Resource
    private CrmBusinessService businessService;

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
        CrmBusinessDO businessDO = businessService.getBusiness(Long.parseLong(value.toString()));
        return businessDO == null ? "" : businessDO.getName();
    }

}
