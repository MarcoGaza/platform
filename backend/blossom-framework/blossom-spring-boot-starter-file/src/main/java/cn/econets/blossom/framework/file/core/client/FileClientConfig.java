package cn.econets.blossom.framework.file.core.client;

import com.fasterxml.jackson.annotation.JsonTypeInfo;

/**
 * File client configuration
 * Clients with different implementations，Needs different configuration，Define through subclass
 *
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS)
// @JsonTypeInfo The role of annotations，Jackson Polymorphism
// 1. When serializing to the database，Increase @class Properties。
// 2. When deserializing to memory objects，Passed @class Attributes，The correct type can be created
public interface FileClientConfig {
}
