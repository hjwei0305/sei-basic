package com.changhong.sei.basic.service;

import com.changhong.sei.basic.controller.FeatureController;
import com.changhong.sei.basic.dao.FeatureDao;
import com.changhong.sei.basic.dao.FeatureRoleFeatureDao;
import com.changhong.sei.basic.dto.AuthTreeVo;
import com.changhong.sei.basic.dto.FeatureDto;
import com.changhong.sei.basic.dto.FeatureNode;
import com.changhong.sei.basic.dto.FeatureType;
import com.changhong.sei.basic.entity.*;
import com.changhong.sei.basic.service.util.AuthorityUtil;
import com.changhong.sei.core.context.ContextUtil;
import com.changhong.sei.core.dao.BaseRelationDao;
import com.changhong.sei.core.dto.ResultData;
import com.changhong.sei.core.service.BaseRelationService;
import com.changhong.sei.core.service.bo.OperateResult;
import com.changhong.sei.exception.ServiceException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * *************************************************************************************************
 * <p/>
 * 实现功能：功能角色分配的功能项的业务逻辑实现
 * <p>
 * ------------------------------------------------------------------------------------------------
 * 版本          变更时间             变更人                     变更原因
 * ------------------------------------------------------------------------------------------------
 * 1.0.00      2017-05-05 9:32      王锦光(wangj)                新建
 * <p/>
 * *************************************************************************************************
 */
@Service
public class FeatureRoleFeatureService extends BaseRelationService<FeatureRoleFeature, FeatureRole, Feature> {
    @Autowired
    private FeatureRoleFeatureDao dao;
    @Autowired
    private FeatureDao featureDao;

    @Override
    protected BaseRelationDao<FeatureRoleFeature, FeatureRole, Feature> getDao() {
        return dao;
    }

    @Autowired
    private UserService userService;
    @Autowired
    private UserFeatureRoleService userFeatureRoleService;
    @Autowired
    private FeatureGroupService featureGroupService;

    /**
     * 获取可以分配的子实体清单
     *
     * @return 子实体清单
     */
    @Override
    protected List<Feature> getCanAssignedChildren(String parentId) {
        String userId = ContextUtil.getUserId();
        return userService.getUserCanAssignFeatures(userId);
    }

    /**
     * 获取未分配的功能项树
     *
     * @param appModuleId   应用模块id
     * @param featureRoleId 角色id
     * @return 功能项树清单
     */
    public List<FeatureNode> getUnassignedFeatureTree(String appModuleId, String featureRoleId) {
        List<FeatureNode> pageNodes = new LinkedList<>();
        // 获取所有未分配的功能项
        List<Feature> unassignedFeatures = getUnassignedChildren(featureRoleId);
        // 限制应用模块
        List<Feature> features = unassignedFeatures.stream().filter(f -> StringUtils.equals(appModuleId, f.getFeatureGroup().getAppModuleId())).collect(Collectors.toList());
        // 获取所有页面
        List<Feature> menuFeatures = features.stream().filter(feature -> feature.getFeatureType().equals(FeatureType.Page)).collect(Collectors.toList());
        // 检查并生成页面功能项清单
        buildPageFeatures(menuFeatures, features);
        // 定义所有页面节点
        menuFeatures.forEach(feature -> buildFeatureTree(pageNodes, features, feature));
        return pageNodes;
    }

    /**
     * 创建分配关系
     *
     * @param parentId 父实体Id
     * @param childIds 子实体Id清单
     * @return 操作结果
     */
    @Override
    public OperateResult insertRelations(String parentId, List<String> childIds) {
        UserFeatureRole userFeatureRole = userFeatureRoleService.getRelation(ContextUtil.getUserId(), parentId);
        if (userFeatureRole != null) {
            //00037 = 不能给当前用户的功能角色分配功能项！
            return OperateResult.operationFailure("00037");
        }
        OperateResult result = super.insertRelations(parentId, childIds);
        // 清除用户权限缓存
        AuthorityUtil.cleanAuthorizedCachesByFeatureRoleId(parentId);
        return result;
    }

    /**
     * 移除分配关系
     *
     * @param parentId 父实体Id
     * @param childIds 子实体Id清单
     * @return 操作结果
     */
    @Override
    public OperateResult removeRelations(String parentId, List<String> childIds) {
        UserFeatureRole userFeatureRole = userFeatureRoleService.getRelation(ContextUtil.getUserId(), parentId);
        if (userFeatureRole != null) {
            //00038 = 不能给当前用户的功能角色移除功能项！
            return OperateResult.operationFailure("00038");
        }
        OperateResult result = super.removeRelations(parentId, childIds);
        // 清除用户权限缓存
        AuthorityUtil.cleanAuthorizedCachesByFeatureRoleId(parentId);
        return result;
    }

