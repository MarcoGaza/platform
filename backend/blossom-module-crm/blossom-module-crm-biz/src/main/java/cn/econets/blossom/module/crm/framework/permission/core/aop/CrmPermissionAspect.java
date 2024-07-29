package cn.econets.blossom.module.crm.framework.permission.core.aop;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjUtil;
import cn.hutool.core.util.StrUtil;
import cn.econets.blossom.framework.common.util.spring.SpringExpressionUtils;
import cn.econets.blossom.framework.web.core.util.WebFrameworkUtils;
import cn.econets.blossom.module.crm.dal.dataobject.permission.CrmPermissionDO;
import cn.econets.blossom.module.crm.enums.common.CrmBizTypeEnum;
import cn.econets.blossom.module.crm.enums.permission.CrmPermissionLevelEnum;
import cn.econets.blossom.module.crm.framework.permission.core.annotations.CrmPermission;
import cn.econets.blossom.module.crm.framework.permission.core.util.CrmPermissionUtils;
import cn.econets.blossom.module.crm.service.permission.CrmPermissionService;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.*;

import static cn.econets.blossom.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.econets.blossom.framework.common.util.collection.CollectionUtils.*;
import static cn.econets.blossom.framework.common.util.json.JsonUtils.toJsonString;
import static cn.econets.blossom.module.crm.enums.ErrorCodeConstants.CRM_PERMISSION_DENIED;

/**
 * Crm Data permission verification AOP Cut surface
 *
 */
@Component
@Aspect
@Slf4j
public class CrmPermissionAspect {

    @Resource
    private CrmPermissionService crmPermissionService;

    @Before("@annotation(crmPermission)")
    public void doBefore(JoinPoint joinPoint, CrmPermission crmPermission) {
        // 1.1 Get relevant attribute values
        Map<String, Object> expressionValues = parseExpressions(joinPoint, crmPermission);
        Integer bizType = StrUtil.isEmpty(crmPermission.bizTypeValue()) ?
                crmPermission.bizType()[0].getType() : (Integer) expressionValues.get(crmPermission.bizTypeValue()); // Module type
        // 1.2 Handling compatible multiple bizId Situation
        Object object = expressionValues.get(crmPermission.bizId()); // Module data number
        Set<Long> bizIds = new HashSet<>();
        if (object instanceof Collection<?>) {
            bizIds.addAll(convertSet((Collection<?>) object, item -> Long.parseLong(item.toString())));
        } else {
            bizIds.add(Long.parseLong(object.toString()));
        }
        Integer permissionLevel = crmPermission.level().getLevel(); // Required permission level

        // 2. Check permissions one by one
        List<CrmPermissionDO> permissionList = crmPermissionService.getPermissionListByBiz(bizType, bizIds);
        Map<Long, List<CrmPermissionDO>> multiMap = convertMultiMap(permissionList, CrmPermissionDO::getBizId);
        bizIds.forEach(bizId -> validatePermission(bizType, multiMap.get(bizId), permissionLevel));
    }

    private void validatePermission(Integer bizType, List<CrmPermissionDO> bizPermissions, Integer permissionLevel) {
        // 1. If you are a super administrator, pass directly
        if (CrmPermissionUtils.isCrmAdmin()) {
            return;
        }
        // 1.1 No data permission
        if (CollUtil.isEmpty(bizPermissions)) {
            // If there are no team members, everyone should have read permissions for the high seas data
            if (CrmPermissionLevelEnum.isRead(permissionLevel)) {
                return;
            }
            // Without data permission, the read permission is exceeded and an error is reported directly，Avoid checking null pointers later
            throw exception(CRM_PERMISSION_DENIED, CrmBizTypeEnum.getNameByType(bizType));
        } else { // 1.2 There is data permission but no person in charge
            if (!anyMatch(bizPermissions, item -> CrmPermissionLevelEnum.isOwner(item.getLevel()))) {
                if (CrmPermissionLevelEnum.isRead(permissionLevel)) {
                    return;
                }
            }
        }

        // 2.1 Situation 1：If you are the person in charge，All permissions are granted by default
        CrmPermissionDO userPermission = CollUtil.findOne(bizPermissions, permission -> ObjUtil.equal(permission.getUserId(), getUserId()));
        if (userPermission != null) {
            if (CrmPermissionLevelEnum.isOwner(userPermission.getLevel())) {
                return;
            }
            // 2.2 Case 2：Check whether you have read permission
            if (CrmPermissionLevelEnum.isRead(permissionLevel)) {
                if (CrmPermissionLevelEnum.isRead(userPermission.getLevel()) // Check whether the current user has read permission
                        || CrmPermissionLevelEnum.isWrite(userPermission.getLevel())) { // Check whether the current user has write permission
                    return;
                }
            }
            // 2.3 Case 3：Check whether you have write permission
            if (CrmPermissionLevelEnum.isWrite(permissionLevel)) {
                if (CrmPermissionLevelEnum.isWrite(userPermission.getLevel())) { // Check whether the current user has write permission
                    return;
                }
            }
        }
        // 2.4 No permission，Throws an exception
        log.info("[doBefore][userId({}) Requesting permissions({}) Actual permissions({}) Data verification error]", // Hit info Log，Convenient for subsequent troubleshooting、Audit
                getUserId(), permissionLevel, toJsonString(userPermission));
        throw exception(CRM_PERMISSION_DENIED, CrmBizTypeEnum.getNameByType(bizType));
    }

    /**
     * Get user ID
     *
     * @return User Number
     */
    private static Long getUserId() {
        return WebFrameworkUtils.getLoginUserId();
    }

    private static Map<String, Object> parseExpressions(JoinPoint joinPoint, CrmPermission crmPermission) {
        // 1. Expression to be parsed
        List<String> expressionStrings = new ArrayList<>(2);
        expressionStrings.add(crmPermission.bizId());
        if (StrUtil.isNotEmpty(crmPermission.bizTypeValue())) { // If it is empty, it means bizType Value
            expressionStrings.add(crmPermission.bizTypeValue());
        }
        // 2. Execute parsing
        return SpringExpressionUtils.parseExpressions(joinPoint, expressionStrings);
    }

}
