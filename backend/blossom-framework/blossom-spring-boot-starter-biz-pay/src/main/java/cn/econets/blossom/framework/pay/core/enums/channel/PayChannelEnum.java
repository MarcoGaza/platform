package cn.econets.blossom.framework.pay.core.enums.channel;

import cn.hutool.core.util.ArrayUtil;
import cn.econets.blossom.framework.pay.core.client.PayClientConfig;
import cn.econets.blossom.framework.pay.core.client.impl.NonePayClientConfig;
import cn.econets.blossom.framework.pay.core.client.impl.alipay.AlipayPayClientConfig;
import cn.econets.blossom.framework.pay.core.client.impl.weixin.WxPayClientConfig;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Enumeration of payment channel codes
 *
 */
@Getter
@AllArgsConstructor
public enum PayChannelEnum {

    WX_PUB("wx_pub", "WeChat JSAPI Payment", WxPayClientConfig.class), // Official account webpage
    WX_LITE("wx_lite", "WeChat Mini Program Payment", WxPayClientConfig.class),
    WX_APP("wx_app", "WeChat App Payment", WxPayClientConfig.class),
    WX_NATIVE("wx_native", "WeChat Native Payment", WxPayClientConfig.class),
    WX_BAR("wx_bar", "WeChat payment code payment", WxPayClientConfig.class),

    ALIPAY_PC("alipay_pc", "Alipay PC Website payment", AlipayPayClientConfig.class),
    ALIPAY_WAP("alipay_wap", "Alipay Wap Website payment", AlipayPayClientConfig.class),
    ALIPAY_APP("alipay_app", "AlipayApp Payment", AlipayPayClientConfig.class),
    ALIPAY_QR("alipay_qr", "Alipay scan code payment", AlipayPayClientConfig.class),
    ALIPAY_BAR("alipay_bar", "Alipay barcode payment", AlipayPayClientConfig.class),
    MOCK("mock", "Simulated payment", NonePayClientConfig.class),

    WALLET("wallet", "Wallet Payment", NonePayClientConfig.class);

    /**
     * Encoding
     *
     * Reference <a href="https://www.pingxx.com/api/Payment channel attribute value.html">Payment channel attribute value</a>
     */
    private final String code;
    /**
     * Name
     */
    private final String name;

    /**
     * Configuration Class
     */
    private final Class<? extends PayClientConfig> configClass;

    /**
     * WeChat Pay
     */
    public static final String WECHAT = "WECHAT";

    /**
     * Alipay payment
     */
    public static final String ALIPAY = "ALIPAY";

    public static PayChannelEnum getByCode(String code) {
        return ArrayUtil.firstMatch(o -> o.getCode().equals(code), values());
    }

    public static boolean isAlipay(String channelCode) {
        return channelCode != null && channelCode.startsWith("alipay");
    }
}
