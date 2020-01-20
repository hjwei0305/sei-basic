package com.changhong.sei.basic.manager;

import com.changhong.sei.basic.dao.MenuDao;
import com.changhong.sei.basic.entity.Feature;
import com.changhong.sei.basic.entity.Menu;
import com.changhong.sei.basic.manager.client.NumberGenerator;
import com.changhong.sei.core.context.ContextUtil;
import com.changhong.sei.core.dao.BaseTreeDao;
import com.changhong.sei.core.dto.serach.SearchFilter;
import com.changhong.sei.core.manager.BaseTreeManager;
import com.changhong.sei.core.manager.bo.OperateResultWithData;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * *************************************************************************************************
 * <br>
 * 实现功能：系统菜单服务实现类
 * <br>
 * ------------------------------------------------------------------------------------------------
 * <br>
 * 版本          变更时间                  变更人                 变更原因
 * <br>
 * ------------------------------------------------------------------------------------------------
 * <br>
 * 1.0.00      2017/4/20 13:44              李汶强                  新建
 * 1.0.00      2017/5/10 17:58              高银军                   修改
 * <br>
 * *************************************************************************************************<br>
 */
@Component
public class MenuManager extends BaseTreeManager<Menu> {

    @Autowired
    private MenuDao menuDao;
    @Autowired
    private NumberGenerator numberGenerator;

    @Override
    protected BaseTreeDao<Menu> getDao() {
        return menuDao;
    }

    /**
     * 保存菜单项
     *
     * @param menu 要保存的菜单
     * @return 操作后的结果
     */
    @Override
    public OperateResultWithData<Menu> save(Menu menu) {
        if (StringUtils.isBlank(menu.getCode())) {
            menu.setCode(numberGenerator.getNumber(Menu.class));
        }
        return super.save(menu);
    }

    /**
     * 获取整个Menu多根树的树形对象
     *
     * @return Menu多根树对象集合
     */
    public List<Menu> getMenuTree() {
        List<Menu> rootTree = getAllRootNode();
        List<Menu> rootMenuTree = new ArrayList<>();
        for (Menu aRootTree : rootTree) {
            Menu menu = getTree(aRootTree.getId());
            rootMenuTree.add(menu);
        }
        for (Menu menu : rootMenuTree) {
            translateBaseAddress(menu);
        }
        return rootMenuTree;
    }

    /**
     * 翻译基地址
     */
    private void translateBaseAddress(Menu menu) {
        if (menu == null) {
            return;
        }
        if (menu.getFeature() != null) {
            if (menu.getFeature().getFeatureGroup() != null) {
                if (menu.getFeature().getFeatureGroup().getAppModule() != null) {
                    String apiBaseAddress = ContextUtil.getProperty(menu.getFeature().getFeatureGroup().getAppModule().getApiBaseAddress());
                    String webBaseAddress = ContextUtil.getProperty(menu.getFeature().getFeatureGroup().getAppModule().getWebBaseAddress());
                    menu.getFeature().getFeatureGroup().getAppModule().setApiBaseAddress(apiBaseAddress);
                    menu.getFeature().getFeatureGroup().getAppModule().setWebBaseAddress(webBaseAddress);
                }
            }
        }
        List<Menu> children = menu.getChildren();
        if (children != null && children.size() > 0) {
            for (Menu tempMenu : children) {
                translateBaseAddress(tempMenu);
            }
        }
    }

    /**
     * 获取名称匹配的菜单
     * @param name 名称
     * @return
     */
    public List<Menu> findByNameLike(String name) {
        String nameLike = "%" + name + "%";
        List<Menu> results = menuDao.findByNameLike(nameLike);
        if (!Objects.isNull(results)) {
            return results;
        } else {
            return Collections.emptyList();
        }
    }

    /**
     * 通过功能项清单获取菜单
     * @param features 功能项清单
     * @return 菜单
     */
    List<Menu> findByFeatures(List<Feature> features) {
        if (features == null || features.size() == 0) {
            return Collections.emptyList();
        }
        List<String> menuFeatureIds = new ArrayList<>();
        features.forEach((f) -> {
            if (f.getCanMenu()) {
                menuFeatureIds.add(f.getId());
            }
        });
        //修复列表为空时，查询全部的数据
        if (menuFeatureIds.size() == 0) {
            return Collections.emptyList();
        }
        //通过功能项获取菜单项
        SearchFilter filter = new SearchFilter("feature.id", menuFeatureIds, SearchFilter.Operator.IN);
        return findByFilter(filter);
    }
}
