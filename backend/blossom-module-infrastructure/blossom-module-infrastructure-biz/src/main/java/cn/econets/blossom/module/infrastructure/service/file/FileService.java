package cn.econets.blossom.module.infrastructure.service.file;

import cn.econets.blossom.framework.common.pojo.PageResult;
import cn.econets.blossom.module.infrastructure.controller.admin.file.vo.file.FilePageReqVO;
import cn.econets.blossom.module.infrastructure.dal.dataobject.file.FileDO;

/**
 * File Service Interface
 *
 */
public interface FileService {

    /**
     * Get file paging
     *
     * @param pageReqVO Paged query
     * @return File paging
     */
    PageResult<FileDO> getFilePage(FilePageReqVO pageReqVO);

    /**
     * Save file，And return the access path of the file
     *
     * @param name File name
     * @param path File path
     * @param content File contents
     * @return File path
     */
    String createFile(String name, String path, byte[] content);

    /**
     * Delete file
     *
     * @param id Number
     */
    void deleteFile(Long id) throws Exception;

    /**
     * Get file content
     *
     * @param configId Configuration number
     * @param path File path
     * @return File contents
     */
    byte[] getFileContent(Long configId, String path) throws Exception;

}
