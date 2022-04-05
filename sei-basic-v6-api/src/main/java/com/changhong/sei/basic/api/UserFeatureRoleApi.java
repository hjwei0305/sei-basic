package com.changhong.sei.basic.api;

import com.changhong.sei.basic.dto.FeatureRoleDto;
import com.changhong.sei.basic.dto.RelationEffective;
import com.changhong.sei.basic.dto.UserDto;
import com.changhong.sei.basic.dto.UserFeatureRoleDto;
import com.changhong.sei.core.api.BaseRelationApi;
import com.changhong.sei.core.dto.ResultData;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.List;

/**
 * 实现功能: 用户分配的功能角色API接口
 *
 * @author 王锦光 wangjg
 * @version 2020-01-30 9:24
 */
@FeignClient(name = "sei-basic", path = "userFeatureRole")
public interface UserFeatureRoleApi extends BaseRelationApi<UserFeatureRoleDto, UserDto, FeatureRoleDto> {
    /**
     * 保存授权有效期
     * @param effective 授权有效期
     * @return 操作结果
     */
    @PostMapping(path = "saveEffective", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "保存授权有效期", notes = "保存用户分配的功能角色有效期")
    ResultData<String> saveEffective(@RequestBody @Valid RelationEffective effective);

    /**
     * 获取用户的功能角色Id清单
     * @param userId 用户Id
     * @return 功能角色Id清单
     */
    @GetMapping(path = "getRoleIdsByUserId")
    @ApiOperation(value = "获取用户的功能角色Id清单", notes = "获取用户的功能角色Id清单")
    ResultData<List<String>> getRoleIdsByUserId(@RequestParam("userId") String userId);
}
