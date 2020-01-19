package com.changhong.sei.basic.service;

import com.changhong.sei.basic.api.TenantAppModuleService;
import com.changhong.sei.basic.dto.AppModuleDto;
import com.changhong.sei.basic.dto.TenantAppModuleDto;
import com.changhong.sei.basic.dto.TenantDto;
import com.changhong.sei.basic.entity.AppModule;
import com.changhong.sei.basic.entity.Tenant;
import com.changhong.sei.basic.entity.TenantAppModule;
import com.changhong.sei.basic.manager.TenantAppModuleManager;
import com.changhong.sei.core.dto.ResultData;
import com.changhong.sei.core.manager.BaseRelationManager;
import com.changhong.sei.core.service.DefaultRelationService;
import io.swagger.annotations.Api;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 实现功能: 租户分配应用模块API服务
 *
 * @author 王锦光 wangjg
 * @version 2020-01-19 15:16
 */
@Service
@Api(value = "TenantAppModuleService", tags = "租户分配应用模块API服务")
public class TenantAppModuleServiceImpl implements DefaultRelationService<TenantAppModule, Tenant, AppModule, TenantAppModuleDto, TenantDto, AppModuleDto>,
        TenantAppModuleService {
    @Autowired
    private TenantAppModuleManager manager;
    @Autowired
    private ModelMapper modelMapper;
    /**
     * 获取当前用户可用的应用模块代码清单
     *
     * @return 应用模块代码清单
     */
    @Override
    public ResultData<List<String>> getAppModuleCodes() {
        List<String> codes = manager.getAppModuleCodes();
        return ResultData.success(codes);
    }

    /**
     * 获取当前用户可用的应用模块清单
     *
     * @return 应用模块清单
     */
    @Override
    public ResultData<List<AppModuleDto>> getTenantAppModules() {
        List<AppModule> appModules = manager.getTenantAppModules();
        List<AppModuleDto> dtos = appModules.stream().map(this::convertChildToDto).collect(Collectors.toList());
        return ResultData.success(dtos);
    }

    @Override
    public BaseRelationManager<TenantAppModule, Tenant, AppModule> getManager() {
        return manager;
    }

    @Override
    public ModelMapper getModelMapper() {
        return modelMapper;
    }

    /**
     * 获取关系型数据实体的类型
     *
     * @return 类型Class
     */
    @Override
    public Class<TenantAppModule> getRelationEntityClass() {
        return TenantAppModule.class;
    }

    /**
     * 获取关系型传输实体的类型
     *
     * @return 类型Class
     */
    @Override
    public Class<TenantAppModuleDto> getRelationDtoClass() {
        return TenantAppModuleDto.class;
    }

    /**
     * 获取父数据实体的类型
     *
     * @return 类型Class
     */
    @Override
    public Class<Tenant> getParentEntityClass() {
        return Tenant.class;
    }

    /**
     * 获取父传输实体的类型
     *
     * @return 类型Class
     */
    @Override
    public Class<TenantDto> getParentDtoClass() {
        return TenantDto.class;
    }

    /**
     * 获取子数据实体的类型
     *
     * @return 类型Class
     */
    @Override
    public Class<AppModule> getChildEntityClass() {
        return AppModule.class;
    }

    /**
     * 获取子传输实体的类型
     *
     * @return 类型Class
     */
    @Override
    public Class<AppModuleDto> getChildDtoClass() {
        return AppModuleDto.class;
    }
}
