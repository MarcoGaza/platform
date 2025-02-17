package cn.econets.blossom.module.system.api.notify;

import cn.econets.blossom.module.system.api.notify.dto.NotifySendSingleToUserReqDTO;
import cn.econets.blossom.module.system.service.notify.NotifySendService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Send internal message API Implementation class
 *
 */
@Service
public class NotifyMessageSendApiImpl implements NotifyMessageSendApi {

    @Resource
    private NotifySendService notifySendService;

    @Override
    public Long sendSingleMessageToAdmin(NotifySendSingleToUserReqDTO reqDTO) {
        return notifySendService.sendSingleNotifyToAdmin(reqDTO.getUserId(),
                reqDTO.getTemplateCode(), reqDTO.getTemplateParams());
    }

    @Override
    public Long sendSingleMessageToMember(NotifySendSingleToUserReqDTO reqDTO) {
        return notifySendService.sendSingleNotifyToMember(reqDTO.getUserId(),
                reqDTO.getTemplateCode(), reqDTO.getTemplateParams());
    }

}
