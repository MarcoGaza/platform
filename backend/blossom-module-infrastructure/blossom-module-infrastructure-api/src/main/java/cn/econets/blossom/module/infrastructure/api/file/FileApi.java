package cn.econets.blossom.module.infrastructure.api.file;

/**
 * File API Interface
 *
 */
public interface FileApi {

    /**
     * Save file，And return the access path of the file
     *
     * @param content File contents
     * @return File path
     */
    default String createFile(byte[] content) {
        return createFile(null, null, content);
    }

    /**
     * Save file，And return the access path of the file
     *
     * @param path File path
     * @param content File contents
     * @return File path
     */
    default String createFile(String path, byte[] content) {
        return createFile(null, path, content);
    }

    /**
     * Save file，And return the access path of the file
     *
     * @param name File name
     * @param path File path
     * @param content File contents
     * @return File path
     */
    String createFile(String name, String path, byte[] content);

}
