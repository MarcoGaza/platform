package cn.econets.blossom.module.member.message.user;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * Member user creates message
 *
 *
 */
@Data
public class MemberUserCreateMessage {

    /**
     * User Number
     */
    @NotNull(message = "User ID cannot be empty")
    private Long userId;

}
