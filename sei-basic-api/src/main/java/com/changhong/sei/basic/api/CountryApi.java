package com.changhong.sei.basic.api;

import com.changhong.sei.basic.dto.CountryDto;
import com.changhong.sei.core.api.BaseEntityApi;
import com.changhong.sei.core.api.FindAllApi;
import com.changhong.sei.core.dto.ResultData;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 实现功能: 国家API接口
 *
 * @author 王锦光 wangjg
 * @version 2020-01-26 19:37
 */
@FeignClient(name = "sei-basic", path = "country")
public interface CountryApi extends BaseEntityApi<CountryDto>,
        FindAllApi<CountryDto> {
    /**
     * 根据代码查询国家
     * @param code 代码
     * @return 国家信息
     */
    @GetMapping(path = "findByCode")
    @ApiOperation(value = "根据代码查询国家",notes = "根据代码查询国家")
    ResultData<CountryDto> findByCode(@RequestParam("code") String code);
}
