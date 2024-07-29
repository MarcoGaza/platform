package cn.econets.blossom.module.crm.framework.operatelog.core;

import cn.hutool.core.util.StrUtil;
import cn.econets.blossom.module.system.api.user.AdminUserApi;
import cn.econets.blossom.module.system.api.user.dto.AdminUserRespDTO;
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
public class SysAdminUserParseFunction implements IParseFunction {

    public static final String NAME = "getAdminUserById";

    @Resource
    private AdminUserApi adminUserApi;

    @Override
    public String functionName() {
        return NAME;
    }

    @Override
    public String apply(Object value) {
        if (StrUtil.isEmptyIfStr(value)) {
            return "";
        }

        // Get user information
        AdminUserRespDTO user = adminUserApi.getUser(Long.parseLong(value.toString()));
        if (user == null) {
            log.warn("[apply][Get User{{}}Empty", value);
            return "";
        }
        // Return format Yudao source code(13888888888)
        String nickname = user.getNickname();
        if (StrUtil.isEmpty(user.getMobile())) {
            return nickname;
        }
        return StrUtil.format("{}({})", nickname, user.getMobile());
    }

}
