package cn.econets.blossom.module.pay.enums.transfer;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Objects;

/**
 *
 */
@Getter
@AllArgsConstructor
public enum PayTransferStatusEnum {

    WAITING(0, "Waiting for transfer"),
    /**
     * TODO Transfer to bank card. There will beT+0 T+1 Please check the status of the account received。 Not yet implemented
     */
    IN_PROGRESS(10, "Transfer in progress"),

    SUCCESS(20, "Transfer successful"),
    /**
     * Transfer closed (Failed，Or other situations) // TODO Change to Transfer failed status
     */
    CLOSED(30, "Transfer closed");

    /**
     * Status
     */
    private final Integer status;
    /**
     * Status name
     */
    private final String name;

    public static boolean isSuccess(Integer status) {
        return Objects.equals(status, SUCCESS.getStatus());
    }

    public static boolean isClosed(Integer status) {
        return Objects.equals(status, CLOSED.getStatus());
    }

    public static boolean isWaiting(Integer status) {
        return Objects.equals(status, WAITING.getStatus());
    }
    public static boolean isInProgress(Integer status) {
        return Objects.equals(status, IN_PROGRESS.getStatus());
    }

    /**
     * Whether it is in the state of waiting for transfer or transferring
     * @param status Status
     */
    public static boolean isPendingStatus(Integer status) {
        return Objects.equals(status, WAITING.getStatus()) || Objects.equals(status, IN_PROGRESS.getStatus());
    }

}
