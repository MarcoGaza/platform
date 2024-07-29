package cn.econets.blossom.module.infrastructure.controller.admin.file;

import cn.econets.blossom.framework.common.pojo.CommonResult;
import cn.econets.blossom.framework.common.pojo.PageResult;
import cn.econets.blossom.framework.common.util.object.BeanUtils;
import cn.econets.blossom.framework.common.util.servlet.ServletUtils;
import cn.econets.blossom.framework.operatelog.core.annotations.OperateLog;
import cn.econets.blossom.module.infrastructure.controller.admin.file.vo.file.FilePageReqVO;
import cn.econets.blossom.module.infrastructure.controller.admin.file.vo.file.FileRespVO;
import cn.econets.blossom.module.infrastructure.controller.admin.file.vo.file.FileUploadReqVO;
import cn.econets.blossom.module.infrastructure.dal.dataobject.file.FileDO;
import cn.econets.blossom.module.infrastructure.service.file.FileService;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.StrUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.annotation.security.PermitAll;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import static cn.econets.blossom.framework.common.pojo.CommonResult.success;

@Tag(name = "Management Backend - File storage")
@RestController
@RequestMapping("/infra/file")
@Validated
@Slf4j
public class FileController {

    @Resource
    private FileService fileService;

    @PostMapping("/upload")
    @Operation(summary = "Upload file")
    @OperateLog(logArgs = false) // Upload file，No need to record operation logs
    public CommonResult<String> uploadFile(FileUploadReqVO uploadReqVO) throws Exception {
        MultipartFile file = uploadReqVO.getFile();
        String path = uploadReqVO.getPath();
        return success(fileService.createFile(file.getOriginalFilename(), path, IoUtil.readBytes(file.getInputStream())));
    }

    @DeleteMapping("/delete")
    @Operation(summary = "Delete file")
    @Parameter(name = "id", description = "Number", required = true)
    @PreAuthorize("@ss.hasPermission('infra:file:delete')")
    public CommonResult<Boolean> deleteFile(@RequestParam("id") Long id) throws Exception {
        fileService.deleteFile(id);
        return success(true);
    }

    @GetMapping("/{configId}/get/**")
    @PermitAll
    @Operation(summary = "Download file")
    @Parameter(name = "configId", description = "Configuration number",  required = true)
    public void getFileContent(HttpServletRequest request,
                               HttpServletResponse response,
                               @PathVariable("configId") Long configId) throws Exception {
        // Get the requested path
        String path = StrUtil.subAfter(request.getRequestURI(), "/get/", false);
        if (StrUtil.isEmpty(path)) {
            throw new IllegalArgumentException("The end path Path must be passed");
        }

        // Read content
        byte[] content = fileService.getFileContent(configId, path);
        if (content == null) {
            log.warn("[getFileContent][configId({}) path({}) File does not exist]", configId, path);
            response.setStatus(HttpStatus.NOT_FOUND.value());
            return;
        }
        ServletUtils.writeAttachment(response, path, content);
    }

    @GetMapping("/page")
    @Operation(summary = "Get file paging")
    @PreAuthorize("@ss.hasPermission('infra:file:query')")
    public CommonResult<PageResult<FileRespVO>> getFilePage(@Valid FilePageReqVO pageVO) {
        PageResult<FileDO> pageResult = fileService.getFilePage(pageVO);
        return success(BeanUtils.toBean(pageResult, FileRespVO.class));
    }

}
