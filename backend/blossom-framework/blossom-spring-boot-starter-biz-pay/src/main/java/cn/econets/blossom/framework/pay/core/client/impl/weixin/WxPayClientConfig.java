package cn.econets.blossom.framework.pay.core.client.impl.weixin;

import cn.hutool.core.io.IoUtil;
import cn.econets.blossom.framework.common.util.validation.ValidationUtils;
import cn.econets.blossom.framework.pay.core.client.PayClientConfig;
import lombok.Data;

import javax.validation.Validator;
import javax.validation.constraints.NotBlank;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

/**
 * WeChat payment PayClientConfig Implementation class
 * Attributes mainly come from {@link com.github.binarywang.wxpay.config.WxPayConfig} Required attributes
 *
 */
@Data
public class WxPayClientConfig implements PayClientConfig {

    /**
     * API Version - V2
     *
     * <a href="https://pay.weixin.qq.com/wiki/doc/api/jsapi.php?chapter=4_1">V2 Protocol Description</a>
     */
    public static final String API_VERSION_V2 = "v2";
    /**
     * API Version - V3
     *
     * <a href="https://pay.weixin.qq.com/wiki/doc/apiv3/wechatpay/wechatpay-1.shtml">V3 Protocol Description</a>
     */
    public static final String API_VERSION_V3 = "v3";

    /**
     * Official account or mini program appid
     *
     * This field is only required for public accounts or mini programs
     */
    @NotBlank(message = "APPID Cannot be empty", groups = {V2.class, V3.class})
    private String appId;
    /**
     * Merchant Number
     */
    @NotBlank(message = "Merchant number cannot be empty", groups = {V2.class, V3.class})
    private String mchId;
    /**
     * API Version
     */
    @NotBlank(message = "API Version cannot be empty", groups = {V2.class, V3.class})
    private String apiVersion;

    // ========== V2 Version parameters ==========

    /**
     * Merchant Key
     */
    @NotBlank(message = "Merchant key cannot be empty", groups = V2.class)
    private String mchKey;
    /**
     * apiclient_cert.p12 The corresponding string of the certificate file【base64 Format】
     *
     * Why adopt base64 Format？Because p12 Read is binary，Need to be converted into base64 The format is easy to transmit and store
     */
    @NotBlank(message = "apiclient_cert.p12 Cannot be empty", groups = V2.class)
    private String keyContent;

    // ========== V3 Version parameters ==========
    /**
     * apiclient_key.pem The corresponding string of the certificate file
     */
    @NotBlank(message = "apiclient_key Cannot be empty", groups = V3.class)
    private String privateKeyContent;
    /**
     * apiclient_cert.pem The corresponding string of the certificate file
     */
    @NotBlank(message = "apiclient_cert Cannot be empty", groups = V3.class)
    private String privateCertContent;
    /**
     * apiV3 Key value
     */
    @NotBlank(message = "apiV3 The key value cannot be empty", groups = V3.class)
    private String apiV3Key;

    /**
     * Group verification v2Version
     */
    public interface V2 {
    }

    /**
     * Group verification v3Version
     */
    public interface V3 {
    }

    @Override
    public void validate(Validator validator) {
        ValidationUtils.validate(validator, this,
                API_VERSION_V2.equals(this.getApiVersion()) ? V2.class : V3.class);
    }

    public static void main(String[] args) throws FileNotFoundException {
        String path = "/Users/ximu/Downloads/wx_pay/apiclient_cert.p12";
        /// String path = "/Users/ximu/Downloads/wx_pay/apiclient_key.pem";
        /// String path = "/Users/ximu/Downloads/wx_pay/apiclient_cert.pem";
        System.out.println(IoUtil.readUtf8(new FileInputStream(path)));
    }

}
