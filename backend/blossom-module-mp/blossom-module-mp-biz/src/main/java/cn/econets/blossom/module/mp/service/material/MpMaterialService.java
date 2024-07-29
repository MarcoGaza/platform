package cn.econets.blossom.module.mp.service.material;

import cn.econets.blossom.framework.common.pojo.PageResult;
import cn.econets.blossom.module.mp.controller.admin.material.vo.MpMaterialPageReqVO;
import cn.econets.blossom.module.mp.controller.admin.material.vo.MpMaterialUploadNewsImageReqVO;
import cn.econets.blossom.module.mp.controller.admin.material.vo.MpMaterialUploadPermanentReqVO;
import cn.econets.blossom.module.mp.controller.admin.material.vo.MpMaterialUploadTemporaryReqVO;
import cn.econets.blossom.module.mp.dal.dataobject.material.MpMaterialDO;
import me.chanjar.weixin.common.api.WxConsts;

import javax.validation.Valid;
import java.io.IOException;
import java.util.Collection;
import java.util.List;

/**
 * Public account material Service Interface
 *
 *
 */
public interface MpMaterialService {

    /**
     * Obtaining materials URL
     *
     * This URL Stored from our own file server URL，Not stored in the public account URL
     *
     * @param accountId Public account number
     * @param mediaId Public account material id
     * @param type File Type {@link WxConsts.MediaFileType}
     * @return Material URL
     */
    String downloadMaterialUrl(Long accountId, String mediaId, String type);

    /**
     * Upload temporary material
     *
     * @param reqVO Request
     * @return Material
     * @throws IOException File operation exception occurred
     */
    MpMaterialDO uploadTemporaryMaterial(@Valid MpMaterialUploadTemporaryReqVO reqVO) throws IOException;

    /**
     * Upload permanent materials
     *
     * @param reqVO Request
     * @return Material
     * @throws IOException File operation exception occurred
     */
    MpMaterialDO uploadPermanentMaterial(@Valid MpMaterialUploadPermanentReqVO reqVO) throws IOException;

    /**
     * Upload pictures in graphic content
     *
     * @param reqVO Upload request
     * @return Image address
     */
    String uploadNewsImage(MpMaterialUploadNewsImageReqVO reqVO) throws IOException;

    /**
     * Get material paging
     *
     * @param pageReqVO Pagination request
     * @return Material Paging
     */
    PageResult<MpMaterialDO> getMaterialPage(MpMaterialPageReqVO pageReqVO);

    /**
     * Get material list
     *
     * @param mediaIds Material mediaId List
     * @return Material List
     */
    List<MpMaterialDO> getMaterialListByMediaId(Collection<String> mediaIds);

    /**
     * Delete material
     *
     * @param id Number
     */
    void deleteMaterial(Long id);

}
