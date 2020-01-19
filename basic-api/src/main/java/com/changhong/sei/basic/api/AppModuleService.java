package com.changhong.sei.basic.api;

import com.changhong.sei.basic.dto.AppModuleDto;
import com.changhong.sei.core.api.BaseEntityService;
import com.changhong.sei.core.api.FindAllService;
import com.changhong.sei.core.dto.ResultData;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * <strong>实现功能:</strong>
 * <p>应用模块API接口</p>
 *
 * @author 王锦光 wangj
 * @version 1.0.1 2020-01-19 13:41
 */
@FeignClient(name = "sei-basic", path = "appModule")
@RestController
@RequestMapping(path = "appModule", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public interface AppModuleService extends BaseEntityService<AppModuleDto>,
        FindAllService<AppModuleDto> {
    /**
     * 通过代码查询应用模块
     * @param code 代码
     * @return 操作结果
     */
    @GetMapping(path = "findByCode")
    @ApiOperation(value = "获取应用模块", notes = "通过代码获取应用模块")
    ResultData<AppModuleDto> findByCode(@RequestParam("code") String code);
}
