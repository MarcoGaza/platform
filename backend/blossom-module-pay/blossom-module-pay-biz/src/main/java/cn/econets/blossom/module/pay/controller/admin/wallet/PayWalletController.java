package cn.econets.blossom.module.pay.controller.admin.wallet;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import cn.econets.blossom.framework.common.pojo.CommonResult;
import cn.econets.blossom.framework.common.pojo.PageResult;
import cn.econets.blossom.module.member.api.user.MemberUserApi;
import cn.econets.blossom.module.member.api.user.dto.MemberUserRespDTO;
import cn.econets.blossom.module.pay.controller.admin.wallet.vo.wallet.PayWalletPageReqVO;
import cn.econets.blossom.module.pay.controller.admin.wallet.vo.wallet.PayWalletRespVO;
import cn.econets.blossom.module.pay.controller.admin.wallet.vo.wallet.PayWalletUserReqVO;
import cn.econets.blossom.module.pay.convert.wallet.PayWalletConvert;
import cn.econets.blossom.module.pay.dal.dataobject.wallet.PayWalletDO;
import cn.econets.blossom.module.pay.service.wallet.PayWalletService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;
import java.util.Map;

import static cn.econets.blossom.framework.common.enums.UserTypeEnum.MEMBER;
import static cn.econets.blossom.framework.common.pojo.CommonResult.success;
import static cn.econets.blossom.framework.common.util.collection.CollectionUtils.*;

@Tag(name = "Management Backend - User wallet")
@RestController
@RequestMapping("/pay/wallet")
@Validated
@Slf4j
public class PayWalletController {

    @Resource
    private PayWalletService payWalletService;
    @Resource
    private MemberUserApi memberUserApi;

    @GetMapping("/get")
    @PreAuthorize("@ss.hasPermission('pay:wallet:query')")
    @Operation(summary = "Get user wallet details")
    public CommonResult<PayWalletRespVO> getWallet(PayWalletUserReqVO reqVO) {
        PayWalletDO wallet = payWalletService.getOrCreateWallet(reqVO.getUserId(), MEMBER.getValue());
        // TODO jason：If empty，Return to the front end only null That's it
        MemberUserRespDTO memberUser = memberUserApi.getUser(reqVO.getUserId());
        String nickname = memberUser == null ? "" : memberUser.getNickname();
        String avatar = memberUser == null ? "" : memberUser.getAvatar();
        return success(PayWalletConvert.INSTANCE.convert02(nickname, avatar, wallet));
    }

    @GetMapping("/page")
    @Operation(summary = "Get member wallet page")
    @PreAuthorize("@ss.hasPermission('pay:wallet:query')")
    public CommonResult<PageResult<PayWalletRespVO>> getWalletPage(@Valid PayWalletPageReqVO pageVO) {
        if (StrUtil.isNotEmpty(pageVO.getNickname())) {
            List<MemberUserRespDTO> users = memberUserApi.getUserListByNickname(pageVO.getNickname());
            pageVO.setUserIds(convertSet(users, MemberUserRespDTO::getId));
        }
        // TODO ：Administrators can also check first。。
        // Temporarily support query userType Member Type。Administrator type does not know the usage scenario yet
        PageResult<PayWalletDO> pageResult = payWalletService.getWalletPage(MEMBER.getValue(),pageVO);
        if (CollectionUtil.isEmpty(pageResult.getList())) {
            return success(new PageResult<>(pageResult.getTotal()));
        }
        List<MemberUserRespDTO> users = memberUserApi.getUserList(convertList(pageResult.getList(), PayWalletDO::getUserId));
        Map<Long, MemberUserRespDTO> userMap = convertMap(users, MemberUserRespDTO::getId);
        return success(PayWalletConvert.INSTANCE.convertPage(pageResult, userMap));
    }

}
