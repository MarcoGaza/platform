package cn.econets.blossom.module.crm.controller.admin.contact.vo;

import cn.econets.blossom.framework.common.pojo.PageParam;
import cn.econets.blossom.framework.common.validation.InEnum;
import cn.econets.blossom.module.crm.enums.common.CrmSceneTypeEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Schema(description = "Management Backend - CRM Contacts Paging Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class CrmContactPageReqVO extends PageParam {

    @Schema(description = "Name", example = "econets")
    private String name;

    @Schema(description = "Customer Number", example = "10795")
    private Long customerId;

    @Schema(description = "Mobile phone number", example = "13898273941")
    private String mobile;

    @Schema(description = "Phone", example = "021-383773")
    private String telephone;

    @Schema(description = "Email", example = "111@22.com")
    private String email;

    @Schema(description = "QQ", example = "3882872")
    private Long qq;

    @Schema(description = "WeChat", example = "zzZ98373")
    private String wechat;

    @Schema(description = "Scene Type", example = "1")
    @InEnum(CrmSceneTypeEnum.class)
    private Integer sceneType; // Scene Type，for null When it means all

}
