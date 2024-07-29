package cn.econets.blossom.framework.operatelog.core.util;

import cn.econets.blossom.framework.operatelog.core.aop.OperateLogAspect;

/**
 * Operation log tools
 * Current main function，Provided to the business code，Record operation details and extended fields
 *
 */
public class OperateLogUtils {

    public static void setContent(String content) {
        OperateLogAspect.setContent(content);
    }

    public static void addExt(String key, Object value) {
        OperateLogAspect.addExt(key, value);
    }

}
