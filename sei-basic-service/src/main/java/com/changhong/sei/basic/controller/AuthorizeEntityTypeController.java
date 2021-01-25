package com.changhong.sei.basic.controller;

import com.changhong.sei.basic.api.AuthorizeEntityTypeApi;
import com.changhong.sei.basic.dto.AuthorizeEntityTypeDto;
import com.changhong.sei.basic.entity.AuthorizeEntityType;
import com.changhong.sei.basic.service.AuthorizeEntityTypeService;
import com.changhong.sei.core.controller.BaseEntityController;
import com.changhong.sei.core.dto.ResultData;
import com.changhong.sei.core.service.BaseEntityService;
import io.swagger.annotations.Api;
import org.modelmapper.PropertyMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 实现功能: 权限对象类型API服务
 *
 * @author 王锦光 wangjg
 * @version 2020-01-20 11:21
 */
@RestController
@Api(value = "AuthorizeEntityTypeApi", tags = "权限对象类型API服务")
@RequestMapping(path = "authorizeEntityType", produces = MediaType.APPLICATION_JSON_VALUE)
public class AuthorizeEntityTypeController extends BaseEntityController<AuthorizeEntityType, AuthorizeEntityTypeDto>
        implements AuthorizeEntityTypeApi {
    @Autowired
    private AuthorizeEntityTypeService service;
    @Override
    public BaseEntityService<AuthorizeEntityType> getService() {
        return service;
    }

    /**
     * 自定义设置Entity转换为DTO的转换器
     */
    @Override
    protected void customConvertToDtoMapper() {
        // 创建自定义映射规则
        PropertyMap<AuthorizeEntityType,AuthorizeEntityTypeDto> propertyMap = new PropertyMap<AuthorizeEntityType, AuthorizeEntityTypeDto>() {
            @Override
            protected void configure() {
                // 使用自定义转换规则(明确AppModuleId来自source的appModuleId而不是source.appModule.id)
                map().setAppModuleId(source.getAppModuleId());
            }
        };
        // 添加映射器
        dtoModelMapper.addMappings(propertyMap);
    }

    /**
     * 获取所有业务实体
     *
     * @return 业务实体清单
     */
    @Override
    public ResultData<List<AuthorizeEntityTypeDto>> findAll() {
        return ResultData.success(convertToDtos(service.findAll()));
    }

    /**
     * 获取所有未冻结的业务实体
     *
     * @return 业务实体清单
     */
    @Override
    public ResultData<List<AuthorizeEntityTypeDto>> findAllUnfrozen() {
        return ResultData.success(convertToDtos(service.findAllUnfrozen()));
    }
}
