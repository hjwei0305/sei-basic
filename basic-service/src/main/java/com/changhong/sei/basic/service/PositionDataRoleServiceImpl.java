package com.changhong.sei.basic.service;

import com.changhong.sei.basic.api.PositionDataRoleService;
import com.changhong.sei.basic.dto.DataRoleDto;
import com.changhong.sei.basic.dto.PositionDataRoleDto;
import com.changhong.sei.basic.dto.PositionDto;
import com.changhong.sei.basic.entity.DataRole;
import com.changhong.sei.basic.entity.Position;
import com.changhong.sei.basic.entity.PositionDataRole;
import com.changhong.sei.basic.manager.PositionDataRoleManager;
import com.changhong.sei.core.manager.BaseRelationManager;
import com.changhong.sei.core.service.DefaultRelationService;
import io.swagger.annotations.Api;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 实现功能: 岗位分配的数据角色API服务实现
 *
 * @author 王锦光 wangjg
 * @version 2020-01-29 10:11
 */
@Service
@Api(value = "PositionDataRoleService", tags = "岗位分配的数据角色API服务实现")
public class PositionDataRoleServiceImpl implements DefaultRelationService<PositionDataRole, Position, DataRole, PositionDataRoleDto, PositionDto, DataRoleDto>,
        PositionDataRoleService {
    @Autowired
    private PositionDataRoleManager manager;
    @Autowired
    private ModelMapper modelMapper;
    @Override
    public BaseRelationManager<PositionDataRole, Position, DataRole> getManager() {
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
    public Class<PositionDataRole> getRelationEntityClass() {
        return PositionDataRole.class;
    }

    /**
     * 获取关系型传输实体的类型
     *
     * @return 类型Class
     */
    @Override
    public Class<PositionDataRoleDto> getRelationDtoClass() {
        return PositionDataRoleDto.class;
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
