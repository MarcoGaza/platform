package cn.econets.blossom.module.mp.service.user;

import cn.econets.blossom.framework.common.pojo.PageResult;
import cn.econets.blossom.module.mp.controller.admin.user.vo.MpUserPageReqVO;
import cn.econets.blossom.module.mp.controller.admin.user.vo.MpUserUpdateReqVO;
import cn.econets.blossom.module.mp.dal.dataobject.user.MpUserDO;
import me.chanjar.weixin.mp.bean.result.WxMpUser;

import java.util.Collection;
import java.util.List;

import static cn.econets.blossom.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.econets.blossom.module.mp.enums.ErrorCodeConstants.USER_NOT_EXISTS;

/**
 * Public Account Fans Service Interface
 *
 *
 */
public interface MpUserService {

    /**
     * Get public account fans
     *
     * @param id Number
     * @return Public Account Fans
     */
    MpUserDO getUser(Long id);

    /**
     * Use appId + openId，Get public account fans
     *
     * @param appId Public Account appId
     * @param openId Public Account openId
     * @return Public Account Fans
     */
    MpUserDO getUser(String appId, String openId);

    /**
     * Get public account fans
     *
     * @param id Number
     * @return Public Account Fans
     */
    default MpUserDO getRequiredUser(Long id) {
        MpUserDO user = getUser(id);
        if (user == null) {
            throw exception(USER_NOT_EXISTS);
        }
        return user;
    }

    /**
     * Get the public account fans list
     *
     * @param ids Number
     * @return Public account fans list
     */
    List<MpUserDO> getUserList(Collection<Long> ids);

    /**
     * Get the public account fans page
     *
     * @param pageReqVO Paged query
     * @return Public account fans page
     */
    PageResult<MpUserDO> getUserPage(MpUserPageReqVO pageReqVO);

    /**
     * Save public account fans
     *
     * Add or update，Based on whether it exists in the database
     *
     * @param appId Public Account appId
     * @param wxMpUser Information of public account fans
     * @return Public Account Fans
     */
    MpUserDO saveUser(String appId, WxMpUser wxMpUser);

    /**
     * Synchronize a public account's fans
     *
     * @param accountId The public account number
     */
    void syncUser(Long accountId);

    /**
     * Update public account fans，Unfollow
     *
     * @param appId Public Account appId
     * @param openId Fans of the public account openid
     */
    void updateUserUnsubscribe(String appId, String openId);

    /**
     * Update public account fans
     *
     * @param updateReqVO Update information
     */
    void updateUser(MpUserUpdateReqVO updateReqVO);

}
