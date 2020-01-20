package com.changhong.sei.basic.manager;

import com.changhong.sei.basic.dao.FeatureDao;
import com.changhong.sei.basic.dao.FeatureGroupDao;
import com.changhong.sei.basic.dao.MenuDao;
import com.changhong.sei.basic.dto.FeatureType;
import com.changhong.sei.basic.entity.Feature;
import com.changhong.sei.basic.entity.FeatureGroup;
import com.changhong.sei.basic.entity.Menu;
import com.changhong.sei.basic.entity.Tenant;
import com.changhong.sei.core.dao.BaseEntityDao;
import com.changhong.sei.core.dto.serach.Search;
import com.changhong.sei.core.dto.serach.SearchFilter;
import com.changhong.sei.core.manager.BaseEntityManager;
import com.changhong.sei.core.manager.bo.OperateResult;
import com.changhong.sei.core.manager.bo.OperateResultWithData;
import com.google.common.collect.Lists;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * *************************************************************************************************
 * <br>
 * 实现功能：功能项业务逻辑
 * <br>
 * ------------------------------------------------------------------------------------------------
 * <br>
 * 版本          变更时间                  变更人                 变更原因
 * <br>
 * ------------------------------------------------------------------------------------------------
 * <br>
 * 1.0.00      2017/4/20 9:16              李汶强                   新建
 * 1.0.00      2017/5/10 17:58             高银军                   修改
 * <br>
 * *************************************************************************************************<br>
 */
@Component
public class FeatureManager extends BaseEntityManager<Feature>{
    @Autowired
    private FeatureDao featureDao;
    @Autowired
    private MenuDao menuDao;
    @Autowired
    private FeatureGroupDao featureGroupDao;

    @Override
    protected BaseEntityDao<Feature> getDao() {
        return featureDao;
    }


    /**
     * 重写save 保存的时除去首尾的'/'
     *
     * @param entity
     * @return
     */
    @Override
    @Transactional
    public OperateResultWithData<Feature> save(Feature entity) {
        String url = entity.getUrl();
        entity.setUrl(StringUtils.strip(url, "/"));
        if(entity.isNew()){
            return super.save(entity);
        }else {
            Feature feature = findOne(entity.getId());
            if(feature.getGroupCode().equals(entity.getGroupCode())){
                return super.save(entity);
            }else {
                featureDao.updateGroup(feature.getGroupCode(),entity.getGroupCode());
                return super.save(entity);
            }
        }

    }

    /**
     * 通过代码获取功能项
     *
     * @param code 功能项代码
     * @return 功能项
     */
    public Feature findByCode(String code) {
        return featureDao.findByCode(code);
    }

    /**
     * 根据功能项组id查询功能项
     *
     * @param featureGroupId 功能项组的id
     * @return 功能项清单
     */
    public List<Feature> findByFeatureGroupId(String featureGroupId) {
        if (StringUtils.isBlank(featureGroupId)) {
            return Collections.emptyList();
        } else {
            List<SearchFilter> searchFilters = Lists.newArrayList(
                    new SearchFilter("featureGroup.id", featureGroupId, SearchFilter.Operator.EQ)
            );
            return findByFilters(Search.createSearch().setFilters(searchFilters));
        }
    }

    /**
     * 根据功能项组id以及功能项类型查询功能项
     *
     * @param featureGroupId 功能项组的id
     * @param featureTypes   功能项类型清单
     * @return 查询的结果
     */
    public List<Feature> findByFeatureGroupAndType(String featureGroupId, List<FeatureType> featureTypes) {
        if (StringUtils.isBlank(featureGroupId)) {
            return Collections.emptyList();
        } else {
            List<SearchFilter> searchFilters = Lists.newArrayList(
                    new SearchFilter("featureGroup.id", featureGroupId, SearchFilter.Operator.EQ)
            );
            //筛选操作类型功能项
            if (Objects.nonNull(featureTypes) && featureTypes.size() > 0) {
                searchFilters.add(new SearchFilter("featureType", featureTypes, SearchFilter.Operator.IN));
            }
            return findByFilters(Search.createSearch().setFilters(searchFilters));
        }
    }

    public List<Feature> findByAppModuleId(String appModuleId) {
        if (StringUtils.isNotBlank(appModuleId)) {
            SearchFilter searchFilter;
            searchFilter = new SearchFilter("appModule.id", appModuleId, SearchFilter.Operator.EQ);
            List<FeatureGroup> featureGroupList = featureGroupDao.findByFilter(searchFilter);
            if (CollectionUtils.isNotEmpty(featureGroupList)) {
                List<String> featureGroupIds = featureGroupList.stream().map(FeatureGroup::getId).collect(Collectors.toList());
                searchFilter = new SearchFilter("featureGroup.id", featureGroupIds, SearchFilter.Operator.IN);
                List<Feature> features = featureDao.findByFilter(searchFilter);
                return features;
            }
        }
        return Collections.emptyList();
    }

    /**
     * 根据功能项id查询子功能项
     *
     * @param featureId 功能项的id
     * @return 功能项列表
     */
    public List<Feature> findChildByFeatureId(String featureId) {
        Feature parentFeature = findOne(featureId);
        if (Objects.isNull(parentFeature)) {
            return new ArrayList<>();
        }
        //页面分组代码
        String groupCode = parentFeature.getGroupCode();
        //功能项分组id
        String featureGroupId = parentFeature.getFeatureGroup().getId();

        Search search = Search.createSearch();
        //同组下
        search.addFilter(new SearchFilter("featureGroup.id", featureGroupId));
        //页面分组代码
        search.addFilter(new SearchFilter("groupCode", groupCode));
        //功能项类型
        search.addFilter(new SearchFilter("featureType", FeatureType.Operate));
        return featureDao.findByFilters(search);
    }

    /**
     * 获取租户已经分配的应用模块对应的功能项清单
     *
     * @param tenant 租户
     * @return 功能项清单
     */
    public List<Feature> getTenantCanUseFeatures(Tenant tenant) {
        if (Objects.isNull(tenant)) {
            return Collections.emptyList();
        }
        return featureDao.getTenantCanUseFeatures(tenant.getId());
    }

    /**
     * 删除数据保存数据之前额外操作回调方法 子类根据需要覆写添加逻辑即可
     *
     * @param s 待删除数据对象主键
     */
    @Override
    protected OperateResult preDelete(String s) {
        List<Menu> menus = menuDao.findByFeatureId(s);
        if (menus != null && menus.size() > 0) {
            //该功能项存在菜单，禁止删除！
            return OperateResult.operationFailure("00015");
        }
        return super.preDelete(s);
    }
}
