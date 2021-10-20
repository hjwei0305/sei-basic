package com.changhong.sei.basic.controller;

import com.changhong.sei.basic.api.FeatureRoleFeatureApi;
import com.changhong.sei.basic.dto.*;
import com.changhong.sei.basic.entity.Feature;
import com.changhong.sei.basic.entity.FeatureRole;
import com.changhong.sei.basic.entity.FeatureRoleFeature;
import com.changhong.sei.basic.service.FeatureRoleFeatureService;
import com.changhong.sei.core.controller.BaseRelationController;
import com.changhong.sei.core.dto.ResultData;
import com.changhong.sei.core.service.BaseRelationService;
import io.swagger.annotations.Api;
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
@RequestMapping(path = "featureRoleFeature", produces = MediaType.APPLICATION_JSON_VALUE)
public class FeatureRoleFeatureController extends BaseRelationController<FeatureRoleFeature, FeatureRole, Feature, FeatureRoleFeatureDto, FeatureRoleDto, FeatureDto>
        implements FeatureRoleFeatureApi {
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

    /**
     * 获取未分配的功能项树
     *
     * @param appModuleId   应用模块id
     * @param featureRoleId 角色id
     * @return 功能项树清单
     */
    @Override
    public ResultData<List<FeatureNode>> getUnassignedFeatureTree(String appModuleId, String featureRoleId) {
        return ResultData.success(service.getUnassignedFeatureTree(appModuleId, featureRoleId));
    }

    @Override
    public BaseRelationService<FeatureRoleFeature, FeatureRole, Feature> getService() {
        return service;
    }

    /**
     * 将父数据实体转换成DTO
     *
     * @param entity 业务实体
     * @return DTO
     */
    @Override
    public FeatureRoleDto convertParentToDto(FeatureRole entity) {
        return FeatureRoleController.convertToDtoStatic(entity);
    }

    /**
     * 将子数据实体转换成DTO
     *
     * @param entity 业务实体
     * @return DTO
     */
    @Override
    public FeatureDto convertChildToDto(Feature entity) {
        return FeatureController.convertToDtoStatic(entity);
    }
}
