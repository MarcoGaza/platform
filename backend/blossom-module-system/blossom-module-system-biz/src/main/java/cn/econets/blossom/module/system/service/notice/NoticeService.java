package cn.econets.blossom.module.system.service.notice;

import cn.econets.blossom.framework.common.pojo.PageResult;
import cn.econets.blossom.module.system.controller.admin.notice.vo.NoticePageReqVO;
import cn.econets.blossom.module.system.controller.admin.notice.vo.NoticeSaveReqVO;
import cn.econets.blossom.module.system.dal.dataobject.notice.NoticeDO;

/**
 * Notices and Announcements Service Interface
 */
public interface NoticeService {

    /**
     * Create a notification announcement
     *
     * @param createReqVO Notices and Announcements
     * @return Number
     */
    Long createNotice(NoticeSaveReqVO createReqVO);

    /**
     * Update Notice
     *
     * @param reqVO Notices and Announcements
     */
    void updateNotice(NoticeSaveReqVO reqVO);

    /**
     * Delete notification announcement
     *
     * @param id Number
     */
    void deleteNotice(Long id);

    /**
     * Get the paginated list of notifications and announcements
     *
     * @param reqVO Pagination Conditions
     * @return Department pagination list
     */
    PageResult<NoticeDO> getNoticePage(NoticePageReqVO reqVO);

    /**
     * Get notification announcement
     *
     * @param id Number
     * @return Notices and Announcements
     */
    NoticeDO getNotice(Long id);

}
