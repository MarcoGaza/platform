package cn.econets.blossom.framework.flowable.core.context;

import cn.hutool.core.collection.CollUtil;
import com.alibaba.ttl.TransmittableThreadLocal;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Workflow--Contextual information used by the user
 */
public class FlowableContextHolder {

    private static final ThreadLocal<Map<String, List<Long>>> ASSIGNEE = new TransmittableThreadLocal<>();

    /**
     * Through the definition of process tasks key ，Get the pre-selected approver
     * Purpose of this method：When a process instance is created for the first time，Cannot find anything in the database yet assignee Field，So store it in the context to get it
     *
     * @param taskDefinitionKey Process Task key
     * @return Approver ID Collection
     */
    public static List<Long> getAssigneeByTaskDefinitionKey(String taskDefinitionKey) {
        if (CollUtil.isNotEmpty(ASSIGNEE.get())) {
            return ASSIGNEE.get().get(taskDefinitionKey);
        }
        return Collections.emptyList();
    }

    /**
     * Store the pre-selected approver into the context thread variable
     *
     * @param assignee Process Task key -> Approver ID Jionghe
     */
    public static void setAssignee(Map<String, List<Long>> assignee) {
        ASSIGNEE.set(assignee);
    }

}
