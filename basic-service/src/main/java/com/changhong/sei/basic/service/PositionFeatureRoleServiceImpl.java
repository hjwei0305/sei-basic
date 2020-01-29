package com.changhong.sei.basic.service;

import com.changhong.sei.basic.api.PositionFeatureRoleService;
import com.changhong.sei.basic.dto.FeatureRoleDto;
import com.changhong.sei.basic.dto.PositionDto;
import com.changhong.sei.basic.dto.PositionFeatureRoleDto;
import com.changhong.sei.basic.entity.FeatureRole;
import com.changhong.sei.basic.entity.Position;
import com.changhong.sei.basic.entity.PositionFeatureRole;
import com.changhong.sei.basic.manager.PositionFeatureRoleManager;
import com.changhong.sei.core.manager.BaseRelationManager;
import com.changhong.sei.core.service.DefaultRelationService;
import io.swagger.annotations.Api;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 实现功能: 岗位分配的功能角色API服务实现
 *
 * @author 王锦光 wangjg
 * @version 2020-01-29 10:11
 */
@Service
@Api(value = "PositionFeatureRoleService", tags = "岗位分配的功能角色API服务实现")
public class PositionFeatureRoleServiceImpl implements DefaultRelationService<PositionFeatureRole, Position, FeatureRole, PositionFeatureRoleDto, PositionDto, FeatureRoleDto>,
        PositionFeatureRoleService {
    @Autowired
    private PositionFeatureRoleManager manager;
    @Autowired
    private ModelMapper modelMapper;
    @Override
    public BaseRelationManager<PositionFeatureRole, Position, FeatureRole> getManager() {
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
    public Class<PositionFeatureRole> getRelationEntityClass() {
        return PositionFeatureRole.class;
    }

    /**
     * 获取关系型传输实体的类型
     *
     * @return 类型Class
     */
    @Override
    public Class<PositionFeatureRoleDto> getRelationDtoClass() {
        return PositionFeatureRoleDto.class;
    }

    /**
     * 获取父数据实体的类型
     *
     * @return 类型Class
     */
    @Override
    public Class<Position> getParentEntityClass() {
        return Position.class;
    }

    /**
     * 获取父传输实体的类型
     *
     * @return 类型Class
     */
    @Override
    public Class<PositionDto> getParentDtoClass() {
        return PositionDto.class;
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
