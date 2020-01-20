package com.changhong.sei.basic.manager;

import com.changhong.sei.basic.dao.AuthorizeEntityTypeDao;
import com.changhong.sei.basic.entity.AuthorizeEntityType;
import com.changhong.sei.core.dao.BaseEntityDao;
import com.changhong.sei.core.manager.BaseEntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * *************************************************************************************************
 * <p/>
 * 实现功能：权限对象类型业务逻辑实现
 * <p>
 * ------------------------------------------------------------------------------------------------
 * 版本          变更时间             变更人                     变更原因
 * ------------------------------------------------------------------------------------------------
 * 1.0.00      2017-05-04 14:04      王锦光(wangj)                新建
 * <p/>
 * *************************************************************************************************
 */
@Component
public class AuthorizeEntityTypeManager extends BaseEntityManager<AuthorizeEntityType> {
    @Autowired
    private AuthorizeEntityTypeDao dao;
    @Override
    protected BaseEntityDao<AuthorizeEntityType> getDao() {
        return dao;
    }
}
