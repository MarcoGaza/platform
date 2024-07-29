package cn.econets.blossom.module.promotion.convert.combination;

import cn.hutool.core.util.ObjectUtil;
import cn.econets.blossom.framework.common.pojo.PageResult;
import cn.econets.blossom.framework.common.util.collection.CollectionUtils;
import cn.econets.blossom.framework.common.util.collection.MapUtils;
import cn.econets.blossom.module.member.api.user.dto.MemberUserRespDTO;
import cn.econets.blossom.module.product.api.sku.dto.ProductSkuRespDTO;
import cn.econets.blossom.module.product.api.spu.dto.ProductSpuRespDTO;
import cn.econets.blossom.module.promotion.api.combination.dto.CombinationRecordCreateReqDTO;
import cn.econets.blossom.module.promotion.api.combination.dto.CombinationRecordCreateRespDTO;
import cn.econets.blossom.module.promotion.controller.admin.combination.vo.activity.CombinationActivityCreateReqVO;
import cn.econets.blossom.module.promotion.controller.admin.combination.vo.activity.CombinationActivityPageItemRespVO;
import cn.econets.blossom.module.promotion.controller.admin.combination.vo.activity.CombinationActivityRespVO;
import cn.econets.blossom.module.promotion.controller.admin.combination.vo.activity.CombinationActivityUpdateReqVO;
import cn.econets.blossom.module.promotion.controller.admin.combination.vo.product.CombinationProductBaseVO;
import cn.econets.blossom.module.promotion.controller.admin.combination.vo.product.CombinationProductRespVO;
import cn.econets.blossom.module.promotion.controller.admin.combination.vo.recrod.CombinationRecordPageItemRespVO;
import cn.econets.blossom.module.promotion.controller.app.combination.vo.activity.AppCombinationActivityDetailRespVO;
import cn.econets.blossom.module.promotion.controller.app.combination.vo.activity.AppCombinationActivityRespVO;
import cn.econets.blossom.module.promotion.controller.app.combination.vo.record.AppCombinationRecordDetailRespVO;
import cn.econets.blossom.module.promotion.controller.app.combination.vo.record.AppCombinationRecordRespVO;
import cn.econets.blossom.module.promotion.dal.dataobject.combination.CombinationActivityDO;
import cn.econets.blossom.module.promotion.dal.dataobject.combination.CombinationProductDO;
import cn.econets.blossom.module.promotion.dal.dataobject.combination.CombinationRecordDO;
import cn.econets.blossom.module.promotion.enums.combination.CombinationRecordStatusEnum;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static cn.econets.blossom.framework.common.util.collection.CollectionUtils.*;
import static cn.econets.blossom.framework.common.util.collection.MapUtils.findAndThen;

/**
 * Group buying activity Convert
 *
 */
@Mapper
public interface CombinationActivityConvert {

    CombinationActivityConvert INSTANCE = Mappers.getMapper(CombinationActivityConvert.class);

    CombinationActivityDO convert(CombinationActivityCreateReqVO bean);

    CombinationActivityDO convert(CombinationActivityUpdateReqVO bean);

    CombinationActivityRespVO convert(CombinationActivityDO bean);

    CombinationProductRespVO convert(CombinationProductDO bean);

    default CombinationActivityRespVO convert(CombinationActivityDO activity, List<CombinationProductDO> products) {
        return convert(activity).setProducts(convertList2(products));
    }

    List<CombinationActivityRespVO> convertList(List<CombinationActivityDO> list);

    default PageResult<CombinationActivityPageItemRespVO> convertPage(PageResult<CombinationActivityDO> page,
                                                                      List<CombinationProductDO> productList,
                                                                      Map<Long, Integer> groupCountMap,
                                                                      Map<Long, Integer> groupSuccessCountMap,
                                                                      Map<Long, Integer> recordCountMap,
                                                                      List<ProductSpuRespDTO> spuList) {
        PageResult<CombinationActivityPageItemRespVO> pageResult = convertPage(page);
        Map<Long, ProductSpuRespDTO> spuMap = convertMap(spuList, ProductSpuRespDTO::getId);
        pageResult.getList().forEach(item -> {
            MapUtils.findAndThen(spuMap, item.getSpuId(), spu -> item.setSpuName(spu.getName()).setPicUrl(spu.getPicUrl())
                    .setMarketPrice(spu.getMarketPrice()));
            item.setProducts(convertList2(productList));
            // Set statistical fields
            item.setGroupCount(groupCountMap.getOrDefault(item.getId(), 0))
                    .setGroupSuccessCount(groupSuccessCountMap.getOrDefault(item.getId(), 0))
                    .setRecordCount(recordCountMap.getOrDefault(item.getId(), 0));
        });
        return pageResult;
    }

