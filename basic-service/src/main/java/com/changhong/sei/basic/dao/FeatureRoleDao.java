package com.changhong.sei.basic.dao;

import com.changhong.sei.basic.entity.FeatureRole;
import com.changhong.sei.core.dao.BaseEntityDao;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * *************************************************************************************************
 * <p/>
 * 实现功能：功能角色数据访问接口
 * <p>
 * ------------------------------------------------------------------------------------------------
 * 版本          变更时间             变更人                     变更原因
 * ------------------------------------------------------------------------------------------------
 * 1.0.00      2017-05-04 13:17      王锦光(wangj)                新建
 * <p/>
 * *************************************************************************************************
 */
public interface FeatureRoleDao extends BaseEntityDao<FeatureRole>, FeatureRoleExtDao {
    /**
     * 获取用户本人创建的角色
     * @param account 用户账号
     * @param tenantCode 租户代码
     * @return 角色清单
     */
    List<FeatureRole> findByCreatorAccountAndTenantCode(String account, String tenantCode);

    /**
     * 获取用户本人创建的角色
     * @param groupId 角色组Id
     * @param account 用户账号
     * @param tenantCode 租户代码
     * @return 角色清单
     */
    @Query("select r from FeatureRole r where r.featureRoleGroup.id=:groupId and r.creatorAccount=:account and r.tenantCode=:tenantCode")
    List<FeatureRole> findByCreator(@Param("groupId") String groupId, @Param("account") String account, @Param("tenantCode") String tenantCode);
}
