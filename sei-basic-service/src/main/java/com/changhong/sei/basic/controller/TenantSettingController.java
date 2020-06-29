package com.changhong.sei.basic.controller;

import com.changhong.sei.basic.api.TenantSettingApi;
import com.changhong.sei.basic.dto.TenantSettingDto;
import com.changhong.sei.basic.entity.TenantSetting;
import com.changhong.sei.basic.service.TenantSettingService;
import com.changhong.sei.core.dto.ResultData;
import com.changhong.sei.core.service.bo.OperateResultWithData;
import io.swagger.annotations.Api;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Objects;

/**
 * 租户配置(TenantSetting)控制类
 *
 * @author sei
 * @since 2020-06-29 13:54:44
 */
@RestController
@Api(value = "TenantSettingApi", tags = "租户配置服务")
@RequestMapping(path = "tenantSetting", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class TenantSettingController implements TenantSettingApi {
    /**
     * 租户配置服务对象
     */
    @Autowired
    private TenantSettingService service;
    @Autowired
    private ModelMapper modelMapper;

    /**
     * 保存业务实体
     *
     * @param dto 业务实体DTO
     * @return 操作结果
     */
    @Override
    public ResultData<String> save(@Valid TenantSettingDto dto) {
        TenantSetting setting = modelMapper.map(dto, TenantSetting.class);
        OperateResultWithData operateResultWithData = service.save(setting);
        if (operateResultWithData.successful()) {
            return ResultData.success("OK");
        } else {
            return ResultData.fail(operateResultWithData.getMessage());
        }
    }

    /**
     * 通过Id获取一个业务实体
     *
     * @param tenantCode 租户代码
     * @return 业务实体
     */
    @Override
    public ResultData<TenantSettingDto> findCode(String tenantCode) {
        TenantSettingDto dto;
        TenantSetting tenantSetting = service.findByProperty(TenantSetting.CODE_FIELD, tenantCode);
        if (Objects.nonNull(tenantSetting)) {
            dto = modelMapper.map(tenantSetting, TenantSettingDto.class);
        } else {
            dto = new TenantSettingDto();
        }
        return ResultData.success(dto);
    }
}