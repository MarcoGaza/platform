package cn.econets.blossom.framework.common.util.object;


import cn.econets.blossom.framework.common.pojo.PageParam;
import cn.econets.blossom.framework.common.pojo.SortablePageParam;
import cn.econets.blossom.framework.common.pojo.SortingField;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.func.Func1;
import cn.hutool.core.lang.func.LambdaUtil;
import cn.hutool.core.util.ArrayUtil;
import org.springframework.util.Assert;

import static java.util.Collections.singletonList;

/**
 * {@link PageParam} Tools
 *
 */
public class PageUtils {

    private static final Object[] ORDER_TYPES = new String[]{SortingField.ORDER_ASC, SortingField.ORDER_DESC};

    public static int getStart(PageParam pageParam) {
        return (pageParam.getPageNo() - 1) * pageParam.getPageSize();
    }

    /**
     * Build sort fields（Default reverse order）
     *
     * @param func Sort fields Lambda Expression
     * @param <T>  Type of sorting field
     * @return Sort Field
     */
    public static <T> SortingField buildSortingField(Func1<T, ?> func) {
        return buildSortingField(func, SortingField.ORDER_DESC);
    }

    /**
     * Build sort fields
     *
     * @param func  Sort fields Lambda Expression
     * @param order Sort type {@link SortingField#ORDER_ASC} {@link SortingField#ORDER_DESC}
     * @param <T>   The type of sorting field
     * @return Sort Field
     */
    public static <T> SortingField buildSortingField(Func1<T, ?> func, String order) {
        Assert.isTrue(ArrayUtil.contains(ORDER_TYPES, order), String.format("The sort type of the field can only be %s/%s", ORDER_TYPES));

        String fieldName = LambdaUtil.getFieldName(func);
        return new SortingField(fieldName, order);
    }

    /**
     * Build a default sort field
     * If the sort field is empty，Set the sorting field；Otherwise ignored
     *
     * @param sortablePageParam Sorting and paging query parameters
     * @param func              Sort fields Lambda Expression
     * @param <T>               The type of sorting field
     */
    public static <T> void buildDefaultSortingField(SortablePageParam sortablePageParam, Func1<T, ?> func) {
        if (sortablePageParam != null && CollUtil.isEmpty(sortablePageParam.getSortingFields())) {
            sortablePageParam.setSortingFields(singletonList(buildSortingField(func)));
        }
    }

}
