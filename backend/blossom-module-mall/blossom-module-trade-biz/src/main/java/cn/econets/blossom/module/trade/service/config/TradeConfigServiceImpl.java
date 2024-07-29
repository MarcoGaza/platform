package cn.econets.blossom.module.trade.service.config;

import cn.econets.blossom.framework.common.util.collection.CollectionUtils;
import cn.econets.blossom.module.trade.controller.admin.config.vo.TradeConfigSaveReqVO;
import cn.econets.blossom.module.trade.convert.config.TradeConfigConvert;
import cn.econets.blossom.module.trade.dal.dataobject.config.TradeConfigDO;
import cn.econets.blossom.module.trade.dal.mysql.config.TradeConfigMapper;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.util.List;

/**
 * Trading Center Configuration Service Implementation class
 *
 */
@Service
@Validated
public class TradeConfigServiceImpl implements TradeConfigService {

    @Resource
    private TradeConfigMapper tradeConfigMapper;

    @Override
    public void saveTradeConfig(TradeConfigSaveReqVO saveReqVO) {
        // Exists，Then update
        TradeConfigDO dbConfig = getTradeConfig();
        if (dbConfig != null) {
            tradeConfigMapper.updateById(TradeConfigConvert.INSTANCE.convert(saveReqVO).setId(dbConfig.getId()));
            return;
        }
        // Does not exist，Insert
        tradeConfigMapper.insert(TradeConfigConvert.INSTANCE.convert(saveReqVO));
    }

    @Override
    public TradeConfigDO getTradeConfig() {
        List<TradeConfigDO> list = tradeConfigMapper.selectList();
        return CollectionUtils.getFirst(list);
    }

}
