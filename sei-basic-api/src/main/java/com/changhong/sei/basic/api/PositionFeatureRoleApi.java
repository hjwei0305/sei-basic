package com.changhong.sei.basic.api;

import com.changhong.sei.basic.dto.FeatureRoleDto;
import com.changhong.sei.basic.dto.PositionDto;
import com.changhong.sei.basic.dto.PositionFeatureRoleDto;
import com.changhong.sei.core.api.BaseRelationApi;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 实现功能: 岗位分配的功能角色API接口
 *
 * @author 王锦光 wangjg
 * @version 2020-01-29 10:09
 */
@FeignClient(name = "sei-basic", path = "positionFeatureRole")
@RequestMapping(path = "positionFeatureRole", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public interface PositionFeatureRoleApi extends BaseRelationApi<PositionFeatureRoleDto, PositionDto, FeatureRoleDto> {
}
