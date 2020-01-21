package com.changhong.sei.basic.dao.impl;

import com.changhong.sei.basic.dao.FeatureRoleFeatureExtDao;
import com.changhong.sei.basic.entity.Feature;
import com.changhong.sei.basic.entity.FeatureRole;
import com.changhong.sei.basic.entity.FeatureRoleFeature;
import com.changhong.sei.core.dao.impl.BaseRelationDaoImpl;

import javax.persistence.EntityManager;

/**
 * *************************************************************************************************
 * <p/>
 * 实现功能：功能角色分配功能项数据访问实现
 * <p>
 * ------------------------------------------------------------------------------------------------
 * 版本          变更时间             变更人                     变更原因
 * ------------------------------------------------------------------------------------------------
 * 1.0.00      2017-05-09 8:58      王锦光(wangj)                新建
 * <p/>
 * *************************************************************************************************
 */
public class FeatureRoleFeatureDaoImpl extends BaseRelationDaoImpl<FeatureRoleFeature, FeatureRole, Feature>
        implements FeatureRoleFeatureExtDao {

    public FeatureRoleFeatureDaoImpl(EntityManager entityManager) {
        super(FeatureRoleFeature.class, entityManager);
    }
}
