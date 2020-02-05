package com.changhong.sei.basic.dao;

import com.changhong.sei.basic.entity.DataRoleAuthTypeValue;
import com.changhong.sei.core.dao.BaseEntityDao;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * *************************************************************************************************
 * <p/>
 * 实现功能：数据角色分配权限类型的值数据访问接口
 * <p>
 * ------------------------------------------------------------------------------------------------
 * 版本          变更时间             变更人                     变更原因
 * ------------------------------------------------------------------------------------------------
 * 1.0.00      2017-05-04 13:17      王锦光(wangj)                新建
 * <p/>
 * *************************************************************************************************
 */
public interface DataRoleAuthTypeValueDao extends BaseEntityDao<DataRoleAuthTypeValue>, DataRoleAuthTypeValueExtDao {

    /**
     * 根据数据权限类型id以及数据id列表获取角色id列表
     *
     * @param dataAuthorizeTypeId 数据权限类型id
     * @param entityIds           数据id列表
     * @return 角色id列表
     */
    @Query("select distinct t.dataRole.id from DataRoleAuthTypeValue as t where t.dataAuthorizeType.id=:dataAuthorizeTypeId and t.entityId in:entityIds")
    List<String> getRoleIdsByDataAuthTypeAndEntityIds(@Param("dataAuthorizeTypeId") String dataAuthorizeTypeId, @Param("entityIds") List<String> entityIds);
}
