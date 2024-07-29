package cn.econets.blossom.module.system.service.member;

/**
 * Member Service Interface
 *
 */
public interface MemberService {

    /**
     * Get the member user's mobile phone number
     *
     * @param id Member User Number
     * @return Mobile phone number
     */
    String getMemberUserMobile(Long id);

    /**
     * Get the member user's email address
     *
     * @param id Member User Number
     * @return Mailbox
     */
    String getMemberUserEmail(Long id);

}
