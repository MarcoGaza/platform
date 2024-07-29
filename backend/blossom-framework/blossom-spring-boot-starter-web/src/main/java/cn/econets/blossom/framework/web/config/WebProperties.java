package cn.econets.blossom.framework.web.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@ConfigurationProperties(prefix = "application.web")
@Validated
@Data
public class WebProperties {

    @NotNull(message = "APP API Cannot be empty")
    private Api appApi = new Api("/app-api", "**.controller.app.**");
    @NotNull(message = "Admin API Cannot be empty")
    private Api adminApi = new Api("/admin-api", "**.controller.admin.**");

    @NotNull(message = "Admin UI Cannot be empty")
    private Ui adminUi;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Valid
    public static class Api {

        /**
         * API Prefix，Achieve all Controller Provided RESTFul API Uniform prefix of
         *
         *
         * Meaning：Through this prefix，Avoid Swagger、Actuator Accidental Passage Nginx Exposed to the outside，Brings security issues
         *      Like this，Nginx Just need to configure forwarding to /api/* All interfaces are OK。
         *
         * @see WebAutoConfiguration#configurePathMatch(PathMatchConfigurer)
         */
        @NotEmpty(message = "API The prefix cannot be empty")
        private String prefix;

        /**
         * Controller The package Ant Path rules
         *
         * The main purpose is to，Give it Controller Set the specified {@link #prefix}
         */
        @NotEmpty(message = "Controller The package cannot be empty")
        private String controller;

    }

    @Data
    @Valid
    public static class Ui {

        /**
         * Access address
         */
        private String url;

    }

}
