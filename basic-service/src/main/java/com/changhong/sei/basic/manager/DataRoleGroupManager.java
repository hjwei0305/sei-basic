package com.changhong.sei.basic.manager;

import com.changhong.sei.basic.dao.DataRoleGroupDao;
import com.changhong.sei.basic.entity.DataRoleGroup;
import com.changhong.sei.core.dao.BaseEntityDao;
import com.changhong.sei.core.manager.BaseEntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * *************************************************************************************************
 * <p/>
 * 实现功能：数据角色组业务逻辑实现
 * <p>
 * ------------------------------------------------------------------------------------------------
 * 版本          变更时间             变更人                     变更原因
 * ------------------------------------------------------------------------------------------------
 * 1.0.00      2017-05-04 14:04      王锦光(wangj)                新建
 * <p/>
 * *************************************************************************************************
 */
@Service
public class DataRoleGroupManager extends BaseEntityManager<DataRoleGroup> {
    @Autowired
    private DataRoleGroupDao dao;
    @Override
    protected BaseEntityDao<DataRoleGroup> getDao() {
        return dao;
    }
}