    /**
     * 根据模块，获取指定个角色授权树
     *
     * @param appModuleId   应用模块id
     * @param featureRoleId 角色id
     * @return 指定模块授权树形对象集合
     */
    public ResultData<List<AuthTreeVo>> getAuthTree(String appModuleId, String featureRoleId) {
        List<AuthTreeVo> authTreeVos = new ArrayList<>();
        //校验参数
        if (StringUtils.isBlank(appModuleId)) {
            return ResultData.fail(ContextUtil.getMessage("00067", "appModuleId"));
        }
        if (StringUtils.isBlank(featureRoleId)) {
            return ResultData.fail(ContextUtil.getMessage("00067", "featureRoleId"));
        }
        //先根据appModuleId获取指定功能项组id
        List<FeatureGroup> featureGroups = featureGroupService.findByAppModuleId(appModuleId);
        if (featureGroups.size() > 0) {
            Set<String> featureGroupIds = featureGroups.stream().map(FeatureGroup::getId).collect(Collectors.toSet());
            //获取用户全部可分配的功能项 有缓存不用担心数据量
            List<Feature> canAssginedFeatures = this.getCanAssignedChildren(featureRoleId);
            //检查是否属于该功能项组
            if (canAssginedFeatures.size() > 0) {
                List<Feature> groupAssginedFeatures = canAssginedFeatures.stream().filter(feature -> featureGroupIds.contains(feature.getFeatureGroup().getId())).collect(Collectors.toList());
                if (groupAssginedFeatures.size() > 0) {
                    //获取已经分配的
                    List<Feature> assginedFeatures = this.getChildrenFromParentId(featureRoleId);
                    Map<String, String> assginedFeatureIds = assginedFeatures.stream().collect(Collectors.toMap(Feature::getId, Feature::getName));
                    //生成AuthTreeVo
                    authTreeVos = buildTree(groupAssginedFeatures, assginedFeatureIds);
                }
            }
        }
        return ResultData.success(authTreeVos);
    }

    /**
     * 构建权限树
     *
     * @param features         指定模块下所有功能项
     * @param assginedFeatures 指定模块下已分配功能项
     * @return
     */
    private List<AuthTreeVo> buildTree(List<Feature> features, Map<String, String> assginedFeatures) {
        //作为缓存,找寻父节点使用
        Map<String, AuthTreeVo> authTreeVos = new HashMap<>();
        //先对数据排序，页面级在最前面
        List<Feature> sortFeatures = features.stream().sorted(Comparator.comparing(Feature::getFeatureType).reversed()).collect(Collectors.toList());
        //1.先获取功能项为页面的，作为根节点
        Iterator<Feature> iter = sortFeatures.iterator();
        while (iter.hasNext()) {
            Feature feature = iter.next();
            FeatureDto featureDto = FeatureController.custConvertToDto(feature);
            if (FeatureType.Page.equals(feature.getFeatureType())) {
                AuthTreeVo authTreeVo = new AuthTreeVo(featureDto);
                //层级
                authTreeVo.setNodeLevel(1);
                //是否勾选
                authTreeVo.setAssigned(Objects.nonNull(assginedFeatures.get(feature.getId())));
                //groupId+url作为key
                authTreeVos.put(feature.getFeatureGroup().getId() + feature.getUrl(), authTreeVo);
            } else {
                //寻找父节点
                AuthTreeVo parent = authTreeVos.get(feature.getFeatureGroup().getId() + feature.getUrl());
                if (Objects.isNull(parent)) {
                    //如果没有父节点，说明数据有问题，先记录 加在根节点
                    // log.warn("功能型[{0}]没有所属页面级功能项,请检查数据", feature.getId());
                    AuthTreeVo authTreeVo = new AuthTreeVo(featureDto);
                    //层级
                    authTreeVo.setNodeLevel(1);
                    //是否勾选
                    authTreeVo.setAssigned(Objects.nonNull(assginedFeatures.get(feature.getId())));
                } else {
                    AuthTreeVo authTreeVo = new AuthTreeVo(featureDto);
                    //层级
                    authTreeVo.setNodeLevel(2);
                    //是否勾选
                    authTreeVo.setAssigned(Objects.nonNull(assginedFeatures.get(feature.getId())));
                    List<AuthTreeVo> children = parent.getChildren();
                    if (Objects.isNull(children)) {
                        children = new ArrayList<>();
                        parent.setChildren(children);
                    }
                    children.add(authTreeVo);
                }
            }
        }
        return new ArrayList<>(authTreeVos.values());
    }

