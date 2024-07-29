package cn.econets.blossom.framework.flowable.core.util;

import org.flowable.common.engine.impl.identity.Authentication;

/**
 * Flowable Related tools and methods
 *
 */
public class FlowableUtils {

    // ========== User Related tools and methods ==========

    public static void setAuthenticatedUserId(Long userId) {
        Authentication.setAuthenticatedUserId(String.valueOf(userId));
    }

    public static void clearAuthenticatedUserId() {
        Authentication.setAuthenticatedUserId(null);
    }

    // ========== Execution Related tools and methods ==========

    public static String formatCollectionVariable(String activityId) {
        return activityId + "_assignees";
    }

    public static String formatCollectionElementVariable(String activityId) {
        return activityId + "_assignee";
    }

}
