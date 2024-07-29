package cn.econets.blossom.framework.ip.core;

import cn.econets.blossom.framework.ip.core.enums.AreaTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Regional Node，Including countries、Province、City、Region and other information
 *
 * Data is visible resources/area.csv File
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Area {

    /**
     * Number - Global，The root directory
     */
    public static final Integer ID_GLOBAL = 0;
    /**
     * Number - China
     */
    public static final Integer ID_CHINA = 1;

    /**
     * Number
     */
    private Integer id;
    /**
     * Name
     */
    private String name;
    /**
     * Type
     *
     * Enumeration {@link AreaTypeEnum}
     */
    private Integer type;

    /**
     * Parent node
     */
    private Area parent;
    /**
     * Subnode
     */
    private List<Area> children;

}
