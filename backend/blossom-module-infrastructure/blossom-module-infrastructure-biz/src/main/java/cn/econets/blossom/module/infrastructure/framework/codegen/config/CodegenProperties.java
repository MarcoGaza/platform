package cn.econets.blossom.module.infrastructure.framework.codegen.config;

import cn.econets.blossom.module.infrastructure.enums.codegen.CodegenFrontTypeEnum;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Collection;

@ConfigurationProperties(prefix = "application.codegen")
@Validated
@Data
public class CodegenProperties {

    /**
     * Generated Java Basic package of code
     */
    @NotNull(message = "Java The base package of the code cannot be empty")
    private String basePackage;

    /**
     * Database name array
     */
    @NotEmpty(message = "The database cannot be empty")
    private Collection<String> dbSchemas;

    /**
     * Front-end type of code generation（Default）
     *
     * Enumeration {@link CodegenFrontTypeEnum#getType()}
     */
    @NotNull(message = "The front-end type of code generation cannot be empty")
    private Integer frontType;

}
