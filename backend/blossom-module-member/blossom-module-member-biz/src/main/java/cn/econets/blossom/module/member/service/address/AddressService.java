package cn.econets.blossom.module.member.service.address;

import cn.econets.blossom.module.member.controller.app.address.vo.AppAddressCreateReqVO;
import cn.econets.blossom.module.member.controller.app.address.vo.AppAddressUpdateReqVO;
import cn.econets.blossom.module.member.dal.dataobject.address.MemberAddressDO;

import javax.validation.Valid;
import java.util.List;

/**
 * User's mailing address Service Interface
 *
 * 
 */
public interface AddressService {

    /**
     * Create a user recipient address
     *
     *
     * @param userId User Number
     * @param createReqVO Create information
     * @return Number
     */
    Long createAddress(Long userId, @Valid AppAddressCreateReqVO createReqVO);

    /**
     * Update user's mailing address
     *
     * @param userId User Number
     * @param updateReqVO Update information
     */
    void updateAddress(Long userId, @Valid AppAddressUpdateReqVO updateReqVO);

    /**
     * Delete the user's recipient address
     *
     * @param userId User Number
     * @param id Number
     */
    void deleteAddress(Long userId, Long id);

    /**
     * Get the user's mailing address
     *
     * @param id Number
     * @return User's mailing address
     */
    MemberAddressDO getAddress(Long userId, Long id);

    /**
     * Get the user's mailing address list
     *
     * @param userId User Number
     * @return User Receiving Address List
     */
    List<MemberAddressDO> getAddressList(Long userId);

    /**
     * Get the user's default recipient address
     *
     * @param userId User Number
     * @return User's mailing address
     */
    MemberAddressDO getDefaultUserAddress(Long userId);

}
