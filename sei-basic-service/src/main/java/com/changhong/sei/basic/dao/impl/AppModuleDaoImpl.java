package com.changhong.sei.basic.dao.impl;

import com.changhong.sei.basic.dao.AppModuleExtDao;
import com.changhong.sei.basic.entity.AppModule;
import com.changhong.sei.core.dao.impl.BaseEntityDaoImpl;

import javax.persistence.EntityManager;

/**
 * 应用模块数据访问实现
 *
 * @author Vision.Mac
 * @version 1.0.1 2019/2/16 0:24
 */
public class AppModuleDaoImpl extends BaseEntityDaoImpl<AppModule> implements AppModuleExtDao {
    public AppModuleDaoImpl(EntityManager entityManager) {
        super(AppModule.class, entityManager);
    }
}
