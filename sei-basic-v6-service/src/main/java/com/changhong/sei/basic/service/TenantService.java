package com.changhong.sei.basic.service;

import com.changhong.sei.basic.dao.TenantAppModuleDao;
import com.changhong.sei.basic.dao.TenantDao;
import com.changhong.sei.basic.entity.AppModule;
import com.changhong.sei.basic.entity.Organization;
import com.changhong.sei.basic.entity.Tenant;
import com.changhong.sei.core.context.ContextUtil;
import com.changhong.sei.core.dao.BaseEntityDao;
import com.changhong.sei.core.dto.ResultData;
import com.changhong.sei.core.service.BaseEntityService;
import com.changhong.sei.core.service.bo.OperateResult;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
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
@Service
public class TenantService extends BaseEntityService<Tenant> {

    @Autowired
    private TenantDao dao;
    @Autowired
    private TenantAppModuleDao tenantAppModuleDao;
    @Autowired
    private OrganizationService organizationService;

    @Override
    protected BaseEntityDao<Tenant> getDao() {
        return dao;
    }

    /**
     * 是否冻结
     *
     * @param tenantCode 租户代码
     * @return 返回true，则已被冻结；反之正常
     */
    public boolean isFrozen(String tenantCode) {
        Tenant tenant = dao.findByFrozenFalseAndCode(tenantCode);
        return Objects.isNull(tenant);
    }

    /**
     * 删除数据保存数据之前额外操作回调方法 子类根据需要覆写添加逻辑即可
     *
     * @param id 待删除数据对象主键
     */
    @Override
    protected OperateResult preDelete(String id) {
        if (tenantAppModuleDao.isExistsByProperty("parent.id", id)){
            // 租户存在已分配的应用，禁止删除！
            return OperateResult.operationFailure("00099");
        }
        return super.preDelete(id);
    }

    /**
     * 判断是否启用信用管理
     *
     * @return 否启用信用管理
     */
    public Boolean enableCreditManagement() {
        List<AppModule> appModules = tenantAppModuleDao.getAppModuleByTenantCode(ContextUtil.getTenantCode());
        if (appModules.stream().anyMatch(a -> StringUtils.startsWith(a.getCode(), "SOMS"))) {
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }

    /**
     * 主键删除
     *
     * @param id 主键
     * @return 返回操作结果对象
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public OperateResult delete(String id) {
        // 先级联删除对应的组织机构根节点
        Tenant tenant = dao.findOne(id);
        if (Objects.isNull(tenant)) {
            // 需要删除的租户【{0}】不存在！
            return OperateResult.operationSuccess("00100", id);
        }
        Organization deleteOrg = organizationService.findRootByTenantCode(tenant.getCode());
        if (Objects.nonNull(deleteOrg)) {
            OperateResult deleteOrgResult = organizationService.delete(deleteOrg.getId());
            if (deleteOrgResult.notSuccessful()) {
                return deleteOrgResult;
            }
        }
        // 删除租户
        return super.delete(id);
    }
}
