package cn.econets.blossom.module.member.service.address;

import cn.hutool.core.collection.CollUtil;
import cn.econets.blossom.module.member.controller.app.address.vo.AppAddressCreateReqVO;
import cn.econets.blossom.module.member.controller.app.address.vo.AppAddressUpdateReqVO;
import cn.econets.blossom.module.member.convert.address.AddressConvert;
import cn.econets.blossom.module.member.dal.dataobject.address.MemberAddressDO;
import cn.econets.blossom.module.member.dal.mysql.address.MemberAddressMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.util.List;

import static cn.econets.blossom.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.econets.blossom.module.member.enums.ErrorCodeConstants.ADDRESS_NOT_EXISTS;

/**
 * User's mailing address Service Implementation class
 *
 * 
 */
@Service
@Validated
public class AddressServiceImpl implements AddressService {

    @Resource
    private MemberAddressMapper memberAddressMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long createAddress(Long userId, AppAddressCreateReqVO createReqVO) {
        // If you add a default recipient address，Change the original default address to non-default
        if (Boolean.TRUE.equals(createReqVO.getDefaultStatus())) {
            List<MemberAddressDO> addresses = memberAddressMapper.selectListByUserIdAndDefaulted(userId, true);
            addresses.forEach(address -> memberAddressMapper.updateById(new MemberAddressDO().setId(address.getId()).setDefaultStatus(false)));
        }

        // Insert
        MemberAddressDO address = AddressConvert.INSTANCE.convert(createReqVO);
        address.setUserId(userId);
        memberAddressMapper.insert(address);
        // Return
        return address.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateAddress(Long userId, AppAddressUpdateReqVO updateReqVO) {
        // Check existence,Check whether the operation is possible
        validAddressExists(userId, updateReqVO.getId());

        // If the default recipient address is modified，Change the original default address to non-default
        if (Boolean.TRUE.equals(updateReqVO.getDefaultStatus())) {
            List<MemberAddressDO> addresses = memberAddressMapper.selectListByUserIdAndDefaulted(userId, true);
            addresses.stream().filter(u -> !u.getId().equals(updateReqVO.getId())) // Exclude yourself
                    .forEach(address -> memberAddressMapper.updateById(new MemberAddressDO().setId(address.getId()).setDefaultStatus(false)));
        }

        // Update
        MemberAddressDO updateObj = AddressConvert.INSTANCE.convert(updateReqVO);
        memberAddressMapper.updateById(updateObj);
    }

    @Override
    public void deleteAddress(Long userId, Long id) {
        // Check existence,Check whether the operation is possible
        validAddressExists(userId, id);
        // Delete
        memberAddressMapper.deleteById(id);
    }

    private void validAddressExists(Long userId, Long id) {
        MemberAddressDO addressDO = getAddress(userId, id);
        if (addressDO == null) {
            throw exception(ADDRESS_NOT_EXISTS);
        }
    }

    @Override
    public MemberAddressDO getAddress(Long userId, Long id) {
        return memberAddressMapper.selectByIdAndUserId(id, userId);
    }

    @Override
    public List<MemberAddressDO> getAddressList(Long userId) {
        return memberAddressMapper.selectListByUserIdAndDefaulted(userId, null);
    }

    @Override
    public MemberAddressDO getDefaultUserAddress(Long userId) {
        List<MemberAddressDO> addresses = memberAddressMapper.selectListByUserIdAndDefaulted(userId, true);
        return CollUtil.getFirst(addresses);
    }

}
