package com.changhong.sei.basic.service;

import com.changhong.sei.basic.api.FeatureRoleFeatureService;
import com.changhong.sei.basic.dto.AuthTreeVo;
import com.changhong.sei.basic.dto.FeatureDto;
import com.changhong.sei.basic.dto.FeatureRoleDto;
import com.changhong.sei.basic.dto.FeatureRoleFeatureDto;
import com.changhong.sei.basic.entity.Feature;
import com.changhong.sei.basic.entity.FeatureRole;
import com.changhong.sei.basic.entity.FeatureRoleFeature;
import com.changhong.sei.basic.manager.FeatureRoleFeatureManager;
import com.changhong.sei.core.dto.ResultData;
import com.changhong.sei.core.manager.BaseRelationManager;
import com.changhong.sei.core.service.DefaultRelationService;
import io.swagger.annotations.Api;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 实现功能: 功能角色分配的功能项API服务实现
 *
 * @author 王锦光 wangjg
 * @version 2020-01-28 10:41
 */
@Service
@Api(value = "FeatureRoleFeatureService", tags = "功能角色分配的功能项API服务实现")
public class FeatureRoleFeatureServiceImpl implements DefaultRelationService<FeatureRoleFeature, FeatureRole, Feature, FeatureRoleFeatureDto, FeatureRoleDto, FeatureDto>,
        FeatureRoleFeatureService {
    @Autowired
    private FeatureRoleFeatureManager manager;
    @Autowired
    private ModelMapper modelMapper;
    /**
     * 根据模块，获取指定角色授权树
     *
     * @param appModuleId   应用模块id
     * @param featureRoleId 角色id
     * @return 指定模块授权树形对象集合
     */
    @Override
    public ResultData<List<AuthTreeVo>> getAuthTree(String appModuleId, String featureRoleId) {
        return manager.getAuthTree(appModuleId, featureRoleId);
    }

    @Override
    public BaseRelationManager<FeatureRoleFeature, FeatureRole, Feature> getManager() {
        return manager;
    }

    @Override
    public ModelMapper getModelMapper() {
        return modelMapper;
    }

    /**
     * 获取关系型数据实体的类型
     *
     * @return 类型Class
     */
    @Override
    public Class<FeatureRoleFeature> getRelationEntityClass() {
        return FeatureRoleFeature.class;
    }

    /**
     * 获取关系型传输实体的类型
     *
     * @return 类型Class
     */
    @Override
    public Class<FeatureRoleFeatureDto> getRelationDtoClass() {
        return FeatureRoleFeatureDto.class;
    }

    /**
     * 获取父数据实体的类型
     *
     * @return 类型Class
     */
    @Override
    public Class<FeatureRole> getParentEntityClass() {
        return FeatureRole.class;
    }

    /**
     * 获取父传输实体的类型
     *
     * @return 类型Class
     */
    @Override
    public Class<FeatureRoleDto> getParentDtoClass() {
        return FeatureRoleDto.class;
    }

    /**
     * 获取子数据实体的类型
     *
     * @return 类型Class
     */
    @Override
    public Class<Feature> getChildEntityClass() {
        return Feature.class;
    }

    /**
     * 获取子传输实体的类型
     *
     * @return 类型Class
     */
    @Override
    public Class<FeatureDto> getChildDtoClass() {
        return FeatureDto.class;
    }
}
