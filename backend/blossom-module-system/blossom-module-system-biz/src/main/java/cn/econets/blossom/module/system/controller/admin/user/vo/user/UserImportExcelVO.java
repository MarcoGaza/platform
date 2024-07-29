package cn.econets.blossom.module.system.controller.admin.user.vo.user;

import cn.econets.blossom.framework.excel.core.annotations.DictFormat;
import cn.econets.blossom.framework.excel.core.convert.DictConvert;
import cn.econets.blossom.module.system.enums.DictTypeConstants;
import com.alibaba.excel.annotation.ExcelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * User Excel Import VO
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = false) // Settings chain = false，Avoid user import problems
public class UserImportExcelVO {

    @ExcelProperty("Login Name")
    private String username;

    @ExcelProperty("User Name")
    private String nickname;

    @ExcelProperty("Department Number")
    private Long deptId;

    @ExcelProperty("User mailbox")
    private String email;

    @ExcelProperty("Mobile phone number")
    private String mobile;

    @ExcelProperty(value = "User gender", converter = DictConvert.class)
    @DictFormat(DictTypeConstants.USER_SEX)
    private Integer sex;

    @ExcelProperty(value = "Account Status", converter = DictConvert.class)
    @DictFormat(DictTypeConstants.COMMON_STATUS)
    private Integer status;

}
