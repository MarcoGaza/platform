package cn.econets.blossom.module.promotion.service.bargain;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.ObjUtil;
import cn.econets.blossom.framework.common.pojo.PageResult;
import cn.econets.blossom.module.promotion.controller.admin.bargain.vo.help.BargainHelpPageReqVO;
import cn.econets.blossom.module.promotion.controller.app.bargain.vo.help.AppBargainHelpCreateReqVO;
import cn.econets.blossom.module.promotion.dal.dataobject.bargain.BargainActivityDO;
import cn.econets.blossom.module.promotion.dal.dataobject.bargain.BargainHelpDO;
import cn.econets.blossom.module.promotion.dal.dataobject.bargain.BargainRecordDO;
import cn.econets.blossom.module.promotion.dal.mysql.bargain.BargainHelpMapper;
import cn.econets.blossom.module.promotion.enums.bargain.BargainRecordStatusEnum;
import jodd.util.MathUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import static cn.econets.blossom.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.econets.blossom.module.promotion.enums.ErrorCodeConstants.*;

/**
 * Bargaining assistance Service Implementation class
 *
 */
@Service
@Validated
public class BargainHelpServiceImpl implements BargainHelpService {

    @Resource
    private BargainHelpMapper bargainHelpMapper;

    @Resource
    private BargainRecordService bargainRecordService;
    @Resource
    private BargainActivityService bargainActivityService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BargainHelpDO createBargainHelp(Long userId, AppBargainHelpCreateReqVO reqVO) {
        // 1.1 Check if bargaining record exists，and in progress
        BargainRecordDO record = bargainRecordService.getBargainRecord(reqVO.getRecordId());
        if (record == null) {
            throw exception(BARGAIN_RECORD_NOT_EXISTS);
        }
        if (ObjUtil.notEqual(record.getStatus(), BargainRecordStatusEnum.IN_PROGRESS.getStatus())) {
            throw exception(BARGAIN_HELP_CREATE_FAIL_RECORD_NOT_IN_PROCESS);
        }
        // 1.2 You can't bargain for yourself
        if (ObjUtil.equal(record.getUserId(), userId)) {
            throw exception(BARGAIN_HELP_CREATE_FAIL_RECORD_SELF);
        }

        // 2.1 Check the bargaining activity
        BargainActivityDO activity = bargainActivityService.getBargainActivity(record.getActivityId());
        // 2.2 Check whether you have reached the upper limit of the number of assists
        if (bargainHelpMapper.selectCountByUserIdAndActivityId(userId, activity.getId())
                >= activity.getBargainCount()) {
            throw exception(BARGAIN_HELP_CREATE_FAIL_LIMIT);
        }
        // 2.3 Special circumstances：The price has been bargained down to the lowest price，Can't cut anymore
        if (record.getBargainPrice() <= activity.getBargainMinPrice()) {
            throw exception(BARGAIN_HELP_CREATE_FAIL_RECORD_NOT_IN_PROCESS);
        }

        // 3. Have helped
        if (bargainHelpMapper.selectByUserIdAndRecordId(userId, record.getId()) != null) {
            throw exception(BARGAIN_HELP_CREATE_FAIL_HELP_EXISTS);
        }

        // 4.1 Calculate the bargaining amount
        Integer reducePrice = calculateReducePrice(activity, record);
        Assert.isTrue(reducePrice > 0, "The bargaining amount must be greater than 0 Yuan");
        // 4.2 Create a support record
        BargainHelpDO help = BargainHelpDO.builder().userId(userId).activityId(activity.getId())
                .recordId(record.getId()).reducePrice(reducePrice).build();
        bargainHelpMapper.insert(help);

        // 5. Judge whether the bargaining record is completed
        Boolean success = record.getBargainPrice() - reducePrice <= activity.getBargainMinPrice() // Situation 1：The price has been bargained down to the lowest price
                || bargainHelpMapper.selectUserCountMapByRecordId(reqVO.getRecordId()) >= activity.getHelpMaxCount(); // Situation 2：The bargaining power has reached the upper limit
        if (!bargainRecordService.updateBargainRecordBargainPrice(
                record.getId(), record.getBargainPrice(), reducePrice, success)) {
            // Many people bargain together，Need to retry
            throw exception(BARGAIN_HELP_CREATE_FAIL_CONFLICT);
        }
        return help;
    }

    // TODO Optimization point：Implement a more random logic，You can follow your own business；
    private Integer calculateReducePrice(BargainActivityDO activity, BargainRecordDO record) {
        // 1. Random amount
        Integer reducePrice = MathUtil.randomInt(activity.getBargainMinPrice(),
                activity.getRandomMaxPrice() + 1); // + 1 The reason is，randomInt The second parameter is not included by default
        // 2. Check whether the price exceeds the upper limit
        if (record.getBargainPrice() - reducePrice < activity.getBargainMinPrice()) {
            reducePrice = record.getBargainPrice() - activity.getBargainMinPrice();
        }
        return reducePrice;
    }

    @Override
    public Map<Long, Integer> getBargainHelpUserCountMapByActivity(Collection<Long> activityIds) {
        return bargainHelpMapper.selectUserCountMapByActivityId(activityIds);
    }

    @Override
    public Map<Long, Integer> getBargainHelpUserCountMapByRecord(Collection<Long> recordIds) {
        return bargainHelpMapper.selectUserCountMapByRecordId(recordIds);
    }

    @Override
    public Long getBargainHelpCountByActivity(Long activityId, Long userId) {
        return bargainHelpMapper.selectCountByUserIdAndActivityId(userId, activityId);
    }

    @Override
    public PageResult<BargainHelpDO> getBargainHelpPage(BargainHelpPageReqVO pageReqVO) {
        return bargainHelpMapper.selectPage(pageReqVO);
    }

    @Override
    public List<BargainHelpDO> getBargainHelpListByRecordId(Long recordId) {
        return bargainHelpMapper.selectListByRecordId(recordId);
    }

    @Override
    public BargainHelpDO getBargainHelp(Long recordId, Long userId) {
        return bargainHelpMapper.selectByUserIdAndRecordId(userId, recordId);
    }

}
