package com.changhong.sei.basic.service;

import com.changhong.sei.basic.api.FeatureRoleService;
import com.changhong.sei.basic.dto.FeatureRoleDto;
import com.changhong.sei.basic.dto.PositionDto;
import com.changhong.sei.basic.dto.RoleType;
import com.changhong.sei.basic.dto.UserDto;
import com.changhong.sei.basic.entity.FeatureRole;
import com.changhong.sei.basic.entity.Position;
import com.changhong.sei.basic.entity.User;
import com.changhong.sei.basic.manager.FeatureRoleManager;
import com.changhong.sei.core.dto.ResultData;
import com.changhong.sei.core.manager.BaseEntityManager;
import com.changhong.sei.core.service.DefaultBaseEntityService;
import com.changhong.sei.core.utils.ResultDataUtil;
import com.chonghong.sei.enums.UserType;
import io.swagger.annotations.Api;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 实现功能: 功能角色API服务实现
 *
 * @author 王锦光 wangjg
 * @version 2020-01-29 9:18
 */
@Service
@Api(value = "FeatureRoleService", tags = "功能角色API服务实现")
public class FeatureRoleServiceImpl implements DefaultBaseEntityService<FeatureRole, FeatureRoleDto>,
        FeatureRoleService {
    @Autowired
    private FeatureRoleManager manager;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public BaseEntityManager<FeatureRole> getManager() {
        return manager;
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
        return ResultData.success(convertToDtos(manager.findByFeatureRoleGroup(roleGroupId)));
    }

    /**
     * 数据实体转换为DTO
     * @param entity 数据实体
     * @return DTO
     */
    private static UserDto custConvertToDto(User entity){
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
        List<User> users = manager.getAssignedEmployeesByFeatureRole(featureRoleId);
        List<UserDto> dtos = users.stream().map(FeatureRoleServiceImpl::custConvertToDto).collect(Collectors.toList());
        return ResultData.success(dtos);
    }

    /**
     * 自定义将数据实体转换成DTO
     *
     * @param entity 业务实体
     * @return DTO
     */
    public static PositionDto custConvertToDto(Position entity) {
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
        List<Position> positions = manager.getAssignedPositionsByFeatureRole(featureRoleId);
        List<PositionDto> dtos = positions.stream().map(FeatureRoleServiceImpl::custConvertToDto).collect(Collectors.toList());
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
        return ResultData.success(convertToDto(manager.findByCode(code)));
    }

    /**
     * 获取用户本人可以分配的角色
     *
     * @param roleGroupId 角色组Id
     * @return 可以分配的角色
     */
    @Override
    public ResultData<List<FeatureRoleDto>> getCanAssignedRoles(String roleGroupId) {
        return ResultData.success(convertToDtos(manager.getCanAssignedRoles(roleGroupId)));
    }

    /**
     * 获取角色类型
     *
     * @return 用户角色类型列表
     */
    @Override
    public ResultData<Map<String, String>> getRoleTypeList() {
        return ResultDataUtil.getEnumMap(RoleType.class);
    }

    /**
     * 获取用户类型
     *
     * @return 用户类型列表
     */
    @Override
    public ResultData<Map<String, String>> listAllUserType() {
        return ResultDataUtil.getEnumMap(UserType.class);
    }
}
