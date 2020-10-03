package com.changhong.sei.basic.controller;

import com.changhong.sei.basic.api.DataRoleAuthTypeValueApi;
import com.changhong.sei.basic.dto.DataAuthorizeTypeDto;
import com.changhong.sei.basic.dto.DataRoleRelation;
import com.changhong.sei.basic.entity.DataAuthorizeType;
import com.changhong.sei.basic.service.DataRoleAuthTypeValueService;
import com.changhong.sei.core.dto.ResultData;
import com.changhong.sei.core.dto.auth.AuthEntityData;
import com.changhong.sei.core.dto.auth.AuthTreeEntityData;
import com.changhong.sei.core.service.bo.OperateResult;
import com.changhong.sei.core.utils.ResultDataUtil;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 实现功能: 数据角色分配权限类型的值API服务实现
 *
 * @author 王锦光 wangjg
 * @version 2020-01-27 14:34
 */
@RestController
@Api(value = "DataRoleAuthTypeValueApi", tags = "数据角色分配权限类型的值API服务实现")
@RequestMapping(path = "dataRoleAuthTypeValue", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class DataRoleAuthTypeValueController implements DataRoleAuthTypeValueApi {
    @Autowired
    private DataRoleAuthTypeValueService service;
    /**
     * 创建数据角色的分配关系
     *
     * @param relation 数据角色分配参数
     * @return 操作结果
     */
    @Override
    public ResultData<?> insertRelations(DataRoleRelation relation) {
        OperateResult result = service.insertRelations(relation);
        return ResultDataUtil.convertFromOperateResult(result);
    }

    /**
     * 移除数据角色的分配关系
     *
     * @param relation 数据角色分配参数
     * @return 操作结果
     */
    @Override
    public ResultData<?> removeRelations(DataRoleRelation relation) {
        OperateResult result = service.removeRelations(relation);
        return ResultDataUtil.convertFromOperateResult(result);
    }

    /**
     * 通过数据角色和权限类型获取已分配的业务实体数据
     *
     * @param roleId     数据角色Id
     * @param authTypeId 权限类型Id
     * @return 业务实体数据
     */
    @Override
    public ResultData<List<AuthEntityData>> getAssignedAuthDataList(String roleId, String authTypeId) {
        List<AuthEntityData> authEntityDatas = service.getAssignedAuthDataList(roleId, authTypeId);
        return ResultData.success(authEntityDatas);
    }

    /**
     * 通过数据角色和权限类型获取未分配的业务实体数据
     *
     * @param roleId     数据角色Id
     * @param authTypeId 权限类型Id
     * @return 业务实体数据
     */
    @Override
    public ResultData<List<AuthEntityData>> getUnassignedAuthDataList(String roleId, String authTypeId) {
        List<AuthEntityData> authEntityDatas = service.getUnassignedAuthDataList(roleId, authTypeId);
        return ResultData.success(authEntityDatas);
    }

    /**
     * 通过数据角色和权限类型获取已分配的树形业务实体数据
     *
     * @param roleId     数据角色Id
     * @param authTypeId 权限类型Id
     * @return 树形业务实体数据
     */
    @Override
    public ResultData<List<AuthTreeEntityData>> getAssignedAuthTreeDataList(String roleId, String authTypeId) {
        List<AuthTreeEntityData> authEntityDatas = service.getAssignedAuthTreeDataList(roleId, authTypeId);
        return ResultData.success(authEntityDatas);
    }

    /**
     * 通过数据角色和权限类型获取未分配的树形业务实体数据(不去除已分配的节点)
     *
     * @param roleId     数据角色Id
     * @param authTypeId 权限类型Id
     * @return 树形业务实体数据
     */
    @Override
    public ResultData<List<AuthTreeEntityData>> getUnassignedAuthTreeDataList(String roleId, String authTypeId) {
        List<AuthTreeEntityData> authEntityDatas = service.getUnassignedAuthTreeDataList(roleId, authTypeId);
        return ResultData.success(authEntityDatas);
    }

    /**
     * 通过数据角色Id获取数据权限类型
     *
     * @param roleId 数据角色Id
     * @return 数据权限类型清单
     */
    @Override
    public ResultData<List<DataAuthorizeTypeDto>> getAuthorizeTypesByRoleId(String roleId) {
        List<DataAuthorizeType> authorizeTypes = service.getAuthorizeTypesByRoleId(roleId);
        List<DataAuthorizeTypeDto> dtos = authorizeTypes.stream().map(DataAuthorizeTypeController::convertToDtoStatic).collect(Collectors.toList());
        return ResultData.success(dtos);
    }
}
