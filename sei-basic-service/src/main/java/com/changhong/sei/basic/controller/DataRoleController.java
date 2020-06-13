package com.changhong.sei.basic.controller;

import com.changhong.sei.basic.api.DataRoleApi;
import com.changhong.sei.basic.dto.DataRoleDto;
import com.changhong.sei.basic.entity.DataRole;
import com.changhong.sei.basic.service.DataRoleService;
import com.changhong.sei.core.controller.DefaultBaseEntityController;
import com.changhong.sei.core.dto.ResultData;
import com.changhong.sei.core.dto.auth.AuthEntityData;
import com.changhong.sei.core.service.BaseEntityService;
import io.swagger.annotations.Api;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 实现功能: 数据角色API服务实现
 *
 * @author 王锦光 wangjg
 * @version 2020-01-21 8:42
 */
@RestController
@Api(value = "DataRoleApi", tags = "数据角色API服务")
@RequestMapping(path = "dataRole", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class DataRoleController implements DefaultBaseEntityController<DataRole, DataRoleDto>,
        DataRoleApi {
    @Autowired
    private DataRoleService service;
    @Override
    public BaseEntityService<DataRole> getService() {
        return service;
    }

    /**
     * 获取数据实体的类型
     *
     * @return 类型Class
     */
    @Override
    public Class<DataRole> getEntityClass() {
        return DataRole.class;
    }

    /**
     * 获取传输实体的类型
     *
     * @return 类型Class
     */
    @Override
    public Class<DataRoleDto> getDtoClass() {
        return DataRoleDto.class;
    }

    /**
     * 通过角色组Id获取角色清单
     *
     * @param roleGroupId 角色组Id
     * @return 角色清单
     */
    @Override
    public ResultData<List<DataRoleDto>> findByDataRoleGroup(String roleGroupId) {
        return ResultData.success(convertToDtos(service.getCanAssignedRoles(roleGroupId, true)));
    }

    /**
     * 获取用户本人可以分配的角色
     *
     * @param roleGroupId 角色组Id
     * @return 可以分配的角色
     */
    @Override
    public ResultData<List<DataRoleDto>> getCanAssignedRoles(String roleGroupId) {
        List<DataRole> roles = service.getCanAssignedRoles(roleGroupId, false);
        List<DataRoleDto> dtos = roles.stream().map(this::convertToDto).collect(Collectors.toList());
        return ResultData.success(dtos);
    }

    /**
     * 将数据实体转换成DTO
     *
     * @param entity 业务实体
     * @return DTO
     */
    @Override
    public DataRoleDto convertToDto(DataRole entity) {
        return custConvertToDto(entity);
    }

    /**
     * 转换数据角色数据实体为DTO
     * @param entity 数据角色数据实体
     * @return 数据角色DTO
     */
    static DataRoleDto custConvertToDto(DataRole entity){
        if (Objects.isNull(entity)){
            return null;
        }
        ModelMapper custMapper = new ModelMapper();
        // 创建自定义映射规则
        PropertyMap<DataRole, DataRoleDto> propertyMap = new PropertyMap<DataRole, DataRoleDto>() {
            @Override
            protected void configure() {
                // 使用自定义转换规则
                map().setDataRoleGroupId(source.getDataRoleGroupId());
                map().setPublicOrgId(source.getPublicOrgId());
            }
        };
        // 添加映射器
        custMapper.addMappings(propertyMap);
        // 转换
        return custMapper.map(entity, DataRoleDto.class);
    }

    /**
     * 通过业务实体Id清单获取数据权限实体清单
     *
     * @param ids 业务实体Id清单
     * @return 数据权限实体清单
     */
    @Override
    public ResultData<List<AuthEntityData>> getAuthEntityDataByIds(List<String> ids) {
        return ResultData.success(service.getAuthEntityDataByIds(ids));
    }

    /**
     * 获取所有数据权限实体清单
     *
     * @return 数据权限实体清单
     */
    @Override
    public ResultData<List<AuthEntityData>> findAllAuthEntityData() {
        return ResultData.success(service.findAllAuthEntityData());
    }

    /**
     * 获取当前用户有权限的业务实体清单
     *
     * @param featureCode 功能项代码
     * @return 有权限的业务实体清单
     */
    @Override
    public ResultData<List<DataRoleDto>> getUserAuthorizedEntities(String featureCode) {
        return ResultData.success(convertToDtos(service.getUserAuthorizedEntities(featureCode)));
    }
}
