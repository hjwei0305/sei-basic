package com.changhong.sei.basic.controller;

import com.changhong.sei.basic.api.FeatureRoleApi;
import com.changhong.sei.basic.dto.FeatureRoleDto;
import com.changhong.sei.basic.dto.PositionDto;
import com.changhong.sei.basic.dto.RoleType;
import com.changhong.sei.basic.dto.UserDto;
import com.changhong.sei.basic.entity.FeatureRole;
import com.changhong.sei.basic.entity.Position;
import com.changhong.sei.basic.entity.User;
import com.changhong.sei.basic.service.FeatureRoleService;
import com.changhong.sei.core.controller.DefaultBaseEntityController;
import com.changhong.sei.core.dto.ResultData;
import com.changhong.sei.core.service.BaseEntityService;
import com.chonghong.sei.enums.UserType;
import com.chonghong.sei.util.EnumUtils;
import io.swagger.annotations.Api;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 实现功能: 功能角色API服务实现
 *
 * @author 王锦光 wangjg
 * @version 2020-01-29 9:18
 */
@RestController
@Api(value = "FeatureRoleService", tags = "功能角色API服务实现")
public class FeatureRoleController implements DefaultBaseEntityController<FeatureRole, FeatureRoleDto>,
        FeatureRoleApi {
    @Autowired
    private FeatureRoleService service;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public BaseEntityService<FeatureRole> getService() {
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
    public Class<FeatureRole> getEntityClass() {
        return FeatureRole.class;
    }

    /**
     * 获取传输实体的类型
     *
     * @return 类型Class
     */
    @Override
    public Class<FeatureRoleDto> getDtoClass() {
        return FeatureRoleDto.class;
    }

    /**
     * 通过角色组Id获取角色清单
     *
     * @param roleGroupId 角色组Id
     * @return 角色清单
     */
    @Override
    public ResultData<List<FeatureRoleDto>> findByFeatureRoleGroup(String roleGroupId) {
        return ResultData.success(convertToDtos(service.findByFeatureRoleGroup(roleGroupId)));
    }

    /**
     * 数据实体转换为DTO
     * @param entity 数据实体
     * @return DTO
     */
    static UserDto custConvertToDto(User entity){
        if (Objects.isNull(entity)){
            return null;
        }
        ModelMapper custMapper = new ModelMapper();
        return custMapper.map(entity, UserDto.class);
    }

    /**
     * 根据功能角色的id获取已分配的用户
     *
     * @param featureRoleId 功能角色的id
     * @return 用户清单
     */
    @Override
    public ResultData<List<UserDto>> getAssignedEmployeesByFeatureRole(String featureRoleId) {
        List<User> users = service.getAssignedEmployeesByFeatureRole(featureRoleId);
        List<UserDto> dtos = users.stream().map(FeatureRoleController::custConvertToDto).collect(Collectors.toList());
        return ResultData.success(dtos);
    }

    /**
     * 自定义将数据实体转换成DTO
     *
     * @param entity 业务实体
     * @return DTO
     */
    static PositionDto custConvertToDto(Position entity) {
        if (Objects.isNull(entity)){
            return null;
        }
        ModelMapper custMapper = new ModelMapper();
        // 创建自定义映射规则
        PropertyMap<Position,PositionDto> propertyMap = new PropertyMap<Position, PositionDto>() {
            @Override
            protected void configure() {
                // 使用自定义转换规则
                map().setOrganizationId(source.getOrganizationId());
                map().setPositionCategoryId(source.getPositionCategoryId());
            }
        };
        // 添加映射器
        custMapper.addMappings(propertyMap);
        // 转换
        return custMapper.map(entity,PositionDto.class);
    }

    /**
     * 根据功能角色的id获取已分配的岗位
     *
     * @param featureRoleId 功能角色的id
     * @return 岗位清单
     */
    @Override
    public ResultData<List<PositionDto>> getAssignedPositionsByFeatureRole(String featureRoleId) {
        List<Position> positions = service.getAssignedPositionsByFeatureRole(featureRoleId);
        List<PositionDto> dtos = positions.stream().map(FeatureRoleController::custConvertToDto).collect(Collectors.toList());
        return ResultData.success(dtos);
    }

    /**
     * 根据代码查询实体
     *
     * @param code 代码
     * @return 实体
     */
    @Override
    public ResultData<FeatureRoleDto> findByCode(String code) {
        return ResultData.success(convertToDto(service.findByCode(code)));
    }

    /**
     * 获取用户本人可以分配的角色
     *
     * @param roleGroupId 角色组Id
     * @return 可以分配的角色
     */
    @Override
    public ResultData<List<FeatureRoleDto>> getCanAssignedRoles(String roleGroupId) {
        return ResultData.success(convertToDtos(service.getCanAssignedRoles(roleGroupId)));
    }

    /**
     * 获取角色类型
     *
     * @return 用户角色类型列表
     */
    @Override
    public ResultData<List<EnumUtils.EnumEntity>> getRoleTypeList() {
        return ResultData.success(EnumUtils.getEnumDataList(RoleType.class));
    }

    /**
     * 获取用户类型
     *
     * @return 用户类型列表
     */
    @Override
    public ResultData<List<EnumUtils.EnumEntity>> listAllUserType() {
        return ResultData.success(EnumUtils.getEnumDataList(UserType.class));
    }

    /**
     * 将数据实体转换成DTO
     *
     * @param entity 业务实体
     * @return DTO
     */
    @Override
    public FeatureRoleDto convertToDto(FeatureRole entity) {
        return FeatureRoleController.custConvertToDto(entity);
    }

    /**
     * 转换功能角色数据实体为DTO
     * @param entity 功能角色数据实体
     * @return 功能角色DTO
     */
    static FeatureRoleDto custConvertToDto(FeatureRole entity){
        if (Objects.isNull(entity)){
            return null;
        }
        ModelMapper custMapper = new ModelMapper();
        // 创建自定义映射规则
        PropertyMap<FeatureRole, FeatureRoleDto> propertyMap = new PropertyMap<FeatureRole, FeatureRoleDto>() {
            @Override
            protected void configure() {
                // 使用自定义转换规则
                map().setFeatureGroupId(source.getFeatureGroupId());
                map().setPublicOrgId(source.getPublicOrgId());
            }
        };
        // 添加映射器
        custMapper.addMappings(propertyMap);
        // 转换
        return custMapper.map(entity, FeatureRoleDto.class);
    }
}
