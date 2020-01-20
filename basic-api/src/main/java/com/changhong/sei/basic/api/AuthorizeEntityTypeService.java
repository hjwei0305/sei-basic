package com.changhong.sei.basic.api;

import com.changhong.sei.basic.dto.AuthorizeEntityTypeDto;
import com.changhong.sei.core.api.BaseEntityService;
import com.changhong.sei.core.api.FindAllService;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 实现功能: 权限对象类型API接口
 *
 * @author 王锦光 wangjg
 * @version 2020-01-20 11:08
 */
@FeignClient(name = "sei-basic", path = "authorizeEntityType")
@RestController
@RequestMapping(path = "authorizeEntityType", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public interface AuthorizeEntityTypeService extends BaseEntityService<AuthorizeEntityTypeDto>,
        FindAllService<AuthorizeEntityTypeDto> {
}
