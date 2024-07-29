package cn.econets.blossom.module.promotion.api.bargain;

/**
 * Bargaining activity Api Interface

 */
public interface BargainActivityApi {

    /**
     * Update bargaining activity inventory
     *
     * @param id Bargaining activity number
     * @param count Purchase quantity
     */
    void updateBargainActivityStock(Long id, Integer count);

}