    PageResult<CombinationActivityPageItemRespVO> convertPage(PageResult<CombinationActivityDO> page);

    List<CombinationProductRespVO> convertList2(List<CombinationProductDO> productDOs);

    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "activityId", source = "activity.id"),
            @Mapping(target = "spuId", source = "activity.spuId"),
            @Mapping(target = "skuId", source = "product.skuId"),
            @Mapping(target = "combinationPrice", source = "product.combinationPrice"),
            @Mapping(target = "activityStartTime", source = "activity.startTime"),
            @Mapping(target = "activityEndTime", source = "activity.endTime")
    })
    CombinationProductDO convert(CombinationActivityDO activity, CombinationProductBaseVO product);

    default List<CombinationProductDO> convertList(List<? extends CombinationProductBaseVO> products, CombinationActivityDO activity) {
        return CollectionUtils.convertList(products, item -> convert(activity, item).setActivityStatus(activity.getStatus()));
    }

    default List<CombinationProductDO> convertList(List<CombinationProductBaseVO> updateProductVOs,
                                                   List<CombinationProductDO> products, CombinationActivityDO activity) {
        Map<Long, Long> productMap = convertMap(products, CombinationProductDO::getSkuId, CombinationProductDO::getId);
        return CollectionUtils.convertList(updateProductVOs, updateProductVO -> convert(activity, updateProductVO)
                .setId(productMap.get(updateProductVO.getSkuId()))
                .setActivityStatus(activity.getStatus()));
    }

    CombinationRecordDO convert(CombinationRecordCreateReqDTO reqDTO);

    default CombinationRecordCreateRespDTO convert4(CombinationRecordDO combinationRecord) {
        return new CombinationRecordCreateRespDTO().setCombinationActivityId(combinationRecord.getActivityId())
                .setCombinationRecordId(combinationRecord.getId()).setCombinationHeadId(combinationRecord.getHeadId());
    }

    default CombinationRecordDO convert(CombinationRecordCreateReqDTO reqDTO,
                                        CombinationActivityDO activity, MemberUserRespDTO user,
                                        ProductSpuRespDTO spu, ProductSkuRespDTO sku) {
        return convert(reqDTO).setVirtualGroup(false)
                .setStatus(CombinationRecordStatusEnum.IN_PROGRESS.getStatus()) // The default status after creation is in progress
                .setUserSize(activity.getUserSize()).setUserCount(1) // Default is 1 After inserting, all group purchase records will be updated once
                // User Information
                .setNickname(user.getNickname()).setAvatar(user.getAvatar())
                // Product Information
                .setSpuName(spu.getName()).setPicUrl(sku.getPicUrl());
    }

    List<AppCombinationActivityRespVO> convertAppList(List<CombinationActivityDO> list);

    default List<AppCombinationActivityRespVO> convertAppList(List<CombinationActivityDO> list,
                                                              List<CombinationProductDO> productList,
                                                              List<ProductSpuRespDTO> spuList) {
        List<AppCombinationActivityRespVO> activityList = convertAppList(list);
        Map<Long, ProductSpuRespDTO> spuMap = convertMap(spuList, ProductSpuRespDTO::getId);
        Map<Long, List<CombinationProductDO>> productMap = convertMultiMap(productList, CombinationProductDO::getActivityId);
        return CollectionUtils.convertList(activityList, item -> {
            // Settings product Information
            item.setCombinationPrice(getMinValue(productMap.get(item.getId()), CombinationProductDO::getCombinationPrice));
            // Settings SPU Information
            findAndThen(spuMap, item.getSpuId(), spu -> item.setPicUrl(spu.getPicUrl()).setMarketPrice(spu.getMarketPrice()));
            return item;
        });
    }

    PageResult<AppCombinationActivityRespVO> convertAppPage(PageResult<CombinationActivityDO> result);

    default PageResult<AppCombinationActivityRespVO> convertAppPage(PageResult<CombinationActivityDO> result,
                                                                    List<CombinationProductDO> productList,
                                                                    List<ProductSpuRespDTO> spuList) {
        PageResult<AppCombinationActivityRespVO> appPage = convertAppPage(result);
        Map<Long, ProductSpuRespDTO> spuMap = convertMap(spuList, ProductSpuRespDTO::getId);
        Map<Long, List<CombinationProductDO>> productMap = convertMultiMap(productList, CombinationProductDO::getActivityId);
        List<AppCombinationActivityRespVO> list = CollectionUtils.convertList(appPage.getList(), item -> {
            // Settings product Information
            item.setCombinationPrice(getMinValue(productMap.get(item.getId()), CombinationProductDO::getCombinationPrice));
            // Settings SPU Information
            findAndThen(spuMap, item.getSpuId(), spu -> item.setPicUrl(spu.getPicUrl()).setMarketPrice(spu.getMarketPrice()));
            return item;
        });
        appPage.setList(list);
        return appPage;
    }

    AppCombinationActivityDetailRespVO convert2(CombinationActivityDO combinationActivity);

    List<AppCombinationActivityDetailRespVO.Product> convertList1(List<CombinationProductDO> products);

    default AppCombinationActivityDetailRespVO convert3(CombinationActivityDO combinationActivity, List<CombinationProductDO> products) {
        return convert2(combinationActivity).setProducts(convertList1(products));
    }

    List<AppCombinationRecordRespVO> convertList3(List<CombinationRecordDO> records);

    AppCombinationRecordRespVO convert(CombinationRecordDO record);

    PageResult<CombinationRecordPageItemRespVO> convert(PageResult<CombinationRecordDO> result);

    default PageResult<CombinationRecordPageItemRespVO> convert(PageResult<CombinationRecordDO> recordPage, List<CombinationActivityDO> activities, List<CombinationProductDO> products) {
        PageResult<CombinationRecordPageItemRespVO> result = convert(recordPage);
        // Splicing related attributes
        Map<Long, CombinationActivityDO> activityMap = convertMap(activities, CombinationActivityDO::getId);
        Map<Long, List<CombinationProductDO>> productsMap = convertMultiMap(products, CombinationProductDO::getActivityId);
        result.setList(CollectionUtils.convertList(result.getList(), item -> {
            findAndThen(activityMap, item.getActivityId(), activity -> {
                item.setActivity(convert(activity).setProducts(convertList2(productsMap.get(item.getActivityId()))));
            });
            return item;
        }));
        return result;
    }

    default AppCombinationRecordDetailRespVO convert(Long userId, CombinationRecordDO headRecord, List<CombinationRecordDO> memberRecords) {
        AppCombinationRecordDetailRespVO respVO = new AppCombinationRecordDetailRespVO()
                .setHeadRecord(convert(headRecord)).setMemberRecords(convertList3(memberRecords));
        // Handle your own participation in group buying orderId
        CombinationRecordDO userRecord = CollectionUtils.findFirst(memberRecords, r -> ObjectUtil.equal(r.getUserId(), userId));
        if (userRecord == null && ObjectUtil.equal(headRecord.getUserId(), userId)) {
            userRecord = headRecord;
        }
        respVO.setOrderId(userRecord == null ? null : userRecord.getOrderId());
        return respVO;
    }

    /**
     * Convert to generate virtual grouping and virtual records
     *
     * @param headRecord Virtual group leader record
     * @return Virtual record list
     */
    default List<CombinationRecordDO> convertVirtualRecordList(CombinationRecordDO headRecord) {
        int count = headRecord.getUserSize() - headRecord.getUserCount();
        List<CombinationRecordDO> createRecords = new ArrayList<>(count);
        for (int i = 0; i < count; i++) {
            // Basic information is consistent with the leader
            CombinationRecordDO newRecord = convert5(headRecord);
            // Virtual information
            newRecord.setCount(0) // Will be updated separately，In the follow-up Service Logic
                    .setUserId(0L).setNickname("").setAvatar("").setOrderId(0L);
            createRecords.add(newRecord);
        }
        return createRecords;
    }
    @Mapping(target = "id", ignore = true)
    CombinationRecordDO convert5(CombinationRecordDO headRecord);

}
