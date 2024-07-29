package cn.econets.blossom.framework.common.util.collection;

import cn.econets.blossom.framework.common.core.KeyValue;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.CollectionUtil;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimap;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

/**
 * Map Tools
 *
 */
public class MapUtils {

    /**
     * From the hash table，Get keys All corresponding value Array
     *
     * @param multimap Hash table
     * @param keys keys
     * @return value Array
     */
    public static <K, V> List<V> getList(Multimap<K, V> multimap, Collection<K> keys) {
        List<V> result = new ArrayList<>();
        keys.forEach(k -> {
            Collection<V> values = multimap.get(k);
            if (CollectionUtil.isEmpty(values)) {
                return;
            }
            result.addAll(values);
        });
        return result;
    }

    /**
     * Search from hash table key Corresponding value，Then further process
     * Attention，If found value for null Time，No processing
     *
     * @param map Hash table
     * @param key key
     * @param consumer Logic for further processing
     */
    public static <K, V> void findAndThen(Map<K, V> map, K key, Consumer<V> consumer) {
        if (CollUtil.isEmpty(map)) {
            return;
        }
        V value = map.get(key);
        if (value == null) {
            return;
        }
        consumer.accept(value);
    }

    public static <K, V> Map<K, V> convertMap(List<KeyValue<K, V>> keyValues) {
        Map<K, V> map = Maps.newLinkedHashMapWithExpectedSize(keyValues.size());
        keyValues.forEach(keyValue -> map.put(keyValue.getKey(), keyValue.getValue()));
        return map;
    }

}
