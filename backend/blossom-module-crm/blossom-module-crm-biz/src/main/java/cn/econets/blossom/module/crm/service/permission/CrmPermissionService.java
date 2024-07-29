package cn.econets.blossom.module.crm.service.permission;


import cn.econets.blossom.module.crm.controller.admin.permission.vo.CrmPermissionUpdateReqVO;
import cn.econets.blossom.module.crm.dal.dataobject.permission.CrmPermissionDO;
import cn.econets.blossom.module.crm.enums.common.CrmBizTypeEnum;
import cn.econets.blossom.module.crm.enums.permission.CrmPermissionLevelEnum;
import cn.econets.blossom.module.crm.service.permission.bo.CrmPermissionCreateReqBO;
import cn.econets.blossom.module.crm.service.permission.bo.CrmPermissionTransferReqBO;

import javax.validation.Valid;
import java.util.Collection;
import java.util.List;

/**
 * crm Data permissions Service Interface
 *
 */
public interface CrmPermissionService {

    /**
     * Create data permissions
     *
     * @param createReqBO Create information
     * @return Number
     */
    Long createPermission(@Valid CrmPermissionCreateReqBO createReqBO);

    /**
     * Create data permissions
     *
     * @param createReqBOs Create information
     */
    void createPermissionBatch(@Valid List<CrmPermissionCreateReqBO> createReqBOs);

    /**
     * Update data permissions
     *
     * @param updateReqVO Update information
     */
    void updatePermission(CrmPermissionUpdateReqVO updateReqVO);

    /**
     * Data permission transfer
     *
     * @param crmPermissionTransferReqBO Data permission transfer request
     */
    void transferPermission(@Valid CrmPermissionTransferReqBO crmPermissionTransferReqBO);

    /**
     * Delete data permissions
     *
     * @param bizType Data type，Relationship {@link CrmBizTypeEnum}
     * @param bizId   Data number，Relationship {@link CrmBizTypeEnum} Corresponding module DO#getId()
     * @param level   Data permission level，Relationship {@link CrmPermissionLevelEnum}
     */
    void deletePermission(Integer bizType, Long bizId, Integer level);

    /**
     * Delete data permission
     *
     * @param bizType Data type，Relationship {@link CrmBizTypeEnum}
     * @param bizId   Data number，Relationship {@link CrmBizTypeEnum} Corresponding module DO#getId()
     */
    void deletePermission(Integer bizType, Long bizId);

    /**
     * Batch delete data permissions
     *
     * @param ids    Authorization number
     * @param userId User ID
     */
    void deletePermissionBatch(Collection<Long> ids, Long userId);

    /**
     * Delete the specified user's data permissions
     *
     * @param id     Authorization number
     * @param userId User Number
     */
    void deleteSelfPermission(Long id, Long userId);

    /**
     * Get data permission list，Passed Data type x Some data
     *
     * @param bizType Data type，Relationship {@link CrmBizTypeEnum}
     * @param bizId   Data number，Relationship {@link CrmBizTypeEnum} Corresponding module DO#getId()
     * @return Crm Data permission list
     */
    List<CrmPermissionDO> getPermissionListByBiz(Integer bizType, Long bizId);

    /**
     * Get data permission list，Passed Data type x Some data
     *
     * @param bizType Data type，Relationship {@link CrmBizTypeEnum}
     * @param bizIds  Data number，Relationship {@link CrmBizTypeEnum} Corresponding module DO#getId()
     * @return Crm Data permission list
     */
    List<CrmPermissionDO> getPermissionListByBiz(Integer bizType, Collection<Long> bizIds);

    /**
     * Get the module data list in which the user participates
     *
     * @param bizType Module type
     * @param userId  User Number
     * @return Module data list
     */
    List<CrmPermissionDO> getPermissionListByBizTypeAndUserId(Integer bizType, Long userId);

    /**
     * Check whether you have the permission to operate the specified data
     *
     * @param bizType   Data type，Relationship {@link CrmBizTypeEnum}
     * @param bizId     Data number，Relationship {@link CrmBizTypeEnum} Corresponding module DO#getId()
     * @param userId    User Number
     * @param level Permission Level
     * @return Do you have permission?
     */
    boolean hasPermission(Integer bizType, Long bizId, Long userId, CrmPermissionLevelEnum level);

}
