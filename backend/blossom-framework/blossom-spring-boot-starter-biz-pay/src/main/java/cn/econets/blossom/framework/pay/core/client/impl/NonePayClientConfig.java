package cn.econets.blossom.framework.pay.core.client.impl;

import cn.econets.blossom.framework.pay.core.client.PayClientConfig;
import lombok.Data;

import javax.validation.Validator;

/**
 * No configuration required PayClientConfig Implementation class
 *
 */
@Data
public class NonePayClientConfig implements PayClientConfig {

    /**
     * Configuration name
     * <p>
     * If no attributes are added，JsonUtils.parseObject2 Parsing will report an error，So add a name temporarily
     */
    private String name;

    public NonePayClientConfig(){
        this.name = "none-config";
    }

    @Override
    public void validate(Validator validator) {
        // No configuration, no verification required
    }
}
