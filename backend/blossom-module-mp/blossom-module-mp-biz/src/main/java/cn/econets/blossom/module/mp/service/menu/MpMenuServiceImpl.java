package cn.econets.blossom.module.mp.service.menu;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import cn.econets.blossom.module.mp.controller.admin.menu.vo.MpMenuSaveReqVO;
import cn.econets.blossom.module.mp.convert.menu.MpMenuConvert;
import cn.econets.blossom.module.mp.dal.dataobject.account.MpAccountDO;
import cn.econets.blossom.module.mp.dal.dataobject.menu.MpMenuDO;
import cn.econets.blossom.module.mp.dal.mysql.menu.MpMenuMapper;
import cn.econets.blossom.module.mp.framework.mp.core.MpServiceFactory;
import cn.econets.blossom.module.mp.framework.mp.core.util.MpUtils;
import cn.econets.blossom.module.mp.service.account.MpAccountService;
import cn.econets.blossom.module.mp.service.message.MpMessageService;
import cn.econets.blossom.module.mp.service.message.bo.MpMessageSendOutReqBO;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.bean.menu.WxMenu;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import javax.validation.Validator;
import java.util.List;

import static cn.econets.blossom.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.econets.blossom.module.mp.enums.ErrorCodeConstants.MENU_DELETE_FAIL;
import static cn.econets.blossom.module.mp.enums.ErrorCodeConstants.MENU_SAVE_FAIL;

/**
 * Official Account Menu Service Implementation class
 *
 *
 */
@Service
@Validated
@Slf4j
public class MpMenuServiceImpl implements MpMenuService {

    @Resource
    private MpMessageService mpMessageService;
    @Resource
    @Lazy // Delayed loading，Avoid circular reference errors
    private MpAccountService mpAccountService;

    @Resource
    @Lazy // Delayed loading，Avoid circular reference errors
    private MpServiceFactory mpServiceFactory;

    @Resource
    private Validator validator;

    @Resource
    private MpMenuMapper mpMenuMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveMenu(MpMenuSaveReqVO createReqVO) {
        MpAccountDO account = mpAccountService.getRequiredAccount(createReqVO.getAccountId());
        WxMpService mpService = mpServiceFactory.getRequiredMpService(createReqVO.getAccountId());

        // Parameter verification
        createReqVO.getMenus().forEach(this::validateMenu);

        // First step，Synchronize public account
        WxMenu wxMenu = new WxMenu();
        wxMenu.setButtons(MpMenuConvert.INSTANCE.convert(createReqVO.getMenus()));
        try {
            mpService.getMenuService().menuCreate(wxMenu);
        } catch (WxErrorException e) {
            throw exception(MENU_SAVE_FAIL, e.getError().getErrorMsg());
        }

        // Step 2，Store in database
        mpMenuMapper.deleteByAccountId(createReqVO.getAccountId());
        createReqVO.getMenus().forEach(menu -> {
            // Save the top menu first
            MpMenuDO menuDO = createMenu(menu, null, account);
            // Save the submenu again
            if (CollUtil.isEmpty(menu.getChildren())) {
                return;
            }
            menu.getChildren().forEach(childMenu -> createMenu(childMenu, menuDO, account));
        });
    }

    /**
     * Check if the menu format is correct
     *
     * @param menu Menu
     */
    private void validateMenu(MpMenuSaveReqVO.Menu menu) {
        MpUtils.validateButton(validator, menu.getType(), menu.getReplyMessageType(), menu);
        // Submenu
        if (CollUtil.isEmpty(menu.getChildren())) {
            return;
        }
        menu.getChildren().forEach(this::validateMenu);
    }

    /**
     * Create menu，and store in the database
     *
     * @param wxMenu Menu information
     * @param parentMenu Parent Menu
     * @param account Public Account
     * @return Menu after creation
     */
    private MpMenuDO createMenu(MpMenuSaveReqVO.Menu wxMenu, MpMenuDO parentMenu, MpAccountDO account) {
        // Create menu
        MpMenuDO menu = CollUtil.isNotEmpty(wxMenu.getChildren())
                ? new MpMenuDO().setName(wxMenu.getName())
                : MpMenuConvert.INSTANCE.convert02(wxMenu);
        // Set the public account information of the menu
        if (account != null) {
            menu.setAccountId(account.getId()).setAppId(account.getAppId());
        }
        // Set parent number
        if (parentMenu != null) {
            menu.setParentId(parentMenu.getId());
        } else {
            menu.setParentId(MpMenuDO.ID_ROOT);
        }

        // Insert into database
        mpMenuMapper.insert(menu);
        return menu;
    }

    @Override
    public void deleteMenuByAccountId(Long accountId) {
        WxMpService mpService = mpServiceFactory.getRequiredMpService(accountId);
        // First step，Synchronize public account
        try {
            mpService.getMenuService().menuDelete();
        } catch (WxErrorException e) {
            throw exception(MENU_DELETE_FAIL, e.getError().getErrorMsg());
        }

        // Step 2，Store in database
        mpMenuMapper.deleteByAccountId(accountId);
    }

    @Override
    public WxMpXmlOutMessage reply(String appId, String key, String openid) {
        // First step，Get menu
        MpMenuDO menu = mpMenuMapper.selectByAppIdAndMenuKey(appId, key);
        if (menu == null) {
            log.error("[reply][appId({}) key({}) Cannot find the corresponding menu]", appId, key);
            return null;
        }
        // Buttons must have a message type，Otherwise you won't be able to reply to messages later
        if (StrUtil.isEmpty(menu.getReplyMessageType())) {
            log.error("[reply][menu({}) There is no corresponding message type]", menu);
            return null;
        }

        // Step 2，Reply message
        MpMessageSendOutReqBO sendReqBO = MpMenuConvert.INSTANCE.convert(openid, menu);
        return mpMessageService.sendOutMessage(sendReqBO);
    }

    @Override
    public List<MpMenuDO> getMenuListByAccountId(Long accountId) {
        return mpMenuMapper.selectListByAccountId(accountId);
    }

}
