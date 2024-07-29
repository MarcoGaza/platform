package cn.econets.blossom.module.system.api.notify.dto;

import cn.econets.blossom.framework.common.enums.CommonStatusEnum;
import cn.econets.blossom.framework.common.validation.InEnum;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class NotifyTemplateReqDTO {

    @NotEmpty(message = "Template name cannot be empty")
    private String name;

    @NotNull(message = "Template code cannot be empty")
    private String code;

    @NotNull(message = "Template type cannot be empty")
    private Integer type;

    @NotEmpty(message = "The sender name cannot be empty")
    private String nickname;

    @NotEmpty(message = "Template content cannot be empty")
    private String content;

    @NotNull(message = "Status cannot be empty")
    @InEnum(value = CommonStatusEnum.class, message = "The status must be {value}")
    private Integer status;

    private String remark;

}
