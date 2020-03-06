package com.changhong.sei.basic.dao;

import com.changhong.sei.basic.entity.DataAuthorizeType;
import com.changhong.sei.core.dao.BaseEntityDao;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * *************************************************************************************************
 * <p/>
 * 实现功能：数据权限类型数据访问定义
 * <p>
 * ------------------------------------------------------------------------------------------------
 * 版本          变更时间             变更人                     变更原因
 * ------------------------------------------------------------------------------------------------
 * 1.0.00     2017/4/19  16:50    王锦光(wangj)                 新建
 * <p/>
 * *************************************************************************************************
 */
@Repository
public interface DataAuthorizeTypeDao extends BaseEntityDao<DataAuthorizeType> {
    /**
     * 根据应用模块Id获取数据权限类型
     *
     * @param appModuleId 应用模块Id
     *
     * @return 数据权限类型清单
     */
    List<DataAuthorizeType> findByAuthorizeEntityTypeAppModuleId(String appModuleId);

    /**
     * 根据应用模块Id获取数据权限类型
     *
     * @param appModuleIds 应用模块Id清单
     *
     * @return 数据权限类型清单
     */
    List<DataAuthorizeType> findByAuthorizeEntityTypeAppModuleIdIn(List<String> appModuleIds);

    /**
     * 获取指定类名的数据权限类型
     * @param entityClassName 对象类型名
     * @param featureCode 功能项代码
     * @return 数据权限类型
     */
    @Query("select t from DataAuthorizeType t where t.authorizeEntityType.entityClassName=?1 and t.feature is not null and t.feature.code=?2")
    List<DataAuthorizeType> findByClassNameAndFeatureCode(String entityClassName, String featureCode);

    /**
     * 获取指定类名的数据权限类型
     * @param entityClassName 对象类型名
     * @return 数据权限类型
     */
    @Query("select t from DataAuthorizeType t where t.authorizeEntityType.entityClassName=?1 and t.feature is null")
    List<DataAuthorizeType> findByClassName(String entityClassName);

    /**
     * 通过权限对象Id和功能项Id获取数据权限类型
     * @param authorizeEntityTypeId 权限对象Id
     * @param featureId 功能项Id
     * @return 数据权限类型
     */
    DataAuthorizeType findByAuthorizeEntityTypeIdAndFeatureId(String authorizeEntityTypeId, String featureId);
}
