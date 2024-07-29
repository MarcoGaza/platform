package cn.econets.blossom.module.system.service.permission;

import cn.econets.blossom.framework.common.util.collection.CollectionUtils;
import cn.econets.blossom.framework.common.util.object.BeanUtils;
import cn.econets.blossom.module.system.controller.admin.permission.vo.menu.MenuListReqVO;
import cn.econets.blossom.module.system.controller.admin.permission.vo.menu.MenuSaveVO;
import cn.econets.blossom.module.system.dal.mysql.permission.MenuMapper;
import cn.econets.blossom.module.system.dal.dataobject.permission.MenuDO;
import cn.econets.blossom.module.system.dal.redis.RedisKeyConstants;
import cn.econets.blossom.module.system.enums.ErrorCodeConstants;
import cn.econets.blossom.module.system.enums.permission.MenuTypeEnum;
import cn.econets.blossom.module.system.service.tenant.TenantService;
import cn.hutool.core.collection.CollUtil;
import com.google.common.annotations.VisibleForTesting;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.List;

import static cn.econets.blossom.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.econets.blossom.module.system.dal.dataobject.permission.MenuDO.ID_ROOT;
import static cn.econets.blossom.module.system.enums.ErrorCodeConstants.MENU_PARENT_ERROR;

/**
 * Menu Service Realization
 *
 */
@Service
@Slf4j
public class MenuServiceImpl implements MenuService {

    @Resource
    private MenuMapper menuMapper;
    @Resource
    private PermissionService permissionService;
    @Resource
    @Lazy // Delay，Avoid circular dependency errors
    private TenantService tenantService;

    @Override
    @CacheEvict(value = RedisKeyConstants.PERMISSION_MENU_ID_LIST, key = "#createReqVO.permission",
            condition = "#createReqVO.permission != null")
    public Long createMenu(MenuSaveVO createReqVO) {
        // Check if the parent menu exists
        validateParentMenu(createReqVO.getParentId(), null);
        // Verify menu（Myself）
        validateMenu(createReqVO.getParentId(), createReqVO.getName(), null);

        // Insert into database
        MenuDO menu = BeanUtils.toBean(createReqVO, MenuDO.class);
        initMenuProperty(menu);
        menuMapper.insert(menu);
        // Return
        return menu.getId();
    }

    @Override
    @CacheEvict(value = RedisKeyConstants.PERMISSION_MENU_ID_LIST,
            allEntries = true) // allEntries Clear all caches，Because permission If changed，Involving both new and old permission。Clean directly，Simple and effective
    public void updateMenu(MenuSaveVO updateReqVO) {
        // Check if the updated menu exists
        if (menuMapper.selectById(updateReqVO.getId()) == null) {
            throw exception(ErrorCodeConstants.MENU_NOT_EXISTS);
        }
        // Check if the parent menu exists
        validateParentMenu(updateReqVO.getParentId(), updateReqVO.getId());
        // Verify menu（Myself）
        validateMenu(updateReqVO.getParentId(), updateReqVO.getName(), updateReqVO.getId());

        // Update to database
        MenuDO updateObj = BeanUtils.toBean(updateReqVO, MenuDO.class);
        initMenuProperty(updateObj);
        menuMapper.updateById(updateObj);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(value = RedisKeyConstants.PERMISSION_MENU_ID_LIST,
            allEntries = true) // allEntries Clear all caches，Because I don't know at this time id Corresponding permission How much is it。Clean directly，Simple and effective
    public void deleteMenu(Long id) {
        // Check if there is a submenu
        if (menuMapper.selectCountByParentId(id) > 0) {
            throw exception(ErrorCodeConstants.MENU_EXISTS_CHILDREN);
        }
        // Check if the deleted menu exists
        if (menuMapper.selectById(id) == null) {
            throw exception(ErrorCodeConstants.MENU_NOT_EXISTS);
        }
        // Mark for deletion
        menuMapper.deleteById(id);
        // Delete the permissions granted to the role
        permissionService.processMenuDeleted(id);
    }

    @Override
    public List<MenuDO> getMenuList() {
        return menuMapper.selectList();
    }

    @Override
    public List<MenuDO> getMenuListByTenant(MenuListReqVO reqVO) {
        List<MenuDO> menus = getMenuList(reqVO);
        // When multi-tenancy is enabled，Need to filter out unopened menus
        tenantService.handleTenantMenu(menuIds -> menus.removeIf(menu -> !CollUtil.contains(menuIds, menu.getId())));
        return menus;
    }

    @Override
    public List<MenuDO> getMenuList(MenuListReqVO reqVO) {
        return menuMapper.selectList(reqVO);
    }

    @Override
    @Cacheable(value = RedisKeyConstants.PERMISSION_MENU_ID_LIST, key = "#permission")
    public List<Long> getMenuIdListByPermissionFromCache(String permission) {
        List<MenuDO> menus = menuMapper.selectListByPermission(permission);
        return CollectionUtils.convertList(menus, MenuDO::getId);
    }

    @Override
    public MenuDO getMenu(Long id) {
        return menuMapper.selectById(id);
    }

    @Override
    public List<MenuDO> getMenuList(Collection<Long> ids) {
        return menuMapper.selectBatchIds(ids);
    }

    /**
     * Check whether the parent menu is legal
     * <p>
     * 1. Cannot set itself as parent menu
     * 2. The parent menu does not exist
     * 3. The parent menu must be {@link MenuTypeEnum#MENU} Menu Type
     *
     * @param parentId Parent menu number
     * @param childId  Current menu number
     */
    @VisibleForTesting
    void validateParentMenu(Long parentId, Long childId) {
        if (parentId == null || ID_ROOT.equals(parentId)) {
            return;
        }
        // Cannot set itself as parent menu
        if (parentId.equals(childId)) {
            throw exception(MENU_PARENT_ERROR);
        }
        MenuDO menu = menuMapper.selectById(parentId);
        // The parent menu does not exist
        if (menu == null) {
            throw exception(ErrorCodeConstants.MENU_PARENT_NOT_EXISTS);
        }
        // The parent menu must be a directory or menu type
        if (!MenuTypeEnum.DIR.getType().equals(menu.getType())
                && !MenuTypeEnum.MENU.getType().equals(menu.getType())) {
            throw exception(ErrorCodeConstants.MENU_PARENT_NOT_DIR_OR_MENU);
        }
    }

    /**
     * Check if the menu is legal
     * <p>
     * 1. Check for the same parent menu number，Does the same menu name exist?
     *
     * @param name     Menu name
     * @param parentId Parent menu number
     * @param id       Menu number
     */
    @VisibleForTesting
    void validateMenu(Long parentId, String name, Long id) {
        MenuDO menu = menuMapper.selectByParentIdAndName(parentId, name);
        if (menu == null) {
            return;
        }
        // If id Empty，Indicates that there is no need to compare whether they are the same id Menu
        if (id == null) {
            throw exception(ErrorCodeConstants.MENU_NAME_DUPLICATE);
        }
        if (!menu.getId().equals(id)) {
            throw exception(ErrorCodeConstants.MENU_NAME_DUPLICATE);
        }
    }

    /**
     * Initialize the general properties of the menu。
     * <p>
     * For example，Menus of type directory or menu only，Just set icon
     *
     * @param menu Menu
     */
    private void initMenuProperty(MenuDO menu) {
        // When the menu is a button type，No need component、icon、path Properties，Set to zero
        if (MenuTypeEnum.BUTTON.getType().equals(menu.getType())) {
            menu.setComponent("");
            menu.setComponentName("");
            menu.setIcon("");
            menu.setPath("");
        }
    }

}
