package com.changhong.sei.basic.api;

import com.changhong.sei.basic.dto.DataAuthorizeTypeDto;
import com.changhong.sei.basic.dto.DataAuthorizeTypeVo;
import com.changhong.sei.core.api.BaseEntityApi;
import com.changhong.sei.core.api.FindAllApi;
import com.changhong.sei.core.dto.ResultData;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * 实现功能: 数据权限类型API接口
 *
 * @author 王锦光 wangjg
 * @version 2020-01-20 14:23
 */
@FeignClient(name = "sei-basic", path = "dataAuthorizeType")
public interface DataAuthorizeTypeApi extends BaseEntityApi<DataAuthorizeTypeDto>,
        FindAllApi<DataAuthorizeTypeDto> {
    /**
     * 通过数据角色Id获取数据权限类型（VO）
     * @param roleId 数据角色Id
     * @return 数据权限类型
     */
    @GetMapping(path = "getByDataRole")
    @ApiOperation(value = "获取数据权限类型清单",notes = "通过数据角色Id获取数据权限类型（VO）")
    ResultData<List<DataAuthorizeTypeVo>> getByDataRole(@RequestParam("roleId") String roleId);

    /**
     * 通过数据角色Id获取数据权限类型（VO）
     * @param roleId 数据角色Id
     * @return 数据权限类型
     */
    @GetMapping(path = "getByAppModuleAndDataRole")
    @ApiOperation(value = "获取数据权限类型清单",notes = "通过应用模块Id和数据角色Id获取数据权限类型（VO）")
    ResultData<List<DataAuthorizeTypeVo>> getByAppModuleAndDataRole(@RequestParam("appModuleId") String appModuleId, @RequestParam("roleId") String roleId);
}
