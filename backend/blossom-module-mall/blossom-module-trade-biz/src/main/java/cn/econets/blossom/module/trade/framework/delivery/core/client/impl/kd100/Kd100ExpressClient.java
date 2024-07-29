package cn.econets.blossom.module.trade.framework.delivery.core.client.impl.kd100;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.HexUtil;
import cn.hutool.crypto.digest.DigestUtil;
import cn.econets.blossom.framework.common.util.json.JsonUtils;
import cn.econets.blossom.module.trade.framework.delivery.config.TradeExpressProperties;
import cn.econets.blossom.module.trade.framework.delivery.core.client.ExpressClient;
import cn.econets.blossom.module.trade.framework.delivery.core.client.dto.ExpressTrackQueryReqDTO;
import cn.econets.blossom.module.trade.framework.delivery.core.client.dto.ExpressTrackRespDTO;
import cn.econets.blossom.module.trade.framework.delivery.core.client.dto.kd100.Kd100ExpressQueryReqDTO;
import cn.econets.blossom.module.trade.framework.delivery.core.client.dto.kd100.Kd100ExpressQueryRespDTO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

import static cn.econets.blossom.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.econets.blossom.module.trade.enums.ErrorCodeConstants.EXPRESS_API_QUERY_ERROR;
import static cn.econets.blossom.module.trade.enums.ErrorCodeConstants.EXPRESS_API_QUERY_FAILED;
import static cn.econets.blossom.module.trade.framework.delivery.core.client.convert.ExpressQueryConvert.INSTANCE;

/**
 * Express Delivery 100 Client
 *
 */
@Slf4j
@AllArgsConstructor
public class Kd100ExpressClient implements ExpressClient {

    private static final String REAL_TIME_QUERY_URL = "https://poll.kuaidi100.com/poll/query.do";

    private final RestTemplate restTemplate;
    private final TradeExpressProperties.Kd100Config config;

    /**
     * Query the express delivery track
     *
     * @see <a href="https://api.kuaidi100.com/debug-tool/query/">Interface Documentation</a>
     *
     * @param reqDTO Query request parameters
     * @return Express Track
     */
    @Override
    public List<ExpressTrackRespDTO> getExpressTrackList(ExpressTrackQueryReqDTO reqDTO) {
        // Initiate request
        Kd100ExpressQueryReqDTO requestDTO = INSTANCE.convert2(reqDTO)
                .setExpressCode(reqDTO.getExpressCode().toLowerCase());
        Kd100ExpressQueryRespDTO respDTO = httpRequest(REAL_TIME_QUERY_URL, requestDTO,
                Kd100ExpressQueryRespDTO.class);

        // Processing results
        if (Objects.equals("false", respDTO.getResult())) {
            throw exception(EXPRESS_API_QUERY_FAILED, respDTO.getMessage());
        }
        if (CollUtil.isEmpty(respDTO.getTracks())) {
            return Collections.emptyList();
        }
        return INSTANCE.convertList2(respDTO.getTracks());
    }

    /**
     * Express Delivery 100 API Request
     *
     * @param url Request url
     * @param req Request parameters corresponding to the request
     * @param respClass Response to the request class
     * @param <Req> Request structure for each request Req DTO
     * @param <Resp> The response structure of each request Resp DTO
     */
    private <Req, Resp> Resp httpRequest(String url, Req req, Class<Resp> respClass) {
        // Request header
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        // Request body
        String param = JsonUtils.toJsonString(req);
        String sign = generateReqSign(param, config.getKey(), config.getCustomer()); // Signature
        MultiValueMap<String, String> requestBody = new LinkedMultiValueMap<>();
        requestBody.add("customer", config.getCustomer());
        requestBody.add("sign", sign);
        requestBody.add("param", param);
        log.debug("[httpRequest][Request parameters({})]", requestBody);

        // Send request
        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(requestBody, headers);
        ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);
        log.debug("[httpRequest][Response result({})]", responseEntity);
        // Processing Response
        if (!responseEntity.getStatusCode().is2xxSuccessful()) {
            throw exception(EXPRESS_API_QUERY_ERROR);
        }
        return JsonUtils.parseObject(responseEntity.getBody(), respClass);
    }

    private String generateReqSign(String param, String key, String customer) {
        String plainText = String.format("%s%s%s", param, key, customer);
        return HexUtil.encodeHexStr(DigestUtil.md5(plainText), false);
    }

}
