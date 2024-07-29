package cn.econets.blossom.module.system.job;

import cn.econets.blossom.framework.quartz.core.handler.JobHandler;
import cn.econets.blossom.framework.tenant.core.context.TenantContextHolder;
import cn.econets.blossom.framework.tenant.core.job.TenantJob;
import cn.econets.blossom.module.system.dal.dataobject.user.AdminUserDO;
import cn.econets.blossom.module.system.dal.mysql.user.AdminUserMapper;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

@Component
public class DemoJob implements JobHandler {

    @Resource
    private AdminUserMapper adminUserMapper;

    @Override
    @TenantJob // Mark multi-tenant
    public String execute(String param) {
        System.out.println("Current tenant：" + TenantContextHolder.getTenantId());
        List<AdminUserDO> users = adminUserMapper.selectList();
        return "Number of users：" + users.size();
    }

}
