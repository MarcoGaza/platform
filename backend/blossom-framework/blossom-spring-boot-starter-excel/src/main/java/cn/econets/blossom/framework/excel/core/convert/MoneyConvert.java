package cn.econets.blossom.framework.excel.core.convert;

import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.metadata.GlobalConfiguration;
import com.alibaba.excel.metadata.data.WriteCellData;
import com.alibaba.excel.metadata.property.ExcelContentProperty;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Amount Converter
 *
 * Amount unit：Points
 *
 */
public class MoneyConvert implements Converter<Integer> {

    @Override
    public Class<?> supportJavaTypeKey() {
        throw new UnsupportedOperationException("Not supported yet，Not necessary either");
    }

    @Override
    public CellDataTypeEnum supportExcelTypeKey() {
        throw new UnsupportedOperationException("Not supported yet，Not necessary either");
    }

    @Override
    public WriteCellData<String> convertToExcelData(Integer value, ExcelContentProperty contentProperty,
                                                    GlobalConfiguration globalConfiguration) {
        BigDecimal result = BigDecimal.valueOf(value)
                .divide(new BigDecimal(100), 2, RoundingMode.HALF_UP);
        return new WriteCellData<>(result.toString());
    }

}
