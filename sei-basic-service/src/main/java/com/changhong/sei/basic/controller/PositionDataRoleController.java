package com.changhong.sei.basic.controller;

import com.changhong.sei.basic.api.PositionDataRoleApi;
import com.changhong.sei.basic.dto.DataRoleDto;
import com.changhong.sei.basic.dto.PositionDataRoleDto;
import com.changhong.sei.basic.dto.PositionDto;
import com.changhong.sei.basic.entity.DataRole;
import com.changhong.sei.basic.entity.Position;
import com.changhong.sei.basic.entity.PositionDataRole;
import com.changhong.sei.basic.service.PositionDataRoleService;
import com.changhong.sei.core.controller.DefaultRelationController;
import com.changhong.sei.core.service.BaseRelationService;
import io.swagger.annotations.Api;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 实现功能: 岗位分配的数据角色API服务实现
 *
 * @author 王锦光 wangjg
 * @version 2020-01-29 10:11
 */
@RestController
@Api(value = "PositionDataRoleApi", tags = "岗位分配的数据角色API服务实现")
@RequestMapping(path = "positionDataRole", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class PositionDataRoleController implements DefaultRelationController<PositionDataRole, Position, DataRole, PositionDataRoleDto, PositionDto, DataRoleDto>,
        PositionDataRoleApi {
    @Autowired
    private PositionDataRoleService service;
    @Autowired
    private ModelMapper modelMapper;
    @Override
    public BaseRelationService<PositionDataRole, Position, DataRole> getService() {
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

    /**
     * 将父数据实体转换成DTO
     *
     * @param entity 业务实体
     * @return DTO
     */
    @Override
    public PositionDto convertParentToDto(Position entity) {
        return PositionController.custConvertToDto(entity);
    }

    /**
     * 将子数据实体转换成DTO
     *
     * @param entity 业务实体
     * @return DTO
     */
    @Override
    public DataRoleDto convertChildToDto(DataRole entity) {
        return DataRoleController.custConvertToDto(entity);
    }
}
