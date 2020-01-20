package com.changhong.sei.basic.dao;

import com.changhong.sei.basic.entity.Organization;
import com.changhong.sei.core.dao.BaseTreeDao;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * *************************************************************************************************
 * <p/>
 * 实现功能：组织机构数据访问接口
 * <p>
 * ------------------------------------------------------------------------------------------------
 * 版本          变更时间             变更人                     变更原因
 * ------------------------------------------------------------------------------------------------
 * 1.0.00      2017/4/19 15:39        秦有宝                      新建
 * <p/>
 * *************************************************************************************************
 */
@Repository
public interface OrganizationDao extends BaseTreeDao<Organization> {

    Organization findByCodeAndTenantCode(String code, String tenantCode);

    Organization findByParentIdIsNullAndTenantCode(String tenantCode);

    Organization findByParentIdIsNullAndId(String id);

    @Query("select max(t.lastEditedDate) FROM Organization t")
    Date findMaxUpdateDate();

    List<Organization> findByTenantCodeAndIdIn(String tenantCode, Collection<String> ids);
}
