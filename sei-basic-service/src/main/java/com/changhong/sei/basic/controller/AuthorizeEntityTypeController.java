package com.changhong.sei.basic.controller;

import com.changhong.sei.basic.api.AuthorizeEntityTypeApi;
import com.changhong.sei.basic.dto.AuthorizeEntityTypeDto;
import com.changhong.sei.basic.entity.AuthorizeEntityType;
import com.changhong.sei.basic.service.AuthorizeEntityTypeService;
import com.changhong.sei.core.controller.DefaultBaseEntityController;
import com.changhong.sei.core.dto.ResultData;
import com.changhong.sei.core.service.BaseEntityService;
import io.swagger.annotations.Api;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 实现功能: 权限对象类型API服务
 *
 * @author 王锦光 wangjg
 * @version 2020-01-20 11:21
 */
@RestController
@Api(value = "AuthorizeEntityTypeService", tags = "权限对象类型API服务")
public class AuthorizeEntityTypeController implements DefaultBaseEntityController<AuthorizeEntityType, AuthorizeEntityTypeDto>,
        AuthorizeEntityTypeApi {
    @Autowired
    private AuthorizeEntityTypeService service;
    @Autowired
    private ModelMapper modelMapper;
    @Override
    public BaseEntityService<AuthorizeEntityType> getService() {
        return service;
    }

    @Override
    public ModelMapper getModelMapper() {
        return modelMapper;
    }

    /**
     * 获取数据实体的类型
     *
     * @return 类型Class
     */
    @Override
    public Class<AuthorizeEntityType> getEntityClass() {
        return AuthorizeEntityType.class;
    }

    /**
     * 获取传输实体的类型
     *
     * @return 类型Class
     */
    @Override
    public Class<AuthorizeEntityTypeDto> getDtoClass() {
        return AuthorizeEntityTypeDto.class;
    }

    /**
     * 将数据实体转换成DTO
     *
     * @param entity 业务实体
     * @return DTO
     */
    @Override
    public AuthorizeEntityTypeDto convertToDto(AuthorizeEntityType entity) {
        ModelMapper custMapper = new ModelMapper();
        // 创建自定义映射规则
        PropertyMap<AuthorizeEntityType,AuthorizeEntityTypeDto> propertyMap = new PropertyMap<AuthorizeEntityType, AuthorizeEntityTypeDto>() {
            @Override
            protected void configure() {
                // 使用自定义转换规则(明确AppModuleId来自source的appModuleId而不是source.appModule.id)
                map().setAppModuleId(source.getAppModuleId());
            }
        };
        // 添加映射器
        custMapper.addMappings(propertyMap);
        // 转换
        return custMapper.map(entity,AuthorizeEntityTypeDto.class);
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
