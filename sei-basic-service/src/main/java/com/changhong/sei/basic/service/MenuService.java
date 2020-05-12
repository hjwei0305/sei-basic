package com.changhong.sei.basic.service;

import com.changhong.sei.basic.dao.MenuDao;
import com.changhong.sei.basic.entity.AppModule;
import com.changhong.sei.basic.entity.Feature;
import com.changhong.sei.basic.entity.Menu;
import com.changhong.sei.basic.service.client.NumberGenerator;
import com.changhong.sei.core.context.ContextUtil;
import com.changhong.sei.core.dao.BaseTreeDao;
import com.changhong.sei.core.dto.serach.SearchFilter;
import com.changhong.sei.core.service.BaseTreeService;
import com.changhong.sei.core.service.bo.OperateResult;
import com.changhong.sei.core.service.bo.OperateResultWithData;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Predicate;

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
@Service
@CacheConfig(cacheNames = "menu-cache")
public class MenuService extends BaseTreeService<Menu> {

    @Autowired
    private MenuDao menuDao;
    @Autowired
    private NumberGenerator numberGenerator;

    @Override
    protected BaseTreeDao<Menu> getDao() {
        return menuDao;
    }

    /**
     * 基于主键集合查询集合数据对象
     */
    @Override
    @Cacheable
    public List<Menu> findAll() {
        return super.findAll();
    }

    /**
     * 保存菜单项
     *
     * @param menu 要保存的菜单
     * @return 操作后的结果
     */
    @Override
    @CacheEvict(allEntries = true)
    public OperateResultWithData<Menu> save(Menu menu) {
        if (StringUtils.isBlank(menu.getCode())) {
            menu.setCode(numberGenerator.getNumber(Menu.class));
        }
        return super.save(menu);
    }

    /**
     * 通过Id标识删除一个树节点
     *
     * @param id 主键
     * @return 操作结果
     */
    @Override
    @CacheEvict(allEntries = true)
    public OperateResult delete(String id) {
        return super.delete(id);
    }

    /**
     * 移动节点
     *
     * @param nodeId         当前节点ID
     * @param targetParentId 目标父节点ID
     * @return 返回操作结果对象
     */
    @Override
    @CacheEvict(allEntries = true)
    public OperateResult move(String nodeId, String targetParentId) {
        return super.move(nodeId, targetParentId);
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
                    AppModule appModule = menu.getFeature().getFeatureGroup().getAppModule();
                    String apiBaseAddress = appModule.getApiBaseAddress();
                    if (StringUtils.isNotBlank(apiBaseAddress)){
                        String baseAddress = ContextUtil.getProperty(apiBaseAddress);
                        menu.getFeature().getFeatureGroup().getAppModule().setApiBaseAddress(baseAddress);
                    }
                    String webBaseAddress = appModule.getWebBaseAddress();
                    if (StringUtils.isNotBlank(webBaseAddress)){
                        String baseAddress = ContextUtil.getProperty(webBaseAddress);
                        menu.getFeature().getFeatureGroup().getAppModule().setWebBaseAddress(baseAddress);
                    }
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

    /**
     * 通过功能项清单获取菜单
     * @param features 功能项清单
     * @param allMenus 所有菜单项
     * @return 菜单
     */
    public static List<Menu> getMenusByFeatures(List<Feature> features, List<Menu> allMenus) {
        if (CollectionUtils.isEmpty(features) || CollectionUtils.isEmpty(allMenus)) {
            return Collections.emptyList();
        }
        Set<Menu> featureMenus = new LinkedHashSet<>();
        features.forEach((f) -> {
            if (f.getCanMenu()) {
                Optional<Menu> menuOptional = allMenus.stream().filter(m-> StringUtils.equals(f.getId(), m.getFeatureId())).findAny();
                menuOptional.ifPresent(featureMenus::add);
            }
        });
        return new LinkedList<>(featureMenus);
    }

    /**
     * 生成所有菜单节点
     * @param userMenus 用户的功能项菜单节点
     * @param nodes 功能项菜单节点
     * @param allMenus 所有菜单项
     * @return 菜单
     */
    public static void generateUserMenuNodes(Set<Menu> userMenus, List<Menu> nodes, List<Menu> allMenus) {
        nodes.forEach(node-> {
            // 获取父节点
            getParentMenu(userMenus, node, allMenus);
        });
    }

    private static void getParentMenu(Set<Menu> userMenus, Menu node, List<Menu> allMenus) {
        // 获取父节点
        String parentId = node.getParentId();
        if (StringUtils.isNotBlank(parentId)) {
            // 判断是否已经包含在结果中
            Predicate<Menu> predicate = menu -> StringUtils.equals(parentId, menu.getId());
            if (userMenus.stream().anyMatch(predicate)) {
                return;
            }
            Optional<Menu> menuOptional = allMenus.stream().filter(m-> StringUtils.equals(parentId, m.getId())).findAny();
            if (menuOptional.isPresent()) {
                Menu parentNode = menuOptional.get();
                userMenus.add(parentNode);
                getParentMenu(userMenus, parentNode, allMenus);
            }
        }
    }
}
