package cn.econets.blossom.framework.file.core.client.s3;

import cn.econets.blossom.framework.file.core.client.AbstractFileClient;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpUtil;
import io.minio.*;

import java.io.ByteArrayInputStream;

import static cn.econets.blossom.framework.file.core.client.s3.S3FileClientConfig.ENDPOINT_ALIYUN;
import static cn.econets.blossom.framework.file.core.client.s3.S3FileClientConfig.ENDPOINT_TENCENT;

/**
 * Based on S3 Protocol file client，Realization MinIO、Alibaba Cloud、Tencent Cloud、Qiniu Cloud、Huawei Cloud and other cloud services
 * <p>
 * S3 Client of the protocol，Using Amazon's software.amazon.awssdk.s3 Library
 *
 */
public class S3FileClient extends AbstractFileClient<S3FileClientConfig> {

    private MinioClient client;

    public S3FileClient(Long id, S3FileClientConfig config) {
        super(id, config);
    }

    @Override
    protected void doInit() {
        // Complete domain
        if (StrUtil.isEmpty(config.getDomain())) {
            config.setDomain(buildDomain());
        }
        // Initialize client
        client = MinioClient.builder()
                .endpoint(buildEndpointURL()) // Endpoint URL
                .region(buildRegion()) // Region
                .credentials(config.getAccessKey(), config.getAccessSecret()) // Authentication key
                .build();
    }

    /**
     * Based on endpoint Build a call to cloud service URL Address
     *
     * @return URI Address
     */
    private String buildEndpointURL() {
        // If it is already http Or https，No splicing is performed.Main adaptation MinIO
        if (HttpUtil.isHttp(config.getEndpoint()) || HttpUtil.isHttps(config.getEndpoint())) {
            return config.getEndpoint();
        }
        return StrUtil.format("https://{}", config.getEndpoint());
    }

    /**
     * Based on bucket + endpoint Build access Domain Address
     *
     * @return Domain Address
     */
    private String buildDomain() {
        // If it is already http Or https，No splicing is performed.Main adaptation MinIO
        if (HttpUtil.isHttp(config.getEndpoint()) || HttpUtil.isHttps(config.getEndpoint())) {
            return StrUtil.format("{}/{}", config.getEndpoint(), config.getBucket());
        }
        // Alibaba Cloud、Tencent Cloud、Huawei Cloud is suitable for all。Qiniu Cloud is quite special，Must have a custom domain name
        return StrUtil.format("https://{}.{}", config.getBucket(), config.getEndpoint());
    }

    /**
     * Based on bucket Build region Region
     *
     * @return region Region
     */
    private String buildRegion() {
        // Alibaba Cloud must have region，Otherwise an error will be reported
        if (config.getEndpoint().contains(ENDPOINT_ALIYUN)) {
            return StrUtil.subBefore(config.getEndpoint(), '.', false)
                    .replaceAll("-internal", "")// Remove the intranet Endpoint Suffix of
                    .replaceAll("https://", "");
        }
        // Tencent Cloud must have region，Otherwise an error will be reported
        if (config.getEndpoint().contains(ENDPOINT_TENCENT)) {
            return StrUtil.subAfter(config.getEndpoint(), ".cos.", false)
                    .replaceAll("." + ENDPOINT_TENCENT, ""); // Remove Endpoint
        }
        return null;
    }

    @Override
    public String upload(byte[] content, String path, String type) throws Exception {

        // File main path
        if (StrUtil.isNotEmpty(config.getHostFolder())){
            path = config.getHostFolder() + "/" + path;
        }

        // Execute upload
        client.putObject(PutObjectArgs.builder()
                .bucket(config.getBucket()) // bucket Must pass
                .contentType(type)
                .object(path) // Relative path as key
                .stream(new ByteArrayInputStream(content), content.length, -1) // File contents
                .build());
        // Splicing return path
        return config.getDomain() + "/" + path;
    }

    @Override
    public void delete(String path) throws Exception {
        client.removeObject(RemoveObjectArgs.builder()
                .bucket(config.getBucket()) // bucket Must pass
                .object(path) // Relative path as key
                .build());
    }

    @Override
    public byte[] getContent(String path) throws Exception {
        GetObjectResponse response = client.getObject(GetObjectArgs.builder()
                .bucket(config.getBucket()) // bucket Must pass
                .object(path) // Relative path as key
                .build());
        return IoUtil.readBytes(response);
    }

}
