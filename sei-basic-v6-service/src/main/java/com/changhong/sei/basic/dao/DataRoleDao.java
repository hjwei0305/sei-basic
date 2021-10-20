package com.changhong.sei.basic.dao;

import com.changhong.sei.basic.entity.DataRole;
import com.changhong.sei.basic.entity.FeatureRole;
import com.changhong.sei.core.dao.BaseEntityDao;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * *************************************************************************************************
 * <p/>
 * 实现功能：数据角色数据访问接口
 * <p>
 * ------------------------------------------------------------------------------------------------
 * 版本          变更时间             变更人                     变更原因
 * ------------------------------------------------------------------------------------------------
 * 1.0.00      2017-05-04 13:17      王锦光(wangj)                新建
 * <p/>
 * *************************************************************************************************
 */
public interface DataRoleDao extends BaseEntityDao<DataRole>,DataRoleExtDao {
    /**
     * 通过代码获取角色
     * @param code 代码
     * @param tenantCode 租户代码
     * @return 角色
     */
    DataRole findFirstByCodeAndTenantCode(String code, String tenantCode);

    /**
     * 通过角色组Id获取角色清单
     * @param dataRoleGroupId 角色组Id
     * @return 角色清单
     */
    List<DataRole> findByDataRoleGroupId(String dataRoleGroupId);

    /**
     * 获取用户本人创建的角色
     * @param account 用户账号
     * @param tenantCode 租户代码
     * @return 角色清单
     */
    List<DataRole> findByCreatorAccountAndTenantCode(String account, String tenantCode);

    /**
     * 获取用户本人创建的角色
     * @param groupId 角色组Id
     * @param account 用户账号
     * @param tenantCode 租户代码
     * @return 角色清单
     */
    @Query("select r from DataRole r where r.dataRoleGroupId=:groupId and r.creatorAccount=:account and r.tenantCode=:tenantCode")
    List<DataRole> findByCreator(@Param("groupId") String groupId, @Param("account") String account, @Param("tenantCode") String tenantCode);
}
