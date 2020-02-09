package com.changhong.sei.basic.api;

import com.changhong.sei.basic.dto.DataRoleDto;
import com.changhong.sei.core.api.BaseEntityApi;
import com.changhong.sei.core.dto.ResultData;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * 实现功能: 数据角色API接口
 *
 * @author 王锦光 wangjg
 * @version 2020-01-21 8:40
 */
@FeignClient(name = "sei-basic", path = "dataRole")
public interface DataRoleApi extends BaseEntityApi<DataRoleDto> {
    /**
     * 通过角色组Id获取角色清单
     * @param roleGroupId 角色组Id
     * @return 角色清单
     */
    @GetMapping(path = "findByDataRoleGroup")
    @ApiOperation(value = "通过角色组获取角色清单",notes = "通过角色组Id获取角色清单")
    ResultData<List<DataRoleDto>> findByDataRoleGroup(@RequestParam("roleGroupId") String roleGroupId);

    /**
     * 获取用户本人可以分配的角色
     * @param roleGroupId 角色组Id
     * @return 可以分配的角色
     */
    @GetMapping(path = "getCanAssignedRoles")
    @ApiOperation(value = "根据角色组Id获取可分配的角色", notes = "根据角色组Id获取用户本人可以分配的角色")
    ResultData<List<DataRoleDto>> getCanAssignedRoles(@RequestParam("roleGroupId") String roleGroupId);
}
