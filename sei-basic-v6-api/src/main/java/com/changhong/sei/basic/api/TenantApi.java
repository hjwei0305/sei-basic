package com.changhong.sei.basic.api;

import com.changhong.sei.basic.dto.TenantDto;
import com.changhong.sei.core.api.BaseEntityApi;
import com.changhong.sei.core.api.FindAllApi;
import com.changhong.sei.core.dto.ResultData;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * 实现功能: 租户主数据API接口
 *
 * @author 王锦光 wangjg
 * @version 2020-01-19 14:45
 */
@FeignClient(name = "sei-basic", path = "tenant")
public interface TenantApi extends BaseEntityApi<TenantDto>,
        FindAllApi<TenantDto> {
    /**
     * 判断是否启用信用管理
     * @return 否启用信用管理
     */
    @GetMapping(path = "enableCreditManagement")
    @ApiOperation(value = "判断是否启用信用管理", notes = "判断是否启用信用管理，当前租户")
    ResultData<Boolean> enableCreditManagement();
}
