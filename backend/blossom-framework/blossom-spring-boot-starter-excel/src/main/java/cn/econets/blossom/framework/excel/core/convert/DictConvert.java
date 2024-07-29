package cn.econets.blossom.framework.excel.core.convert;

import cn.econets.blossom.framework.dict.core.util.DictFrameworkUtils;
import cn.econets.blossom.framework.excel.core.annotations.DictFormat;
import cn.hutool.core.convert.Convert;
import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.metadata.GlobalConfiguration;
import com.alibaba.excel.metadata.data.ReadCellData;
import com.alibaba.excel.metadata.data.WriteCellData;
import com.alibaba.excel.metadata.property.ExcelContentProperty;
import lombok.extern.slf4j.Slf4j;

/**
 * Excel Data dictionary converter
 *
 */
@Slf4j
public class DictConvert implements Converter<Object> {

    @Override
    public Class<?> supportJavaTypeKey() {
        throw new UnsupportedOperationException("Not supported yet，Not necessary either");
    }

    @Override
    public CellDataTypeEnum supportExcelTypeKey() {
        throw new UnsupportedOperationException("Not supported yet，Not necessary either");
    }

    @Override
    public Object convertToJavaData(ReadCellData readCellData, ExcelContentProperty contentProperty,
                                    GlobalConfiguration globalConfiguration) {
        // Use dictionary parsing
        String type = getType(contentProperty);
        String label = readCellData.getStringValue();
        String value = DictFrameworkUtils.parseDictDataValue(type, label);
        if (value == null) {
            log.error("[convertToJavaData][type({}) Cannot be parsed label({})]", type, label);
            return null;
        }
        // Will String of value Convert to corresponding attributes
        Class<?> fieldClazz = contentProperty.getField().getType();
        return Convert.convert(fieldClazz, value);
    }

    @Override
    public WriteCellData<String> convertToExcelData(Object object, ExcelContentProperty contentProperty,
                                                    GlobalConfiguration globalConfiguration) {
        // Space Time，Returns empty
        if (object == null) {
            return new WriteCellData<>("");
        }

        // Use dictionary formatting
        String type = getType(contentProperty);
        String value = String.valueOf(object);
        String label = DictFrameworkUtils.getDictDataLabel(type, value);
        if (label == null) {
            log.error("[convertToExcelData][type({}) Cannot convert label({})]", type, value);
            return new WriteCellData<>("");
        }
        // Generate Excel Small table
        return new WriteCellData<>(label);
    }

    private static String getType(ExcelContentProperty contentProperty) {
        return contentProperty.getField().getAnnotation(DictFormat.class).value();
    }

}
