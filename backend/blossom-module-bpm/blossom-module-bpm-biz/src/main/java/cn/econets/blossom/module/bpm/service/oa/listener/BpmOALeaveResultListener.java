package cn.econets.blossom.module.bpm.service.oa.listener;

import cn.econets.blossom.module.bpm.framework.bpm.core.event.BpmProcessInstanceResultEvent;
import cn.econets.blossom.module.bpm.framework.bpm.core.event.BpmProcessInstanceResultEventListener;
import cn.econets.blossom.module.bpm.service.oa.BpmOALeaveService;
import cn.econets.blossom.module.bpm.service.oa.BpmOALeaveServiceImpl;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * OA Listener implementation class for leave request result
 *
 * 
 */
@Component
public class BpmOALeaveResultListener extends BpmProcessInstanceResultEventListener {

    @Resource
    private BpmOALeaveService leaveService;

    @Override
    protected String getProcessDefinitionKey() {
        return BpmOALeaveServiceImpl.PROCESS_KEY;
    }

    @Override
    protected void onEvent(BpmProcessInstanceResultEvent event) {
        leaveService.updateLeaveResult(Long.parseLong(event.getBusinessKey()), event.getResult());
    }

}
