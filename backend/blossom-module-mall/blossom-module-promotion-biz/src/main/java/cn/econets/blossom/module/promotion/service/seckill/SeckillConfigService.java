package cn.econets.blossom.module.promotion.service.seckill;

import cn.econets.blossom.framework.common.pojo.PageResult;
import cn.econets.blossom.module.promotion.controller.admin.seckill.vo.config.SeckillConfigCreateReqVO;
import cn.econets.blossom.module.promotion.controller.admin.seckill.vo.config.SeckillConfigPageReqVO;
import cn.econets.blossom.module.promotion.controller.admin.seckill.vo.config.SeckillConfigUpdateReqVO;
import cn.econets.blossom.module.promotion.dal.dataobject.seckill.SeckillConfigDO;

import javax.validation.Valid;
import java.util.Collection;
import java.util.List;

/**
 * Second sale period Service Interface
 *
 */
public interface SeckillConfigService {

    /**
     * Create a flash sale period
     *
     * @param createReqVO Create information
     * @return Number
     */
    Long createSeckillConfig(@Valid SeckillConfigCreateReqVO createReqVO);

    /**
     * Update flash sale period
     *
     * @param updateReqVO Update information
     */
    void updateSeckillConfig(@Valid SeckillConfigUpdateReqVO updateReqVO);

    /**
     * Delete flash sale period
     *
     * @param id Number
     */
    void deleteSeckillConfig(Long id);

    /**
     * Get the flash sale time
     *
     * @param id Number
     * @return Second sale period
     */
    SeckillConfigDO getSeckillConfig(Long id);

    /**
     * Get a list of all flash sale periods
     *
     * @return List of all flash sale periods
     */
    List<SeckillConfigDO> getSeckillConfigList();

    /**
     * Check if the flash sale period exists
     *
     * @param ids Second sale period id Collection
     */
    void validateSeckillConfigExists(Collection<Long> ids);

    /**
     * Get the paging data for flash sale time period configuration
     *
     * @param pageVO Paging request parameters
     * @return Second sale period page list
     */
    PageResult<SeckillConfigDO> getSeckillConfigPage(SeckillConfigPageReqVO pageVO);

    /**
     * Get a list of all normal time slot configurations
     *
     * @param status Status
     * @return Second sale time list
     */
    List<SeckillConfigDO> getSeckillConfigListByStatus(Integer status);

    /**
     * Update the flash sale period configuration status
     *
     * @param id     id
     * @param status Status
     */
    void updateSeckillConfigStatus(Long id, Integer status);

    /**
     * Get the current flash sale period
     *
     * Requires to be turned on、and within the current time period
     *
     * @return Time period
     */
    SeckillConfigDO getCurrentSeckillConfig();

}
