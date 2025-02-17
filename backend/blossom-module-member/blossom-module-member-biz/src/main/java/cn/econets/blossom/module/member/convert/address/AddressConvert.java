package cn.econets.blossom.module.member.convert.address;

import cn.econets.blossom.framework.ip.core.utils.AreaUtils;
import cn.econets.blossom.module.member.api.address.dto.MemberAddressRespDTO;
import cn.econets.blossom.module.member.controller.admin.address.vo.AddressRespVO;
import cn.econets.blossom.module.member.controller.app.address.vo.AppAddressCreateReqVO;
import cn.econets.blossom.module.member.controller.app.address.vo.AppAddressRespVO;
import cn.econets.blossom.module.member.controller.app.address.vo.AppAddressUpdateReqVO;
import cn.econets.blossom.module.member.dal.dataobject.address.MemberAddressDO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * User's mailing address Convert
 *
 * 
 */
@Mapper
public interface AddressConvert {

    AddressConvert INSTANCE = Mappers.getMapper(AddressConvert.class);

    MemberAddressDO convert(AppAddressCreateReqVO bean);

    MemberAddressDO convert(AppAddressUpdateReqVO bean);

    @Mapping(source = "areaId", target = "areaName",  qualifiedByName = "convertAreaIdToAreaName")
    AppAddressRespVO convert(MemberAddressDO bean);

    List<AppAddressRespVO> convertList(List<MemberAddressDO> list);

    MemberAddressRespDTO convert02(MemberAddressDO bean);

    @Named("convertAreaIdToAreaName")
    default String convertAreaIdToAreaName(Integer areaId) {
        return AreaUtils.format(areaId);
    }

    List<AddressRespVO> convertList2(List<MemberAddressDO> list);

}
