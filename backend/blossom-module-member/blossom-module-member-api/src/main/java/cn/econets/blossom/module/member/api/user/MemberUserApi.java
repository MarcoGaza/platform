package cn.econets.blossom.module.member.api.user;

import cn.econets.blossom.module.member.api.user.dto.MemberUserRespDTO;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import static cn.econets.blossom.framework.common.util.collection.CollectionUtils.convertMap;

/**
 * Member user's API Interface
 *
 * 
 */
public interface MemberUserApi {

    /**
     * Get member user information
     *
     * @param id User Number
     * @return User Information
     */
    MemberUserRespDTO getUser(Long id);

    /**
     * Get member user information
     *
     * @param ids Array of user numbers
     * @return User information
     */
    List<MemberUserRespDTO> getUserList(Collection<Long> ids);

    /**
     * Get member user Map
     *
     * @param ids Array of user numbers
     * @return Member User Map
     */
    default Map<Long, MemberUserRespDTO> getUserMap(Collection<Long> ids) {
        List<MemberUserRespDTO> list = getUserList(ids);
        return convertMap(list, MemberUserRespDTO::getId);
    }

    /**
     * Based on user nickname，Fuzzy matching user list
     *
     * @param nickname User Nickname，Fuzzy matching
     * @return List of user information
     */
    List<MemberUserRespDTO> getUserListByNickname(String nickname);

    /**
     * Based on mobile phone number，Precisely match users
     *
     * @param mobile Mobile phone number
     * @return User Information
     */
    MemberUserRespDTO getUserByMobile(String mobile);
}
