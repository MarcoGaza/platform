package cn.econets.blossom.framework.flowable.core.util;

import cn.hutool.core.collection.CollUtil;
import org.flowable.bpmn.converter.BpmnXMLConverter;
import org.flowable.bpmn.model.Process;
import org.flowable.bpmn.model.*;

import java.util.*;

/**
 * Process model to operation tool class
 */
public class BpmnModelUtils {

    /**
     * Based on the node，Get the entry connection
     *
     * @param source Starting Node
     * @return Entry connection list
     */
    public static List<SequenceFlow> getElementIncomingFlows(FlowElement source) {
        if (source instanceof FlowNode) {
            return ((FlowNode) source).getIncomingFlows();
        }
        return new ArrayList<>();
    }

    /**
     * Based on the node，Get the export connection
     *
     * @param source Starting Node
     * @return Export connection list
     */
    public static List<SequenceFlow> getElementOutgoingFlows(FlowElement source) {
        if (source instanceof FlowNode) {
            return ((FlowNode) source).getOutgoingFlows();
        }
        return new ArrayList<>();
    }

    /**
     * Get process element information
     *
     * @param model         bpmnModel Object
     * @param flowElementId Elements ID
     * @return Element Information
     */
    public static FlowElement getFlowElementById(BpmnModel model, String flowElementId) {
        Process process = model.getMainProcess();
        return process.getFlowElement(flowElementId);
    }

    /**
     * Obtain BPMN In process，Specified elements
     *
     * @param model
     * @param clazz Specify element。For example，{@link UserTask}、{@link Gateway} Wait
     * @return Elements
     */
    public static <T extends FlowElement> List<T> getBpmnModelElements(BpmnModel model, Class<T> clazz) {
        List<T> result = new ArrayList<>();
        model.getProcesses().forEach(process -> {
            process.getFlowElements().forEach(flowElement -> {
                if (flowElement.getClass().isAssignableFrom(clazz)) {
                    result.add((T) flowElement);
                }
            });
        });
        return result;
    }

    /**
     * Compare TwobpmnModel Are they the same?
     * @param oldModel  Oldbpmn model
     * @param newModel Newbpmn model
     */
    public static boolean equals(BpmnModel oldModel, BpmnModel newModel) {
        // Because BpmnModel Not provided equals Method，So it can only be converted into a byte array，Compare
        return Arrays.equals(getBpmnBytes(oldModel), getBpmnBytes(newModel));
    }

    /**
     * Put bpmnModel Convert to byte[]
     * @param model  bpmnModel
     */
    public static byte[] getBpmnBytes(BpmnModel model) {
        if (model == null) {
            return new byte[0];
        }
        BpmnXMLConverter converter = new BpmnXMLConverter();
        return converter.convertToXML(model);
    }

    // ========== Traversal related methods ==========

    /**
     * Found source All user task nodes before this node
     *
     * @param source          Starting Node
     * @param hasSequenceFlow Already passed the connection ID，Used to determine whether the line is repeated
     * @param userTaskList    User task node found
     * @return User task node Array
     */
    public static List<UserTask> getPreviousUserTaskList(FlowElement source, Set<String> hasSequenceFlow, List<UserTask> userTaskList) {
        userTaskList = userTaskList == null ? new ArrayList<>() : userTaskList;
        hasSequenceFlow = hasSequenceFlow == null ? new HashSet<>() : hasSequenceFlow;
        // If this node is the start node，and there is an upper-level child node，Continue iterating along the parent child node
        if (source instanceof StartEvent && source.getSubProcess() != null) {
            userTaskList = getPreviousUserTaskList(source.getSubProcess(), hasSequenceFlow, userTaskList);
        }

        // By type，Get the entry connection
        List<SequenceFlow> sequenceFlows = getElementIncomingFlows(source);
        if (sequenceFlows == null) {
            return userTaskList;
        }
        // Loop to find the target element
        for (SequenceFlow sequenceFlow : sequenceFlows) {
            // If duplicate connections are found，It means it is looping，Skip this loop
            if (hasSequenceFlow.contains(sequenceFlow.getId())) {
                continue;
            }
            // Add the connection that has been traveled
            hasSequenceFlow.add(sequenceFlow.getId());
            // Type is user node，Then add a parent node
            if (sequenceFlow.getSourceFlowElement() instanceof UserTask) {
                userTaskList.add((UserTask) sequenceFlow.getSourceFlowElement());
            }
            // Type is subprocess，Add the node connected to the exit of the sub-process start node
            if (sequenceFlow.getSourceFlowElement() instanceof SubProcess) {
                // Get the sub-process user task node
                List<UserTask> childUserTaskList = findChildProcessUserTaskList((StartEvent) ((SubProcess) sequenceFlow.getSourceFlowElement()).getFlowElements().toArray()[0], null, null);
                // If the node is found，This means that the line has found a node，Do not continue searching downwards，Continue otherwise
                if (CollUtil.isNotEmpty(childUserTaskList)) {
                    userTaskList.addAll(childUserTaskList);
                }
            }
            // Continue iteration
            userTaskList = getPreviousUserTaskList(sequenceFlow.getSourceFlowElement(), hasSequenceFlow, userTaskList);
        }
        return userTaskList;
    }

