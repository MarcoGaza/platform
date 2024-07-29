package cn.econets.blossom.framework.common.util.object;

import cn.econets.blossom.framework.common.pojo.PageResult;
import cn.econets.blossom.framework.common.util.collection.CollectionUtils;
import cn.hutool.core.bean.BeanUtil;

import java.util.List;
import java.util.function.Consumer;

/**
 * Bean Tools
 *
 * 1. Default use {@link BeanUtil} As an implementation class，Although different bean The performance of the tools varies，But for most of the students' projects，Don't worry about this performance
 * 2. For complex object conversion，You can search for references AuthConvert Realization，Passed mapstruct + default Cooperation to achieve
 *
 */
public class BeanUtils {

    public static <T> T toBean(Object source, Class<T> targetClass) {
        return BeanUtil.toBean(source, targetClass);
    }

    public static <T> T toBean(Object source, Class<T> targetClass, Consumer<T> peek) {
        T target = toBean(source, targetClass);
        if (target != null) {
            peek.accept(target);
        }
        return target;
    }

    public static <S, T> List<T> toBean(List<S> source, Class<T> targetType) {
        if (source == null) {
            return null;
        }
        return CollectionUtils.convertList(source, s -> toBean(s, targetType));
    }

    public static <S, T> List<T> toBean(List<S> source, Class<T> targetType, Consumer<T> peek) {
        List<T> list = toBean(source, targetType);
        if (list != null) {
            list.forEach(peek);
        }
        return list;
    }

    public static <S, T> PageResult<T> toBean(PageResult<S> source, Class<T> targetType) {
        return toBean(source, targetType, null);
    }

    public static <S, T> PageResult<T> toBean(PageResult<S> source, Class<T> targetType, Consumer<T> peek) {
        if (source == null) {
            return null;
        }
        List<T> list = toBean(source.getList(), targetType);
        if (peek != null) {
            list.forEach(peek);
        }
        return new PageResult<>(list, source.getTotal());
    }

}
