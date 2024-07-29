package cn.econets.blossom.module.system.service.permission;


import cn.econets.blossom.module.system.controller.admin.permission.vo.menu.MenuListReqVO;
import cn.econets.blossom.module.system.controller.admin.permission.vo.menu.MenuSaveVO;
import cn.econets.blossom.module.system.dal.dataobject.permission.MenuDO;

import java.util.Collection;
import java.util.List;

/**
 * Menu Service Interface
 *
 */
public interface MenuService {

    /**
     * Create menu
     *
     * @param createReqVO Menu information
     * @return The menu number created
     */
    Long createMenu(MenuSaveVO createReqVO);

    /**
     * Update menu
     *
     * @param updateReqVO Menu information
     */
    void updateMenu(MenuSaveVO updateReqVO);

    /**
     * Delete menu
     *
     * @param id Menu number
     */
    void deleteMenu(Long id);

    /**
     * Get all menu lists
     *
     * @return Menu List
     */
    List<MenuDO> getMenuList();

    /**
     * Tenant-based，Filter menu list
     * Attention，If it is a system tenant，The full menu is returned
     *
     * @param reqVO Filter condition request VO
     * @return Menu List
     */
    List<MenuDO> getMenuListByTenant(MenuListReqVO reqVO);

    /**
     * Filter menu list
     *
     * @param reqVO Filter condition request VO
     * @return Menu list
     */
    List<MenuDO> getMenuList(MenuListReqVO reqVO);

    /**
     * Get the menu number array corresponding to the permission
     *
     * @param permission Permission flag
     * @return Array
     */
    List<Long> getMenuIdListByPermissionFromCache(String permission);

    /**
     * Get menu
     *
     * @param id Menu number
     * @return Menu
     */
    MenuDO getMenu(Long id);

    /**
     * Get menu array
     *
     * @param ids Menu number array
     * @return Menu array
     */
    List<MenuDO> getMenuList(Collection<Long> ids);

}