    /**
     * Iterate to get sub-process user task nodes
     *
     * @param source          Starting Node
     * @param hasSequenceFlow Already passed the connection ID，Used to determine whether the line is repeated
     * @param userTaskList    User task list that needs to be withdrawn
     * @return User task node
     */
    public static List<UserTask> findChildProcessUserTaskList(FlowElement source, Set<String> hasSequenceFlow, List<UserTask> userTaskList) {
        hasSequenceFlow = hasSequenceFlow == null ? new HashSet<>() : hasSequenceFlow;
        userTaskList = userTaskList == null ? new ArrayList<>() : userTaskList;

        // By type，Get the export connection
        List<SequenceFlow> sequenceFlows = getElementOutgoingFlows(source);
        if (sequenceFlows == null) {
            return userTaskList;
        }
        // Loop to find the target element
        for (SequenceFlow sequenceFlow : sequenceFlows) {
            // If duplicate connections are found，It means it is looping，Skip this loop
            if (hasSequenceFlow.contains(sequenceFlow.getId())) {
                continue;
            }
            // Add the connection that has been traveled
            hasSequenceFlow.add(sequenceFlow.getId());
            // If it is a user task type，And the task node Key Exists in the running task，Add
            if (sequenceFlow.getTargetFlowElement() instanceof UserTask) {
                userTaskList.add((UserTask) sequenceFlow.getTargetFlowElement());
                continue;
            }
            // If the node is a sub-process node，Start from the first node in the node
            if (sequenceFlow.getTargetFlowElement() instanceof SubProcess) {
                List<UserTask> childUserTaskList = findChildProcessUserTaskList((FlowElement) (((SubProcess) sequenceFlow.getTargetFlowElement()).getFlowElements().toArray()[0]), hasSequenceFlow, null);
                // If the node is found，This means that the line has found a node，Do not continue searching downwards，Continue otherwise
                if (CollUtil.isNotEmpty(childUserTaskList)) {
                    userTaskList.addAll(childUserTaskList);
                    continue;
                }
            }
            // Continue iteration
            userTaskList = findChildProcessUserTaskList(sequenceFlow.getTargetFlowElement(), hasSequenceFlow, userTaskList);
        }
        return userTaskList;
    }