    /**
     * 获取角色的功能项树
     *
     * @param featureRoleId 角色id
     * @return 功能项树清单
     */
    public List<FeatureNode> getFeatureTree(String featureRoleId) {
        List<FeatureNode> appNodes = new LinkedList<>();
        // 获取所有已分配的功能项
        List<Feature> features = getChildrenFromParentId(featureRoleId);
        // 获取所有应用模块
        List<AppModule> appModules = features.stream().map(feature -> feature.getFeatureGroup().getAppModule()).distinct().collect(Collectors.toList());
        appModules.forEach(appModule -> {
            FeatureNode appNode = constructAppNode(appModule);
            appNodes.add(appNode);
        });
        // 获取所有页面
        List<Feature> menuFeatures = features.stream().filter(feature -> feature.getFeatureType().equals(FeatureType.Page)).collect(Collectors.toList());
        // 定义所有页面节点
        menuFeatures.forEach(feature -> {
            // 获取应用模块
            AppModule appModule = feature.getFeatureGroup().getAppModule();
            Optional<FeatureNode> appNodeOptional = appNodes.stream().filter(f -> StringUtils.equals(appModule.getId(), f.getId())).findAny();
            if (!appNodeOptional.isPresent()) {
                throw new ServiceException("应用模块没有定义！" + appModule.getCode());
            }
            FeatureNode appNode = appNodeOptional.get();
            List<FeatureNode> pageNodes = appNode.getChildren();
            // 构造页面的功能项树
            buildFeatureTree(pageNodes, features, feature);
        });
        return appNodes;
    }

    /**
     * 检查并生成页面功能项清单
     * @param menuFeatures 菜单功能项
     * @param features 需要展示的功能项
     */
    private void buildPageFeatures(List<Feature> menuFeatures, List<Feature> features) {
        features.forEach(feature -> {
            Optional<Feature> featureOptional = menuFeatures.stream().filter(f -> f.getGroupCode().equals(feature.getGroupCode())).findAny();
            if (!featureOptional.isPresent()) {
                // 获取菜单项，并追加到页面清单中
                Feature pageFeature = featureDao.findFirstByGroupCodeAndFeatureType(feature.getGroupCode(), FeatureType.Page);
                if (Objects.isNull(pageFeature)) {
                    throw new ServiceException("功能项【"+feature.getCode()+"-"+feature.getName()+"】"+"没有配置对应的页面："+feature.getGroupCode());
                }
                menuFeatures.add(pageFeature);
            }
        });
    }

    /**
     * 构造页面的功能项树
     *
     * @param pageNodes 要构造的页面节点清单
     * @param features  使用的功能项清单
     */
    private void buildFeatureTree(List<FeatureNode> pageNodes, List<Feature> features, Feature pageFeature) {
        FeatureNode pageNode = constructNode(pageFeature);
        pageNodes.add(pageNode);
        // 获取所有非页面的功能项
        String pageCode = pageFeature.getGroupCode();
        List<Feature> otherFeatures = features.stream().filter(f -> !StringUtils.equals(f.getId(), pageFeature.getId())
                && StringUtils.equals(pageCode, f.getGroupCode()))
                .collect(Collectors.toList());
        otherFeatures.forEach(f -> {
            FeatureNode node = constructNode(f);
            pageNode.getChildren().add(node);
        });
    }

    /**
     * 构造树节点
     *
     * @param appModule 应用模块
     * @return 功能项节点
     */
    private FeatureNode constructAppNode(AppModule appModule) {
        FeatureNode node = new FeatureNode();
        node.setId(appModule.getId());
        node.setCode(appModule.getCode());
        node.setName(appModule.getName());
        node.setFeatureType(null);
        node.setChildren(new LinkedList<>());
        return node;
    }

    /**
     * 构造树节点
     *
     * @param feature 功能项
     * @return 功能项节点
     */
    private FeatureNode constructNode(Feature feature) {
        FeatureNode node = new FeatureNode();
        node.setId(feature.getId());
        node.setCode(feature.getCode());
        node.setName(feature.getName());
        node.setFeatureType(feature.getFeatureType());
        node.setChildren(new LinkedList<>());
        return node;
    }
}
