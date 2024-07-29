package cn.econets.blossom.module.crm.enums.business;

import cn.hutool.core.util.ObjectUtil;
import cn.econets.blossom.framework.common.core.IntArrayValuable;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

// TODO 1）title、description、create Can be deleted，Non-standard javadoc Comment Ha，Then you can add comments to the class；2）CrmBizEndStatus Change to CrmBusinessEndStatus，Don't abbreviate unless necessary，Readability is more important
@RequiredArgsConstructor
@Getter
public enum CrmBizEndStatus implements IntArrayValuable {

    WIN(1, "Win the order"),
    LOSE(2, "Enter order"),
    INVALID(3, "Invalid");

    public static final int[] ARRAYS = Arrays.stream(values()).mapToInt(CrmBizEndStatus::getStatus).toArray();

    // TODO @lzxhqs：Here's the method，It is recommended to put it in 49 After the line；Generally speaking，Static variables，Ordinary variables；Static method；Normal method
    public static boolean isWin(Integer status) {
        return ObjectUtil.equal(WIN.getStatus(), status);
    }

    public static boolean isLose(Integer status) {
        return ObjectUtil.equal(LOSE.getStatus(), status);
    }

    public static boolean isInvalid(Integer status) {
        return ObjectUtil.equal(INVALID.getStatus(), status);
    }

    /**
     * Scene Type
     */
    private final Integer status;
    /**
     * Scene Name
     */
    private final String name;

    @Override
    public int[] array() {
        return ARRAYS;
    }

}
