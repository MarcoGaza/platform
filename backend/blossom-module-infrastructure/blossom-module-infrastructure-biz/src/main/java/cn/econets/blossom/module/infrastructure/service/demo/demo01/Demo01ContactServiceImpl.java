package cn.econets.blossom.module.infrastructure.service.demo.demo01;

import cn.econets.blossom.framework.common.pojo.PageResult;
import cn.econets.blossom.framework.common.util.object.BeanUtils;
import cn.econets.blossom.module.infrastructure.controller.admin.demo.demo01.vo.Demo01ContactPageReqVO;
import cn.econets.blossom.module.infrastructure.controller.admin.demo.demo01.vo.Demo01ContactSaveReqVO;
import cn.econets.blossom.module.infrastructure.dal.dataobject.demo.demo01.Demo01ContactDO;
import cn.econets.blossom.module.infrastructure.dal.mysql.demo.demo01.Demo01ContactMapper;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;

import static cn.econets.blossom.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.econets.blossom.module.infrastructure.enums.ErrorCodeConstants.DEMO01_CONTACT_NOT_EXISTS;

/**
 * Sample Contact Service Implementation class
 *
 *
 */
@Service
@Validated
public class Demo01ContactServiceImpl implements Demo01ContactService {

    @Resource
    private Demo01ContactMapper demo01ContactMapper;

    @Override
    public Long createDemo01Contact(Demo01ContactSaveReqVO createReqVO) {
        // Insert
        Demo01ContactDO demo01Contact = BeanUtils.toBean(createReqVO, Demo01ContactDO.class);
        demo01ContactMapper.insert(demo01Contact);
        // Return
        return demo01Contact.getId();
    }

    @Override
    public void updateDemo01Contact(Demo01ContactSaveReqVO updateReqVO) {
        // Check existence
        validateDemo01ContactExists(updateReqVO.getId());
        // Update
        Demo01ContactDO updateObj = BeanUtils.toBean(updateReqVO, Demo01ContactDO.class);
        demo01ContactMapper.updateById(updateObj);
    }

    @Override
    public void deleteDemo01Contact(Long id) {
        // Check existence
        validateDemo01ContactExists(id);
        // Delete
        demo01ContactMapper.deleteById(id);
    }

    private void validateDemo01ContactExists(Long id) {
        if (demo01ContactMapper.selectById(id) == null) {
            throw exception(DEMO01_CONTACT_NOT_EXISTS);
        }
    }

    @Override
    public Demo01ContactDO getDemo01Contact(Long id) {
        return demo01ContactMapper.selectById(id);
    }

    @Override
    public PageResult<Demo01ContactDO> getDemo01ContactPage(Demo01ContactPageReqVO pageReqVO) {
        return demo01ContactMapper.selectPage(pageReqVO);
    }

}
