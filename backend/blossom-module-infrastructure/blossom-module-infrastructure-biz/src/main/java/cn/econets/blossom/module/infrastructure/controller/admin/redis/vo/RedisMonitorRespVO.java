package cn.econets.blossom.module.infrastructure.controller.admin.redis.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.Properties;

@Schema(description = "Management Backend - Redis Monitoring information Response VO")
@Data
@Builder
@AllArgsConstructor
public class RedisMonitorRespVO {

    @Schema(description = "Redis info Command result,Specific fields，View Redis Document", requiredMode = Schema.RequiredMode.REQUIRED)
    private Properties info;

    @Schema(description = "Redis key Quantity", requiredMode = Schema.RequiredMode.REQUIRED, example = "1024")
    private Long dbSize;

    @Schema(description = "CommandStat Array", requiredMode = Schema.RequiredMode.REQUIRED)
    private List<CommandStat> commandStats;

    @Schema(description = "Redis Command statistics results")
    @Data
    @Builder
    @AllArgsConstructor
    public static class CommandStat {

        @Schema(description = "Redis Command", requiredMode = Schema.RequiredMode.REQUIRED, example = "get")
        private String command;

        @Schema(description = "Number of calls", requiredMode = Schema.RequiredMode.REQUIRED, example = "1024")
        private Long calls;

        @Schema(description = "Consumption CPU Seconds", requiredMode = Schema.RequiredMode.REQUIRED, example = "666")
        private Long usec;

    }

}
