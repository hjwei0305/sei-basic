package com.changhong.sei.basic.api;

import com.changhong.sei.basic.dto.TenantDto;
import com.changhong.sei.core.api.BaseEntityService;
import com.changhong.sei.core.api.FindAllService;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 实现功能: 租户主数据API接口
 *
 * @author 王锦光 wangjg
 * @version 2020-01-19 14:45
 */
@FeignClient(name = "sei-basic", path = "tenant")
@RestController
@RequestMapping(path = "tenant", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public interface TenantService extends BaseEntityService<TenantDto>, FindAllService<TenantDto> {
}
