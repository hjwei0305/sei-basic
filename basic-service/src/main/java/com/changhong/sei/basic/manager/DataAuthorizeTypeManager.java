package com.changhong.sei.basic.manager;

import com.changhong.sei.basic.dao.DataAuthorizeTypeDao;
import com.changhong.sei.basic.entity.DataAuthorizeType;
import com.changhong.sei.core.dao.BaseEntityDao;
import com.changhong.sei.core.manager.BaseEntityManager;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * *************************************************************************************************
 * <p/>
 * 实现功能：数据权限类型业务逻辑实现
 * <p>
 * ------------------------------------------------------------------------------------------------
 * 版本          变更时间             变更人                     变更原因
 * ------------------------------------------------------------------------------------------------
 * 1.0.00      2017-05-04 14:04      王锦光(wangj)                新建
 * <p/>
 * *************************************************************************************************
 */
@Service
public class DataAuthorizeTypeManager extends BaseEntityManager<DataAuthorizeType> {
    @Autowired
    private DataAuthorizeTypeDao dao;
    @Autowired
    private TenantAppModuleManager tenantAppModuleManager;

    @Override
    protected BaseEntityDao<DataAuthorizeType> getDao() {
        return dao;
    }

    /**
     * 通过实体类型名和功能项代码获取数据权限类型
     *
     * @param entityClassName 实体类型名
     * @param featureCode     功能项代码
     * @return 数据权限类型
     */
    DataAuthorizeType getByEntityClassNameAndFeature(String entityClassName, String featureCode) {
        //判断功能项代码是否为空
        List<DataAuthorizeType> result;
        if (StringUtils.isNotBlank(featureCode)) {
            result = dao.findByClassNameAndFeatureCode(entityClassName, featureCode);
        } else {
            result = dao.findByClassName(entityClassName);
        }
        if (CollectionUtils.isEmpty(result)){
            return null;
        }
        return result.get(0);
    }
}
