package cn.econets.blossom.framework.pay.core.client.impl.mock;

import cn.econets.blossom.framework.pay.core.client.dto.order.PayOrderRespDTO;
import cn.econets.blossom.framework.pay.core.client.dto.order.PayOrderUnifiedReqDTO;
import cn.econets.blossom.framework.pay.core.client.dto.refund.PayRefundRespDTO;
import cn.econets.blossom.framework.pay.core.client.dto.refund.PayRefundUnifiedReqDTO;
import cn.econets.blossom.framework.pay.core.client.dto.transfer.PayTransferRespDTO;
import cn.econets.blossom.framework.pay.core.client.dto.transfer.PayTransferUnifiedReqDTO;
import cn.econets.blossom.framework.pay.core.client.impl.AbstractPayClient;
import cn.econets.blossom.framework.pay.core.client.impl.NonePayClientConfig;
import cn.econets.blossom.framework.pay.core.enums.channel.PayChannelEnum;
import cn.econets.blossom.framework.pay.core.enums.transfer.PayTransferTypeEnum;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * Simulated payment PayClient Implementation class
 *
 * The simulated payment returns are all successful，Convenient for everyone's daily life
 *
 */
public class MockPayClient extends AbstractPayClient<NonePayClientConfig> {

    private static final String MOCK_RESP_SUCCESS_DATA = "MOCK_SUCCESS";

    public MockPayClient(Long channelId, NonePayClientConfig config) {
        super(channelId, PayChannelEnum.MOCK.getCode(), config);
    }

    @Override
    protected void doInit() {
    }

    @Override
    protected PayOrderRespDTO doUnifiedOrder(PayOrderUnifiedReqDTO reqDTO) {
        return PayOrderRespDTO.successOf("MOCK-P-" + reqDTO.getOutTradeNo(), "", LocalDateTime.now(),
                reqDTO.getOutTradeNo(), MOCK_RESP_SUCCESS_DATA);
    }

    @Override
    protected PayOrderRespDTO doGetOrder(String outTradeNo) {
        return PayOrderRespDTO.successOf("MOCK-P-" + outTradeNo, "", LocalDateTime.now(),
                outTradeNo, MOCK_RESP_SUCCESS_DATA);
    }

    @Override
    protected PayRefundRespDTO doUnifiedRefund(PayRefundUnifiedReqDTO reqDTO) {
        return PayRefundRespDTO.successOf("MOCK-R-" + reqDTO.getOutRefundNo(), LocalDateTime.now(),
                reqDTO.getOutRefundNo(), MOCK_RESP_SUCCESS_DATA);
    }

    @Override
    protected PayRefundRespDTO doGetRefund(String outTradeNo, String outRefundNo) {
        return PayRefundRespDTO.successOf("MOCK-R-" + outRefundNo, LocalDateTime.now(),
                outRefundNo, MOCK_RESP_SUCCESS_DATA);
    }

    @Override
    protected PayRefundRespDTO doParseRefundNotify(Map<String, String> params, String body) {
        throw new UnsupportedOperationException("Simulated payment without refund callback");
    }

    @Override
    protected PayOrderRespDTO doParseOrderNotify(Map<String, String> params, String body) {
        throw new UnsupportedOperationException("Simulated payment without payment callback");
    }

    @Override
    protected PayTransferRespDTO doUnifiedTransfer(PayTransferUnifiedReqDTO reqDTO) {
        throw new UnsupportedOperationException("To be implemented");
    }

    @Override
    protected PayTransferRespDTO doGetTransfer(String outTradeNo, PayTransferTypeEnum type) {
        throw new UnsupportedOperationException("To be implemented");
    }

}
