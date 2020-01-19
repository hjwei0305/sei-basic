package com.changhong.sei.basic.manager;

import com.changhong.sei.basic.dao.FeatureGroupDao;
import com.changhong.sei.basic.entity.FeatureGroup;
import com.changhong.sei.core.dao.BaseEntityDao;
import com.changhong.sei.core.dto.serach.SearchFilter;
import com.changhong.sei.core.manager.BaseEntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * *************************************************************************************************
 * <br>
 * 实现功能：
 * 应用模块Entity定义
 * <br>
 * ------------------------------------------------------------------------------------------------
 * <br>
 * 版本          变更时间             变更人                     变更原因
 * <br>
 * ------------------------------------------------------------------------------------------------
 * <br>
 * 1.0.00     2017/4/19  19:22    余思豆 (yusidou)                 新建
 * <br>
 * *************************************************************************************************<br>
 */
@Component
public class FeatureGroupManager extends BaseEntityManager<FeatureGroup> {

    @Autowired
    private FeatureGroupDao dao;

    @Override
    protected BaseEntityDao<FeatureGroup> getDao() {
        return dao;
    }

    /**
     * 根据功能项组名称模糊查询功能项组
     *
     * @param name 功能项组代码
     * @return 功能项组清单
     */
    public List<FeatureGroup> findByNameLike(String name) {
        String likeName = "%"+name+"%";
        return dao.findByNameLike(likeName);
    }

    /**
     * 根据应用模块id查询功能项组
     *
     * @param appModuleId 应用模块id
     * @return 功能项组
     */
    public List<FeatureGroup> findByAppModuleId(String appModuleId) {
        SearchFilter searchFilter = new SearchFilter("appModule.id", appModuleId, SearchFilter.Operator.EQ);
        return findByFilter(searchFilter);
    }
}
