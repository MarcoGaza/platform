package cn.econets.blossom.framework.pay.core.client.impl.weixin;

import cn.econets.blossom.framework.pay.core.enums.channel.PayChannelEnum;
import lombok.extern.slf4j.Slf4j;

/**
 * WeChat Pay【Mini Program】of PayClient Implementation class
 *
 * Since the WeChat payment logic of the official account and the mini program is consistent，So directly inherit
 *
 * Document：<a href="https://pay.weixin.qq.com/wiki/doc/apiv3/apis/chapter3_5_1.shtml">JSAPI Place an order</>
 *
 */
@Slf4j
public class WxLitePayClient extends WxPubPayClient {

    public WxLitePayClient(Long channelId, WxPayClientConfig config) {
        super(channelId, PayChannelEnum.WX_LITE.getCode(), config);
    }

}
