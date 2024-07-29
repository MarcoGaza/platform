package cn.econets.blossom.module.crm.controller.admin.contact;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.NumberUtil;
import cn.econets.blossom.framework.common.pojo.CommonResult;
import cn.econets.blossom.framework.common.pojo.PageResult;
import cn.econets.blossom.framework.common.util.number.NumberUtils;
import cn.econets.blossom.framework.common.util.object.BeanUtils;
import cn.econets.blossom.framework.excel.core.util.ExcelUtils;
import cn.econets.blossom.framework.operatelog.core.annotations.OperateLog;
import cn.econets.blossom.module.crm.controller.admin.contact.vo.*;
import cn.econets.blossom.module.crm.convert.contact.CrmContactConvert;
import cn.econets.blossom.module.crm.dal.dataobject.contact.CrmContactDO;
import cn.econets.blossom.module.crm.dal.dataobject.customer.CrmCustomerDO;
import cn.econets.blossom.module.crm.enums.ErrorCodeConstants;
import cn.econets.blossom.module.crm.service.contact.CrmContactBusinessService;
import cn.econets.blossom.module.crm.service.contact.CrmContactService;
import cn.econets.blossom.module.crm.service.customer.CrmCustomerService;
import cn.econets.blossom.module.system.api.user.AdminUserApi;
import cn.econets.blossom.module.system.api.user.dto.AdminUserRespDTO;
import com.google.common.collect.Lists;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static cn.econets.blossom.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.econets.blossom.framework.common.pojo.CommonResult.success;
import static cn.econets.blossom.framework.common.pojo.PageParam.PAGE_SIZE_NONE;
import static cn.econets.blossom.framework.common.util.collection.CollectionUtils.*;
import static cn.econets.blossom.framework.operatelog.core.enums.OperateTypeEnum.EXPORT;
import static cn.econets.blossom.framework.security.core.util.SecurityFrameworkUtils.getLoginUserId;

@Tag(name = "Management Backend - CRM Contact Person")
@RestController
@RequestMapping("/crm/contact")
@Validated
@Slf4j
public class CrmContactController {

    @Resource
    private CrmContactService contactService;
    @Resource
    private CrmCustomerService customerService;
    @Resource
    private CrmContactBusinessService contactBusinessLinkService;

    @Resource
    private AdminUserApi adminUserApi;

    @PostMapping("/create")
    @Operation(summary = "Create contact")
    @PreAuthorize("@ss.hasPermission('crm:contact:create')")
    public CommonResult<Long> createContact(@Valid @RequestBody CrmContactSaveReqVO createReqVO) {
        return success(contactService.createContact(createReqVO, getLoginUserId()));
    }

