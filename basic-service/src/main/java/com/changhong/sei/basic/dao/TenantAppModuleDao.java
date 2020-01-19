package com.changhong.sei.basic.dao;

import com.changhong.sei.basic.entity.AppModule;
import com.changhong.sei.basic.entity.Tenant;
import com.changhong.sei.basic.entity.TenantAppModule;
import com.changhong.sei.core.dao.BaseRelationDao;
import org.springframework.stereotype.Repository;

/**
 * *************************************************************************************************
 * <p/>
 * 实现功能：租户分配应用模块数据访问接口
 * <p>
 * ------------------------------------------------------------------------------------------------
 * 版本          变更时间             变更人                     变更原因
 * ------------------------------------------------------------------------------------------------
 * 1.0.00      2017-05-04 13:24      王锦光(wangj)                新建
 * <p/>
 * *************************************************************************************************
 */
@Repository
public interface TenantAppModuleDao extends BaseRelationDao<TenantAppModule, Tenant, AppModule>,TenantAppModuleExtDao {
}
