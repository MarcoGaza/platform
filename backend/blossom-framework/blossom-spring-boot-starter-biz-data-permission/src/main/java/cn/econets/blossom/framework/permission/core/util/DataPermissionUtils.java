package cn.econets.blossom.framework.permission.core.util;

import cn.econets.blossom.framework.permission.core.annotation.DataPermission;
import cn.econets.blossom.framework.permission.core.aop.DataPermissionContextHolder;
import lombok.SneakyThrows;

/**
 * Data permissions Util
 *
 */
public class DataPermissionUtils {

    private static DataPermission DATA_PERMISSION_DISABLE;

    @DataPermission(enable = false)
    @SneakyThrows
    private static DataPermission getDisableDataPermissionDisable() {
        if (DATA_PERMISSION_DISABLE == null) {
            DATA_PERMISSION_DISABLE = DataPermissionUtils.class
                    .getDeclaredMethod("getDisableDataPermissionDisable")
                    .getAnnotation(DataPermission.class);
        }
        return DATA_PERMISSION_DISABLE;
    }

    /**
     * Ignore data permissions，Execute the corresponding logic
     *
     * @param runnable Logic
     */
    public static void executeIgnore(Runnable runnable) {
        DataPermission dataPermission = getDisableDataPermissionDisable();
        DataPermissionContextHolder.add(dataPermission);
        try {
            // Execute runnable
            runnable.run();
        } finally {
            DataPermissionContextHolder.remove();
        }
    }

}
