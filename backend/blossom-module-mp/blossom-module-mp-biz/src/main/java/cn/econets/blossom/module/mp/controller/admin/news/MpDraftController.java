package cn.econets.blossom.module.mp.controller.admin.news;

import cn.hutool.core.collection.CollUtil;
import cn.econets.blossom.framework.common.pojo.CommonResult;
import cn.econets.blossom.framework.common.pojo.PageResult;
import cn.econets.blossom.framework.common.util.collection.CollectionUtils;
import cn.econets.blossom.framework.common.util.object.PageUtils;
import cn.econets.blossom.module.mp.controller.admin.news.vo.MpDraftPageReqVO;
import cn.econets.blossom.module.mp.dal.dataobject.material.MpMaterialDO;
import cn.econets.blossom.module.mp.framework.mp.core.MpServiceFactory;
import cn.econets.blossom.module.mp.service.material.MpMaterialService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.draft.*;
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

@Tag(name = "Management Backend - Draft of Public Account")
@RestController
@RequestMapping("/mp/draft")
@Validated
public class MpDraftController {

    @Resource
    private MpServiceFactory mpServiceFactory;

    @Resource
    private MpMaterialService mpMaterialService;

    @GetMapping("/page")
    @Operation(summary = "Get draft paging")
    @PreAuthorize("@ss.hasPermission('mp:draft:query')")
    public CommonResult<PageResult<WxMpDraftItem>> getDraftPage(MpDraftPageReqVO reqVO) {
        // Query the draft box from the public account
        WxMpService mpService = mpServiceFactory.getRequiredMpService(reqVO.getAccountId());
        WxMpDraftList draftList;
        try {
            draftList = mpService.getDraftService().listDraft(PageUtils.getStart(reqVO), reqVO.getPageSize());
        } catch (WxErrorException e) {
            throw exception(DRAFT_LIST_FAIL, e.getError().getErrorMsg());
        }
        // Query the corresponding image address。Purpose：Solve the problem that the image link of the public account cannot be displayed in our backend
        setDraftThumbUrl(draftList.getItems());

        // Return to page
        return success(new PageResult<>(draftList.getItems(), draftList.getTotalCount().longValue()));
    }

    private void setDraftThumbUrl(List<WxMpDraftItem> items) {
        // 1.1 Get mediaId Array
        Set<String> mediaIds = new HashSet<>();
        items.forEach(item -> item.getContent().getNewsItem().forEach(newsItem -> mediaIds.add(newsItem.getThumbMediaId())));
        if (CollUtil.isEmpty(mediaIds)) {
            return;
        }
        // 1.2 Batch query corresponding to Media Material
        Map<String, MpMaterialDO> materials = CollectionUtils.convertMap(mpMaterialService.getMaterialListByMediaId(mediaIds),
                MpMaterialDO::getMediaId);

        // 2. Set back WxMpDraftItem Record
        items.forEach(item -> item.getContent().getNewsItem().forEach(newsItem ->
                findAndThen(materials, newsItem.getThumbMediaId(), material -> newsItem.setThumbUrl(material.getUrl()))));
    }

    @PostMapping("/create")
    @Operation(summary = "Create draft")
    @Parameter(name = "accountId", description = "The public account number", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('mp:draft:create')")
    public CommonResult<String> deleteDraft(@RequestParam("accountId") Long accountId,
                                            @RequestBody WxMpAddDraft draft) {
        WxMpService mpService = mpServiceFactory.getRequiredMpService(accountId);
        try {
            String mediaId = mpService.getDraftService().addDraft(draft);
            return success(mediaId);
        } catch (WxErrorException e) {
            throw exception(DRAFT_CREATE_FAIL, e.getError().getErrorMsg());
        }
    }

    @PutMapping("/update")
    @Operation(summary = "Update draft")
    @Parameters({
            @Parameter(name = "accountId", description = "The public account number", required = true, example = "1024"),
            @Parameter(name = "mediaId", description = "Draft material number", required = true, example = "xxx")
    })
    @PreAuthorize("@ss.hasPermission('mp:draft:update')")
    public CommonResult<Boolean> deleteDraft(@RequestParam("accountId") Long accountId,
                                             @RequestParam("mediaId") String mediaId,
                                             @RequestBody List<WxMpDraftArticles> articles) {
        WxMpService mpService = mpServiceFactory.getRequiredMpService(accountId);
        try {
            for (int i = 0; i < articles.size(); i++) {
                WxMpDraftArticles article = articles.get(i);
                mpService.getDraftService().updateDraft(new WxMpUpdateDraft(mediaId, i, article));
            }
            return success(true);
        } catch (WxErrorException e) {
            throw exception(DRAFT_UPDATE_FAIL, e.getError().getErrorMsg());
        }
    }

    @DeleteMapping("/delete")
    @Operation(summary = "Delete draft")
    @Parameters({
            @Parameter(name = "accountId", description = "The public account number", required = true, example = "1024"),
            @Parameter(name = "mediaId", description = "Draft material number", required = true, example = "xxx")
    })
    @PreAuthorize("@ss.hasPermission('mp:draft:delete')")
    public CommonResult<Boolean> deleteDraft(@RequestParam("accountId") Long accountId,
                                             @RequestParam("mediaId") String mediaId) {
        WxMpService mpService = mpServiceFactory.getRequiredMpService(accountId);
        try {
            mpService.getDraftService().delDraft(mediaId);
            return success(true);
        } catch (WxErrorException e) {
            throw exception(DRAFT_DELETE_FAIL, e.getError().getErrorMsg());
        }
    }

}
