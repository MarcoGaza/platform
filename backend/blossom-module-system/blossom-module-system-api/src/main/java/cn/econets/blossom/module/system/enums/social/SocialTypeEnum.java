package cn.econets.blossom.module.system.enums.social;

import cn.econets.blossom.framework.common.core.IntArrayValuable;
import cn.hutool.core.util.ArrayUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

/**
 * Social platform type enumeration
 *
 */
@Getter
@AllArgsConstructor
public enum SocialTypeEnum implements IntArrayValuable {

    /**
     * Gitee
     *
     * @see <a href="https://gitee.com/api/v5/oauth_doc#/">Access Document</a>
     */
    GITEE(10, "GITEE"),
    /**
     * DingTalk
     *
     * @see <a href="https://developers.dingtalk.com/document/app/obtain-identity-credentials">Access Document</a>
     */
    DINGTALK(20, "DINGTALK"),

    /**
     * Enterprise WeChat
     *
     * @see <a href="https://xkcoding.com/2019/08/06/use-justauth-integration-wechat-enterprise.html">Access Document</a>
     */
    WECHAT_ENTERPRISE(30, "WECHAT_ENTERPRISE"),
    /**
     * WeChat public platform - Mobile terminal H5
     *
     * @see <a href="https://www.cnblogs.com/juewuzhe/p/11905461.html">Access Document</a>
     */
    WECHAT_MP(31, "WECHAT_MP"),
    /**
     * WeChat Open Platform - Website Application PC Scan the QR code to authorize login
     *
     * @see <a href="https://justauth.wiki/guide/oauth/wechat_open/#_2-Apply for developer qualification certification">Access Document</a>
     */
    WECHAT_OPEN(32, "WECHAT_OPEN"),
    /**
     * WeChat Mini Program
     *
     * @see <a href="https://developers.weixin.qq.com/miniprogram/dev/framework/open-ability/login.html">Access Document</a>
     */
    WECHAT_MINI_APP(34, "WECHAT_MINI_APP"),
    ;

    public static final int[] ARRAYS = Arrays.stream(values()).mapToInt(SocialTypeEnum::getType).toArray();

    /**
     * Type
     */
    private final Integer type;
    /**
     * Type identification
     */
    private final String source;

    @Override
    public int[] array() {
        return ARRAYS;
    }

    public static SocialTypeEnum valueOfType(Integer type) {
        return ArrayUtil.firstMatch(o -> o.getType().equals(type), values());
    }

}
