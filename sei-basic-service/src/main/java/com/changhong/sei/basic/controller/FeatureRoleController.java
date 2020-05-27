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
import com.changhong.sei.core.dto.auth.AuthEntityData;
import com.changhong.sei.core.dto.serach.PageResult;
import com.changhong.sei.core.dto.serach.Search;
import com.changhong.sei.core.service.BaseEntityService;
import com.changhong.sei.core.utils.ResultDataUtil;
import com.changhong.sei.enums.UserType;
import com.changhong.sei.util.EnumUtils;
import io.swagger.annotations.Api;
import org.apache.commons.collections.CollectionUtils;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Collection;
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
@Api(value = "FeatureRoleApi", tags = "功能角色API服务实现")
@RequestMapping(path = "featureRole", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class FeatureRoleController implements DefaultBaseEntityController<FeatureRole, FeatureRoleDto>,
        FeatureRoleApi {
    @Autowired
    private FeatureRoleService service;

    @Override
    public BaseEntityService<FeatureRole> getService() {
        return service;
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
        return ResultData.success(convertToDtos(service.getCanAssignedRoles(roleGroupId)));
    }

    /**
     * 数据实体转换为DTO
     *
     * @param entity 数据实体
     * @return DTO
     */
    static UserDto custConvertToDto(User entity) {
        if (Objects.isNull(entity)) {
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
     * 根据功能角色的code获取已分配的用户id
     *
     * @param featureRoleCode 功能角色的code
     * @return 用户id清单
     */
    @Override
    public ResultData<List<String>> getUserIdsByFeatureRole(String featureRoleCode) {
        List<String> result = new ArrayList<>();
        FeatureRole featureRole = service.findByCode(featureRoleCode);
        if (Objects.nonNull(featureRole)) {
            List<User> users = service.getAssignedEmployeesByFeatureRole(featureRole.getId());
            List<String> dtos = users.stream().map(User::getId).collect(Collectors.toList());
            if (CollectionUtils.isNotEmpty(dtos)) {
                result.addAll(dtos);
            }
        }
        return ResultData.success(result);
    }

    /**
     * 自定义将数据实体转换成DTO
     *
     * @param entity 业务实体
     * @return DTO
     */
    static PositionDto custConvertToDto(Position entity) {
        if (Objects.isNull(entity)) {
            return null;
        }
        ModelMapper custMapper = new ModelMapper();
        // 创建自定义映射规则
        PropertyMap<Position, PositionDto> propertyMap = new PropertyMap<Position, PositionDto>() {
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
        return custMapper.map(entity, PositionDto.class);
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
        return ResultDataUtil.getEnumEntities(RoleType.class);
    }

    /**
     * 获取用户类型
     *
     * @return 用户类型列表
     */
    @Override
    public ResultData<List<EnumUtils.EnumEntity>> listAllUserType() {
        return ResultDataUtil.getEnumEntities(UserType.class);
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
     *
     * @param entity 功能角色数据实体
     * @return 功能角色DTO
     */
    static FeatureRoleDto custConvertToDto(FeatureRole entity) {
        if (Objects.isNull(entity)) {
            return null;
        }
        ModelMapper custMapper = new ModelMapper();
        // 创建自定义映射规则
        PropertyMap<FeatureRole, FeatureRoleDto> propertyMap = new PropertyMap<FeatureRole, FeatureRoleDto>() {
            @Override
            protected void configure() {
                // 使用自定义转换规则
                map().setFeatureRoleGroupId(source.getFeatureRoleGroupId());
                map().setPublicOrgId(source.getPublicOrgId());
            }
        };
        // 添加映射器
        custMapper.addMappings(propertyMap);
        // 转换
        return custMapper.map(entity, FeatureRoleDto.class);
    }

    /**
     * 分页查询业务实体
     *
     * @param search 查询参数
     * @return 分页查询结果
     */
    @Override
    public ResultData<PageResult<FeatureRoleDto>> findByPage(Search search) {
        return convertToDtoPageResult(service.findByPage(search));
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
    public ResultData<List<FeatureRoleDto>> getUserAuthorizedEntities(String featureCode) {
        return ResultData.success(convertToDtos(service.getUserAuthorizedEntities(featureCode)));
    }
}
