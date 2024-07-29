package cn.econets.blossom.framework.ip.core.utils;

import cn.econets.blossom.framework.common.util.collection.CollectionUtils;
import cn.econets.blossom.framework.common.util.object.ObjectUtils;
import cn.econets.blossom.framework.ip.core.Area;
import cn.econets.blossom.framework.ip.core.enums.AreaTypeEnum;
import cn.hutool.core.io.resource.ResourceUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.text.csv.CsvRow;
import cn.hutool.core.text.csv.CsvUtil;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;


/**
 * Regional Tools
 *
 */
@Slf4j
public class AreaUtils {

    /**
     * Initialization SEARCHER
     */
    @SuppressWarnings("InstantiationOfUtilityClass")
    private final static AreaUtils INSTANCE = new AreaUtils();

    /**
     * Area Memory Cache，Improve access speed
     */
    private static Map<Integer, Area> areas;

    private AreaUtils() {
        long now = System.currentTimeMillis();
        areas = new HashMap<>();
        areas.put(Area.ID_GLOBAL, new Area(Area.ID_GLOBAL, "Global", 0,
                null, new ArrayList<>()));
        // From csv Loading data
        List<CsvRow> rows = CsvUtil.getReader().read(ResourceUtil.getUtf8Reader("area.csv")).getRows();
        rows.remove(0); // Delete header
        for (CsvRow row : rows) {
            // Create Area Object
            Area area = new Area(Integer.valueOf(row.get(0)), row.get(1), Integer.valueOf(row.get(2)),
                    null, new ArrayList<>());
            // Add to areas Medium
            areas.put(area.getId(), area);
        }

        // Building parent-child relationship：Because Area Not in parentId Field，So it needs to be read repeatedly
        for (CsvRow row : rows) {
            Area area = areas.get(Integer.valueOf(row.get(0))); // Myself
            Area parent = areas.get(Integer.valueOf(row.get(3))); // Father
            Assert.isTrue(area != parent, "{}:The parent and child nodes are the same", area.getName());
            area.setParent(parent);
            parent.getChildren().add(area);
        }
        log.info("Start loading AreaUtils Success，Time-consuming ({}) Milliseconds", System.currentTimeMillis() - now);
    }

    /**
     * Get the area corresponding to the specified number
     *
     * @param id Area Number
     * @return Region
     */
    public static Area getArea(Integer id) {
        return areas.get(id);
    }

    /**
     * Format area
     *
     * @param id Area Number
     * @return Formatted area
     */
    public static String format(Integer id) {
        return format(id, " ");
    }

    /**
     * Format area
     *
     * For example：
     *      1. id = “Jing'an District”Time：Shanghai Shanghai Jing'an District
     *      2. id = “Shanghai”Time：Shanghai Shanghai
     *      3. id = “Shanghai”Time：Shanghai
     *      4. id = “United States”Time：United States
     * When the region is in China，China is not displayed by default
     *
     * @param id Area Number
     * @param separator Separator
     * @return Formatted area
     */
    public static String format(Integer id, String separator) {
        // Get area
        Area area = areas.get(id);
        if (area == null) {
            return null;
        }

        // Format
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < AreaTypeEnum.values().length; i++) { // Avoid infinite loop
            sb.insert(0, area.getName());
            // “Recursion”Parent node
            area = area.getParent();
            if (area == null
                    || ObjectUtils.equalsAny(area.getId(), Area.ID_GLOBAL, Area.ID_CHINA)) { // Skip the case where the parent node is China
                break;
            }
            sb.insert(0, separator);
        }
        return sb.toString();
    }

    /**
     * Get a list of areas of a specified type
     *
     * @param type Region Type
     * @param func Conversion function
     * @param <T>  Result type
     * @return Region List
     */
    public static <T> List<T> getByType(AreaTypeEnum type, Function<Area, T> func) {
        return CollectionUtils.convertList(areas.values(), func, area -> type.getType().equals(area.getType()));
    }

    /**
     * According to the area number、Parent area type，Get the parent area number
     *
     * @param id   Area Number
     * @param type Region Type
     * @return Superior area number
     */
    public static Integer getParentIdByType(Integer id, @NonNull AreaTypeEnum type) {
        for (int i = 0; i < Byte.MAX_VALUE; i++) {
            Area area = AreaUtils.getArea(id);
            if (area == null) {
                return null;
            }
            // Situation 1：Matched，Return it
            if (type.getType().equals(area.getType())) {
                return area.getId();
            }
            // Case 2：Found the root node，Returns empty
            if (area.getParent() == null || area.getParent().getId() == null) {
                return null;
            }
            // Other：Continue to search upwards
            id = area.getParent().getId();
        }
        return null;
    }

}
