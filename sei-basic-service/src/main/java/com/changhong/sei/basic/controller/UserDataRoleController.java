package com.changhong.sei.basic.controller;

import com.changhong.sei.basic.api.UserDataRoleApi;
import com.changhong.sei.basic.dto.DataRoleDto;
import com.changhong.sei.basic.dto.RelationEffective;
import com.changhong.sei.basic.dto.UserDataRoleDto;
import com.changhong.sei.basic.dto.UserDto;
import com.changhong.sei.basic.entity.DataRole;
import com.changhong.sei.basic.entity.User;
import com.changhong.sei.basic.entity.UserDataRole;
import com.changhong.sei.basic.service.UserDataRoleService;
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
 * 实现功能: 用户分配的数据角色API服务实现
 *
 * @author 王锦光 wangjg
 * @version 2020-01-30 9:26
 */
@RestController
@Api(value = "UserDataRoleApi", tags = "用户分配的数据角色API服务实现")
@RequestMapping(path = "userDataRole", produces = MediaType.APPLICATION_JSON_VALUE)
public class UserDataRoleController extends BaseRelationController<UserDataRole, User, DataRole, UserDataRoleDto, UserDto, DataRoleDto>
        implements UserDataRoleApi {
    @Autowired
    private UserDataRoleService service;

    @Override
    public BaseRelationService<UserDataRole, User, DataRole> getService() {
        return service;
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
