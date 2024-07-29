package cn.econets.blossom.framework.common.util.monitor;

import org.apache.skywalking.apm.toolkit.trace.TraceContext;

/**
 * Link tracking tools
 *
 * Taking into account each starter This tool class is required，So put it in common Under the module util Book now
 *
 */
public class TracerUtils {

    /**
     * Privatization construction method
     */
    private TracerUtils() {
    }

    /**
     * Get link tracking number，Return directly SkyWalking of TraceId。
     * Empty string if it does not exist！！！
     *
     * @return Link tracking number
     */
    public static String getTraceId() {
        return TraceContext.traceId();
    }

}
