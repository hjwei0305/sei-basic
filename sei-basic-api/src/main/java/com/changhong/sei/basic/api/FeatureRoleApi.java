package com.changhong.sei.basic.api;

import com.changhong.sei.basic.dto.FeatureRoleDto;
import com.changhong.sei.basic.dto.PositionDto;
import com.changhong.sei.basic.dto.UserDto;
import com.changhong.sei.core.api.BaseEntityApi;
import com.changhong.sei.core.api.FindByPageApi;
import com.changhong.sei.core.dto.ResultData;
import com.changhong.sei.util.EnumUtils;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * 实现功能: 功能角色API接口
 *
 * @author 王锦光 wangjg
 * @version 2020-01-28 10:56
 */
@FeignClient(name = "sei-basic", path = "featureRole")
public interface FeatureRoleApi extends BaseEntityApi<FeatureRoleDto>, FindByPageApi<FeatureRoleDto> {
    /**
     * 通过角色组Id获取角色清单
     * @param roleGroupId 角色组Id
     * @return 角色清单
     */
    @GetMapping(path = "findByFeatureRoleGroup")
    @ApiOperation(value = "通过角色组获取角色清单",notes = "通过角色组Id获取角色清单")
    ResultData<List<FeatureRoleDto>> findByFeatureRoleGroup(@RequestParam("roleGroupId") String roleGroupId);

    /**
     * 根据功能角色的id获取已分配的用户
     *
     * @param featureRoleId 功能角色的id
     * @return 用户清单
     */
    @GetMapping(path = "getAssignedEmployeesByFeatureRole")
    @ApiOperation(value = "根据功能角色的id获取已分配的用户", notes = "根据功能角色的id获取已分配的用户")
    ResultData<List<UserDto>> getAssignedEmployeesByFeatureRole(@RequestParam("featureRoleId") String featureRoleId);

    /**
     * 根据功能角色的code获取已分配的用户id
     *
     * @param featureRoleCode 功能角色的code
     * @return 用户id清单
     */
    @GetMapping(path = "getUserIdsByFeatureRole")
    @ApiOperation(value = "根据功能角色的code获取已分配的用户id", notes = "根据功能角色的code获取已分配的用户id")
    ResultData<List<String>> getUserIdsByFeatureRole(@RequestParam("featureRoleCode") String featureRoleCode);

    /**
     * 根据功能角色的id获取已分配的岗位
     *
     * @param featureRoleId 功能角色的id
     * @return 岗位清单
     */
    @GetMapping(path = "getAssignedPositionsByFeatureRole")
    @ApiOperation(value = "根据功能角色的id获取已分配的岗位", notes = "根据功能角色的id获取已分配的岗位")
    ResultData<List<PositionDto>> getAssignedPositionsByFeatureRole(@RequestParam("featureRoleId") String featureRoleId);

    /**
     * 根据代码查询实体
     *
     * @param code 代码
     * @return 实体
     */
    @GetMapping(path = "findByCode")
    @ApiOperation(value = "根据代码查询实体", notes = "根据代码查询实体")
    ResultData<FeatureRoleDto> findByCode(@RequestParam("code") String code);

    /**
     * 获取用户本人可以分配的角色
     * @param roleGroupId 角色组Id
     * @return 可以分配的角色
     */
    @GetMapping(path = "getCanAssignedRoles")
    @ApiOperation(value = "根据角色组Id获取可分配的角色", notes = "根据角色组Id获取用户本人可以分配的角色")
    ResultData<List<FeatureRoleDto>> getCanAssignedRoles(@RequestParam("roleGroupId") String roleGroupId);

    /**
     * 获取角色类型
     * @return 用户角色类型列表
     */
    @GetMapping(path = "listAllRoleTypeList")
    @ApiOperation(value = "获取角色类型", notes = "获取角色类型")
    ResultData<List<EnumUtils.EnumEntity>> getRoleTypeList();

    /**
     * 获取用户类型
     * @return 用户类型列表
     */
    @GetMapping(path = "listAllUserType")
    @ApiOperation(value = "获取用户类型", notes = "获取用户类型")
    ResultData<List<EnumUtils.EnumEntity>> listAllUserType();
}
