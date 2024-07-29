package cn.econets.blossom.module.infrastructure.controller.admin.redis;

import cn.econets.blossom.framework.common.pojo.CommonResult;
import cn.econets.blossom.module.infrastructure.controller.admin.redis.vo.RedisMonitorRespVO;
import cn.econets.blossom.module.infrastructure.convert.redis.RedisConvert;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.redis.connection.RedisServerCommands;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Properties;

import static cn.econets.blossom.framework.common.pojo.CommonResult.success;

@Tag(name = "Management Backend - Redis Monitoring")
@RestController
@RequestMapping("/infra/redis")
public class RedisController {

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @GetMapping("/get-monitor-info")
    @Operation(summary = "Get Redis Monitoring information")
    @PreAuthorize("@ss.hasPermission('infra:redis:get-monitor-info')")
    public CommonResult<RedisMonitorRespVO> getRedisMonitorInfo() {
        // Get Redis Statistics
        Properties info = stringRedisTemplate.execute((RedisCallback<Properties>) RedisServerCommands::info);
        Long dbSize = stringRedisTemplate.execute(RedisServerCommands::dbSize);
        Properties commandStats = stringRedisTemplate.execute((
                RedisCallback<Properties>) connection -> connection.info("commandstats"));
        assert commandStats != null; // Assertion，Avoid warnings
        // The splicing result is returned
        return success(RedisConvert.INSTANCE.build(info, dbSize, commandStats));
    }

}
