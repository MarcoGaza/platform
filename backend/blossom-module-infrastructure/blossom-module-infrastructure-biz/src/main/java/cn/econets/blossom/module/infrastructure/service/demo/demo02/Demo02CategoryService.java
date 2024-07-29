package cn.econets.blossom.module.infrastructure.service.demo.demo02;

import cn.econets.blossom.module.infrastructure.controller.admin.demo.demo02.vo.Demo02CategoryListReqVO;
import cn.econets.blossom.module.infrastructure.controller.admin.demo.demo02.vo.Demo02CategorySaveReqVO;
import cn.econets.blossom.module.infrastructure.dal.dataobject.demo.demo02.Demo02CategoryDO;

import javax.validation.Valid;
import java.util.List;

/**
 * Example Category Service Interface
 *
 *
 */
public interface Demo02CategoryService {

    /**
     * Create sample category
     *
     * @param createReqVO Create information
     * @return Number
     */
    Long createDemo02Category(@Valid Demo02CategorySaveReqVO createReqVO);

    /**
     * Update example categories
     *
     * @param updateReqVO Update information
     */
    void updateDemo02Category(@Valid Demo02CategorySaveReqVO updateReqVO);

    /**
     * Delete example category
     *
     * @param id Number
     */
    void deleteDemo02Category(Long id);

    /**
     * Get sample classification
     *
     * @param id Number
     * @return Example Category
     */
    Demo02CategoryDO getDemo02Category(Long id);

    /**
     * Get a list of example categories
     *
     * @param listReqVO Query conditions
     * @return Example category list
     */
    List<Demo02CategoryDO> getDemo02CategoryList(Demo02CategoryListReqVO listReqVO);

}
