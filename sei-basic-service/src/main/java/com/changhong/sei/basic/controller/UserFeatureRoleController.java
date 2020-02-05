package com.changhong.sei.basic.controller;

import com.changhong.sei.basic.api.UserFeatureRoleApi;
import com.changhong.sei.basic.dto.FeatureRoleDto;
import com.changhong.sei.basic.dto.UserDto;
import com.changhong.sei.basic.dto.UserFeatureRoleDto;
import com.changhong.sei.basic.entity.FeatureRole;
import com.changhong.sei.basic.entity.User;
import com.changhong.sei.basic.entity.UserFeatureRole;
import com.changhong.sei.basic.service.UserFeatureRoleService;
import com.changhong.sei.core.controller.DefaultRelationController;
import com.changhong.sei.core.service.BaseRelationService;
import io.swagger.annotations.Api;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

/**
 * 实现功能: 用户分配的功能角色API服务实现
 *
 * @author 王锦光 wangjg
 * @version 2020-01-30 9:26
 */
@RestController
@Api(value = "UserFeatureRoleService", tags = "用户分配的功能角色API服务实现")
public class UserFeatureRoleController implements DefaultRelationController<UserFeatureRole, User, FeatureRole, UserFeatureRoleDto, UserDto, FeatureRoleDto>,
        UserFeatureRoleApi {
    @Autowired
    private UserFeatureRoleService service;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public BaseRelationService<UserFeatureRole, User, FeatureRole> getService() {
        return service;
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
