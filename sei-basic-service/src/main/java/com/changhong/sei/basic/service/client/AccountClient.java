package com.changhong.sei.basic.service.client;

import com.changhong.sei.basic.service.client.dto.SessionUserResponse;
import com.changhong.sei.core.dto.ResultData;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.constraints.NotBlank;

/**
 * 实现功能: 调用AUTH的用户账户服务
 *
 * @author 王锦光 wangjg
 * @version 2020-02-26 16:11
 */
@FeignClient(name = "sei-auth", path = "account")
public interface AccountClient {
    /**
     * 通过租户和账号获取已有账户
     */
    @GetMapping(path = "getByTenantAccount")
    ResultData<SessionUserResponse> getByTenantAccount(@RequestParam("tenant") @NotBlank String tenant,
                                                       @RequestParam("account") @NotBlank String account);
}
