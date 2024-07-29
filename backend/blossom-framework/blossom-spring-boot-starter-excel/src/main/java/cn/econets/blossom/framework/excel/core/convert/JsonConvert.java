package cn.econets.blossom.framework.excel.core.convert;

import cn.econets.blossom.framework.common.util.json.JsonUtils;
import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.metadata.GlobalConfiguration;
import com.alibaba.excel.metadata.data.WriteCellData;
import com.alibaba.excel.metadata.property.ExcelContentProperty;

/**
 * Excel Json Converter
 *
 */
public class JsonConvert implements Converter<Object> {

    @Override
    public Class<?> supportJavaTypeKey() {
        throw new UnsupportedOperationException("Not supported yet，Not necessary either");
    }

    @Override
    public CellDataTypeEnum supportExcelTypeKey() {
        throw new UnsupportedOperationException("Not supported yet，Not necessary either");
    }

    @Override
    public WriteCellData<String> convertToExcelData(Object value, ExcelContentProperty contentProperty,
                                                    GlobalConfiguration globalConfiguration) {
        // Generate Excel Small table
        return new WriteCellData<>(JsonUtils.toJsonString(value));
    }

}
