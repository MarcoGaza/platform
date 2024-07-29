package cn.econets.blossom.module.mp.controller.admin.news;

import cn.hutool.core.collection.CollUtil;
import cn.econets.blossom.framework.common.pojo.CommonResult;
import cn.econets.blossom.framework.common.pojo.PageResult;
import cn.econets.blossom.framework.common.util.collection.CollectionUtils;
import cn.econets.blossom.framework.common.util.object.PageUtils;
import cn.econets.blossom.module.mp.controller.admin.news.vo.MpFreePublishPageReqVO;
import cn.econets.blossom.module.mp.dal.dataobject.material.MpMaterialDO;
import cn.econets.blossom.module.mp.framework.mp.core.MpServiceFactory;
import cn.econets.blossom.module.mp.service.material.MpMaterialService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.freepublish.WxMpFreePublishItem;
import me.chanjar.weixin.mp.bean.freepublish.WxMpFreePublishList;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static cn.econets.blossom.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.econets.blossom.framework.common.pojo.CommonResult.success;
import static cn.econets.blossom.framework.common.util.collection.MapUtils.findAndThen;
import static cn.econets.blossom.module.mp.enums.ErrorCodeConstants.*;

@Tag(name = "Management Backend - Public account publishing capabilities")
@RestController
@RequestMapping("/mp/free-publish")
@Validated
public class MpFreePublishController {

    @Resource
    private MpServiceFactory mpServiceFactory;

    @Resource
    private MpMaterialService mpMaterialService;

    @GetMapping("/page")
    @Operation(summary = "Get published image and text pages")
    @PreAuthorize("@ss.hasPermission('mp:free-publish:query')")
    public CommonResult<PageResult<WxMpFreePublishItem>> getFreePublishPage(MpFreePublishPageReqVO reqVO) {
        // Query the published pictures and texts list from the public account
        WxMpService mpService = mpServiceFactory.getRequiredMpService(reqVO.getAccountId());
        WxMpFreePublishList publicationRecords;
        try {
            publicationRecords = mpService.getFreePublishService().getPublicationRecords(
                    PageUtils.getStart(reqVO), reqVO.getPageSize());
        } catch (WxErrorException e) {
            throw exception(FREE_PUBLISH_LIST_FAIL, e.getError().getErrorMsg());
        }
        // Query the corresponding image address。Purpose：Solve the problem that the image link of the public account cannot be displayed in our backend
        setFreePublishThumbUrl(publicationRecords.getItems());

        // Return to page
        return success(new PageResult<>(publicationRecords.getItems(), publicationRecords.getTotalCount().longValue()));
    }

    private void setFreePublishThumbUrl(List<WxMpFreePublishItem> items) {
        // 1.1 Get mediaId Array
        Set<String> mediaIds = new HashSet<>();
        items.forEach(item -> item.getContent().getNewsItem().forEach(newsItem -> mediaIds.add(newsItem.getThumbMediaId())));
        if (CollUtil.isEmpty(mediaIds)) {
            return;
        }
        // 1.2 Batch query corresponding to Media Material
        Map<String, MpMaterialDO> materials = CollectionUtils.convertMap(mpMaterialService.getMaterialListByMediaId(mediaIds),
                MpMaterialDO::getMediaId);

        // 2. Set back WxMpFreePublishItem Record
        items.forEach(item -> item.getContent().getNewsItem().forEach(newsItem ->
                findAndThen(materials, newsItem.getThumbMediaId(), material -> newsItem.setThumbUrl(material.getUrl()))));
    }

    @PostMapping("/submit")
    @Operation(summary = "Publish draft")
    @Parameters({
            @Parameter(name = "accountId", description = "The public account number", required = true, example = "1024"),
            @Parameter(name = "mediaId", description = "Draft to be published media_id", required = true, example = "2048")
    })
    @PreAuthorize("@ss.hasPermission('mp:free-publish:submit')")
    public CommonResult<String> submitFreePublish(@RequestParam("accountId") Long accountId,
                                                  @RequestParam("mediaId") String mediaId) {
        WxMpService mpService = mpServiceFactory.getRequiredMpService(accountId);
        try {
            String publishId = mpService.getFreePublishService().submit(mediaId);
            return success(publishId);
        } catch (WxErrorException e) {
            throw exception(FREE_PUBLISH_SUBMIT_FAIL, e.getError().getErrorMsg());
        }
    }

    @DeleteMapping("/delete")
    @Operation(summary = "Delete draft")
    @Parameters({
            @Parameter(name = "accountId", description = "The public account number", required = true, example = "1024"),
            @Parameter(name = "articleId", description = "Release record number", required = true, example = "2048")
    })
    @PreAuthorize("@ss.hasPermission('mp:free-publish:delete')")
    public CommonResult<Boolean> deleteFreePublish(@RequestParam("accountId") Long accountId,
                                                   @RequestParam("articleId") String articleId) {
        WxMpService mpService = mpServiceFactory.getRequiredMpService(accountId);
        try {
            mpService.getFreePublishService().deletePushAllArticle(articleId);
            return success(true);
        } catch (WxErrorException e) {
            throw exception(FREE_PUBLISH_DELETE_FAIL, e.getError().getErrorMsg());
        }
    }

}
