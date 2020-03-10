package com.changhong.sei.basic.controller;

import com.changhong.sei.basic.api.FeatureRoleFeatureApi;
import com.changhong.sei.basic.dto.*;
import com.changhong.sei.basic.entity.Feature;
import com.changhong.sei.basic.entity.FeatureRole;
import com.changhong.sei.basic.entity.FeatureRoleFeature;
import com.changhong.sei.basic.service.FeatureRoleFeatureService;
import com.changhong.sei.core.controller.DefaultRelationController;
import com.changhong.sei.core.dto.ResultData;
import com.changhong.sei.core.service.BaseRelationService;
import io.swagger.annotations.Api;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 实现功能: 功能角色分配的功能项API服务实现
 *
 * @author 王锦光 wangjg
 * @version 2020-01-28 10:41
 */
@RestController
@Api(value = "FeatureRoleFeatureApi", tags = "功能角色分配的功能项API服务实现")
@RequestMapping(path = "featureRoleFeature", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class FeatureRoleFeatureController implements DefaultRelationController<FeatureRoleFeature, FeatureRole, Feature, FeatureRoleFeatureDto, FeatureRoleDto, FeatureDto>,
        FeatureRoleFeatureApi {
    @Autowired
    private FeatureRoleFeatureService service;
    /**
     * 根据模块，获取指定角色授权树
     *
     * @param appModuleId   应用模块id
     * @param featureRoleId 角色id
     * @return 指定模块授权树形对象集合
     */
    @Override
    public ResultData<List<AuthTreeVo>> getAuthTree(String appModuleId, String featureRoleId) {
        return service.getAuthTree(appModuleId, featureRoleId);
    }

    /**
     * 获取角色的功能项树
     *
     * @param featureRoleId 角色id
     * @return 功能项树清单
     */
    @Override
    public ResultData<List<FeatureNode>> getFeatureTree(String featureRoleId) {
        return ResultData.success(service.getFeatureTree(featureRoleId));
    }

    @Override
    public BaseRelationService<FeatureRoleFeature, FeatureRole, Feature> getService() {
        return service;
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

    /**
     * 将父数据实体转换成DTO
     *
     * @param entity 业务实体
     * @return DTO
     */
    @Override
    public FeatureRoleDto convertParentToDto(FeatureRole entity) {
        return FeatureRoleController.custConvertToDto(entity);
    }

    /**
     * 将子数据实体转换成DTO
     *
     * @param entity 业务实体
     * @return DTO
     */
    @Override
    public FeatureDto convertChildToDto(Feature entity) {
        return FeatureController.custConvertToDto(entity);
    }
}
