package cn.econets.blossom.framework.quartz.core.handler;

/**
 * Task Processor
 *
 */
public interface JobHandler {

    /**
     * Execute the mission
     *
     * @param param Parameters
     * @return Results
     * @throws Exception Abnormal
     */
    String execute(String param) throws Exception;

}
