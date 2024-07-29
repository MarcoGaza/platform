package cn.econets.blossom.module.crm.framework.operatelog.core;

import cn.hutool.core.util.StrUtil;
import cn.econets.blossom.module.crm.dal.dataobject.contract.CrmContractDO;
import cn.econets.blossom.module.crm.service.contract.CrmContractService;
import com.mzt.logapi.service.IParseFunction;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * CRM Contractual {@link IParseFunction} Implementation class
 *
 */
@Component
@Slf4j
public class CrmContractParseFunction implements IParseFunction {

    public static final String NAME = "getContractById";

    @Resource
    private CrmContractService contractService;

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
        CrmContractDO contract = contractService.getContract(Long.parseLong(value.toString()));
        return contract == null ? "" : contract.getName();
    }

}
