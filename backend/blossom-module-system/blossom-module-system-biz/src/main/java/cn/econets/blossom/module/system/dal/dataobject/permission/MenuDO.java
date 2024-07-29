package cn.econets.blossom.module.system.dal.dataobject.permission;

import cn.econets.blossom.framework.common.enums.CommonStatusEnum;
import cn.econets.blossom.framework.mybatis.core.dataobject.BaseDO;
import cn.econets.blossom.module.system.enums.permission.MenuTypeEnum;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Menu DO
 *
 */
@TableName("system_menu")
@KeySequence("system_menu_seq") // Used for Oracle、PostgreSQL、Kingbase、DB2、H2 The primary key of the database is automatically increased。If yes MySQL Waiting for database，Optional。
@Data
@EqualsAndHashCode(callSuper = true)
public class MenuDO extends BaseDO {

    /**
     * Menu number - Root node
     */
    public static final Long ID_ROOT = 0L;

    /**
     * Menu number
     */
    @TableId
    private Long id;
    /**
     * Menu Name
     */
    private String name;
    /**
     * Permission flag
     *
     * General format is：${System}:${Module}:${Operation}
     * For example：system:admin:add，That is system Add administrator for service。
     *
     * When we put the MenuDO After being given to the character，Means that the character has the resource：
     * - For the backend，Cooperation @PreAuthorize Annotation，Configuration API The interface requires this permission，Thus API Interface for permission control。
     * - For the front end，Match with front-end tags，Configure whether the button is displayed，Avoid the situation where the user does not have the permission，The result shows the operation。
     */
    private String permission;
    /**
     * Menu Type
     *
     * Enumeration {@link MenuTypeEnum}
     */
    private Integer type;
    /**
     * Display order
     */
    private Integer sort;
    /**
     * Parent MenuID
     */
    private Long parentId;
    /**
     * Routing address
     *
     * If path for http(s) Time，Then it is an external link
     */
    private String path;
    /**
     * Menu icon
     */
    private String icon;
    /**
     * Component path
     */
    private String component;
    /**
     * Component name
     */
    private String componentName;
    /**
     * Status
     *
     * Enumeration {@link CommonStatusEnum}
     */
    private Integer status;
    /**
     * Is it visible?
     *
     * Menu only、Directory usage
     * When set to true Time，This menu will not be displayed in the sidebar，But the route still exists。For example，Some independent editing pages /edit/1024 Wait
     */
    private Boolean visible;
    /**
     * Whether to cache
     *
     * Menu only、Directory usage，No use Vue Routing keep-alive Features
     * Attention：If cache is enabled，Required {@link #componentName} Properties，Otherwise it cannot be cached
     */
    private Boolean keepAlive;
    /**
     * Whether to always display
     *
     * If false Time，When the menu has only one submenu，Don't show yourself，Display submenu directly
     */
    private Boolean alwaysShow;

}
