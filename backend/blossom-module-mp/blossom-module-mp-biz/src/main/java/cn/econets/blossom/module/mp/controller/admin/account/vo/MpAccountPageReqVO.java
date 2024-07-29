package cn.econets.blossom.module.mp.controller.admin.account.vo;

import cn.econets.blossom.framework.common.pojo.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Schema(description = "Management Backend - Paging of public account Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class MpAccountPageReqVO extends PageParam {

    @Schema(name = "Public account name", description = "Fuzzy matching")
    private String name;

    @Schema(name = "Public Account", description = "Fuzzy matching")
    private String account;

    @Schema(name = "Public Account appid", description = "Fuzzy matching")
    private String appId;

}
