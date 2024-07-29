package cn.econets.blossom.module.system.framework.sms.core.enums;

import cn.hutool.core.util.ArrayUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * SMS channel enumeration
 *
 */
@Getter
@AllArgsConstructor
public enum SmsChannelEnum {

    DEBUG_DING_TALK("DEBUG_DING_TALK", "Debug(DingTalk)"),
    ALIYUN("ALIYUN", "Alibaba Cloud"),
    TENCENT("TENCENT", "Tencent Cloud"),
//    HUA_WEI("HUA_WEI", "Huawei Cloud"),
    ;

    /**
     * Encoding
     */
    private final String code;
    /**
     * Name
     */
    private final String name;

    public static SmsChannelEnum getByCode(String code) {
        return ArrayUtil.firstMatch(o -> o.getCode().equals(code), values());
    }

}
