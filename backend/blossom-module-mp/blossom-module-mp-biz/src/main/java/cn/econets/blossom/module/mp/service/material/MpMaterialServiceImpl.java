package cn.econets.blossom.module.mp.service.material;

import cn.hutool.core.io.FileTypeUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.ObjUtil;
import cn.hutool.core.util.StrUtil;
import cn.econets.blossom.framework.common.pojo.PageResult;
import cn.econets.blossom.module.infrastructure.api.file.FileApi;
import cn.econets.blossom.module.mp.controller.admin.material.vo.MpMaterialPageReqVO;
import cn.econets.blossom.module.mp.controller.admin.material.vo.MpMaterialUploadNewsImageReqVO;
import cn.econets.blossom.module.mp.controller.admin.material.vo.MpMaterialUploadPermanentReqVO;
import cn.econets.blossom.module.mp.controller.admin.material.vo.MpMaterialUploadTemporaryReqVO;
import cn.econets.blossom.module.mp.convert.material.MpMaterialConvert;
import cn.econets.blossom.module.mp.dal.dataobject.account.MpAccountDO;
import cn.econets.blossom.module.mp.dal.dataobject.material.MpMaterialDO;
import cn.econets.blossom.module.mp.dal.mysql.material.MpMaterialMapper;
import cn.econets.blossom.module.mp.framework.mp.core.MpServiceFactory;
import cn.econets.blossom.module.mp.service.account.MpAccountService;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.bean.result.WxMediaUploadResult;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.material.WxMpMaterialUploadResult;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.List;

import static cn.econets.blossom.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.econets.blossom.module.mp.enums.ErrorCodeConstants.*;

/**
 * Public account material Service Interface
 *
 *
 */
@Service
@Validated
@Slf4j
public class MpMaterialServiceImpl implements MpMaterialService {

    @Resource
    private MpMaterialMapper mpMaterialMapper;

    @Resource
    private FileApi fileApi;

    @Resource
    @Lazy // Delayed loading，Solve the problem of circular dependencies
    private MpAccountService mpAccountService;

    @Resource
    @Lazy // Delayed loading，Solve the problem of circular dependencies
    private MpServiceFactory mpServiceFactory;

    @Override
    public String downloadMaterialUrl(Long accountId, String mediaId, String type) {
        // First step，Query directly from the database。If already downloaded，Return directly
        MpMaterialDO material = mpMaterialMapper.selectByAccountIdAndMediaId(accountId, mediaId);
        if (material != null) {
            return material.getUrl();
        }

        // Step 2，Try to download from temporary source
        String url = downloadMedia(accountId, mediaId);
        if (url == null) {
            return null;
        }
        MpAccountDO account = mpAccountService.getRequiredAccount(accountId);
        material = MpMaterialConvert.INSTANCE.convert(mediaId, type, url, account, null)
                .setPermanent(false);
        mpMaterialMapper.insert(material);

        // Do not consider downloading permanent materials，Because it was saved when it was uploaded
        return url;
    }

    @Override
    public MpMaterialDO uploadTemporaryMaterial(MpMaterialUploadTemporaryReqVO reqVO) throws IOException {
        WxMpService mpService = mpServiceFactory.getRequiredMpService(reqVO.getAccountId());
        // First step，Upload to the official account
        File file = null;
        WxMediaUploadResult result;
        String mediaId;
        String url;
        try {
            // Write to temporary file
            file = FileUtil.newFile(FileUtil.getTmpDirPath() + reqVO.getFile().getOriginalFilename());
            reqVO.getFile().transferTo(file);
            // Upload to the official account
            result = mpService.getMaterialService().mediaUpload(reqVO.getType(), file);
            // Upload to file service
            mediaId = ObjUtil.defaultIfNull(result.getMediaId(), result.getThumbMediaId());
            url = uploadFile(mediaId, file);
        } catch (WxErrorException e) {
            throw exception(MATERIAL_UPLOAD_FAIL, e.getError().getErrorMsg());
        } finally {
            FileUtil.del(file);
        }

        // Step 2，Store in database
        MpAccountDO account = mpAccountService.getRequiredAccount(reqVO.getAccountId());
        MpMaterialDO material = MpMaterialConvert.INSTANCE.convert(mediaId, reqVO.getType(), url, account,
                        reqVO.getFile().getName()).setPermanent(false);
        mpMaterialMapper.insert(material);
        return material;
    }

