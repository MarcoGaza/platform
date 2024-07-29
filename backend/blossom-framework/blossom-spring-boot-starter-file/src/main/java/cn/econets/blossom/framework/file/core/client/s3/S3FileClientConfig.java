package cn.econets.blossom.framework.file.core.client.s3;

import cn.econets.blossom.framework.file.core.client.FileClientConfig;
import cn.hutool.core.util.StrUtil;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotNull;

/**
 * S3 File client configuration class
 *
 */
@Data
public class S3FileClientConfig implements FileClientConfig {

    public static final String ENDPOINT_QINIU = "qiniucs.com";
    public static final String ENDPOINT_ALIYUN = "aliyuncs.com";
    public static final String ENDPOINT_TENCENT = "myqcloud.com";

    /**
     * Node address
     * 1. MinIO：https://www.econets.cn/Spring-Boot/MinIO 。For example，http://127.0.0.1:9000
     * 2. Alibaba Cloud：https://help.aliyun.com/document_detail/31837.html
     * 3. Tencent Cloud：https://cloud.tencent.com/document/product/436/6224
     * 4. Qiniu Cloud：https://developer.qiniu.com/kodo/4088/s3-access-domainname
     * 5. Huawei Cloud：https://developer.huaweicloud.com/endpoint?OBS
     */
    @NotNull(message = "endpoint Cannot be empty")
    private String endpoint;
    /**
     * Custom domain name
     * 1. MinIO：Passed Nginx Configuration
     * 2. Alibaba Cloud：https://help.aliyun.com/document_detail/31836.html
     * 3. Tencent Cloud：https://cloud.tencent.com/document/product/436/11142
     * 4. Qiniu Cloud：https://developer.qiniu.com/kodo/8556/set-the-custom-source-domain-name
     * 5. Huawei Cloud：https://support.huaweicloud.com/usermanual-obs/obs_03_0032.html
     */
    @URL(message = "domain Must be URL Format")
    private String domain;
    /**
     * Storage Bucket
     */
    @NotNull(message = "bucket Cannot be empty")
    private String bucket;

    /**
     * File main path hostFolder
     */
    @NotNull(message = "hostFolder Cannot be empty")
    private String hostFolder;

    /**
     * Visit Key
     * 1. MinIO：https://www.econets.cn/Spring-Boot/MinIO
     * 2. Alibaba Cloud：https://ram.console.aliyun.com/manage/ak
     * 3. Tencent Cloud：https://console.cloud.tencent.com/cam/capi
     * 4. Qiniu Cloud：https://portal.qiniu.com/user/key
     * 5. Huawei Cloud：https://support.huaweicloud.com/qs-obs/obs_qs_0005.html
     */
    @NotNull(message = "accessKey Cannot be empty")
    private String accessKey;
    /**
     * Visit Secret
     */
    @NotNull(message = "accessSecret Cannot be empty")
    private String accessSecret;

    @SuppressWarnings("RedundantIfStatement")
    @AssertTrue(message = "domain Cannot be empty")
    @JsonIgnore
    public boolean isDomainValid() {
        // If it is seven cows，Must have domain
        if (StrUtil.contains(endpoint, ENDPOINT_QINIU) && StrUtil.isEmpty(domain)) {
            return false;
        }
        return true;
    }

}
