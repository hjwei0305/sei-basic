package com.changhong.sei.basic.api;

import com.changhong.sei.basic.dto.DataRoleDto;
import com.changhong.sei.basic.dto.UserDataRoleDto;
import com.changhong.sei.basic.dto.UserDto;
import com.changhong.sei.core.api.BaseRelationService;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 实现功能: 用户分配的数据角色API接口
 *
 * @author 王锦光 wangjg
 * @version 2020-01-30 9:24
 */
@FeignClient(name = "sei-basic", path = "userDataRole")
@RestController
@RequestMapping(path = "userDataRole", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public interface UserDataRoleService extends BaseRelationService<UserDataRoleDto, UserDto, DataRoleDto> {
}
