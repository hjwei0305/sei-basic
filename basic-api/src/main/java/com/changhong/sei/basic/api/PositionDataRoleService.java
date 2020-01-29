package com.changhong.sei.basic.api;

import com.changhong.sei.basic.dto.DataRoleDto;
import com.changhong.sei.basic.dto.PositionDataRoleDto;
import com.changhong.sei.basic.dto.PositionDto;
import com.changhong.sei.core.api.BaseRelationService;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 实现功能: 岗位分配的数据角色API接口
 *
 * @author 王锦光 wangjg
 * @version 2020-01-29 10:09
 */
@FeignClient(name = "sei-basic", path = "positionDataRole")
@RestController
@RequestMapping(path = "positionDataRole", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public interface PositionDataRoleService extends BaseRelationService<PositionDataRoleDto, PositionDto, DataRoleDto> {
}
