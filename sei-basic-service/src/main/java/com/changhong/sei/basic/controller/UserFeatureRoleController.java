package com.changhong.sei.basic.controller;

import com.changhong.sei.basic.api.UserFeatureRoleApi;
import com.changhong.sei.basic.dto.FeatureRoleDto;
import com.changhong.sei.basic.dto.RelationEffective;
import com.changhong.sei.basic.dto.UserDto;
import com.changhong.sei.basic.dto.UserFeatureRoleDto;
import com.changhong.sei.basic.entity.FeatureRole;
import com.changhong.sei.basic.entity.User;
import com.changhong.sei.basic.entity.UserFeatureRole;
import com.changhong.sei.basic.service.UserFeatureRoleService;
import com.changhong.sei.core.controller.BaseRelationController;
import com.changhong.sei.core.dto.ResultData;
import com.changhong.sei.core.service.BaseRelationService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * 实现功能: 用户分配的功能角色API服务实现
 *
 * @author 王锦光 wangjg
 * @version 2020-01-30 9:26
 */
@RestController
@Api(value = "UserFeatureRoleApi", tags = "用户分配的功能角色API服务实现")
@RequestMapping(path = "userFeatureRole", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class UserFeatureRoleController extends BaseRelationController<UserFeatureRole, User, FeatureRole, UserFeatureRoleDto, UserDto, FeatureRoleDto>
        implements UserFeatureRoleApi {
    @Autowired
    private UserFeatureRoleService service;

    @Override
    public BaseRelationService<UserFeatureRole, User, FeatureRole> getService() {
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
     * 保存授权有效期
     *
     * @param effective 授权有效期
     * @return 操作结果
     */
    @Override
    public ResultData<String> saveEffective(@Valid RelationEffective effective) {
        return service.saveEffective(effective);
    }
}
