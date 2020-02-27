package com.changhong.sei.basic.service.client;

import com.changhong.sei.basic.service.client.dto.AccountResponse;
import com.changhong.sei.basic.service.client.dto.CreateAccountRequest;
import com.changhong.sei.basic.service.client.dto.SessionUserResponse;
import com.changhong.sei.basic.service.client.dto.UpdateAccountRequest;
import com.changhong.sei.core.dto.ResultData;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
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

    /**
     * 创建新账户
     */
    @PostMapping(path = "create")
    ResultData<String> create(@RequestBody @Valid CreateAccountRequest request) throws IllegalAccessException;

    /**
     * 更新账户
     */
    @PostMapping(path = "update")
    ResultData<String> update(@RequestBody @Valid UpdateAccountRequest request) throws IllegalAccessException;
}
