package cn.econets.blossom.module.crm.framework.operatelog.core;

import cn.hutool.core.util.StrUtil;
import cn.econets.blossom.framework.dict.core.util.DictFrameworkUtils;
import com.mzt.logapi.service.IParseFunction;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import static cn.econets.blossom.module.crm.enums.DictTypeConstants.CRM_CUSTOMER_LEVEL;

/**
 * Customer level {@link IParseFunction} Implementation class
 *
 */
@Component
@Slf4j
public class CrmCustomerLevelParseFunction implements IParseFunction {

    public static final String NAME = "getCustomerLevel";

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
        return DictFrameworkUtils.getDictDataLabel(CRM_CUSTOMER_LEVEL, value.toString());
    }

}
