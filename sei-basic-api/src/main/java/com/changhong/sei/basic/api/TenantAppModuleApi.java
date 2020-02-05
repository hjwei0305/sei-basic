package com.changhong.sei.basic.api;

import com.changhong.sei.basic.dto.AppModuleDto;
import com.changhong.sei.basic.dto.TenantAppModuleDto;
import com.changhong.sei.basic.dto.TenantDto;
import com.changhong.sei.core.api.BaseRelationApi;
import com.changhong.sei.core.dto.ResultData;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * 实现功能: 租户分配应用模块API接口
 *
 * @author 王锦光 wangjg
 * @version 2020-01-19 15:08
 */
@FeignClient(name = "sei-basic", path = "tenantAppModule")
@RequestMapping(path = "tenantAppModule", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public interface TenantAppModuleApi extends BaseRelationApi<TenantAppModuleDto, TenantDto, AppModuleDto> {
    /**
     * 获取当前用户可用的应用模块代码清单
     * @return 应用模块代码清单
     */
    @GetMapping(path = "getAppModuleCodes")
    @ApiOperation(value = "获取当前用户可用的应用模块代码清单",notes = "获取当前用户对应租户可用的应用模块代码清单")
    ResultData<List<String>> getAppModuleCodes();

    /**
     * 获取当前用户可用的应用模块清单
     * @return 应用模块清单
     */
    @GetMapping(path = "getTenantAppModules")
    @ApiOperation(value = "获取当前用户可用的应用模块清单",notes = "获取当前用户对应租户可用的应用模块清单")
    ResultData<List<AppModuleDto>> getTenantAppModules();
}
