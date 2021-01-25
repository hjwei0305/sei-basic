package com.changhong.sei.basic.controller;

import com.changhong.sei.basic.api.FeatureRoleGroupApi;
import com.changhong.sei.basic.dto.FeatureRoleGroupDto;
import com.changhong.sei.basic.entity.FeatureRoleGroup;
import com.changhong.sei.basic.service.FeatureRoleGroupService;
import com.changhong.sei.core.controller.BaseEntityController;
import com.changhong.sei.core.dto.ResultData;
import com.changhong.sei.core.service.BaseEntityService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 实现功能: 功能角色组API服务实现
 *
 * @author 王锦光 wangjg
 * @version 2020-01-28 10:52
 */
@RestController
@Api(value = "FeatureRoleGroupApi", tags = "功能角色组API服务实现")
@RequestMapping(path = "featureRoleGroup", produces = MediaType.APPLICATION_JSON_VALUE)
public class FeatureRoleGroupController extends BaseEntityController<FeatureRoleGroup, FeatureRoleGroupDto>
        implements FeatureRoleGroupApi {
    @Autowired
    private FeatureRoleGroupService service;

    @Override
    public BaseEntityService<FeatureRoleGroup> getService() {
        return service;
    }

    /**
     * 获取所有业务实体
     *
     * @return 业务实体清单
     */
    @Override
    public ResultData<List<FeatureRoleGroupDto>> findAll() {
        return ResultData.success(convertToDtos(service.findAll()));
    }

    /**
     * 获取所有未冻结的业务实体
     *
     * @return 业务实体清单
     */
    @Override
    public ResultData<List<FeatureRoleGroupDto>> findAllUnfrozen() {
        return ResultData.success(convertToDtos(service.findAllUnfrozen()));
    }
}
