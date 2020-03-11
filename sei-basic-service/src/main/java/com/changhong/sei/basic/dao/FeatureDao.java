package com.changhong.sei.basic.dao;

import com.changhong.sei.basic.dto.FeatureType;
import com.changhong.sei.basic.entity.Feature;
import com.changhong.sei.core.dao.BaseEntityDao;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * *************************************************************************************************
 * <p/>
 * 实现功能：实现功能项数据访问接口
 * <p>
 * ------------------------------------------------------------------------------------------------
 * 版本          变更时间                  变更人                 变更原因
 * ------------------------------------------------------------------------------------------------
 * 1.0.00      2017/4/19 16:42             李汶强                  新建
 * <p/>
 * *************************************************************************************************
 */
@Repository
public interface FeatureDao extends BaseEntityDao<Feature> {
    /**
     * 通过代码获取功能项
     * @param code 功能项代码
     * @return 功能项
     */
    Feature findByCode(String code);

    /**
     * 获取租户可用的功能项
     * @param tenantId 租户Id
     * @return 租户可用的功能项清单
     */
    @Query("select f from Feature f inner join FeatureGroup g on f.featureGroup.id=g.id " +
            "inner join AppModule a on g.appModule.id=a.id " +
            "inner join TenantAppModule t on a.id=t.child.id " +
            "where t.parent.id=?1 and f.tenantCanUse=1 ")
    List<Feature> getTenantCanUseFeatures(String tenantId);

    @Query("update Feature f set f.groupCode=:groupCode where f.groupCode = :originGroupCode")
    @Modifying
    void updateGroup(@Param("originGroupCode") String originGroupCode, @Param("groupCode") String groupCode);

    /**
     * 获取指定代码的菜单功能项
     * @param groupCode 页面代码
     * @param featureType 功能项类型
     * @return 功能项
     */
    Feature findFirstByGroupCodeAndFeatureType(String groupCode, FeatureType featureType);

    /**
     * 通过页面代码获取所有下级功能项Id清单
     * @param groupCode 页面代码
     * @return 功能项Id清单
     */
    @Query("select f.id from Feature f where f.groupCode=?1 and f.featureType<>2")
    List<String> getFeatureIdsByGroupCode(String groupCode);
}
