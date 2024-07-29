package cn.econets.blossom.framework.mybatis.core.type;

import cn.econets.blossom.framework.common.util.json.JsonUtils;
import com.baomidou.mybatisplus.extension.handlers.AbstractJsonTypeHandler;
import com.fasterxml.jackson.core.type.TypeReference;

import java.util.Set;

/**
 * Reference {@link com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler} Realization
 * Before we deserialize the string into Set and the generic type is Long Time，If the value of each element is too small，will be processed as Integer Type，May lead to hidden BUG。
 *
 * For example，SysUserDO of postIds Properties
 *
 *
 */
public class JsonLongSetTypeHandler extends AbstractJsonTypeHandler<Object> {

    private static final TypeReference<Set<Long>> TYPE_REFERENCE = new TypeReference<Set<Long>>(){};

    @Override
    protected Object parse(String json) {
        return JsonUtils.parseObject(json, TYPE_REFERENCE);
    }

    @Override
    protected String toJson(Object obj) {
        return JsonUtils.toJsonString(obj);
    }

}
