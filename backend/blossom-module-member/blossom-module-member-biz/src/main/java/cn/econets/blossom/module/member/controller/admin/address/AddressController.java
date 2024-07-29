package cn.econets.blossom.module.member.controller.admin.address;

import cn.econets.blossom.framework.common.pojo.CommonResult;
import cn.econets.blossom.module.member.controller.admin.address.vo.AddressRespVO;
import cn.econets.blossom.module.member.convert.address.AddressConvert;
import cn.econets.blossom.module.member.dal.dataobject.address.MemberAddressDO;
import cn.econets.blossom.module.member.service.address.AddressService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

import static cn.econets.blossom.framework.common.pojo.CommonResult.success;

@Tag(name = "Management Backend - User's mailing address")
@RestController
@RequestMapping("/member/address")
@Validated
public class AddressController {

    @Resource
    private AddressService addressService;

    @GetMapping("/list")
    @Operation(summary = "Get the user's recipient address list")
    @Parameter(name = "userId", description = "User Number", required = true)
    @PreAuthorize("@ss.hasPermission('member:user:query')")
    public CommonResult<List<AddressRespVO>> getAddressList(@RequestParam("userId") Long userId) {
        List<MemberAddressDO> list = addressService.getAddressList(userId);
        return success(AddressConvert.INSTANCE.convertList2(list));
    }

}
