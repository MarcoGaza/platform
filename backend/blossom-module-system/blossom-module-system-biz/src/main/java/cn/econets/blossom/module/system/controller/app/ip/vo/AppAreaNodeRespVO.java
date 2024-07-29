package cn.econets.blossom.module.system.controller.app.ip.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Schema(description = "User App - Regional Node Response VO")
@Data
public class AppAreaNodeRespVO {

    @Schema(description = "Number", requiredMode = Schema.RequiredMode.REQUIRED, example = "110000")
    private Integer id;

    @Schema(description = "Name", requiredMode = Schema.RequiredMode.REQUIRED, example = "Beijing")
    private String name;

    /**
     * Subnode
     */
    private List<AppAreaNodeRespVO> children;

}
