package cn.econets.blossom.framework.ip.core.utils;

import cn.econets.blossom.framework.ip.core.Area;
import cn.hutool.core.io.resource.ResourceUtil;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.lionsoul.ip2region.xdb.Searcher;

import java.io.IOException;

/**
 * IP Tools
 *
 * IP Data source comes from ip2region.xdb Simplified version，Based on <a href="https://gitee.com/zhijiantianya/ip2region"/> Project
 *
 */
@Slf4j
public class IPUtils {

    /**
     * Initialization SEARCHER
     */
    @SuppressWarnings("InstantiationOfUtilityClass")
    private final static IPUtils INSTANCE = new IPUtils();

    /**
     * IP Query Analyzer，Start loading into memory
     */
    private static Searcher SEARCHER;

    /**
     * Privatization structure
     */
    private IPUtils() {
        try {
            long now = System.currentTimeMillis();
            byte[] bytes = ResourceUtil.readBytes("ip2region.xdb");
            SEARCHER = Searcher.newWithBuffer(bytes);
            log.info("Start loading IPUtils Success，Time-consuming ({}) Milliseconds", System.currentTimeMillis() - now);
        } catch (IOException e) {
            log.error("Start loading IPUtils Failed", e);
        }
    }

    /**
     * Query IP Corresponding region number
     *
     * @param ip IP Address，The format is 127.0.0.1
     * @return Regionid
     */
    @SneakyThrows
    public static Integer getAreaId(String ip) {
        return Integer.parseInt(SEARCHER.search(ip.trim()));
    }

    /**
     * Query IP Corresponding region number
     *
     * @param ip IP Timestamp of the address，Format reference{@link Searcher#checkIP(String)} Return
     * @return Region Code
     */
    @SneakyThrows
    public static Integer getAreaId(long ip) {
        return Integer.parseInt(SEARCHER.search(ip));
    }

    /**
     * Query IP Corresponding region
     *
     * @param ip IP Address，The format is 127.0.0.1
     * @return Region
     */
    public static Area getArea(String ip) {
        return AreaUtils.getArea(getAreaId(ip));
    }

    /**
     * Query IP Corresponding region
     *
     * @param ip IP Timestamp of the address，Format reference{@link Searcher#checkIP(String)} Return
     * @return Region
     */
    public static Area getArea(long ip) {
        return AreaUtils.getArea(getAreaId(ip));
    }
}
