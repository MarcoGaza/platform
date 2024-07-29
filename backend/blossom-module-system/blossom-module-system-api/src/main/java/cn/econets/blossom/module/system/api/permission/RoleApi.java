package cn.econets.blossom.module.system.api.permission;

import java.util.Collection;

/**
 * Role API Interface
 *
 */
public interface RoleApi {

    /**
     * Check if the characters are valid。As follows，Deemed invalid：
     * 1. Role number does not exist
     * 2. The character is disabled
     *
     * @param ids Role number array
     */
    void validRoleList(Collection<Long> ids);

}