    /**
     * Iterative scanning from back to front，Judge whether the target node is serial relative to the current node
     * There is no direct fallback to the sub-process，But there is a situation where the sub-process goes out to the parent process
     *
     * @param source          Starting Node
     * @param target          Target node
     * @param visitedElements Already passed the connection ID，Used to determine whether the line is repeated
     * @return Results
     */
    public static boolean isSequentialReachable(FlowElement source, FlowElement target, Set<String> visitedElements) {
        visitedElements = visitedElements == null ? new HashSet<>() : visitedElements;
        // Cannot be a start event or a subprocess
        if (source instanceof StartEvent && isInEventSubprocess(source)) {
            return false;
        }

        // By type，Get the entry connection
        List<SequenceFlow> sequenceFlows = getElementIncomingFlows(source);
        if (CollUtil.isEmpty(sequenceFlows)) {
            return true;
        }
        // Loop to find the target element
        for (SequenceFlow sequenceFlow : sequenceFlows) {
            // If duplicate connections are found，It means it is looping，Skip this loop
            if (visitedElements.contains(sequenceFlow.getId())) {
                continue;
            }
            // Add the connection that has been traveled
            visitedElements.add(sequenceFlow.getId());
            // This line has a target node，This route is completed，Enter the next line
            FlowElement sourceFlowElement = sequenceFlow.getSourceFlowElement();
            if (target.getId().equals(sourceFlowElement.getId())) {
                continue;
            }
            // If the target node is a parallel gateway，Do not continue
            if (sourceFlowElement instanceof ParallelGateway) {
                return false;
            }
            // Otherwise continue iterating
            if (!isSequentialReachable(sourceFlowElement, target, visitedElements)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Judge whether the current node belongs to a different sub-process
     *
     * @param flowElement The node being judged
     * @return true Indicates that it belongs to a sub-process
     */
    private static boolean isInEventSubprocess(FlowElement flowElement) {
        FlowElementsContainer flowElementsContainer = flowElement.getParentContainer();
        while (flowElementsContainer != null) {
            if (flowElementsContainer instanceof EventSubProcess) {
                return true;
            }

            if (flowElementsContainer instanceof FlowElement) {
                flowElementsContainer = ((FlowElement) flowElementsContainer).getParentContainer();
            } else {
                flowElementsContainer = null;
            }
        }
        return false;
    }

    /**
     * Based on the running task nodes，Iterate to get the list of child task nodes，Look back
     *
     * @param source          Starting Node
     * @param runTaskKeyList  Running tasks Key，Used to verify whether the task node is a running node
     * @param hasSequenceFlow Already passed the connection ID，Used to determine whether the line is repeated
     * @param userTaskList    User task list that needs to be withdrawn
     * @return Sub-task node list
     */
    public static List<UserTask> iteratorFindChildUserTasks(FlowElement source, List<String> runTaskKeyList,
                                                            Set<String> hasSequenceFlow, List<UserTask> userTaskList) {
        hasSequenceFlow = hasSequenceFlow == null ? new HashSet<>() : hasSequenceFlow;
        userTaskList = userTaskList == null ? new ArrayList<>() : userTaskList;
        // If this node is the start node，and there is an upper-level child node，Continue iterating along the upper-level child nodes
        if (source instanceof StartEvent && source.getSubProcess() != null) {
            userTaskList = iteratorFindChildUserTasks(source.getSubProcess(), runTaskKeyList, hasSequenceFlow, userTaskList);
        }

        // By type，Get the export connection
        List<SequenceFlow> sequenceFlows = getElementOutgoingFlows(source);
        if (sequenceFlows == null) {
            return userTaskList;
        }
        // Loop to find the target element
        for (SequenceFlow sequenceFlow : sequenceFlows) {
            // If duplicate connections are found，It means it is looping，Skip this loop
            if (hasSequenceFlow.contains(sequenceFlow.getId())) {
                continue;
            }
            // Add the connection that has been traveled
            hasSequenceFlow.add(sequenceFlow.getId());
            // If it is a user task type，and the task node Key Exists in the running task，Add
            if (sequenceFlow.getTargetFlowElement() instanceof UserTask && runTaskKeyList.contains((sequenceFlow.getTargetFlowElement()).getId())) {
                userTaskList.add((UserTask) sequenceFlow.getTargetFlowElement());
                continue;
            }
            // If the node is a sub-process node，Start from the first node in the node
            if (sequenceFlow.getTargetFlowElement() instanceof SubProcess) {
                List<UserTask> childUserTaskList = iteratorFindChildUserTasks((FlowElement) (((SubProcess) sequenceFlow.getTargetFlowElement()).getFlowElements().toArray()[0]), runTaskKeyList, hasSequenceFlow, null);
                // If the node is found，This means that the line has found a node，Do not continue searching downwards，Continue otherwise
                if (CollUtil.isNotEmpty(childUserTaskList)) {
                    userTaskList.addAll(childUserTaskList);
                    continue;
                }
            }
            // Continue iteration
            userTaskList = iteratorFindChildUserTasks(sequenceFlow.getTargetFlowElement(), runTaskKeyList, hasSequenceFlow, userTaskList);
        }
        return userTaskList;
    }

}
