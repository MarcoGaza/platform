package cn.econets.blossom.module.trade.enums.order;

import cn.hutool.core.util.ObjectUtil;
import cn.econets.blossom.framework.common.core.IntArrayValuable;
import cn.econets.blossom.framework.common.util.object.ObjectUtils;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

/**
 * Transaction Order - Status
 *
 */
@RequiredArgsConstructor
@Getter
public enum TradeOrderStatusEnum implements IntArrayValuable {

    UNPAID(0, "Awaiting payment"),
    UNDELIVERED(10, "Waiting for delivery"),
    DELIVERED(20, "Shipment"),
    COMPLETED(30, "Completed"),
    CANCELED(40, "Cancelled");

    public static final int[] ARRAYS = Arrays.stream(values()).mapToInt(TradeOrderStatusEnum::getStatus).toArray();

    /**
     * Status value
     */
    private final Integer status;
    /**
     * Status name
     */
    private final String name;

    @Override
    public int[] array() {
        return ARRAYS;
    }

    // ========== Ask：Why write so much? isXXX Japanese haveXXX What is the judgment logic？ ==========
    // ========== Answer：Convenient to find a certain type of judgment，Which services are currently in use ==========

    /**
     * Judge the specified state，Whether currently in【Unpaid】Status
     *
     * @param status Specify status
     * @return Yes
     */
    public static boolean isUnpaid(Integer status) {
        return ObjectUtil.equal(UNPAID.getStatus(), status);
    }

    /**
     * Judge the specified state，Whether currently in【Waiting for delivery】Status
     *
     * @param status Specify status
     * @return Yes
     */
    public static boolean isUndelivered(Integer status) {
        return ObjectUtil.equal(UNDELIVERED.getStatus(), status);
    }

    /**
     * Judge the specified state，Whether currently in【Shipment】Status
     *
     * @param status Specify status
     * @return Yes
     */
    public static boolean isDelivered(Integer status) {
        return ObjectUtil.equals(status, DELIVERED.getStatus());
    }

    /**
     * Judge the specified state，Whether currently in【Cancelled】Status
     *
     * @param status Specify status
     * @return Yes
     */
    public static boolean isCanceled(Integer status) {
        return ObjectUtil.equals(status, CANCELED.getStatus());
    }

    /**
     * Judge the specified state，Are you currently in the process of【Completed】Status
     *
     * @param status Specify status
     * @return Yes
     */
    public static boolean isCompleted(Integer status) {
        return ObjectUtil.equals(status, COMPLETED.getStatus());
    }

    /**
     * Judge the specified state，Has it happened before?【Paid】Status
     *
     * @param status Specify status
     * @return Yes
     */
    public static boolean havePaid(Integer status) {
        return ObjectUtils.equalsAny(status, UNDELIVERED.getStatus(),
                DELIVERED.getStatus(), COMPLETED.getStatus());
    }

    /**
     * Judge the specified state，Has it happened before?【Shipment】Status
     *
     * @param status Specify status
     * @return Yes
     */
    public static boolean haveDelivered(Integer status) {
        return ObjectUtils.equalsAny(status, DELIVERED.getStatus(), COMPLETED.getStatus());
    }

}
