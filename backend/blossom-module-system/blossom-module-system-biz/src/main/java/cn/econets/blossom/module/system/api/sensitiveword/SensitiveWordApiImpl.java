package cn.econets.blossom.module.system.api.sensitiveword;

import cn.econets.blossom.module.system.service.sensitiveword.SensitiveWordService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Sensitive words API Implementation class
 *
 */
@Service
public class SensitiveWordApiImpl implements SensitiveWordApi {

    @Resource
    private SensitiveWordService sensitiveWordService;

    @Override
    public List<String> validateText(String text, List<String> tags) {
        return sensitiveWordService.validateText(text, tags);
    }

    @Override
    public boolean isTextValid(String text, List<String> tags) {
        return sensitiveWordService.isTextValid(text, tags);
    }
}
