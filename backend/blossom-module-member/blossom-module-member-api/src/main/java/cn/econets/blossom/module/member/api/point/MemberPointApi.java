package cn.econets.blossom.module.member.api.point;

import cn.econets.blossom.module.member.enums.point.MemberPointBizTypeEnum;

import javax.validation.constraints.Min;

/**
 * User points API Interface
 *
 *
 */
public interface MemberPointApi {

    /**
     * Increase user points
     *
     * @param userId  User Number
     * @param point   Points
     * @param bizType Business Type {@link MemberPointBizTypeEnum}
     * @param bizId   Business Number
     */
    void addPoint(Long userId, @Min(value = 1L, message = "The integral must be a positive number") Integer point,
                  Integer bizType, String bizId);

    /**
     * Reduce user points
     *
     * @param userId  User Number
     * @param point   Points
     * @param bizType Business Type {@link MemberPointBizTypeEnum}
     * @param bizId   Business Number
     */
    void reducePoint(Long userId, @Min(value = 1L, message = "The integral must be positive") Integer point,
                     Integer bizType, String bizId);

}
