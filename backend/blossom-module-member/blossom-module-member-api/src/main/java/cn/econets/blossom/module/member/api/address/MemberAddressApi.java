package cn.econets.blossom.module.member.api.address;

import cn.econets.blossom.module.member.api.address.dto.MemberAddressRespDTO;

/**
 * User's mailing address API Interface
 *
 * 
 */
public interface MemberAddressApi {

    /**
     * Get the user's mailing address
     *
     * @param id Receiving address number
     * @param userId User Number
     * @return User's mailing address
     */
    MemberAddressRespDTO getAddress(Long id, Long userId);

    /**
     * Get the user's default recipient address
     *
     * @param userId User Number
     * @return User's mailing address
     */
    MemberAddressRespDTO getDefaultAddress(Long userId);

}
