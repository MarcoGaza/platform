package cn.econets.blossom.module.system.api.permission;

import cn.econets.blossom.module.system.service.permission.RoleService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collection;

/**
 * Role API Implementation class
 *
 */
@Service
public class RoleApiImpl implements RoleApi {

    @Resource
    private RoleService roleService;

    @Override
    public void validRoleList(Collection<Long> ids) {
        roleService.validateRoleList(ids);
    }
}
