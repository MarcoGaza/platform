package cn.econets.blossom.module.trade.framework.delivery.core.client.impl.kdniao;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.net.URLEncodeUtil;
import cn.hutool.crypto.digest.DigestUtil;
import cn.econets.blossom.framework.common.util.json.JsonUtils;
import cn.econets.blossom.module.trade.framework.delivery.config.TradeExpressProperties;
import cn.econets.blossom.module.trade.framework.delivery.core.client.ExpressClient;
import cn.econets.blossom.module.trade.framework.delivery.core.client.dto.ExpressTrackQueryReqDTO;
import cn.econets.blossom.module.trade.framework.delivery.core.client.dto.ExpressTrackRespDTO;
import cn.econets.blossom.module.trade.framework.delivery.core.client.dto.kdniao.KdNiaoExpressQueryReqDTO;
import cn.econets.blossom.module.trade.framework.delivery.core.client.dto.kdniao.KdNiaoExpressQueryRespDTO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;

import static cn.econets.blossom.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.econets.blossom.module.trade.enums.ErrorCodeConstants.EXPRESS_API_QUERY_FAILED;
import static cn.econets.blossom.module.trade.enums.ErrorCodeConstants.EXPRESS_API_QUERY_ERROR;
import static cn.econets.blossom.module.trade.framework.delivery.core.client.convert.ExpressQueryConvert.INSTANCE;

/**
 * Expressbird Client
 *
 */
@Slf4j
@AllArgsConstructor
public class KdNiaoExpressClient implements ExpressClient {

    private static final String REAL_TIME_QUERY_URL = "https://api.kdniao.com/Ebusiness/EbusinessOrderHandle.aspx";

    /**
     * Expressbird Instant Query Free Version RequestType
     */
    private static final String REAL_TIME_FREE_REQ_TYPE = "1002";

    private final RestTemplate restTemplate;
    private final TradeExpressProperties.KdNiaoConfig config;

    /**
     * Query the express delivery track【Free version】
     *
     * Only supported 3 Home：STO Express、YTO Express、BEST EXPRESS
     *
     * @see <a href="https://www.yuque.com/kdnjishuzhichi/dfcrg1/wugo6k">Interface documentation</a>
     *
     * @param reqDTO Query request parameters
     * @return Express Track
     */
    @Override
    public List<ExpressTrackRespDTO> getExpressTrackList(ExpressTrackQueryReqDTO reqDTO) {
        // Initiate request
        KdNiaoExpressQueryReqDTO requestDTO = INSTANCE.convert(reqDTO)
                .setExpressCode(reqDTO.getExpressCode().toUpperCase());
        KdNiaoExpressQueryRespDTO respDTO = httpRequest(REAL_TIME_QUERY_URL, REAL_TIME_FREE_REQ_TYPE,
                requestDTO, KdNiaoExpressQueryRespDTO.class);

        // Processing results
        if (respDTO == null || !respDTO.getSuccess()) {
            throw exception(EXPRESS_API_QUERY_FAILED, respDTO == null ? "" : respDTO.getReason());
        }
        if (CollUtil.isEmpty(respDTO.getTracks())) {
            return Collections.emptyList();
        }
        return INSTANCE.convertList(respDTO.getTracks());
    }

    /**
     * ExpressBird API Request
     *
     * @param url Request url
     * @param requestType Corresponding request instruction (Express Bird RequestType)
     * @param req  Request parameters corresponding to the request
     * @param respClass Response to the request class
     * @param <Req> Request structure for each request Req DTO
     * @param <Resp> The response structure of each request Resp DTO
     */
    private <Req, Resp> Resp httpRequest(String url, String requestType, Req req, Class<Resp> respClass) {
        // Request header
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        // Request body
        String reqData = JsonUtils.toJsonString(req);
        String dataSign = generateDataSign(reqData, config.getApiKey());
        MultiValueMap<String, String> requestBody = new LinkedMultiValueMap<>();
        requestBody.add("RequestData", reqData);
        requestBody.add("DataType", "2");
        requestBody.add("EBusinessID", config.getBusinessId());
        requestBody.add("DataSign", dataSign);
        requestBody.add("RequestType", requestType);
        log.debug("[httpRequest][RequestType({}) Request parameters({})]", requestType, requestBody);

        // Send request
        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(requestBody, headers);
        ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);
        log.debug("[httpRequest][RequestType({}) Response result({})", requestType, responseEntity);
        // Processing response
        if (!responseEntity.getStatusCode().is2xxSuccessful()) {
            throw exception(EXPRESS_API_QUERY_ERROR);
        }
        return JsonUtils.parseObject(responseEntity.getBody(), respClass);
    }

    /**
     * Expressbird generates request signature
     *
     * See <a href="https://www.yuque.com/kdnjishuzhichi/dfcrg1/zes04h">Signature Description</a>
     *
     * @param reqData Request entity
     * @param apiKey  api Key
     */
    private String generateDataSign(String reqData, String apiKey) {
        String plainText = String.format("%s%s", reqData, apiKey);
        return URLEncodeUtil.encode(Base64.encode(DigestUtil.md5Hex(plainText)));
    }

}
