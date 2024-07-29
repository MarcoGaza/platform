package cn.econets.blossom.framework.common.util.io;

import cn.hutool.core.io.IORuntimeException;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.StrUtil;

import java.io.InputStream;

/**
 * IO Tools，Used for {@link IoUtil} Missing method
 *
 */
public class IoUtils {

    /**
     * Read from stream UTF8 Encoded content
     *
     * @param in Input stream
     * @param isClose Whether to close
     * @return Content
     * @throws IORuntimeException IO Abnormal
     */
    public static String readUtf8(InputStream in, boolean isClose) throws IORuntimeException {
        return StrUtil.utf8Str(IoUtil.read(in, isClose));
    }

}
