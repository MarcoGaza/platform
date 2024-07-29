package cn.econets.blossom.module.member.service.tag;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.ListUtil;
import cn.hutool.core.util.StrUtil;
import cn.econets.blossom.framework.common.pojo.PageResult;
import cn.econets.blossom.module.member.controller.admin.tag.vo.MemberTagCreateReqVO;
import cn.econets.blossom.module.member.controller.admin.tag.vo.MemberTagPageReqVO;
import cn.econets.blossom.module.member.controller.admin.tag.vo.MemberTagUpdateReqVO;
import cn.econets.blossom.module.member.convert.tag.MemberTagConvert;
import cn.econets.blossom.module.member.dal.dataobject.tag.MemberTagDO;
import cn.econets.blossom.module.member.dal.mysql.tag.MemberTagMapper;
import cn.econets.blossom.module.member.service.user.MemberUserService;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.List;

import static cn.econets.blossom.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.econets.blossom.module.member.enums.ErrorCodeConstants.*;

/**
 * Member Tag Service Implementation class
 *
 * 
 */
@Service
@Validated
public class MemberTagServiceImpl implements MemberTagService {

    @Resource
    private MemberTagMapper memberTagMapper;

    @Resource
    private MemberUserService memberUserService;

    @Override
    public Long createTag(MemberTagCreateReqVO createReqVO) {
        // Verify name uniqueness
        validateTagNameUnique(null, createReqVO.getName());
        // Insert
        MemberTagDO tag = MemberTagConvert.INSTANCE.convert(createReqVO);
        memberTagMapper.insert(tag);
        // Return
        return tag.getId();
    }

    @Override
    public void updateTag(MemberTagUpdateReqVO updateReqVO) {
        // Check existence
        validateTagExists(updateReqVO.getId());
        // Verify name uniqueness
        validateTagNameUnique(updateReqVO.getId(), updateReqVO.getName());
        // Update
        MemberTagDO updateObj = MemberTagConvert.INSTANCE.convert(updateReqVO);
        memberTagMapper.updateById(updateObj);
    }

    @Override
    public void deleteTag(Long id) {
        // Check existence
        validateTagExists(id);
        // Check if there is a user under the tag
        validateTagHasUser(id);
        // Delete
        memberTagMapper.deleteById(id);
    }

    private void validateTagExists(Long id) {
        if (memberTagMapper.selectById(id) == null) {
            throw exception(TAG_NOT_EXISTS);
        }
    }

    private void validateTagNameUnique(Long id, String name) {
        if (StrUtil.isBlank(name)) {
            return;
        }
        MemberTagDO tag = memberTagMapper.selelctByName(name);
        if (tag == null) {
            return;
        }

        // If id Empty，Indicates that there is no need to compare whether they are the same id Tags
        if (id == null) {
            throw exception(TAG_NAME_EXISTS);
        }
        if (!tag.getId().equals(id)) {
            throw exception(TAG_NAME_EXISTS);
        }
    }

    void validateTagHasUser(Long id) {
        Long count = memberUserService.getUserCountByTagId(id);
        if (count > 0) {
            throw exception(TAG_HAS_USER);
        }
    }

    @Override
    public MemberTagDO getTag(Long id) {
        return memberTagMapper.selectById(id);
    }

    @Override
    public List<MemberTagDO> getTagList(Collection<Long> ids) {
        if (CollUtil.isEmpty(ids)) {
            return ListUtil.empty();
        }
        return memberTagMapper.selectBatchIds(ids);
    }

    @Override
    public PageResult<MemberTagDO> getTagPage(MemberTagPageReqVO pageReqVO) {
        return memberTagMapper.selectPage(pageReqVO);
    }

    @Override
    public List<MemberTagDO> getTagList() {
        return memberTagMapper.selectList();
    }

}
