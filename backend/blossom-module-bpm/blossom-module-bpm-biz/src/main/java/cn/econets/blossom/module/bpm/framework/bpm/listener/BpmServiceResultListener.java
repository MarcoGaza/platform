package cn.econets.blossom.module.bpm.framework.bpm.listener;

import cn.hutool.core.util.StrUtil;
import cn.econets.blossom.framework.common.util.object.BeanUtils;
import cn.econets.blossom.module.bpm.api.listener.BpmResultListenerApi;
import cn.econets.blossom.module.bpm.api.listener.dto.BpmResultListenerRespDTO;
import cn.econets.blossom.module.bpm.framework.bpm.core.event.BpmProcessInstanceResultEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

// TODO Changed to support later RPC
/**
 * Business process result listener implementation class
 *
 */
@Component
public class BpmServiceResultListener implements ApplicationListener<BpmProcessInstanceResultEvent> {

    @Resource
    private List<BpmResultListenerApi> bpmResultListenerApis;

    @Override
    public final void onApplicationEvent(BpmProcessInstanceResultEvent event) {
        bpmResultListenerApis.forEach(bpmResultListenerApi -> {
            if (!StrUtil.equals(event.getProcessDefinitionKey(), bpmResultListenerApi.getProcessDefinitionKey())) {
                return;
            }
            bpmResultListenerApi.onEvent(BeanUtils.toBean(event, BpmResultListenerRespDTO.class));
        });
    }

}