    @PutMapping("/update")
    @Operation(summary = "Update Contact")
    @OperateLog(enable = false)
    @PreAuthorize("@ss.hasPermission('crm:contact:update')")
    public CommonResult<Boolean> updateContact(@Valid @RequestBody CrmContactSaveReqVO updateReqVO) {
        contactService.updateContact(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "Delete contact")
    @Parameter(name = "id", description = "Number", required = true)
    @PreAuthorize("@ss.hasPermission('crm:contact:delete')")
    public CommonResult<Boolean> deleteContact(@RequestParam("id") Long id) {
        contactService.deleteContact(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "Get contacts")
    @Parameter(name = "id", description = "Number", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('crm:contact:query')")
    public CommonResult<CrmContactRespVO> getContact(@RequestParam("id") Long id) {
        CrmContactDO contact = contactService.getContact(id);
        if (contact == null) {
            throw exception(ErrorCodeConstants.CONTACT_NOT_EXISTS);
        }
        // 1. Get username
        Map<Long, AdminUserRespDTO> userMap = adminUserApi.getUserMap(CollUtil.removeNull(Lists.newArrayList(
                NumberUtil.parseLong(contact.getCreator()), contact.getOwnerUserId())));
        // 2. Get customer information
        List<CrmCustomerDO> customerList = customerService.getCustomerList(
                Collections.singletonList(contact.getCustomerId()));
        // 3. Direct superior
        List<CrmContactDO> parentContactList = contactService.getContactListByIds(
                Collections.singletonList(contact.getParentId()), getLoginUserId());
        return success(CrmContactConvert.INSTANCE.convert(contact, userMap, customerList, parentContactList));
    }

    @GetMapping("/list-by-ids")
    @Operation(summary = "Get contact list")
    @Parameter(name = "ids", description = "Number", required = true, example = "[1024]")
    @PreAuthorize("@ss.hasPermission('crm:contact:query')")
    public CommonResult<List<CrmContactRespVO>> getContactListByIds(@RequestParam("ids") List<Long> ids) {
        return success(BeanUtils.toBean(contactService.getContactListByIds(ids, getLoginUserId()), CrmContactRespVO.class));
    }

    @GetMapping("/simple-all-list")
    @Operation(summary = "Get a simplified list of contacts")
    @PreAuthorize("@ss.hasPermission('crm:contact:query')")
    public CommonResult<List<CrmContactRespVO>> getSimpleContactList() {
        List<CrmContactDO> list = contactService.getSimpleContactList(getLoginUserId());
        return success(convertList(list, contact -> // Return only id、name Field
                new CrmContactRespVO().setId(contact.getId()).setName(contact.getName())));
    }

    @GetMapping("/page")
    @Operation(summary = "Get contact paging")
    @PreAuthorize("@ss.hasPermission('crm:contact:query')")
    public CommonResult<PageResult<CrmContactRespVO>> getContactPage(@Valid CrmContactPageReqVO pageVO) {
        PageResult<CrmContactDO> pageResult = contactService.getContactPage(pageVO, getLoginUserId());
        return success(buildContactDetailPage(pageResult));
    }

    @GetMapping("/page-by-customer")
    @Operation(summary = "Get contact paging，Based on specified customers")
    public CommonResult<PageResult<CrmContactRespVO>> getContactPageByCustomer(@Valid CrmContactPageReqVO pageVO) {
        Assert.notNull(pageVO.getCustomerId(), "Customer number cannot be empty");
        PageResult<CrmContactDO> pageResult = contactService.getContactPageByCustomerId(pageVO);
        return success(buildContactDetailPage(pageResult));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "Export contacts Excel")
    @PreAuthorize("@ss.hasPermission('crm:contact:export')")
    @OperateLog(type = EXPORT)
    public void exportContactExcel(@Valid CrmContactPageReqVO exportReqVO,
                                   HttpServletResponse response) throws IOException {
        exportReqVO.setPageNo(PAGE_SIZE_NONE);
        PageResult<CrmContactDO> pageResult = contactService.getContactPage(exportReqVO, getLoginUserId());
        ExcelUtils.write(response, "Contact Person.xls", "Data", CrmContactRespVO.class,
                buildContactDetailPage(pageResult).getList());
    }

    /**
     * Build detailed contact paging results
     *
     * @param pageResult Simple contact paging results
     * @return Detailed contact paging results
     */
    private PageResult<CrmContactRespVO> buildContactDetailPage(PageResult<CrmContactDO> pageResult) {
        List<CrmContactDO> contactList = pageResult.getList();
        if (CollUtil.isEmpty(contactList)) {
            return PageResult.empty(pageResult.getTotal());
        }
        // 1. Get customer list
        List<CrmCustomerDO> crmCustomerDOList = customerService.getCustomerList(
                convertSet(contactList, CrmContactDO::getCustomerId));
        // 2. Get the creator、List of responsible persons
        Map<Long, AdminUserRespDTO> userMap = adminUserApi.getUserMap(convertListByFlatMap(contactList,
                contact -> Stream.of(NumberUtils.parseLong(contact.getCreator()), contact.getOwnerUserId())));
        // 3. Direct superior
        List<CrmContactDO> parentContactList = contactService.getContactListByIds(
                convertSet(contactList, CrmContactDO::getParentId), getLoginUserId());
        return CrmContactConvert.INSTANCE.convertPage(pageResult, userMap, crmCustomerDOList, parentContactList);
    }

    @PutMapping("/transfer")
    @Operation(summary = "Contact transfer")
    @PreAuthorize("@ss.hasPermission('crm:contact:update')")
    public CommonResult<Boolean> transferContact(@Valid @RequestBody CrmContactTransferReqVO reqVO) {
        contactService.transferContact(reqVO, getLoginUserId());
        return success(true);
    }

    // ================== Relationship/Unfollow the contact  ===================

    @PostMapping("/create-business-list")
    @Operation(summary = "Create an association between contacts and opportunities")
    @PreAuthorize("@ss.hasPermission('crm:contact:create-business')")
    public CommonResult<Boolean> createContactBusinessList(@Valid @RequestBody CrmContactBusinessReqVO createReqVO) {
        contactBusinessLinkService.createContactBusinessList(createReqVO);
        return success(true);
    }

    @DeleteMapping("/delete-business-list")
    @Operation(summary = "Delete the association between contacts")
    @PreAuthorize("@ss.hasPermission('crm:contact:delete-business')")
    public CommonResult<Boolean> deleteContactBusinessList(@Valid @RequestBody CrmContactBusinessReqVO deleteReqVO) {
        contactBusinessLinkService.deleteContactBusinessList(deleteReqVO);
        return success(true);
    }

}
