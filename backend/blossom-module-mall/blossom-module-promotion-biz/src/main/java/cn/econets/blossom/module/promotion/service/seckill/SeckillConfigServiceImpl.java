package cn.econets.blossom.module.promotion.service.seckill;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.econets.blossom.framework.common.enums.CommonStatusEnum;
import cn.econets.blossom.framework.common.pojo.PageResult;
import cn.econets.blossom.framework.common.util.date.LocalDateTimeUtils;
import cn.econets.blossom.module.promotion.controller.admin.seckill.vo.config.SeckillConfigCreateReqVO;
import cn.econets.blossom.module.promotion.controller.admin.seckill.vo.config.SeckillConfigPageReqVO;
import cn.econets.blossom.module.promotion.controller.admin.seckill.vo.config.SeckillConfigUpdateReqVO;
import cn.econets.blossom.module.promotion.convert.seckill.seckillconfig.SeckillConfigConvert;
import cn.econets.blossom.module.promotion.dal.dataobject.seckill.SeckillConfigDO;
import cn.econets.blossom.module.promotion.dal.mysql.seckill.seckillconfig.SeckillConfigMapper;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.time.LocalTime;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;

import static cn.econets.blossom.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.econets.blossom.framework.common.util.collection.CollectionUtils.findFirst;
import static cn.econets.blossom.framework.common.util.date.LocalDateTimeUtils.isBetween;
import static cn.econets.blossom.module.promotion.enums.ErrorCodeConstants.*;

/**
 * Second sale period Service Implementation class
 *
 */
@Service
@Validated
public class SeckillConfigServiceImpl implements SeckillConfigService {

    @Resource
    private SeckillConfigMapper seckillConfigMapper;

    @Override
    public Long createSeckillConfig(SeckillConfigCreateReqVO createReqVO) {
        // Check whether the time period conflicts
        validateSeckillConfigConflict(createReqVO.getStartTime(), createReqVO.getEndTime(), null);

        // Insert
        SeckillConfigDO seckillConfig = SeckillConfigConvert.INSTANCE.convert(createReqVO);
        seckillConfigMapper.insert(seckillConfig);
        // Return
        return seckillConfig.getId();
    }

    @Override
    public void updateSeckillConfig(SeckillConfigUpdateReqVO updateReqVO) {
        // Check existence
        validateSeckillConfigExists(updateReqVO.getId());
        // Check whether the time period conflicts
        validateSeckillConfigConflict(updateReqVO.getStartTime(), updateReqVO.getEndTime(), updateReqVO.getId());

        // Update
        SeckillConfigDO updateObj = SeckillConfigConvert.INSTANCE.convert(updateReqVO);
        seckillConfigMapper.updateById(updateObj);
    }

    @Override
    public void updateSeckillConfigStatus(Long id, Integer status) {
        // Check if the flash sale period exists
        validateSeckillConfigExists(id);

        // Update status
        seckillConfigMapper.updateById(new SeckillConfigDO().setId(id).setStatus(status));
    }

    @Override
    public SeckillConfigDO getCurrentSeckillConfig() {
        List<SeckillConfigDO> list = seckillConfigMapper.selectList(SeckillConfigDO::getStatus, CommonStatusEnum.ENABLE.getStatus());
        return findFirst(list, config -> isBetween(config.getStartTime(), config.getEndTime()));
    }

    @Override
    public void deleteSeckillConfig(Long id) {
        // Verify existence
        validateSeckillConfigExists(id);

        // Delete
        seckillConfigMapper.deleteById(id);
    }

    private void validateSeckillConfigExists(Long id) {
        if (seckillConfigMapper.selectById(id) == null) {
            throw exception(SECKILL_CONFIG_NOT_EXISTS);
        }
    }

    /**
     * Check whether there is a conflict in time
     *
     * @param startTimeStr Start time
     * @param endTimeStr   End time
     */
    private void validateSeckillConfigConflict(String startTimeStr, String endTimeStr, Long id) {
        // 1. Query all time period configurations
        LocalTime startTime = LocalTime.parse(startTimeStr);
        LocalTime endTime = LocalTime.parse(endTimeStr);
        List<SeckillConfigDO> configs = seckillConfigMapper.selectList();
        // Exclude yourself when updating
        if (id != null) {
            configs.removeIf(item -> ObjectUtil.equal(item.getId(), id));
        }

        // 2. Judge whether there is overlapping time
        boolean hasConflict = configs.stream().anyMatch(config -> LocalDateTimeUtils.isOverlap(startTime, endTime,
                LocalTime.parse(config.getStartTime()), LocalTime.parse(config.getEndTime())));
        if (hasConflict) {
            throw exception(SECKILL_CONFIG_TIME_CONFLICTS);
        }
    }


    @Override
    public SeckillConfigDO getSeckillConfig(Long id) {
        return seckillConfigMapper.selectById(id);
    }

    @Override
    public List<SeckillConfigDO> getSeckillConfigList() {
        return seckillConfigMapper.selectList();
    }

    @Override
    public void validateSeckillConfigExists(Collection<Long> ids) {
        if (CollUtil.isEmpty(ids)) {
            return;
        }
        // 1. If there is a quantity mismatch，Indicates that something does not exist，Throw SECKILL_CONFIG_NOT_EXISTS Business exception
        List<SeckillConfigDO> configs = seckillConfigMapper.selectBatchIds(ids);
        if (configs.size() != ids.size()) {
            throw exception(SECKILL_CONFIG_NOT_EXISTS);
        }

        // 2. Close if exists，Throw SECKILL_CONFIG_DISABLE Business exception
        configs.forEach(config -> {
            if (ObjectUtil.equal(config.getStatus(), CommonStatusEnum.DISABLE.getStatus())) {
                throw exception(SECKILL_CONFIG_DISABLE);
            }
        });
    }

    @Override
    public PageResult<SeckillConfigDO> getSeckillConfigPage(SeckillConfigPageReqVO pageVO) {
        return seckillConfigMapper.selectPage(pageVO);
    }

    @Override
    public List<SeckillConfigDO> getSeckillConfigListByStatus(Integer status) {
        List<SeckillConfigDO> list = seckillConfigMapper.selectListByStatus(status);
        list.sort(Comparator.comparing(SeckillConfigDO::getStartTime));
        return list;
    }

}
