package com.changhong.sei.basic.service;

import com.changhong.sei.basic.api.DataRoleAuthTypeValueService;
import com.changhong.sei.basic.dto.DataRoleRelation;
import com.changhong.sei.basic.manager.DataRoleAuthTypeValueManager;
import com.changhong.sei.core.dto.ResultData;
import com.changhong.sei.core.dto.auth.AuthEntityData;
import com.changhong.sei.core.dto.auth.AuthTreeEntityData;
import com.changhong.sei.core.manager.bo.OperateResult;
import com.changhong.sei.core.utils.ResultDataUtil;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 实现功能: 数据角色分配权限类型的值API服务实现
 *
 * @author 王锦光 wangjg
 * @version 2020-01-27 14:34
 */
@Service
@Api(value = "DataRoleAuthTypeValueService", tags = "数据角色分配权限类型的值API服务实现")
public class DataRoleAuthTypeValueServiceImpl implements DataRoleAuthTypeValueService {
    @Autowired
    private DataRoleAuthTypeValueManager manager;
    /**
     * 创建数据角色的分配关系
     *
     * @param relation 数据角色分配参数
     * @return 操作结果
     */
    @Override
    public ResultData insertRelations(DataRoleRelation relation) {
        OperateResult result = manager.insertRelations(relation);
        return ResultDataUtil.convertFromOperateResult(result);
    }

    /**
     * 移除数据角色的分配关系
     *
     * @param relation 数据角色分配参数
     * @return 操作结果
     */
    @Override
    public ResultData removeRelations(DataRoleRelation relation) {
        OperateResult result = manager.removeRelations(relation);
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
        List<AuthEntityData> authEntityDatas = manager.getAssignedAuthDataList(roleId, authTypeId);
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
        List<AuthEntityData> authEntityDatas = manager.getUnassignedAuthDataList(roleId, authTypeId);
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
        List<AuthTreeEntityData> authEntityDatas = manager.getAssignedAuthTreeDataList(roleId, authTypeId);
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
        List<AuthTreeEntityData> authEntityDatas = manager.getUnassignedAuthTreeDataList(roleId, authTypeId);
        return ResultData.success(authEntityDatas);
    }
}
