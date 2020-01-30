package com.changhong.sei.basic.service;

import com.changhong.sei.basic.api.UserDataRoleService;
import com.changhong.sei.basic.dto.DataRoleDto;
import com.changhong.sei.basic.dto.UserDataRoleDto;
import com.changhong.sei.basic.dto.UserDto;
import com.changhong.sei.basic.entity.DataRole;
import com.changhong.sei.basic.entity.User;
import com.changhong.sei.basic.entity.UserDataRole;
import com.changhong.sei.basic.manager.UserDataRoleManager;
import com.changhong.sei.core.manager.BaseRelationManager;
import com.changhong.sei.core.service.DefaultRelationService;
import io.swagger.annotations.Api;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 实现功能: 用户分配的数据角色API服务实现
 *
 * @author 王锦光 wangjg
 * @version 2020-01-30 9:26
 */
@Service
@Api(value = "UserDataRoleService", tags = "用户分配的数据角色API服务实现")
public class UserDataRoleServiceImpl implements DefaultRelationService<UserDataRole, User, DataRole, UserDataRoleDto, UserDto, DataRoleDto>,
        UserDataRoleService {
    @Autowired
    private UserDataRoleManager manager;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public BaseRelationManager<UserDataRole, User, DataRole> getManager() {
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
