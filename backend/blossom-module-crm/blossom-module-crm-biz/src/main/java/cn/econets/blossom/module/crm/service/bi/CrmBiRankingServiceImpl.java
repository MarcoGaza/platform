package cn.econets.blossom.module.crm.service.bi;

import cn.hutool.core.collection.CollUtil;
import cn.econets.blossom.framework.common.util.collection.MapUtils;
import cn.econets.blossom.module.crm.controller.admin.bi.vo.CrmBiRanKRespVO;
import cn.econets.blossom.module.crm.controller.admin.bi.vo.CrmBiRankReqVO;
import cn.econets.blossom.module.crm.dal.mysql.bi.CrmBiRankingMapper;
import cn.econets.blossom.module.system.api.dept.DeptApi;
import cn.econets.blossom.module.system.api.dept.dto.DeptRespDTO;
import cn.econets.blossom.module.system.api.user.AdminUserApi;
import cn.econets.blossom.module.system.api.user.dto.AdminUserRespDTO;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import static cn.econets.blossom.framework.common.util.collection.CollectionUtils.convertList;
import static cn.econets.blossom.framework.common.util.collection.CollectionUtils.convertSet;

/**
 * CRM BI Rankings Service Implementation class
 *
 */
@Service
@Validated
public class CrmBiRankingServiceImpl implements CrmBiRankingService {

    @Resource
    private CrmBiRankingMapper biRankingMapper;

    @Resource
    private AdminUserApi adminUserApi;
    @Resource
    private DeptApi deptApi;

    @Override
    public List<CrmBiRanKRespVO> getContractPriceRank(CrmBiRankReqVO rankReqVO) {
        return getRank(rankReqVO, biRankingMapper::selectContractPriceRank);
    }

    @Override
    public List<CrmBiRanKRespVO> getReceivablePriceRank(CrmBiRankReqVO rankReqVO) {
        return getRank(rankReqVO, biRankingMapper::selectReceivablePriceRank);
    }

    @Override
    public List<CrmBiRanKRespVO> getContractCountRank(CrmBiRankReqVO rankReqVO) {
        return getRank(rankReqVO, biRankingMapper::selectContractCountRank);
    }

    @Override
    public List<CrmBiRanKRespVO> getProductSalesRank(CrmBiRankReqVO rankReqVO) {
        return getRank(rankReqVO, biRankingMapper::selectProductSalesRank);
    }

    @Override
    public List<CrmBiRanKRespVO> getCustomerCountRank(CrmBiRankReqVO rankReqVO) {
        return getRank(rankReqVO, biRankingMapper::selectCustomerCountRank);
    }

    @Override
    public List<CrmBiRanKRespVO> getContactsCountRank(CrmBiRankReqVO rankReqVO) {
        return getRank(rankReqVO, biRankingMapper::selectContactsCountRank);
    }

    @Override
    public List<CrmBiRanKRespVO> getFollowCountRank(CrmBiRankReqVO rankReqVO) {
        return getRank(rankReqVO, biRankingMapper::selectFollowCountRank);
    }

    @Override
    public List<CrmBiRanKRespVO> getFollowCustomerCountRank(CrmBiRankReqVO rankReqVO) {
        return getRank(rankReqVO, biRankingMapper::selectFollowCustomerCountRank);
    }

    /**
     * Get ranking data
     *
     * @param rankReqVO  Parameters
     * @param rankFunction Ranking method
     * @return Ranking data
     */
    private List<CrmBiRanKRespVO> getRank(CrmBiRankReqVO rankReqVO, Function<CrmBiRankReqVO, List<CrmBiRanKRespVO>> rankFunction) {
        // 1. Get user number array
        rankReqVO.setUserIds(getUserIds(rankReqVO.getDeptId()));
        if (CollUtil.isEmpty(rankReqVO.getUserIds())) {
            return Collections.emptyList();
        }
        // 2. Get ranking data
        List<CrmBiRanKRespVO> ranks = rankFunction.apply(rankReqVO);
        if (CollUtil.isEmpty(ranks)) {
            return Collections.emptyList();
        }
        ranks.sort(Comparator.comparing(CrmBiRanKRespVO::getCount).reversed());
        // 3. Join user information
        appendUserInfo(ranks);
        return ranks;
    }

    /**
     * Splicing user information（Nickname、Department）
     *
     * @param ranks Ranking data
     */
    private void appendUserInfo(List<CrmBiRanKRespVO> ranks) {
        Map<Long, AdminUserRespDTO> userMap = adminUserApi.getUserMap(convertSet(ranks, CrmBiRanKRespVO::getOwnerUserId));
        Map<Long, DeptRespDTO> deptMap = deptApi.getDeptMap(convertSet(userMap.values(), AdminUserRespDTO::getDeptId));
        ranks.forEach(rank -> MapUtils.findAndThen(userMap, rank.getOwnerUserId(), user -> {
            rank.setNickname(user.getNickname());
            MapUtils.findAndThen(deptMap, user.getDeptId(), dept -> rank.setDeptName(dept.getName()));
        }));
    }

    /**
     * Get the user ID array under the department，Including sub-departments
     *
     * @param deptId Department Number
     * @return User ID array
     */
    public List<Long> getUserIds(Long deptId) {
        // 1. Get department list
        List<Long> deptIds = convertList(deptApi.getChildDeptList(deptId), DeptRespDTO::getId);
        deptIds.add(deptId);
        // 2. Get user ID
        return convertList(adminUserApi.getUserListByDeptIds(deptIds), AdminUserRespDTO::getId);
    }

}
