package com.changhong.sei.basic.service;

import com.changhong.sei.basic.entity.TenantSetting;
import com.changhong.sei.basic.dao.TenantSettingDao;
import com.changhong.sei.core.dao.BaseEntityDao;
import com.changhong.sei.core.service.BaseEntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * 租户配置(TenantSetting)业务逻辑实现类
 *
 * @author sei
 * @since 2020-06-29 13:54:44
 */
@Service("tenantSettingService")
public class TenantSettingService extends BaseEntityService<TenantSetting> {
    @Autowired
    private TenantSettingDao dao;

    @Override
    protected BaseEntityDao<TenantSetting> getDao() {
        return dao;
    }

}