package com.changhong.sei.basic.controller;

import com.changhong.sei.basic.api.DataAuthorizeTypeApi;
import com.changhong.sei.basic.dto.DataAuthorizeTypeDto;
import com.changhong.sei.basic.dto.DataAuthorizeTypeVo;
import com.changhong.sei.basic.entity.DataAuthorizeType;
import com.changhong.sei.basic.service.DataAuthorizeTypeService;
import com.changhong.sei.core.controller.BaseEntityController;
import com.changhong.sei.core.dto.ResultData;
import com.changhong.sei.core.service.BaseEntityService;
import io.swagger.annotations.Api;
import org.apache.commons.lang3.StringUtils;
import org.modelmapper.PropertyMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Objects;

/**
 * 实现功能: 数据权限类型API服务
 *
 * @author 王锦光 wangjg
 * @version 2020-01-20 14:27
 */
@RestController
@Api(value = "DataAuthorizeTypeApi", tags = "数据权限类型API服务")
@RequestMapping(path = "dataAuthorizeType", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class DataAuthorizeTypeController extends BaseEntityController<DataAuthorizeType, DataAuthorizeTypeDto>
        implements DataAuthorizeTypeApi {
    @Autowired
    private DataAuthorizeTypeService service;
    @Override
    public BaseEntityService<DataAuthorizeType> getService() {
        return service;
    }

    /**
     * 自定义设置Entity转换为DTO的转换器
     */
    @Override
    protected void customConvertToDtoMapper() {
        // 创建自定义映射规则
        PropertyMap<DataAuthorizeType,DataAuthorizeTypeDto> propertyMap = new PropertyMap<DataAuthorizeType,DataAuthorizeTypeDto>() {
            @Override
            protected void configure() {
                // 使用自定义转换规则
                map().setAuthorizeEntityTypeId(source.getAuthorizeEntityTypeId());
                map().setAuthorizeEntityTypeAppModuleId(source.getAuthorizeEntityType().getAppModuleId());
                map().setApiPath(source.getAuthorizeEntityType().getApiPath());
                map().setApiBaseAddress(source.getAuthorizeEntityType().getAppModule().getApiBaseAddress());
            }
        };
        // 添加映射器
        dtoModelMapper.addMappings(propertyMap);
    }

    /**
     * 自定义数据实体转换成DTO
     * @param entity 业务实体
     * @return DTO
     */
    public static DataAuthorizeTypeDto convertToDtoStatic(DataAuthorizeType entity) {
        if (Objects.isNull(entity)) {
            return null;
        }
        // 转换
        return dtoModelMapper.map(entity, DataAuthorizeTypeDto.class);
    }

    /**
     * 自定义设置DTO转换为Entity的转换器
     */
    @Override
    protected void customerConvertToEntityMapper() {
        // 创建自定义映射规则
        PropertyMap<DataAuthorizeTypeDto,DataAuthorizeType> propertyMap = new PropertyMap<DataAuthorizeTypeDto,DataAuthorizeType>() {
            @Override
            protected void configure() {
                // 使用自定义转换规则,不映射关联对象
                skip().setAuthorizeEntityType(null);
            }
        };
        // 添加映射器
        entityModelMapper.addMappings(propertyMap);
    }

    /**
     * 通过数据角色Id获取数据权限类型（VO）
     *
     * @param roleId 数据角色Id
     * @return 数据权限类型
     */
    @Override
    public ResultData<List<DataAuthorizeTypeVo>> getByDataRole(String roleId) {
        return ResultData.success(service.getByDataRole(roleId));
    }

    /**
     * 通过数据角色Id获取数据权限类型（VO）
     *
     * @param appModuleId 应用模块Id
     * @param roleId      数据角色Id
     * @return 数据权限类型
     */
    @Override
    public ResultData<List<DataAuthorizeTypeVo>> getByAppModuleAndDataRole(String appModuleId, String roleId) {
        // 判断应用模块Id是否输入
        if (StringUtils.isBlank(appModuleId)){
            return getByDataRole(roleId);
        }
        return ResultData.success(service.getByAppModuleAndDataRole(appModuleId, roleId));
    }

    /**
     * 获取所有业务实体
     *
     * @return 业务实体清单
     */
    @Override
    public ResultData<List<DataAuthorizeTypeDto>> findAll() {
        return ResultData.success(convertToDtos(service.findAll()));
    }

    /**
     * 获取所有未冻结的业务实体
     *
     * @return 业务实体清单
     */
    @Override
    public ResultData<List<DataAuthorizeTypeDto>> findAllUnfrozen() {
        return ResultData.success(convertToDtos(service.findAllUnfrozen()));
    }

    /**
     * 通过实体类型名和功能项代码获取数据权限类型
     *
     * @param entityClassName 实体类型名
     * @param featureCode     功能项代码
     * @return 数据权限类型
     */
    @Override
    public ResultData<DataAuthorizeTypeDto> getByEntityClassNameAndFeature(String entityClassName, String featureCode) {
        return ResultData.success(convertToDto(service.getByEntityClassNameAndFeature(entityClassName, featureCode)));
    }
}
