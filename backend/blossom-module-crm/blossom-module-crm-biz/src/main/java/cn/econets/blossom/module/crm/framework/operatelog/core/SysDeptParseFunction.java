package cn.econets.blossom.module.crm.framework.operatelog.core;

import cn.hutool.core.util.StrUtil;
import cn.econets.blossom.module.system.api.dept.DeptApi;
import cn.econets.blossom.module.system.api.dept.dto.DeptRespDTO;
import com.mzt.logapi.service.IParseFunction;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * Administrator's name {@link IParseFunction} Implementation class
 *
 */
@Slf4j
@Component
public class SysDeptParseFunction implements IParseFunction {

    public static final String NAME = "getDeptById";

    @Resource
    private DeptApi deptApi;

    @Override
    public String functionName() {
        return NAME;
    }

    @Override
    public String apply(Object value) {
        if (StrUtil.isEmptyIfStr(value)) {
            return "";
        }

        // Get department information
        DeptRespDTO dept = deptApi.getDept(Long.parseLong(value.toString()));
        if (dept == null) {
            log.warn("[apply][Get department{{}}Empty", value);
            return "";
        }
        return dept.getName();
    }

}
