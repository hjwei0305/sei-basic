package com.changhong.sei.basic.api;

import com.changhong.sei.basic.dto.DataRoleRelation;
import com.changhong.sei.core.dto.ResultData;
import com.changhong.sei.core.dto.auth.AuthEntityData;
import com.changhong.sei.core.dto.auth.AuthTreeEntityData;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 实现功能: 数据角色分配权限类型的值API接口
 *
 * @author 王锦光 wangjg
 * @version 2020-01-27 10:25
 */
@FeignClient(name = "sei-basic", path = "dataRoleAuthTypeValue")
@RestController
@RequestMapping(path = "dataRoleAuthTypeValue", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public interface DataRoleAuthTypeValueService {
    /**
     * 创建数据角色的分配关系
     *
     * @param relation 数据角色分配参数
     * @return 操作结果
     */
    @PostMapping(path = "insertRelations", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "创建数据角色的分配关系", notes = "通过数据角色分配参数创建分配关系")
    ResultData insertRelations(@RequestBody DataRoleRelation relation);

    /**
     * 移除数据角色的分配关系
     *
     * @param relation 数据角色分配参数
     * @return 操作结果
     */
    @PostMapping(path = "removeRelations", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "移除数据角色的分配关系", notes = "通过数据角色分配参数移除分配关系")
    ResultData removeRelations(@RequestBody DataRoleRelation relation);

    /**
     * 通过数据角色和权限类型获取已分配的业务实体数据
     *
     * @param roleId     数据角色Id
     * @param authTypeId 权限类型Id
     * @return 业务实体数据
     */
    @GetMapping(path = "getAssignedAuthDatas")
    @ApiOperation(value = "通过数据角色和权限类型获取已分配的业务实体数据", notes = "通过数据角色Id和数据权限类型Id获取已分配的业务实体数据")
    ResultData<List<AuthEntityData>> getAssignedAuthDataList(@RequestParam("roleId") String roleId, @RequestParam("authTypeId") String authTypeId);

    /**
     * 通过数据角色和权限类型获取未分配的业务实体数据
     *
     * @param roleId     数据角色Id
     * @param authTypeId 权限类型Id
     * @return 业务实体数据
     */
    @GetMapping(path = "getUnassignedAuthDataList")
    @ApiOperation(value = "通过数据角色和权限类型获取未分配的业务实体数据", notes = "通过数据角色Id和数据权限类型Id获取未分配的业务实体数据")
    ResultData<List<AuthEntityData>> getUnassignedAuthDataList(@RequestParam("roleId") String roleId, @RequestParam("authTypeId") String authTypeId);

    /**
     * 通过数据角色和权限类型获取已分配的树形业务实体数据
     *
     * @param roleId     数据角色Id
     * @param authTypeId 权限类型Id
     * @return 树形业务实体数据
     */
    @GetMapping(path = "getAssignedAuthTreeDataList")
    @ApiOperation(value = "通过数据角色和权限类型获取已分配的树形业务实体数据", notes = "通过数据角色Id和数据权限类型Id获取已分配的树形业务实体数据")
    ResultData<List<AuthTreeEntityData>> getAssignedAuthTreeDataList(@RequestParam("roleId") String roleId, @RequestParam("authTypeId") String authTypeId);

    /**
     * 通过数据角色和权限类型获取未分配的树形业务实体数据(不去除已分配的节点)
     *
     * @param roleId     数据角色Id
     * @param authTypeId 权限类型Id
     * @return 树形业务实体数据
     */
    @GetMapping(path = "getUnassignedAuthTreeDataList")
    @ApiOperation(value = "通过数据角色和权限类型获取未分配的树形业务实体数据", notes = "通过数据角色Id和数据权限类型Id获取未分配的树形业务实体数据")
    ResultData<List<AuthTreeEntityData>> getUnassignedAuthTreeDataList(@RequestParam("roleId") String roleId, @RequestParam("authTypeId") String authTypeId);
}