    @Override
    public MpMaterialDO uploadPermanentMaterial(MpMaterialUploadPermanentReqVO reqVO) throws IOException {
        WxMpService mpService = mpServiceFactory.getRequiredMpService(reqVO.getAccountId());
        // First step，Upload to the official account
        String name = StrUtil.blankToDefault(reqVO.getName(), reqVO.getFile().getName());
        File file = null;
        WxMpMaterialUploadResult result;
        String mediaId;
        String url;
        try {
            // Write to temporary file
            file = FileUtil.newFile(FileUtil.getTmpDirPath() + reqVO.getFile().getOriginalFilename());
            reqVO.getFile().transferTo(file);
            // Upload to the official account
            result = mpService.getMaterialService().materialFileUpload(reqVO.getType(),
                    MpMaterialConvert.INSTANCE.convert(name, file, reqVO.getTitle(), reqVO.getIntroduction()));
            // Upload to file service
            mediaId = ObjUtil.defaultIfNull(result.getMediaId(), result.getMediaId());
            url = uploadFile(mediaId, file);
        } catch (WxErrorException e) {
            throw exception(MATERIAL_UPLOAD_FAIL, e.getError().getErrorMsg());
        } finally {
            FileUtil.del(file);
        }

        // Step 2，Store in database
        MpAccountDO account = mpAccountService.getRequiredAccount(reqVO.getAccountId());
        MpMaterialDO material = MpMaterialConvert.INSTANCE.convert(mediaId, reqVO.getType(), url, account,
                        name, reqVO.getTitle(), reqVO.getIntroduction(), result.getUrl()).setPermanent(true);
        mpMaterialMapper.insert(material);
        return material;
    }

    @Override
    public String uploadNewsImage(MpMaterialUploadNewsImageReqVO reqVO) throws IOException {
        WxMpService mpService = mpServiceFactory.getRequiredMpService(reqVO.getAccountId());
        File file = null;
        try {
            // Write to temporary file
            file = FileUtil.newFile(FileUtil.getTmpDirPath() + reqVO.getFile().getOriginalFilename());
            reqVO.getFile().transferTo(file);
            // Upload to the official account
            return mpService.getMaterialService().mediaImgUpload(file).getUrl();
        } catch (WxErrorException e) {
            throw exception(MATERIAL_IMAGE_UPLOAD_FAIL, e.getError().getErrorMsg());
        } finally {
            FileUtil.del(file);
        }
    }

    @Override
    public PageResult<MpMaterialDO> getMaterialPage(MpMaterialPageReqVO pageReqVO) {
        return mpMaterialMapper.selectPage(pageReqVO);
    }

    @Override
    public List<MpMaterialDO> getMaterialListByMediaId(Collection<String> mediaIds) {
        return mpMaterialMapper.selectListByMediaId(mediaIds);
    }

    @Override
    public void deleteMaterial(Long id) {
        MpMaterialDO material = mpMaterialMapper.selectById(id);
        if (material == null) {
            throw exception(MATERIAL_NOT_EXISTS);
        }

        // First step，Delete from the public account
        if (material.getPermanent()) {
            WxMpService mpService = mpServiceFactory.getRequiredMpService(material.getAppId());
            try {
                mpService.getMaterialService().materialDelete(material.getMediaId());
            } catch (WxErrorException e) {
                throw exception(MATERIAL_DELETE_FAIL, e.getError().getErrorMsg());
            }
        }

        // Step 2，Delete from database
        mpMaterialMapper.deleteById(id);
    }

    /**
     * Download the contents of WeChat media files，And upload to the file service
     *
     * Why download?？Media files are saved in WeChat background for 3 Sky，That is 3 Queen of Heaven media_id Invalid。
     *
     * @param accountId The public account number
     * @param mediaId Media file number
     * @return After uploading URL
     */
    public String downloadMedia(Long accountId, String mediaId) {
        WxMpService mpService = mpServiceFactory.getMpService(accountId);
        for (int i = 0; i < 3; i++) {
            try {
                // First step，Download media files from the public account
                File file = mpService.getMaterialService().mediaDownload(mediaId);
                // Step 2，Upload to file service
                return uploadFile(mediaId, file);
            } catch (WxErrorException e) {
                log.error("[mediaDownload][media({}) No. ({}) Download failed]", mediaId, i);
            }
        }
        return null;
    }

    private String uploadFile(String mediaId, File file) {
        String path = mediaId + "." + FileTypeUtil.getType(file);
        return fileApi.createFile(path, FileUtil.readBytes(file));
    }

}
