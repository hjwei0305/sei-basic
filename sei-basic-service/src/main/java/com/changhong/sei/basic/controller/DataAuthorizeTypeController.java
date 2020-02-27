package com.changhong.sei.basic.controller;

import com.changhong.sei.basic.api.DataAuthorizeTypeApi;
import com.changhong.sei.basic.dto.DataAuthorizeTypeDto;
import com.changhong.sei.basic.dto.DataAuthorizeTypeVo;
import com.changhong.sei.basic.entity.DataAuthorizeType;
import com.changhong.sei.basic.service.DataAuthorizeTypeService;
import com.changhong.sei.core.controller.DefaultBaseEntityController;
import com.changhong.sei.core.dto.ResultData;
import com.changhong.sei.core.service.BaseEntityService;
import io.swagger.annotations.Api;
import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
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
public class DataAuthorizeTypeController implements DefaultBaseEntityController<DataAuthorizeType, DataAuthorizeTypeDto>,
        DataAuthorizeTypeApi {
    @Autowired
    private DataAuthorizeTypeService service;
    @Override
    public BaseEntityService<DataAuthorizeType> getService() {
        return service;
    }

    /**
     * 获取数据实体的类型
     *
     * @return 类型Class
     */
    @Override
    public Class<DataAuthorizeType> getEntityClass() {
        return DataAuthorizeType.class;
    }

    /**
     * 获取传输实体的类型
     *
     * @return 类型Class
     */
    @Override
    public Class<DataAuthorizeTypeDto> getDtoClass() {
        return DataAuthorizeTypeDto.class;
    }

    /**
     * 将数据实体转换成DTO
     *
     * @param entity 业务实体
     * @return DTO
     */
    @Override
    public DataAuthorizeTypeDto convertToDto(DataAuthorizeType entity) {
        return custConvertToDto(entity);
    }

    /**
     * 自定义数据实体转换成DTO
     * @param entity 业务实体
     * @return DTO
     */
    public static DataAuthorizeTypeDto custConvertToDto(DataAuthorizeType entity) {
        if (Objects.isNull(entity)) {
            return null;
        }
        ModelMapper custMapper = new ModelMapper();
        // 创建自定义映射规则
        PropertyMap<DataAuthorizeType,DataAuthorizeTypeDto> propertyMap = new PropertyMap<DataAuthorizeType,DataAuthorizeTypeDto>() {
            @Override
            protected void configure() {
                // 使用自定义转换规则
                map().setAuthorizeEntityTypeId(source.getAuthorizeEntityTypeId());
                map().setFeatureId(source.getFeatureId());
                map().setAuthorizeEntityTypeAppModuleId(source.getAuthorizeEntityType().getAppModuleId());
            }
        };
        // 添加映射器
        custMapper.addMappings(propertyMap);
        // 转换
        return custMapper.map(entity, DataAuthorizeTypeDto.class);
    }

    /**
     * 将DTO转换成数据实体
     *
     * @param dto 业务实体
     * @return 数据实体
     */
    @Override
    public DataAuthorizeType convertToEntity(DataAuthorizeTypeDto dto) {
        if (Objects.isNull(dto)){
            return null;
        }
        ModelMapper custMapper = new ModelMapper();
        // 创建自定义映射规则
        PropertyMap<DataAuthorizeTypeDto,DataAuthorizeType> propertyMap = new PropertyMap<DataAuthorizeTypeDto,DataAuthorizeType>() {
            @Override
            protected void configure() {
                // 使用自定义转换规则,不映射关联对象
                skip().setFeature(null);
                skip().setAuthorizeEntityType(null);
            }
        };
        // 添加映射器
        custMapper.addMappings(propertyMap);
        // 转换
        return custMapper.map(dto, DataAuthorizeType.class);
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
}
