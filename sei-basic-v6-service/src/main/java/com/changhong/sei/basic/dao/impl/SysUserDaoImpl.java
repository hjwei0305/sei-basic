package com.changhong.sei.basic.dao.impl;

import com.changhong.sei.basic.dao.SysUserExtDao;
import com.changhong.sei.basic.entity.SysUser;
import com.changhong.sei.core.context.ContextUtil;
import com.changhong.sei.core.dao.impl.BaseEntityDaoImpl;
import com.changhong.sei.core.entity.ITenant;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.EntityManager;
import java.util.Date;

public class SysUserDaoImpl extends BaseEntityDaoImpl<SysUser>implements SysUserExtDao {

    public SysUserDaoImpl(EntityManager entityManager) {
        super(SysUser.class, entityManager);
    }

    public SysUser save(SysUser entity, boolean isNew) {
        //是否含有业务审计属性实体
        if (entity != null) {
            Date now = new Date();
            String userId = ContextUtil.getUserId();
            String userAccount = ContextUtil.getUserAccount();
            String userName = ContextUtil.getUserName();
            if (isNew) {//创建
                entity.setCreatorId(userId);
                entity.setCreatorName(userName);
                entity.setCreatorAccount(userAccount);
                entity.setCreatedDate(now);
            }
            entity.setLastEditorId(userId);
            entity.setLastEditorName(userName);
            entity.setLastEditorAccount(userAccount);
            entity.setLastEditedDate(now);
        }
        //是否是租户实体(只是在租户代码为空时设置)
        if (isNew) {
            entityManager.persist(entity);
            return entity;
        } else {
            return entityManager.merge(entity);
        }
    }
}
