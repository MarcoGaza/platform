package cn.econets.blossom.framework.common.util.io;

import cn.hutool.core.io.FileTypeUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.file.FileNameUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.digest.DigestUtil;
import lombok.SneakyThrows;

import java.io.ByteArrayInputStream;
import java.io.File;

/**
 * File Tools
 *
 */
public class FileUtils {

    /**
     * Create a temporary file
     * The file will be in JVM When exiting，Delete
     *
     * @param data File content
     * @return File
     */
    @SneakyThrows
    public static File createTempFile(String data) {
        File file = createTempFile();
        // Write content
        FileUtil.writeUtf8String(data, file);
        return file;
    }

    /**
     * Create a temporary file
     * The file will be in JVM When exiting，Delete
     *
     * @param data File content
     * @return File
     */
    @SneakyThrows
    public static File createTempFile(byte[] data) {
        File file = createTempFile();
        // Write content
        FileUtil.writeBytes(data, file);
        return file;
    }

    /**
     * Create a temporary file，No content
     * The file will be in JVM When exiting，Delete
     *
     * @return File
     */
    @SneakyThrows
    public static File createTempFile() {
        // Create file，Passed UUID Guaranteed uniqueness
        File file = File.createTempFile(IdUtil.simpleUUID(), null);
        // Mark JVM When exiting，Automatically delete
        file.deleteOnExit();
        return file;
    }

    /**
     * Generate file path
     *
     * @param content      File content
     * @param originalName Original file name
     * @return path，Unique and non-repeatable
     */
    public static String generatePath(byte[] content, String originalName) {
        String sha256Hex = DigestUtil.sha256Hex(content);
        // Situation 1：If exists name，Then use it first name Suffix of
        if (StrUtil.isNotBlank(originalName)) {
            String extName = FileNameUtil.extName(originalName);
            return StrUtil.isBlank(extName) ? sha256Hex : sha256Hex + "." + extName;
        }
        // Case 2：Based on content Calculation
        return sha256Hex + '.' + FileTypeUtil.getType(new ByteArrayInputStream(content));
    }

}
