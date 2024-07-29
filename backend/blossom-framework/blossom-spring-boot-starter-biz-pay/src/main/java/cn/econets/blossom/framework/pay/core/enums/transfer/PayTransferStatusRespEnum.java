package cn.econets.blossom.framework.pay.core.enums.transfer;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Objects;

/**
 * Channel transfer status enumeration
 *
 */
@Getter
@AllArgsConstructor
public enum PayTransferStatusRespEnum {

    WAITING(0, "Waiting for transfer"),

    /**
     * TODO Transfer to bank card. There will beT+0 T+1 Please check the status of the account received。 Not yet implemented
     * TODO ：You can check out other open source projects，For this scenario，What is the processing strategy?？For example，Active polling every day？List of this status？
     */
    IN_PROGRESS(10, "Transfer in progress"),

    SUCCESS(20, "Transfer successful"),
    /**
     * Transfer closed (Failed，Or other situations)
     */
    CLOSED(30, "Transfer closed");

    private final Integer status;
    private final String name;

    public static boolean isSuccess(Integer status) {
        return Objects.equals(status, SUCCESS.getStatus());
    }

    public static boolean isClosed(Integer status) {
        return Objects.equals(status, CLOSED.getStatus());
    }

    public static boolean isInProgress(Integer status) {
        return Objects.equals(status, IN_PROGRESS.getStatus());
    }
}
