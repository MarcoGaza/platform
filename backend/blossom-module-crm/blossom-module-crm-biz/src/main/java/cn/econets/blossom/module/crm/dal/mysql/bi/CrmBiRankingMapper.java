package cn.econets.blossom.module.crm.dal.mysql.bi;

import cn.econets.blossom.module.crm.controller.admin.bi.vo.CrmBiRanKRespVO;
import cn.econets.blossom.module.crm.controller.admin.bi.vo.CrmBiRankReqVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * CRM BI Rankings Mapper
 *
 */
@Mapper
public interface CrmBiRankingMapper {

    /**
     * Query the contract amount ranking list
     *
     * @param rankReqVO Parameters
     * @return Contract Amount Ranking
     */
    List<CrmBiRanKRespVO> selectContractPriceRank(CrmBiRankReqVO rankReqVO);

    /**
     * Query the ranking of repayment amount
     *
     * @param rankReqVO Parameters
     * @return Ranking of Refund Amount
     */
    List<CrmBiRanKRespVO> selectReceivablePriceRank(CrmBiRankReqVO rankReqVO);

    /**
     * Query the ranking of the number of signed contracts
     *
     * @param rankReqVO Parameters
     * @return Ranking of number of signed contracts
     */
    List<CrmBiRanKRespVO> selectContractCountRank(CrmBiRankReqVO rankReqVO);

    /**
     * Query product sales rankings
     *
     * @param rankReqVO Parameters
     * @return Product sales ranking
     */
    List<CrmBiRanKRespVO> selectProductSalesRank(CrmBiRankReqVO rankReqVO);

    /**
     * Query the ranking of new customers
     *
     * @param rankReqVO Parameters
     * @return Ranking of New Customers
     */
    List<CrmBiRanKRespVO> selectCustomerCountRank(CrmBiRankReqVO rankReqVO);

    /**
     * Query the number of contacts ranking
     *
     * @param rankReqVO Parameters
     * @return Contacts Ranking
     */
    List<CrmBiRanKRespVO> selectContactsCountRank(CrmBiRankReqVO rankReqVO);

    /**
     * Query the ranking of follow-up times
     *
     * @param rankReqVO Parameters
     * @return Ranking of Follow-up Times
     */
    List<CrmBiRanKRespVO> selectFollowCountRank(CrmBiRankReqVO rankReqVO);

    /**
     * Query the ranking of number of follow-up customers
     *
     * @param rankReqVO Parameters
     * @return Ranking of number of follow-up customers
     */
    List<CrmBiRanKRespVO> selectFollowCustomerCountRank(CrmBiRankReqVO rankReqVO);

}
