package cn.econets.blossom.framework.pay.core.client.impl.alipay;

import cn.econets.blossom.framework.common.util.validation.ValidationUtils;
import cn.econets.blossom.framework.pay.core.client.PayClientConfig;
import lombok.Data;

import javax.validation.Validator;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * Alipay PayClientConfig Implementation class
 * The attributes mainly come from {@link com.alipay.api.AlipayConfig} Required attributes
 *
 */
@Data
public class AlipayPayClientConfig implements PayClientConfig {

    /**
     * Public key type - Public Key Mode
     */
    public static final Integer MODE_PUBLIC_KEY = 1;
    /**
     * Public key type - Certificate Mode
     */
    public static final Integer MODE_CERTIFICATE = 2;

    /**
     * Signature algorithm type - RSA
     */
    public static final String SIGN_TYPE_DEFAULT = "RSA2";

    /**
     * Gateway address
     *
     * 1. <a href="https://openapi.alipay.com/gateway.do">Production environment</a>
     * 2. <a href="https://openapi-sandbox.dl.alipaydev.com/gateway.do">Sandbox environment</a>
     */
    @NotBlank(message = "Gateway address cannot be empty", groups = {ModePublicKey.class, ModeCertificate.class})
    private String serverUrl;

    /**
     * Applications created on the open platform ID
     */
    @NotBlank(message = "Applications created on the open platform IDCannot be empty", groups = {ModePublicKey.class, ModeCertificate.class})
    private String appId;

    /**
     * Signature algorithm type，Recommended：RSA2
     * <p>
     * {@link #SIGN_TYPE_DEFAULT}
     */
    @NotBlank(message = "Signature algorithm type cannot be empty", groups = {ModePublicKey.class, ModeCertificate.class})
    private String signType;

    /**
     * Public key type
     * 1. {@link #MODE_PUBLIC_KEY} Situation，privateKey + alipayPublicKey
     * 2. {@link #MODE_CERTIFICATE} Situation，appCertContent + alipayPublicCertContent + rootCertContent
     */
    @NotNull(message = "Public key type cannot be empty", groups = {ModePublicKey.class, ModeCertificate.class})
    private Integer mode;

    // ========== Public key mode ==========
    /**
     * Merchant private key
     */
    @NotBlank(message = "Merchant private key cannot be empty", groups = {ModePublicKey.class})
    private String privateKey;

    /**
     * Alipay public key string
     */
    @NotBlank(message = "Alipay public key string cannot be empty", groups = {ModePublicKey.class})
    private String alipayPublicKey;

    // ========== Certificate Mode ==========
    /**
     * Specify the merchant public key application certificate content string
     */
    @NotBlank(message = "The content of the specified merchant public key application certificate cannot be empty", groups = {ModeCertificate.class})
    private String appCertContent;
    /**
     * Specify the content string of Alipay public key certificate
     */
    @NotBlank(message = "The content of the specified Alipay public key certificate cannot be empty", groups = {ModeCertificate.class})
    private String alipayPublicCertContent;
    /**
     * Specify the root certificate content string
     */
    @NotBlank(message = "The specified root certificate content string cannot be empty", groups = {ModeCertificate.class})
    private String rootCertContent;

    public interface ModePublicKey {
    }

    public interface ModeCertificate {
    }

    @Override
    public void validate(Validator validator) {
        ValidationUtils.validate(validator, this,
                MODE_PUBLIC_KEY.equals(this.getMode()) ? ModePublicKey.class : ModeCertificate.class);
    }

}
