package cn.econets.blossom.module.member.controller.app.address;

import cn.econets.blossom.framework.common.pojo.CommonResult;
import cn.econets.blossom.framework.security.core.annotations.PreAuthenticated;
import cn.econets.blossom.module.member.controller.app.address.vo.AppAddressCreateReqVO;
import cn.econets.blossom.module.member.controller.app.address.vo.AppAddressRespVO;
import cn.econets.blossom.module.member.controller.app.address.vo.AppAddressUpdateReqVO;
import cn.econets.blossom.module.member.convert.address.AddressConvert;
import cn.econets.blossom.module.member.dal.dataobject.address.MemberAddressDO;
import cn.econets.blossom.module.member.service.address.AddressService;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

import static cn.econets.blossom.framework.common.pojo.CommonResult.success;
import static cn.econets.blossom.framework.security.core.util.SecurityFrameworkUtils.getLoginUserId;

@Tag(name = "User APP - User's mailing address")
@RestController
@RequestMapping("/member/address")
@Validated
public class AppAddressController {

    @Resource
    private AddressService addressService;

    @PostMapping("/create")
    @Operation(summary = "Create a user recipient address")
    @PreAuthenticated
    public CommonResult<Long> createAddress(@Valid @RequestBody AppAddressCreateReqVO createReqVO) {
        return success(addressService.createAddress(getLoginUserId(), createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "Update user's mailing address")
    @PreAuthenticated
    public CommonResult<Boolean> updateAddress(@Valid @RequestBody AppAddressUpdateReqVO updateReqVO) {
        addressService.updateAddress(getLoginUserId(), updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "Delete user's receiving address")
    @Parameter(name = "id", description = "Number", required = true)
    @PreAuthenticated
    public CommonResult<Boolean> deleteAddress(@RequestParam("id") Long id) {
        addressService.deleteAddress(getLoginUserId(), id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "Get the user's mailing address")
    @Parameter(name = "id", description = "Number", required = true, example = "1024")
    @PreAuthenticated
    public CommonResult<AppAddressRespVO> getAddress(@RequestParam("id") Long id) {
        MemberAddressDO address = addressService.getAddress(getLoginUserId(), id);
        return success(AddressConvert.INSTANCE.convert(address));
    }

    @GetMapping("/get-default")
    @Operation(summary = "Get the default user recipient address")
    @PreAuthenticated
    public CommonResult<AppAddressRespVO> getDefaultUserAddress() {
        MemberAddressDO address = addressService.getDefaultUserAddress(getLoginUserId());
        return success(AddressConvert.INSTANCE.convert(address));
    }

    @GetMapping("/list")
    @Operation(summary = "Get the user's mailing address list")
    @PreAuthenticated
    public CommonResult<List<AppAddressRespVO>> getAddressList() {
        List<MemberAddressDO> list = addressService.getAddressList(getLoginUserId());
        return success(AddressConvert.INSTANCE.convertList(list));
    }

}
