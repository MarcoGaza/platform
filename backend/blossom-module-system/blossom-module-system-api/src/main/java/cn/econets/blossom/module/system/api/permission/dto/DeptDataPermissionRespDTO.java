package cn.econets.blossom.module.system.api.permission.dto;

import lombok.Data;

import java.util.HashSet;
import java.util.Set;

/**
 * Data permissions for departments Response DTO
 *
 */
@Data
public class DeptDataPermissionRespDTO {

    /**
     * Can I view all data?
     */
    private Boolean all;
    /**
     * Can I view my own data?
     */
    private Boolean self;
    /**
     * Viewable department number array
     */
    private Set<Long> deptIds;

    public DeptDataPermissionRespDTO() {
        this.all = false;
        this.self = false;
        this.deptIds = new HashSet<>();
    }

}
