package cn.econets.blossom.module.crm.util;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjUtil;
import cn.hutool.extra.spring.SpringUtil;
import cn.econets.blossom.module.crm.dal.dataobject.permission.CrmPermissionDO;
import cn.econets.blossom.module.crm.enums.common.CrmBizTypeEnum;
import cn.econets.blossom.module.crm.enums.common.CrmSceneTypeEnum;
import cn.econets.blossom.module.crm.enums.permission.CrmPermissionLevelEnum;
import cn.econets.blossom.module.crm.framework.permission.core.util.CrmPermissionUtils;
import cn.econets.blossom.module.system.api.user.AdminUserApi;
import cn.econets.blossom.module.system.api.user.dto.AdminUserRespDTO;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.github.yulichang.autoconfigure.MybatisPlusJoinProperties;
import com.github.yulichang.wrapper.MPJLambdaWrapper;

import java.util.Collection;
import java.util.List;

import static cn.econets.blossom.framework.common.util.collection.CollectionUtils.convertSet;

/**
 * CRM Query tools
 *
 */
public class CrmQueryWrapperUtils {

    /**
     * Construction CRM Data type data paging query conditions
     *
     * @param query     Join table query object
     * @param bizType   Data type {@link CrmBizTypeEnum}
     * @param bizId     Data number
     * @param userId    User Number
     * @param sceneType Scene Type
     * @param pool      High Seas
     */
    public static <T extends MPJLambdaWrapper<?>, S> void appendPermissionCondition(T query, Integer bizType, SFunction<S, ?> bizId,
                                                                                    Long userId, Integer sceneType, Boolean pool) {
        final String ownerUserIdField = SingletonManager.getMybatisPlusJoinProperties().getTableAlias() + ".owner_user_id";
        // 1. Constructing data permission table connection conditions
        if (!CrmPermissionUtils.isCrmAdmin() && ObjUtil.notEqual(pool, Boolean.TRUE)) { // Administrator，No data permissions are required on the high seas
            query.innerJoin(CrmPermissionDO.class, on -> on.eq(CrmPermissionDO::getBizType, bizType)
                    .eq(CrmPermissionDO::getBizId, bizId) // Can only be used SFunction If passed id Analyzed sql Incorrect
                    .eq(CrmPermissionDO::getUserId, userId));
        }
        // 2.1 Scene 1：The data I am responsible for
        if (CrmSceneTypeEnum.isOwner(sceneType)) {
            query.eq(ownerUserIdField, userId);
        }
        // 2.2 Scene 2：The data I participated in
        if (CrmSceneTypeEnum.isInvolved(sceneType)) {
            query.ne(ownerUserIdField, userId)
                    .in(CrmPermissionDO::getLevel, CrmPermissionLevelEnum.READ.getLevel(), CrmPermissionLevelEnum.WRITE.getLevel());
        }
        // 2.3 Scene 3：Data that subordinates are responsible for
        if (CrmSceneTypeEnum.isSubordinate(sceneType)) {
            List<AdminUserRespDTO> subordinateUsers = SingletonManager.getAdminUserApi().getUserListBySubordinate(userId);
            if (CollUtil.isEmpty(subordinateUsers)) {
                query.eq(ownerUserIdField, -1); // No results returned
            } else {
                query.in(ownerUserIdField, convertSet(subordinateUsers, AdminUserRespDTO::getId));
            }
        }

        // 3. Search conditions for stitching high seas
        if (ObjUtil.equal(pool, Boolean.TRUE)) { // Situation 1：High Seas
            query.isNull(ownerUserIdField);
        } else { // Case 2：Not the high seas
            query.isNotNull(ownerUserIdField);
        }
    }

    /**
     * Construction CRM Data type batch data query conditions
     *
     * @param query   Join table query object
     * @param bizType Data type {@link CrmBizTypeEnum}
     * @param bizIds  Data number
     * @param userId  User Number
     */
    public static <T extends MPJLambdaWrapper<?>> void appendPermissionCondition(T query, Integer bizType, Collection<Long> bizIds, Long userId) {
        if (CrmPermissionUtils.isCrmAdmin()) {// Administrators do not need data permissions
            return;
        }
        query.innerJoin(CrmPermissionDO.class, on ->
                on.eq(CrmPermissionDO::getBizType, bizType).in(CrmPermissionDO::getBizId, bizIds)
                        .eq(CollUtil.isNotEmpty(bizIds), CrmPermissionDO::getUserId, userId));
    }

    /**
     * Static inner class implements singleton acquisition
     *
     * @author HUIHUI
     */
    private static class SingletonManager {

        private static final AdminUserApi ADMIN_USER_API = SpringUtil.getBean(AdminUserApi.class);

        private static final MybatisPlusJoinProperties MYBATIS_PLUS_JOIN_PROPERTIES = SpringUtil.getBean(MybatisPlusJoinProperties.class);

        public static AdminUserApi getAdminUserApi() {
            return ADMIN_USER_API;
        }

        public static MybatisPlusJoinProperties getMybatisPlusJoinProperties() {
            return MYBATIS_PLUS_JOIN_PROPERTIES;
        }

    }

}
