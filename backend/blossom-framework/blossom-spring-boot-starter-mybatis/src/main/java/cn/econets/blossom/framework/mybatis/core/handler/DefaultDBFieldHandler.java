package cn.econets.blossom.framework.mybatis.core.handler;

import cn.econets.blossom.framework.mybatis.core.dataobject.BaseDO;
import cn.econets.blossom.framework.web.core.util.WebFrameworkUtils;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * General parameter filling implementation class
 *
 * If no explicit assignment is made to the general parameters，General parameters will be filled in here、Assignment
 *
 */
public class DefaultDBFieldHandler implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {
        if (Objects.nonNull(metaObject) && metaObject.getOriginalObject() instanceof BaseDO) {
            BaseDO baseDO = (BaseDO) metaObject.getOriginalObject();

            LocalDateTime current = LocalDateTime.now();
            // The creation time is empty，The current time is used as the insertion time
            if (Objects.isNull(baseDO.getCreateTime())) {
                baseDO.setCreateTime(current);
            }
            // Update time is empty，The current time is used as the update time
            if (Objects.isNull(baseDO.getUpdateTime())) {
                baseDO.setUpdateTime(current);
            }

            Long userId = WebFrameworkUtils.getLoginUserId();
            // The current logged-in user is not empty，The creator is empty，The currently logged in user is the creator
            if (Objects.nonNull(userId) && Objects.isNull(baseDO.getCreator())) {
                baseDO.setCreator(userId.toString());
            }
            // The current logged-in user is not empty，Updater is empty，The currently logged in user is the updater
            if (Objects.nonNull(userId) && Objects.isNull(baseDO.getUpdater())) {
                baseDO.setUpdater(userId.toString());
            }
        }
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        // Update time is empty，The current time is used as the update time
        Object modifyTime = getFieldValByName("updateTime", metaObject);
        if (Objects.isNull(modifyTime)) {
            setFieldValByName("updateTime", LocalDateTime.now(), metaObject);
        }

        // The current logged-in user is not empty，Updater is empty，The currently logged in user is the updater
        Object modifier = getFieldValByName("updater", metaObject);
        Long userId = WebFrameworkUtils.getLoginUserId();
        if (Objects.nonNull(userId) && Objects.isNull(modifier)) {
            setFieldValByName("updater", userId.toString(), metaObject);
        }
    }
}
