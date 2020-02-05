package com.changhong.sei.basic.controller;

import com.changhong.sei.basic.api.UserDataRoleApi;
import com.changhong.sei.basic.dto.DataRoleDto;
import com.changhong.sei.basic.dto.UserDataRoleDto;
import com.changhong.sei.basic.dto.UserDto;
import com.changhong.sei.basic.entity.DataRole;
import com.changhong.sei.basic.entity.User;
import com.changhong.sei.basic.entity.UserDataRole;
import com.changhong.sei.basic.service.UserDataRoleService;
import com.changhong.sei.core.controller.DefaultRelationController;
import com.changhong.sei.core.service.BaseRelationService;
import io.swagger.annotations.Api;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

/**
 * 实现功能: 用户分配的数据角色API服务实现
 *
 * @author 王锦光 wangjg
 * @version 2020-01-30 9:26
 */
@RestController
@Api(value = "UserDataRoleService", tags = "用户分配的数据角色API服务实现")
public class UserDataRoleController implements DefaultRelationController<UserDataRole, User, DataRole, UserDataRoleDto, UserDto, DataRoleDto>,
        UserDataRoleApi {
    @Autowired
    private UserDataRoleService service;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public BaseRelationService<UserDataRole, User, DataRole> getService() {
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
    public Class<UserDataRole> getRelationEntityClass() {
        return UserDataRole.class;
    }

    /**
     * 获取关系型传输实体的类型
     *
     * @return 类型Class
     */
    @Override
    public Class<UserDataRoleDto> getRelationDtoClass() {
        return UserDataRoleDto.class;
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
    public Class<DataRole> getChildEntityClass() {
        return DataRole.class;
    }

    /**
     * 获取子传输实体的类型
     *
     * @return 类型Class
     */
    @Override
    public Class<DataRoleDto> getChildDtoClass() {
        return DataRoleDto.class;
    }
}
