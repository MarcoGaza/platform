package cn.econets.blossom.module.bpm.controller.admin.task.vo.instance;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Schema(description = "Management Backend - Process instance Response VO")
@Data
public class BpmProcessInstanceRespVO {

    @Schema(description = "Process instance number", requiredMode = Schema.RequiredMode.REQUIRED, example = "1024")
    private String id;

    @Schema(description = "Process Name", requiredMode = Schema.RequiredMode.REQUIRED, example = "Taro Road")
    private String name;

    @Schema(description = "Process Classification-See bpm_model_category Data dictionary", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private String category;

    @Schema(description = "The status of the process instance-See bpm_process_instance_status", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private Integer status;

    @Schema(description = "Results of process instance-See bpm_process_instance_result", requiredMode = Schema.RequiredMode.REQUIRED, example = "2")
    private Integer result;

    @Schema(description = "Submission time", requiredMode = Schema.RequiredMode.REQUIRED)
    private LocalDateTime createTime;

    @Schema(description = "End time", requiredMode = Schema.RequiredMode.REQUIRED)
    private LocalDateTime endTime;

    @Schema(description = "Submitted form value", requiredMode = Schema.RequiredMode.REQUIRED)
    private Map<String, Object> formVariables;

    @Schema(description = "Unique identifier of the business-For example，Leave application number", example = "1")
    private String businessKey;

    /**
     * The user who initiated the process
     */
    private User startUser;

    /**
     * Process definition
     */
    private ProcessDefinition processDefinition;

    @Schema(description = "User Information")
    @Data
    public static class User {

        @Schema(description = "User Number", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
        private Long id;
        @Schema(description = "User Nickname", requiredMode = Schema.RequiredMode.REQUIRED, example = "econets")
        private String nickname;

        @Schema(description = "Department Number", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
        private Long deptId;
        @Schema(description = "Department Name", requiredMode = Schema.RequiredMode.REQUIRED, example = "R&D Department")
        private String deptName;

    }

    @Schema(description = "Process definition information")
    @Data
    public static class ProcessDefinition {

        @Schema(description = "Number", requiredMode = Schema.RequiredMode.REQUIRED, example = "1024")
        private String id;

        @Schema(description = "Form type-See bpm_model_form_type Data dictionary", example = "1")
        private Integer formType;
        @Schema(description = "Form number-In the form type {@link BpmModelFormTypeEnum#CUSTOM} Time，Must not be empty", example = "1024")
        private Long formId;
        @Schema(description = "Form configuration-JSON String。In the form type {@link BpmModelFormTypeEnum#CUSTOM} Time，Must not be empty", requiredMode = Schema.RequiredMode.REQUIRED)
        private String formConf;
        @Schema(description = "Array of form items-JSON Array of strings。In the form type {@link BpmModelFormTypeEnum#CUSTOM} Time，Must not be empty", requiredMode = Schema.RequiredMode.REQUIRED)
        private List<String> formFields;
        @Schema(description = "Customize the submission path of the form，Use Vue The routing address-In the form type {@link BpmModelFormTypeEnum#CUSTOM} Time，Must not be empty",
                example = "/bpm/oa/leave/create")
        private String formCustomCreatePath;
        @Schema(description = "Customize the viewing path of the form，Use Vue The routing address-In the form type {@link BpmModelFormTypeEnum#CUSTOM} Time，Must not be empty",
                example = "/bpm/oa/leave/view")
        private String formCustomViewPath;

        @Schema(description = "BPMN XML", requiredMode = Schema.RequiredMode.REQUIRED)
        private String bpmnXml;

    }

}
