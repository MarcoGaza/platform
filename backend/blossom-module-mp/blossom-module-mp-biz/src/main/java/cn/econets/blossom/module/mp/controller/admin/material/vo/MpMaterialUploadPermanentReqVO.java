package cn.econets.blossom.module.mp.controller.admin.material.vo;

import cn.hutool.core.util.ObjectUtil;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import me.chanjar.weixin.common.api.WxConsts;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Schema(description = "Management Backend - Permanent upload of public account materials Request VO")
@Data
public class MpMaterialUploadPermanentReqVO {

    @Schema(description = "The public account number", requiredMode = Schema.RequiredMode.REQUIRED, example = "2048")
    @NotNull(message = "The public account number cannot be empty")
    private Long accountId;

    @Schema(description = "File Type See WxConsts.MediaFileType Enumeration", requiredMode = Schema.RequiredMode.REQUIRED, example = "image")
    @NotEmpty(message = "File type cannot be empty")
    private String type;

    @Schema(description = "File attachment", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "The file cannot be empty")
    @JsonIgnore // Avoid manipulation logs，Serialize，Causes an error
    private MultipartFile file;

    @Schema(description = "Name If name Empty，Then use file File name", example = "wechat.mp")
    private String name;

    @Schema(description = "Title of the video material File type is video Time，Required", example = "Title of the video material")
    private String title;
    @Schema(description = "Description of video material File type is video Time，Required", example = "Description of video material")
    private String introduction;

    @AssertTrue(message = "Title cannot be empty")
    public boolean isTitleValid() {
        // When the generated scene is the management background，The parent menu must be set，Otherwise the generated menu SQL There is no parent menu
        return ObjectUtil.notEqual(type, WxConsts.MediaFileType.VIDEO)
                || title != null;
    }

    @AssertTrue(message = "Description cannot be empty")
    public boolean isIntroductionValid() {
        // When the generated scene is the management background，The parent menu must be set，Otherwise the generated menu SQL There is no parent menu
        return ObjectUtil.notEqual(type, WxConsts.MediaFileType.VIDEO)
                || introduction != null;
    }

}
