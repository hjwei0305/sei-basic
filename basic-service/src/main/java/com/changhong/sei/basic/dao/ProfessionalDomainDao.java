package com.changhong.sei.basic.dao;

import com.changhong.sei.basic.entity.ProfessionalDomain;
import com.changhong.sei.core.dao.BaseTreeDao;
import org.springframework.stereotype.Repository;

/**
 * 领域dao
 * Author:jamson
 * date:2018/3/13
 */
@Repository
public interface ProfessionalDomainDao extends BaseTreeDao<ProfessionalDomain> {

    /**
     * 根据code查找实体
     *
     * @param code 代码
     * @return 实体类
     */
    ProfessionalDomain findByCode(String code);

    /**
     * 根据code, tenantCode查找实体
     * @param code 代码
     * @param tenantCode 租户代码
     * @return
     */
    ProfessionalDomain findByCodeAndTenantCode(String code, String tenantCode);
}
