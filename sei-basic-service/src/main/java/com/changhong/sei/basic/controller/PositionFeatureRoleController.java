package com.changhong.sei.basic.controller;

import com.changhong.sei.basic.api.PositionFeatureRoleApi;
import com.changhong.sei.basic.dto.FeatureRoleDto;
import com.changhong.sei.basic.dto.PositionDto;
import com.changhong.sei.basic.dto.PositionFeatureRoleDto;
import com.changhong.sei.basic.entity.FeatureRole;
import com.changhong.sei.basic.entity.Position;
import com.changhong.sei.basic.entity.PositionFeatureRole;
import com.changhong.sei.basic.service.PositionFeatureRoleService;
import com.changhong.sei.core.controller.BaseRelationController;
import com.changhong.sei.core.dto.ParentRelationParam;
import com.changhong.sei.core.dto.ResultData;
import com.changhong.sei.core.service.BaseRelationService;
import com.changhong.sei.core.utils.ResultDataUtil;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 实现功能: 岗位分配的功能角色API服务实现
 *
 * @author 王锦光 wangjg
 * @version 2020-01-29 10:11
 */
@RestController
@Api(value = "PositionFeatureRoleApi", tags = "岗位分配的功能角色API服务实现")
@RequestMapping(path = "positionFeatureRole", produces = MediaType.APPLICATION_JSON_VALUE)
public class PositionFeatureRoleController extends BaseRelationController<PositionFeatureRole, Position, FeatureRole, PositionFeatureRoleDto, PositionDto, FeatureRoleDto>
        implements PositionFeatureRoleApi {
    @Autowired
    private PositionFeatureRoleService service;
    @Override
    public BaseRelationService<PositionFeatureRole, Position, FeatureRole> getService() {
        return service;
    }

    /**
     * 将子数据实体转换成DTO
     *
     * @param entity 业务实体
     * @return DTO
     */
    @Override
    public FeatureRoleDto convertChildToDto(FeatureRole entity) {
        return FeatureRoleController.convertToDtoStatic(entity);
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
     * 通过父实体清单保存分配关系
     *
     * @param relationParam 父实体Id清单的分配参数
     * @return 操作结果
     */
    @Override
    public ResultData<?> saveRelationsByParents(ParentRelationParam relationParam) {
        return ResultDataUtil.convertFromOperateResult(service.saveRelationsByParents(relationParam));
    }
}
