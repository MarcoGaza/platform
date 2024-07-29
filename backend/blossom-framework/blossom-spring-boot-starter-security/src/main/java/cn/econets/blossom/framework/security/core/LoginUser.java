package cn.econets.blossom.framework.security.core;

import cn.econets.blossom.framework.common.enums.UserTypeEnum;
import cn.hutool.core.map.MapUtil;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Login user information
 *
 */
@Data
public class LoginUser {

    /**
     * User Number
     */
    private Long id;
    /**
     * User Type
     *
     * Relationship {@link UserTypeEnum}
     */
    private Integer userType;
    /**
     * Tenant Number
     */
    private Long tenantId;
    /**
     * Authorization scope
     */
    private List<String> scopes;

    // ========== Context ==========
    /**
     * Context Field，Do not persist
     *
     * 1. For use based on LoginUser Temporary cache of dimensions
     */
    @JsonIgnore
    private Map<String, Object> context;

    public void setContext(String key, Object value) {
        if (context == null) {
            context = new HashMap<>();
        }
        context.put(key, value);
    }

    public <T> T getContext(String key, Class<T> type) {
        return MapUtil.get(context, key, type);
    }

}
