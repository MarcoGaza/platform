package cn.econets.blossom.framework.file.core.utils;

import com.alibaba.ttl.TransmittableThreadLocal;
import lombok.SneakyThrows;
import org.apache.tika.Tika;

/**
 * File Type Utils
 *
 */
public class FileTypeUtils {

    private static final ThreadLocal<Tika> TIKA = TransmittableThreadLocal.withInitial(Tika::new);

    /**
     * Get the file mineType，Fordoc，jarThere will be errors in the waiting files
     *
     * @param data File contents
     * @return mineType Will be returned if it cannot be recognized“application/octet-stream”
     */
    @SneakyThrows
    public static String getMineType(byte[] data) {
        return TIKA.get().detect(data);
    }

    /**
     * Known file name，Get file type，More accurate than passing byte arrays in some cases，For example, usejarFile，It is more accurate to use the name
     *
     * @param name File name
     * @return mineType Will be returned if it cannot be recognized“application/octet-stream”
     */
    public static String getMineType(String name) {
        return TIKA.get().detect(name);
    }

    /**
     * In the case of having files and data，It is best to use this method，Most accurate
     *
     * @param data File contents
     * @param name File name
     * @return mineType Will be returned if it cannot be recognized“application/octet-stream”
     */
    public static String getMineType(byte[] data, String name) {
        return TIKA.get().detect(data, name);
    }

}
