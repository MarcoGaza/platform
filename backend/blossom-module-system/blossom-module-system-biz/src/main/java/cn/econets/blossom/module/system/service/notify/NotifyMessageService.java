package cn.econets.blossom.module.system.service.notify;

import cn.econets.blossom.framework.common.pojo.PageResult;
import cn.econets.blossom.module.system.controller.admin.notify.vo.message.NotifyMessageMyPageReqVO;
import cn.econets.blossom.module.system.controller.admin.notify.vo.message.NotifyMessagePageReqVO;
import cn.econets.blossom.module.system.dal.dataobject.notify.NotifyMessageDO;
import cn.econets.blossom.module.system.dal.dataobject.notify.NotifyTemplateDO;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * Internal message Service Interface
 *
 */
public interface NotifyMessageService {

    /**
     * Create a private message
     *
     * @param userId User Number
     * @param userType User Type
     * @param template Template information
     * @param templateContent Template content
     * @param templateParams Template parameters
     * @return Internal message number
     */
    Long createNotifyMessage(Long userId, Integer userType,
                             NotifyTemplateDO template, String templateContent, Map<String, Object> templateParams);

    /**
     * Get the internal message page
     *
     * @param pageReqVO Paged query
     * @return Internal message paging
     */
    PageResult<NotifyMessageDO> getNotifyMessagePage(NotifyMessagePageReqVO pageReqVO);

    /**
     * Get【My】Paging of internal messages
     *
     * @param pageReqVO Paged query
     * @param userId User Number
     * @param userType User Type
     * @return Paging of internal messages
     */
    PageResult<NotifyMessageDO> getMyMyNotifyMessagePage(NotifyMessageMyPageReqVO pageReqVO, Long userId, Integer userType);

    /**
     * Get the internal message
     *
     * @param id Number
     * @return Internal message
     */
    NotifyMessageDO getNotifyMessage(Long id);

    /**
     * Get【My】Unread internal message list
     *
     * @param userId   User Number
     * @param userType User Type
     * @param size     Quantity
     * @return List of internal messages
     */
    List<NotifyMessageDO> getUnreadNotifyMessageList(Long userId, Integer userType, Integer size);

    /**
     * Count the number of unread messages on the site by users
     *
     * @param userId   User Number
     * @param userType User Type
     * @return Return the number of unread messages on the site
     */
    Long getUnreadNotifyMessageCount(Long userId, Integer userType);

    /**
     * Mark the message as read
     *
     * @param ids    Internal message number collection
     * @param userId User Number
     * @param userType User Type
     * @return The number of updated records
     */
    int updateNotifyMessageRead(Collection<Long> ids, Long userId, Integer userType);

    /**
     * Mark all internal messages as read
     *
     * @param userId   User Number
     * @param userType User Type
     * @return The number of updated items
     */
    int updateAllNotifyMessageRead(Long userId, Integer userType);

}
