package cn.econets.blossom.framework.pay.core.enums.order;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Payment UI Display Mode
 *
 */
@Getter
@AllArgsConstructor
public enum PayOrderDisplayModeEnum {

    URL("url"), // Redirect How to jump to the link
    IFRAME("iframe"), // IFrame Embedded link method【Not currently available】
    FORM("form"), // HTML Form submission
    QR_CODE("qr_code"), // Text content of the QR code
    QR_CODE_URL("qr_code_url"), // QR code image link
    BAR_CODE("bar_code"), // Barcode
    APP("app"), // Application：Android、iOS、WeChat Mini Program、WeChat public account, etc.，Custom processing is required
    ;

    /**
     * Display Mode
     */
    private final String mode;

}
