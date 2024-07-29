package cn.econets.blossom.module.system.controller.admin.user.vo.user;

import cn.econets.blossom.framework.excel.core.annotations.DictFormat;
import cn.econets.blossom.framework.excel.core.convert.DictConvert;
import cn.econets.blossom.module.system.enums.DictTypeConstants;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Set;

@Schema(description = "Management Backend - User Information Response VO")
@Data
@ExcelIgnoreUnannotated
public class UserRespVO{

    @Schema(description = "User Number", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @ExcelProperty("User Number")
    private Long id;

    @Schema(description = "User Account", requiredMode = Schema.RequiredMode.REQUIRED, example = "admin")
    @ExcelProperty("User Name")
    private String username;

    @Schema(description = "User Nickname", requiredMode = Schema.RequiredMode.REQUIRED, example = "econets")
    @ExcelProperty("User Nickname")
    private String nickname;

    @Schema(description = "Remarks", example = "I am a user")
    private String remark;

    @Schema(description = "DepartmentID", example = "I am a user")
    private Long deptId;
    @Schema(description = "Department Name", example = "IT Department")
    @ExcelProperty("Department Name")
    private String deptName;

    @Schema(description = "Position number array", example = "1")
    private Set<Long> postIds;

    @Schema(description = "User mailbox", example = "ryximu@qq.com")
    @ExcelProperty("User mailbox")
    private String email;

    @Schema(description = "Mobile phone number", example = "15601691300")
    @ExcelProperty("Mobile phone number")
    private String mobile;

    @Schema(description = "User gender，See SexEnum Enumeration class", example = "1")
    @ExcelProperty(value = "User gender", converter = DictConvert.class)
    @DictFormat(DictTypeConstants.USER_SEX)
    private Integer sex;

    @Schema(description = "User avatar", example = "https://www.econets.cn/xxx.png")
    private String avatar;

    @Schema(description = "Status，See CommonStatusEnum Enumeration class", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @ExcelProperty(value = "Account Status", converter = DictConvert.class)
    @DictFormat(DictTypeConstants.COMMON_STATUS)
    private Integer status;

    @Schema(description = "Last login IP", requiredMode = Schema.RequiredMode.REQUIRED, example = "192.168.1.1")
    @ExcelProperty("Last loginIP")
    private String loginIp;

    @Schema(description = "Last login time", requiredMode = Schema.RequiredMode.REQUIRED, example = "Timestamp format")
    @ExcelProperty("Last login time")
    private LocalDateTime loginDate;

    @Schema(description = "Creation time", requiredMode = Schema.RequiredMode.REQUIRED, example = "Timestamp format")
    private LocalDateTime createTime;

}
