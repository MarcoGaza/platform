package cn.econets.blossom.module.crm.service.bi;


import cn.econets.blossom.module.crm.controller.admin.bi.vo.CrmBiRanKRespVO;
import cn.econets.blossom.module.crm.controller.admin.bi.vo.CrmBiRankReqVO;

import java.util.List;

/**
 * CRM BI Rankings Service Interface
 *
 */
public interface CrmBiRankingService {

    /**
     * Get the contract amount ranking
     *
     * @param rankReqVO Ranking parameters
     * @return Contract Amount Ranking
     */
    List<CrmBiRanKRespVO> getContractPriceRank(CrmBiRankReqVO rankReqVO);

    /**
     * Get the ranking of repayment amount
     *
     * @param rankReqVO Ranking parameters
     * @return Ranking of Refund Amount
     */
    List<CrmBiRanKRespVO> getReceivablePriceRank(CrmBiRankReqVO rankReqVO);

    /**
     * Get the ranking of the number of signed contracts
     *
     * @param rankReqVO Ranking parameters
     * @return Ranking of number of signed contracts
     */
    List<CrmBiRanKRespVO> getContractCountRank(CrmBiRankReqVO rankReqVO);

    /**
     * Get product sales rankings
     *
     * @param rankReqVO Ranking parameters
     * @return Product sales ranking
     */
    List<CrmBiRanKRespVO> getProductSalesRank(CrmBiRankReqVO rankReqVO);

    /**
     * Get the ranking of new customers
     *
     * @param rankReqVO Ranking parameters
     * @return Ranking of New Customers
     */
    List<CrmBiRanKRespVO> getCustomerCountRank(CrmBiRankReqVO rankReqVO);

    /**
     * Get the contact number ranking
     *
     * @param rankReqVO Ranking parameters
     * @return Contacts Ranking
     */
    List<CrmBiRanKRespVO> getContactsCountRank(CrmBiRankReqVO rankReqVO);

    /**
     * Get the follow-up ranking
     *
     * @param rankReqVO Ranking parameters
     * @return Ranking of Follow-up Times
     */
    List<CrmBiRanKRespVO> getFollowCountRank(CrmBiRankReqVO rankReqVO);

    /**
     * Ranking of number of follow-up customers
     *
     * @param rankReqVO Ranking parameters
     * @return Ranking of number of follow-up customers
     */
    List<CrmBiRanKRespVO> getFollowCustomerCountRank(CrmBiRankReqVO rankReqVO);

}
