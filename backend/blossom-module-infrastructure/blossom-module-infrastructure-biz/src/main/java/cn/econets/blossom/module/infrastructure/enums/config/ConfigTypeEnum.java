package cn.econets.blossom.module.infrastructure.enums.config;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ConfigTypeEnum {

    /**
     * System Configuration
     */
    SYSTEM(1),
    /**
     * Custom configuration
     */
    CUSTOM(2);

    private final Integer type;

}
