package cn.econets.blossom.module.system.convert.auth;

import cn.econets.blossom.framework.common.util.collection.CollectionUtils;
import cn.econets.blossom.module.system.api.sms.dto.code.SmsCodeSendReqDTO;
import cn.econets.blossom.module.system.api.sms.dto.code.SmsCodeUseReqDTO;
import cn.econets.blossom.module.system.api.social.dto.SocialUserBindReqDTO;
import cn.econets.blossom.module.system.controller.admin.auth.vo.*;
import cn.econets.blossom.module.system.dal.dataobject.oauth2.OAuth2AccessTokenDO;
import cn.econets.blossom.module.system.dal.dataobject.permission.MenuDO;
import cn.econets.blossom.module.system.dal.dataobject.permission.RoleDO;
import cn.econets.blossom.module.system.dal.dataobject.user.AdminUserDO;
import cn.econets.blossom.module.system.enums.permission.MenuTypeEnum;
import cn.hutool.core.collection.CollUtil;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.slf4j.LoggerFactory;

import java.util.*;

@Mapper
public interface AuthConvert {

    AuthConvert INSTANCE = Mappers.getMapper(AuthConvert.class);

    AuthLoginRespVO convert(OAuth2AccessTokenDO bean);

    default AuthPermissionInfoRespVO convert(AdminUserDO user, List<RoleDO> roleList, List<MenuDO> menuList) {
        return AuthPermissionInfoRespVO.builder()
                .user(AuthPermissionInfoRespVO.UserVO.builder().id(user.getId()).nickname(user.getNickname()).avatar(user.getAvatar()).build())
                .roles(CollectionUtils.convertSet(roleList, RoleDO::getCode))
                // Permission identification information
                .permissions(CollectionUtils.convertSet(menuList, MenuDO::getPermission))
                // Menu Tree
                .menus(buildMenuTree(menuList))
                .build();
    }

    AuthPermissionInfoRespVO.MenuVO convertTreeNode(MenuDO menu);

    /**
     * Menu list，Build into menu tree
     *
     * @param menuList Menu List
     * @return Menu Tree
     */
    default List<AuthPermissionInfoRespVO.MenuVO> buildMenuTree(List<MenuDO> menuList) {
        if (CollUtil.isEmpty(menuList)) {
            return Collections.emptyList();
        }
        // Remove button
        menuList.removeIf(menu -> menu.getType().equals(MenuTypeEnum.BUTTON.getType()));
        // Sort，Ensure the order of the menu
        menuList.sort(Comparator.comparing(MenuDO::getSort));

        // Build menu tree
        // Use LinkedHashMap Reason，It is for sorting 。It can actually be used Stream API ，It's just too ugly。
        Map<Long, AuthPermissionInfoRespVO.MenuVO> treeNodeMap = new LinkedHashMap<>();
        menuList.forEach(menu -> treeNodeMap.put(menu.getId(), AuthConvert.INSTANCE.convertTreeNode(menu)));
        // Handling the father-son relationship
        treeNodeMap.values().stream().filter(node -> !node.getParentId().equals(MenuDO.ID_ROOT)).forEach(childNode -> {
            // Get the parent node
            AuthPermissionInfoRespVO.MenuVO parentNode = treeNodeMap.get(childNode.getParentId());
            if (parentNode == null) {
                LoggerFactory.getLogger(getClass()).error("[buildRouterTree][resource({}) Parent resource not found({})]",
                        childNode.getId(), childNode.getParentId());
                return;
            }
            // Add yourself to the parent node
            if (parentNode.getChildren() == null) {
                parentNode.setChildren(new ArrayList<>());
            }
            parentNode.getChildren().add(childNode);
        });
        // Get all root nodes
        return CollectionUtils.filterList(treeNodeMap.values(), node -> MenuDO.ID_ROOT.equals(node.getParentId()));
    }

    SocialUserBindReqDTO convert(Long userId, Integer userType, AuthSocialLoginReqVO reqVO);

    SmsCodeSendReqDTO convert(AuthSmsSendReqVO reqVO);

    SmsCodeUseReqDTO convert(AuthSmsLoginReqVO reqVO, Integer scene, String usedIp);

}
