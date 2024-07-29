package cn.econets.blossom.framework.common.util.number;

import cn.hutool.core.math.Money;
import cn.hutool.core.util.NumberUtil;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Amount Tools
 *
 */
public class MoneyUtils {

    /**
     * Number of decimal places for amount
     */
    private static final int PRICE_SCALE = 2;

    /**
     * Percentage corresponding to BigDecimal Object
     */
    public static final BigDecimal PERCENT_100 = BigDecimal.valueOf(100);

    /**
     * Calculate percentage amount，Rounding
     *
     * @param price Amount
     * @param rate  Percentage，For example 56.77% Then pass in 56.77
     * @return Percentage amount
     */
    public static Integer calculateRatePrice(Integer price, Double rate) {
        return calculateRatePrice(price, rate, 0, RoundingMode.HALF_UP).intValue();
    }

    /**
     * Calculate percentage amount，Downward transfer
     *
     * @param price Amount
     * @param rate  Percentage，For example 56.77% Then pass in 56.77
     * @return Percentage amount
     */
    public static Integer calculateRatePriceFloor(Integer price, Double rate) {
        return calculateRatePrice(price, rate, 0, RoundingMode.FLOOR).intValue();
    }

    /**
     * Calculate percentage amount
     *
     * @param price   Amount（Unit points）
     * @param count   Quantity
     * @param percent Discount（Unit points），List of examples 60.2%，Then pass in 6020
     * @return Total price of goods
     */
    public static Integer calculator(Integer price, Integer count, Integer percent) {
        price = price * count;
        if (percent == null) {
            return price;
        }
        return MoneyUtils.calculateRatePriceFloor(price, (double) (percent / 100));
    }

    /**
     * Calculate percentage amount
     *
     * @param price        Amount
     * @param rate         Percentage，For example 56.77% Then pass in 56.77
     * @param scale        Keep decimal places
     * @param roundingMode Rounding mode
     */
    public static BigDecimal calculateRatePrice(Number price, Number rate, int scale, RoundingMode roundingMode) {
        return NumberUtil.toBigDecimal(price).multiply(NumberUtil.toBigDecimal(rate)) // Multiply
                .divide(BigDecimal.valueOf(100), scale, roundingMode); // divide by 100
    }

    /**
     * Transfer to Yuan
     *
     * @param fen Points
     * @return Yuan
     */
    public static BigDecimal fenToYuan(int fen) {
        return new Money(0, fen).getAmount();
    }

    /**
     * Transfer to Yuan（String）
     *
     * For example fen for 1 Time，The result is 0.01
     *
     * @param fen Points
     * @return Yuan
     */
    public static String fenToYuanStr(int fen) {
        return new Money(0, fen).toString();
    }

    /**
     * Amount multiplication，Round by default
     *
     * Digits：{@link #PRICE_SCALE}
     *
     * @param price Amount
     * @param count Quantity
     * @return Amount multiplication result
     */
    public static BigDecimal priceMultiply(BigDecimal price, BigDecimal count) {
        if (price == null || count == null) {
            return null;
        }
        return price.multiply(count).setScale(PRICE_SCALE, RoundingMode.HALF_UP);
    }

    /**
     * Amount multiplied（Percentage），Round by default
     *
     * Digits：{@link #PRICE_SCALE}
     *
     * @param price  Amount
     * @param percent Percentage
     * @return Amount multiplication result
     */
    public static BigDecimal priceMultiplyPercent(BigDecimal price, BigDecimal percent) {
        if (price == null || percent == null) {
            return null;
        }
        return price.multiply(percent).divide(PERCENT_100, PRICE_SCALE, RoundingMode.HALF_UP);
    }
}
