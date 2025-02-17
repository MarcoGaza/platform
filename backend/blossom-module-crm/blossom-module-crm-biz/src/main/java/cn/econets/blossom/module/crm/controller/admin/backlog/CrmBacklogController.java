package cn.econets.blossom.module.crm.controller.admin.backlog;

import cn.econets.blossom.framework.common.pojo.CommonResult;
import cn.econets.blossom.framework.common.pojo.PageResult;
import cn.econets.blossom.framework.common.util.object.BeanUtils;
import cn.econets.blossom.module.crm.controller.admin.customer.vo.CrmCustomerRespVO;
import cn.econets.blossom.module.crm.controller.admin.backlog.vo.CrmTodayCustomerPageReqVO;
import cn.econets.blossom.module.crm.dal.dataobject.customer.CrmCustomerDO;
import cn.econets.blossom.module.crm.service.message.CrmBacklogService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;

import static cn.econets.blossom.framework.common.pojo.CommonResult.success;
import static cn.econets.blossom.framework.security.core.util.SecurityFrameworkUtils.getLoginUserId;

@Tag(name = "Management Backend - CRMTo-do messages")
@RestController
@RequestMapping("/crm/backlog")
@Validated
public class CrmBacklogController {

    @Resource
    private CrmBacklogService crmMessageService;

    // TODO May be merged into CrmCustomerController
    @GetMapping("/today-customer-page")
    @Operation(summary = "Need to contact the customer today")
    @PreAuthorize("@ss.hasPermission('crm:customer:query')")
    public CommonResult<PageResult<CrmCustomerRespVO>> getTodayCustomerPage(@Valid CrmTodayCustomerPageReqVO pageReqVO) {
        PageResult<CrmCustomerDO> pageResult = crmMessageService.getTodayCustomerPage(pageReqVO, getLoginUserId());
        return success(BeanUtils.toBean(pageResult, CrmCustomerRespVO.class));
    }

}
