package cn.econets.blossom.module.system.controller.admin.mail;

import cn.econets.blossom.framework.common.pojo.CommonResult;
import cn.econets.blossom.framework.common.pojo.PageResult;
import cn.econets.blossom.framework.common.util.object.BeanUtils;
import cn.econets.blossom.module.system.controller.admin.mail.vo.template.*;
import cn.econets.blossom.module.system.dal.dataobject.mail.MailTemplateDO;
import cn.econets.blossom.module.system.service.mail.MailSendService;
import cn.econets.blossom.module.system.service.mail.MailTemplateService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

import static cn.econets.blossom.framework.common.pojo.CommonResult.success;
import static cn.econets.blossom.framework.security.core.util.SecurityFrameworkUtils.getLoginUserId;


@Tag(name = "Management Backend - Mail Template")
@RestController
@RequestMapping("/system/mail-template")
public class MailTemplateController {

    @Resource
    private MailTemplateService mailTempleService;
    @Resource
    private MailSendService mailSendService;

    @PostMapping("/create")
    @Operation(summary = "Create an email template")
    @PreAuthorize("@ss.hasPermission('system:mail-template:create')")
    public CommonResult<Long> createMailTemplate(@Valid @RequestBody MailTemplateSaveReqVO createReqVO){
        return success(mailTempleService.createMailTemplate(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "Modify email template")
    @PreAuthorize("@ss.hasPermission('system:mail-template:update')")
    public CommonResult<Boolean> updateMailTemplate(@Valid @RequestBody MailTemplateSaveReqVO updateReqVO){
        mailTempleService.updateMailTemplate(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "Delete email template")
    @Parameter(name = "id", description = "Number", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('system:mail-template:delete')")
    public CommonResult<Boolean> deleteMailTemplate(@RequestParam("id") Long id) {
        mailTempleService.deleteMailTemplate(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "Get email template")
    @Parameter(name = "id", description = "Number", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('system:mail-template:get')")
    public CommonResult<MailTemplateRespVO> getMailTemplate(@RequestParam("id") Long id) {
        MailTemplateDO template = mailTempleService.getMailTemplate(id);
        return success(BeanUtils.toBean(template, MailTemplateRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "Get email template paging")
    @PreAuthorize("@ss.hasPermission('system:mail-template:query')")
    public CommonResult<PageResult<MailTemplateRespVO>> getMailTemplatePage(@Valid MailTemplatePageReqVO pageReqVO) {
        PageResult<MailTemplateDO> pageResult = mailTempleService.getMailTemplatePage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, MailTemplateRespVO.class));
    }

    @GetMapping({"/list-all-simple", "simple-list"})
    @Operation(summary = "Get a simplified list of email templates")
    public CommonResult<List<MailTemplateSimpleRespVO>> getSimpleTemplateList() {
        List<MailTemplateDO> list = mailTempleService.getMailTemplateList();
        return success(BeanUtils.toBean(list, MailTemplateSimpleRespVO.class));
    }

    @PostMapping("/send-mail")
    @Operation(summary = "Send SMS")
    @PreAuthorize("@ss.hasPermission('system:mail-template:send-mail')")
    public CommonResult<Long> sendMail(@Valid @RequestBody MailTemplateSendReqVO sendReqVO) {
        return success(mailSendService.sendSingleMailToAdmin(sendReqVO.getMail(), getLoginUserId(),
                sendReqVO.getTemplateCode(), sendReqVO.getTemplateParams()));
    }

}
