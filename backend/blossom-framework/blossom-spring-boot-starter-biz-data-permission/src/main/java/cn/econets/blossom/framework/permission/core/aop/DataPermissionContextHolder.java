package cn.econets.blossom.framework.permission.core.aop;

import cn.econets.blossom.framework.permission.core.annotation.DataPermission;
import com.alibaba.ttl.TransmittableThreadLocal;

import java.util.LinkedList;
import java.util.List;

/**
 * {@link DataPermission} Annotated Context Context
 *
 */
public class DataPermissionContextHolder {

    /**
     * Use List Reason，There may be nested method calls
     */
    private static final ThreadLocal<LinkedList<DataPermission>> DATA_PERMISSIONS =
            TransmittableThreadLocal.withInitial(LinkedList::new);

    /**
     * Get the current DataPermission Annotation
     *
     * @return DataPermission Annotation
     */
    public static DataPermission get() {
        return DATA_PERMISSIONS.get().peekLast();
    }

    /**
     * Push to stack DataPermission Annotation
     *
     * @param dataPermission DataPermission Annotation
     */
    public static void add(DataPermission dataPermission) {
        DATA_PERMISSIONS.get().addLast(dataPermission);
    }

    /**
     * Out of stack DataPermission Annotation
     *
     * @return DataPermission Annotation
     */
    public static DataPermission remove() {
        DataPermission dataPermission = DATA_PERMISSIONS.get().removeLast();
        // When there is no element，Clear ThreadLocal
        if (DATA_PERMISSIONS.get().isEmpty()) {
            DATA_PERMISSIONS.remove();
        }
        return dataPermission;
    }

    /**
     * Get all DataPermission
     *
     * @return DataPermission Queue
     */
    public static List<DataPermission> getAll() {
        return DATA_PERMISSIONS.get();
    }

    /**
     * Clear context
     *
     * Currently only used for single testing
     */
    public static void clear() {
        DATA_PERMISSIONS.remove();
    }

}
