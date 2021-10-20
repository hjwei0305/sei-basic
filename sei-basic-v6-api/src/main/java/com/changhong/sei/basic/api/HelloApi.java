package com.changhong.sei.basic.api;

import com.changhong.sei.core.dto.ResultData;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * <strong>实现功能:</strong>
 * <p>调试API接口你好</p>
 *
 * @author 王锦光 wangj
 * @version 1.0.1 2017-10-23 17:14
 */
@FeignClient(name = "sei-basic", path = "basicHello")
public interface HelloApi {
    /**
     * say hello
     * @param name name
     * @return hello name
     */
    @GetMapping(path = "sayHello")
    @ApiOperation(value = "调试API接口说你好", notes = "备注说明调试API接口说你好")
    ResultData<String> sayHello(@RequestParam("name") String name);
}
