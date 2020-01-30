package com.changhong.sei.basic.service;

import com.changhong.sei.basic.api.UserFeatureRoleService;
import com.changhong.sei.basic.dto.FeatureRoleDto;
import com.changhong.sei.basic.dto.UserDto;
import com.changhong.sei.basic.dto.UserFeatureRoleDto;
import com.changhong.sei.basic.entity.FeatureRole;
import com.changhong.sei.basic.entity.User;
import com.changhong.sei.basic.entity.UserFeatureRole;
import com.changhong.sei.basic.manager.UserFeatureRoleManager;
import com.changhong.sei.core.manager.BaseRelationManager;
import com.changhong.sei.core.service.DefaultRelationService;
import io.swagger.annotations.Api;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 实现功能: 用户分配的功能角色API服务实现
 *
 * @author 王锦光 wangjg
 * @version 2020-01-30 9:26
 */
@Service
@Api(value = "UserFeatureRoleService", tags = "用户分配的功能角色API服务实现")
public class UserFeatureRoleServiceImpl implements DefaultRelationService<UserFeatureRole, User, FeatureRole, UserFeatureRoleDto, UserDto, FeatureRoleDto>,
        UserFeatureRoleService {
    @Autowired
    private UserFeatureRoleManager manager;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public BaseRelationManager<UserFeatureRole, User, FeatureRole> getManager() {
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
    public Class<UserFeatureRole> getRelationEntityClass() {
        return UserFeatureRole.class;
    }

    /**
     * 获取关系型传输实体的类型
     *
     * @return 类型Class
     */
    @Override
    public Class<UserFeatureRoleDto> getRelationDtoClass() {
        return UserFeatureRoleDto.class;
    }

    /**
     * 获取父数据实体的类型
     *
     * @return 类型Class
     */
    @Override
    public Class<User> getParentEntityClass() {
        return User.class;
    }

    /**
     * 获取父传输实体的类型
     *
     * @return 类型Class
     */
    @Override
    public Class<UserDto> getParentDtoClass() {
        return UserDto.class;
    }

    /**
     * 获取子数据实体的类型
     *
     * @return 类型Class
     */
    @Override
    public Class<FeatureRole> getChildEntityClass() {
        return FeatureRole.class;
    }

    /**
     * 获取子传输实体的类型
     *
     * @return 类型Class
     */
    @Override
    public Class<FeatureRoleDto> getChildDtoClass() {
        return FeatureRoleDto.class;
    }
}
