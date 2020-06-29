package com.changhong.sei.basic.dao;

import com.changhong.sei.basic.entity.TenantSetting;
import com.changhong.sei.core.dao.BaseEntityDao;
import org.springframework.stereotype.Repository;

/**
 * 租户配置(TenantSetting)数据库访问类
 *
 * @author sei
 * @since 2020-06-29 13:54:44
 */
@Repository
public interface TenantSettingDao extends BaseEntityDao<TenantSetting> {

}