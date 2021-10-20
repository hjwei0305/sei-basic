package com.changhong.sei.basic.controller;

import com.changhong.sei.basic.api.TenantApi;
import com.changhong.sei.basic.dto.EmployeeDto;
import com.changhong.sei.basic.dto.OrganizationDto;
import com.changhong.sei.basic.dto.TenantDto;
import com.changhong.sei.basic.entity.Tenant;
import com.changhong.sei.basic.service.TenantService;
import com.changhong.sei.core.controller.BaseEntityController;
import com.changhong.sei.core.dto.ResultData;
import com.changhong.sei.core.service.BaseEntityService;
import io.swagger.annotations.Api;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 实现功能: 租户的服务实现
 *
 * @author 王锦光 wangjg
 * @version 2020-01-19 14:51
 */
@RestController
@Api(value = "TenantApi", tags = "租户的API服务")
@RequestMapping(path = "tenant", produces = MediaType.APPLICATION_JSON_VALUE)
public class TenantController extends BaseEntityController<Tenant, TenantDto>
        implements TenantApi {
    @Autowired
    private TenantService service;
    @Autowired
    private OrganizationController organizationController;
    @Autowired
    private EmployeeController employeeController;
    @Override
    public BaseEntityService<Tenant> getService() {
        return service;
    }

    /**
     * 获取所有业务实体
     *
     * @return 业务实体清单
     */
    @Override
    public ResultData<List<TenantDto>> findAll() {
        List<TenantDto> tenantDtos =  convertToDtos(service.findAll());
        if (CollectionUtils.isEmpty(tenantDtos)){
            return ResultData.success(tenantDtos);
        }
        tenantDtos.forEach(tenantDto -> {
            // 获取对应的组织
            ResultData<OrganizationDto> orgData = organizationController.findRootByTenantCode(tenantDto.getCode());
            if (orgData.successful()){
                tenantDto.setOrganizationDto(orgData.getData());
            }
            // 获取对应的系统管理员
            ResultData<EmployeeDto> employeeData = employeeController.findAdminByTenantCode(tenantDto.getCode());
            if (employeeData.successful()){
                tenantDto.setEmployeeDto(employeeData.getData());
            }
        });
        return ResultData.success(tenantDtos);
    }

    /**
     * 获取所有未冻结的业务实体
     *
     * @return 业务实体清单
     */
    @Override
    public ResultData<List<TenantDto>> findAllUnfrozen() {
        return ResultData.success(convertToDtos(service.findAllUnfrozen()));
    }
}
