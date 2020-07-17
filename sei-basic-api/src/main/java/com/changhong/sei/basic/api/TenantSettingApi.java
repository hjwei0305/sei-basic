package com.changhong.sei.basic.api;

import com.changhong.sei.basic.dto.TenantSettingDto;
import com.changhong.sei.core.dto.ResultData;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;

/**
 * 租户配置(TenantSetting)API
 *
 * @author sei
 * @since 2020-06-29 13:54:30
 */
@Valid
@FeignClient(name = "sei-basic", path = "tenantSetting")
public interface TenantSettingApi {

    /**
     * 保存业务实体
     *
     * @param dto 业务实体DTO
     * @return 操作结果
     */
    @PostMapping(path = "save", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "保存租户配置", notes = "保存租户配置")
    ResultData<TenantSettingDto> save(@RequestBody @Valid TenantSettingDto dto);

    /**
     * 通过Id获取一个业务实体
     *
     * @param tenantCode 租户代码
     * @return 业务实体
     */
    @GetMapping(path = "findOne")
    @ApiOperation(value = "获取一个租户配置", notes = "通过租户代码获取租户配置")
    ResultData<TenantSettingDto> findCode(@RequestParam("tenantCode") String tenantCode);
}