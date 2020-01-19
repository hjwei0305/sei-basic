package com.changhong.sei.basic.manager;

import com.changhong.sei.basic.dao.TenantDao;
import com.changhong.sei.basic.entity.Tenant;
import com.changhong.sei.core.dao.BaseEntityDao;
import com.changhong.sei.core.manager.BaseEntityManager;
import com.changhong.sei.core.manager.bo.OperateResultWithData;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * *************************************************************************************************
 * <p/>
 * 实现功能：实现租户的业务逻辑服务
 * <p>
 * ------------------------------------------------------------------------------------------------
 * 版本          变更时间                  变更人                 变更原因
 * ------------------------------------------------------------------------------------------------
 * 1.0.00      2017/4/14 15:45            高银军                  新建
 * <p/>
 * *************************************************************************************************
 */
@Component
public class TenantManager extends BaseEntityManager<Tenant> {

    @Autowired
    private TenantDao tenantDao;

    @Override
    protected BaseEntityDao<Tenant> getDao() {
        return tenantDao;
    }

    /**
     * 是否冻结
     *
     * @param tenantCode 租户代码
     * @return 返回true，则已被冻结；反之正常
     */
    public boolean isFrozen(String tenantCode) {
        Tenant tenant = tenantDao.findByFrozenFalseAndCode(tenantCode);
        return Objects.isNull(tenant);
    }
}
