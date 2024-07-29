package cn.econets.blossom.module.system.enums.sms;

import cn.econets.blossom.framework.common.core.IntArrayValuable;
import cn.hutool.core.util.ArrayUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

/**
 * Enumeration of scenarios for sending user SMS verification codes
 *
 */
@Getter
@AllArgsConstructor
public enum SmsSceneEnum implements IntArrayValuable {

    MEMBER_LOGIN(1, "user-sms-login", "Member User - Login with mobile phone number"),
    MEMBER_UPDATE_MOBILE(2, "user-update-mobile", "Member User - Modify mobile phone"),
    MEMBER_UPDATE_PASSWORD(3, "user-update-mobile", "Member User - Change password"),
    MEMBER_RESET_PASSWORD(4, "user-reset-password", "Member User - Forgot password"),

    ADMIN_MEMBER_LOGIN(21, "admin-sms-login", "Backend User - Login with mobile phone number");

    public static final int[] ARRAYS = Arrays.stream(values()).mapToInt(SmsSceneEnum::getScene).toArray();

    /**
     * Verification scene number
     */
    private final Integer scene;
    /**
     * Template code
     */
    private final String templateCode;
    /**
     * Description
     */
    private final String description;

    @Override
    public int[] array() {
        return ARRAYS;
    }

    public static SmsSceneEnum getCodeByScene(Integer scene) {
        return ArrayUtil.firstMatch(sceneEnum -> sceneEnum.getScene().equals(scene),
                values());
    }

}
