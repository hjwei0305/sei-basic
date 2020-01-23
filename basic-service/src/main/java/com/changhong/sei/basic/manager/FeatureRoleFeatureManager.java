package com.changhong.sei.basic.manager;

import com.changhong.sei.basic.dao.FeatureRoleFeatureDao;
import com.changhong.sei.basic.entity.AuthTreeVo;
import com.changhong.sei.basic.dto.FeatureType;
import com.changhong.sei.basic.entity.*;
import com.changhong.sei.basic.manager.util.AuthorityUtil;
import com.changhong.sei.core.context.ContextUtil;
import com.changhong.sei.core.dao.BaseRelationDao;
import com.changhong.sei.core.dto.ResultData;
import com.changhong.sei.core.manager.BaseRelationManager;
import com.changhong.sei.core.manager.bo.OperateResult;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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
@Component
public class FeatureRoleFeatureManager extends BaseRelationManager<FeatureRoleFeature, FeatureRole, Feature> {
    @Autowired
    private FeatureRoleFeatureDao dao;
    @Override
    protected BaseRelationDao<FeatureRoleFeature,FeatureRole,Feature> getDao() {
        return dao;
    }

    @Autowired
    private UserManager userManager;
    @Autowired
    private UserFeatureRoleManager userFeatureRoleManager;
    @Autowired
    private FeatureGroupManager featureGroupManager;

    /**
     * 获取可以分配的子实体清单
     * @return 子实体清单
     */
    @Override
    protected List<Feature> getCanAssignedChildren(String parentId){
        String userId = ContextUtil.getUserId();
        return userManager.getUserCanAssignFeatures(userId);
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
        UserFeatureRole userFeatureRole = userFeatureRoleManager.getRelation(ContextUtil.getUserId(),parentId);
        if(userFeatureRole!=null){
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
        UserFeatureRole userFeatureRole = userFeatureRoleManager.getRelation(ContextUtil.getUserId(),parentId);
        if(userFeatureRole!=null){
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
        List<FeatureGroup> featureGroups = featureGroupManager.findByAppModuleId(appModuleId);
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
            if (FeatureType.Page.equals(feature.getFeatureType())) {
                AuthTreeVo authTreeVo = new AuthTreeVo(feature);
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
                    AuthTreeVo authTreeVo = new AuthTreeVo(feature);
                    //层级
                    authTreeVo.setNodeLevel(1);
                    //是否勾选
                    authTreeVo.setAssigned(Objects.nonNull(assginedFeatures.get(feature.getId())));
                } else {
                    AuthTreeVo authTreeVo = new AuthTreeVo(feature);
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
}
