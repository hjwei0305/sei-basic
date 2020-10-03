package com.changhong.sei.basic.controller;

import com.changhong.sei.basic.api.PositionDataRoleApi;
import com.changhong.sei.basic.dto.DataRoleDto;
import com.changhong.sei.basic.dto.PositionDataRoleDto;
import com.changhong.sei.basic.dto.PositionDto;
import com.changhong.sei.basic.entity.DataRole;
import com.changhong.sei.basic.entity.Position;
import com.changhong.sei.basic.entity.PositionDataRole;
import com.changhong.sei.basic.service.PositionDataRoleService;
import com.changhong.sei.core.controller.BaseRelationController;
import com.changhong.sei.core.service.BaseRelationService;
import io.swagger.annotations.Api;
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
public class PositionDataRoleController extends BaseRelationController<PositionDataRole, Position, DataRole, PositionDataRoleDto, PositionDto, DataRoleDto>
        implements PositionDataRoleApi {
    @Autowired
    private PositionDataRoleService service;
    @Override
    public BaseRelationService<PositionDataRole, Position, DataRole> getService() {
        return service;
    }

    /**
     * 将父数据实体转换成DTO
     *
     * @param entity 业务实体
     * @return DTO
     */
    @Override
    public PositionDto convertParentToDto(Position entity) {
        return PositionController.convertToDtoStatic(entity);
    }

    /**
     * 将子数据实体转换成DTO
     *
     * @param entity 业务实体
     * @return DTO
     */
    @Override
    public DataRoleDto convertChildToDto(DataRole entity) {
        return DataRoleController.convertToDtoStatic(entity);
    }
}
