package cn.econets.blossom.framework.common.enums;

import cn.econets.blossom.framework.common.core.IntArrayValuable;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

/**
 * Terminal enumeration
 *
 */
@RequiredArgsConstructor
@Getter
public enum TerminalEnum implements IntArrayValuable {

    UNKNOWN(0, "Unknown"), // Purpose：Unable to parse terminal Time，Use it
    WECHAT_MINI_PROGRAM(10, "WeChat Mini Program"),
    WECHAT_WAP(11, "WeChat public account"),
    H5(20, "H5 Webpage"),
    APP(31, "Mobile phone App"),
    ;

    public static final int[] ARRAYS = Arrays.stream(values()).mapToInt(TerminalEnum::getTerminal).toArray();

    /**
     * Terminal
     */
    private final Integer terminal;
    /**
     * Terminal name
     */
    private final String name;

    @Override
    public int[] array() {
        return ARRAYS;
    }
}
