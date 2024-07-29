package cn.econets.blossom.module.promotion.service.banner;

import cn.econets.blossom.framework.common.pojo.PageResult;
import cn.econets.blossom.module.promotion.controller.admin.banner.vo.BannerCreateReqVO;
import cn.econets.blossom.module.promotion.controller.admin.banner.vo.BannerPageReqVO;
import cn.econets.blossom.module.promotion.controller.admin.banner.vo.BannerUpdateReqVO;
import cn.econets.blossom.module.promotion.dal.dataobject.banner.BannerDO;

import javax.validation.Valid;
import java.util.List;

/**
 * Home Banner Service Interface
 *
 */
public interface BannerService {

    /**
     * Create Banner
     *
     * @param createReqVO Create information
     * @return Number
     */
    Long createBanner(@Valid BannerCreateReqVO createReqVO);

    /**
     * Update Banner
     *
     * @param updateReqVO Update information
     */
    void updateBanner(@Valid BannerUpdateReqVO updateReqVO);

    /**
     * Delete Banner
     *
     * @param id Number
     */
    void deleteBanner(Long id);

    /**
     * Get Banner
     *
     * @param id Number
     * @return Banner
     */
    BannerDO getBanner(Long id);

    /**
     * Get Banner Pagination
     *
     * @param pageReqVO Paged query
     * @return BannerPagination
     */
    PageResult<BannerDO> getBannerPage(BannerPageReqVO pageReqVO);

    /**
     * Increase Banner Clicks
     *
     * @param id BannerNumber
     */
    void addBannerBrowseCount(Long id);

    /**
     * Get Banner List
     *
     * @param position Positioning
     * @return Banner List
     */
    List<BannerDO> getBannerListByPosition(Integer position);

}
