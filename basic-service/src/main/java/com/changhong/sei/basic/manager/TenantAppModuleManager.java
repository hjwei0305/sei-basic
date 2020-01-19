package com.changhong.sei.basic.manager;

import com.changhong.sei.basic.dao.TenantAppModuleDao;
import com.changhong.sei.basic.entity.AppModule;
import com.changhong.sei.basic.entity.Tenant;
import com.changhong.sei.basic.entity.TenantAppModule;
import com.changhong.sei.core.context.ContextUtil;
import com.changhong.sei.core.dao.BaseRelationDao;
import com.changhong.sei.core.manager.BaseRelationManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * *************************************************************************************************
 * <p/>
 * 实现功能：租户分配应用模块的业务逻辑实现
 * <p>
 * ------------------------------------------------------------------------------------------------
 * 版本          变更时间             变更人                     变更原因
 * ------------------------------------------------------------------------------------------------
 * 1.0.00      2017-05-05 9:32      王锦光(wangj)                新建
 * <p/>
 * *************************************************************************************************
 */
@Component
public class TenantAppModuleManager extends BaseRelationManager<TenantAppModule, Tenant, AppModule> {

    @Autowired
    private TenantAppModuleDao dao;

    @Override
    protected BaseRelationDao<TenantAppModule, Tenant, AppModule> getDao() {
        return dao;
    }

    @Autowired
    private AppModuleManager appModuleManager;

    /**
     * 获取可以分配的子实体清单
     *
     * @return 子实体清单
     */
    @Override
    protected List<AppModule> getCanAssignedChildren(String parentId) {
        return appModuleManager.findAll();
    }

    /**
     * 获取当前用户可用的应用模块代码清单
     *
     * @return 应用模块代码清单
     */
    public List<String> getAppModuleCodes() {
        String tenantCode = ContextUtil.getTenantCode();
        List<AppModule> appModules = dao.getAppModuleByTenantCode(tenantCode);
        if (Objects.isNull(appModules) || appModules.isEmpty()) {
            return Collections.emptyList();
        }
        return appModules.stream().map((a) -> a.getCode()).collect(Collectors.toList());
    }

    /**
     * 获取当前用户可用的应用模块id清单
     *
     * @return 应用模块id清单
     */
    public List<String> getTenantAppModuleIds() {
        String tenantCode = ContextUtil.getTenantCode();
        List<AppModule> appModules = dao.getAppModuleByTenantCode(tenantCode);
        if (Objects.isNull(appModules) || appModules.isEmpty()) {
            return Collections.emptyList();
        }
        return appModules.stream().map((a) -> a.getId()).collect(Collectors.toList());
    }

    /**
     * 获取当前用户可用的应用模块代码清单
     *
     * @return 应用模块代码清单
     */
    public List<AppModule> getTenantAppModules() {
        String tenantCode = ContextUtil.getTenantCode();
        if(tenantCode.equals("global")){
            return appModuleManager.findAll();
        }
        return dao.getAppModuleByTenantCode(tenantCode);
    }
}
