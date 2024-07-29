package cn.econets.blossom.framework.pay.core.client;

import com.fasterxml.jackson.annotation.JsonTypeInfo;

import javax.validation.Validator;

/**
 * Payment client configuration，The essence is the configuration of payment channels
 * Each different channel，Different configuration required，Defined by subclass
 *
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS)
// @JsonTypeInfo The role of annotations，Jackson Polymorphism
// 1. When serializing to the database，Increase @class Properties。
// 2. When deserializing to memory objects，Passed @class Properties，The correct type can be created
public interface PayClientConfig {

    /**
     * Parameter verification
     *
     * @param validator Verification object
     */
    void validate(Validator validator);

}
