package cn.econets.blossom.module.promotion.service.banner;

import cn.econets.blossom.framework.common.pojo.PageResult;
import cn.econets.blossom.module.promotion.controller.admin.banner.vo.BannerCreateReqVO;
import cn.econets.blossom.module.promotion.controller.admin.banner.vo.BannerPageReqVO;
import cn.econets.blossom.module.promotion.controller.admin.banner.vo.BannerUpdateReqVO;
import cn.econets.blossom.module.promotion.convert.banner.BannerConvert;
import cn.econets.blossom.module.promotion.dal.dataobject.banner.BannerDO;
import cn.econets.blossom.module.promotion.dal.mysql.banner.BannerMapper;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.util.List;

import static cn.econets.blossom.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.econets.blossom.module.promotion.enums.ErrorCodeConstants.BANNER_NOT_EXISTS;

/**
 * Home banner Implementation class
 *
 */
@Service
@Validated
public class BannerServiceImpl implements BannerService {

    @Resource
    private BannerMapper bannerMapper;

    @Override
    public Long createBanner(BannerCreateReqVO createReqVO) {
        // Insert
        BannerDO banner = BannerConvert.INSTANCE.convert(createReqVO);
        bannerMapper.insert(banner);
        // Return
        return banner.getId();
    }

    @Override
    public void updateBanner(BannerUpdateReqVO updateReqVO) {
        // Check existence
        this.validateBannerExists(updateReqVO.getId());
        // Update
        BannerDO updateObj = BannerConvert.INSTANCE.convert(updateReqVO);
        bannerMapper.updateById(updateObj);
    }

    @Override
    public void deleteBanner(Long id) {
        // Verify existence
        this.validateBannerExists(id);
        // Delete
        bannerMapper.deleteById(id);
    }

    private void validateBannerExists(Long id) {
        if (bannerMapper.selectById(id) == null) {
            throw exception(BANNER_NOT_EXISTS);
        }
    }

    @Override
    public BannerDO getBanner(Long id) {
        return bannerMapper.selectById(id);
    }

    @Override
    public PageResult<BannerDO> getBannerPage(BannerPageReqVO pageReqVO) {
        return bannerMapper.selectPage(pageReqVO);
    }

    @Override
    public void addBannerBrowseCount(Long id) {
        // Verification Banner Does it exist?
        validateBannerExists(id);
        // Increase click count
        bannerMapper.updateBrowseCount(id);
    }

    @Override
    public List<BannerDO> getBannerListByPosition(Integer position) {
        return bannerMapper.selectBannerListByPosition(position);
    }

}
