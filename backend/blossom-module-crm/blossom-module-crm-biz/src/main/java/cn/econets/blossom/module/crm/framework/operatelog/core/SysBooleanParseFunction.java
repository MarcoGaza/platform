package cn.econets.blossom.module.crm.framework.operatelog.core;

import cn.hutool.core.util.StrUtil;
import cn.econets.blossom.framework.dict.core.util.DictFrameworkUtils;
import cn.econets.blossom.module.infrastructure.enums.DictTypeConstants;
import com.mzt.logapi.service.IParseFunction;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * Whether the type {@link IParseFunction} Implementation class
 *
 */
@Component
@Slf4j
public class SysBooleanParseFunction implements IParseFunction {

    public static final String NAME = "getBoolean";

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
        return DictFrameworkUtils.getDictDataLabel(DictTypeConstants.BOOLEAN_STRING, value.toString());
    }

}
