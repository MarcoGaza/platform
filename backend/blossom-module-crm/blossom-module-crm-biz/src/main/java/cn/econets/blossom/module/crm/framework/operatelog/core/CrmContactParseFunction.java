package cn.econets.blossom.module.crm.framework.operatelog.core;

import cn.hutool.core.util.StrUtil;
import cn.econets.blossom.module.crm.dal.dataobject.contact.CrmContactDO;
import cn.econets.blossom.module.crm.service.contact.CrmContactService;
import com.mzt.logapi.service.IParseFunction;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * CRM Contact Person {@link IParseFunction} Implementation class
 *
 */
@Component
@Slf4j
public class CrmContactParseFunction implements IParseFunction {

    public static final String NAME = "getContactById";

    @Resource
    private CrmContactService contactService;

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
        CrmContactDO contactDO = contactService.getContact(Long.parseLong(value.toString()));
        return contactDO == null ? "" : contactDO.getName();
    }

}
