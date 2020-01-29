package com.changhong.sei.basic.api;

import com.changhong.sei.basic.dto.FeatureRoleGroupDto;
import com.changhong.sei.core.api.BaseEntityService;
import com.changhong.sei.core.api.FindAllService;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 实现功能: 功能角色组API接口
 *
 * @author 王锦光 wangjg
 * @version 2020-01-28 10:50
 */
@FeignClient(name = "sei-basic", path = "featureRoleGroup")
@RestController
@RequestMapping(path = "featureRoleGroup", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public interface FeatureRoleGroupService extends BaseEntityService<FeatureRoleGroupDto>,
        FindAllService<FeatureRoleGroupDto> {
}
