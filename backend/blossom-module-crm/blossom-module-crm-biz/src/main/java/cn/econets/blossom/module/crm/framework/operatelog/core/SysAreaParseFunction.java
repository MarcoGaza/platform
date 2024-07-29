package cn.econets.blossom.module.crm.framework.operatelog.core;

import cn.hutool.core.util.StrUtil;
import cn.econets.blossom.framework.ip.core.utils.AreaUtils;
import com.mzt.logapi.service.IParseFunction;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * Place name {@link IParseFunction} Implementation class
 *
 */
@Slf4j
@Component
public class SysAreaParseFunction implements IParseFunction {

    public static final String NAME = "getArea";

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
        return AreaUtils.format(Integer.parseInt(value.toString()));
    }

}
