package com.changhong.sei.basic.service.client;

import com.changhong.sei.basic.service.client.dto.*;
import com.changhong.sei.core.dto.ResultData;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.List;

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
     * 通过账户id获取已有账户
     */
    @GetMapping(path = "getByUserId")
    ResultData<List<AccountResponse>> getByUserId(@RequestParam("userId") @NotBlank String userId);

    /**
     * 创建新账户
     */
    @PostMapping(path = "create")
    ResultData<String> create(@RequestBody @Valid CreateAccountRequest request) throws IllegalAccessException;

    /**
     * 更新账户
     */
    @PostMapping(path = "updateByTenantAccount")
    @ApiOperation("按租户账号修改账户")
    ResultData<String> updateByTenantAccount(@RequestBody @Valid UpdateAccountByAccountRequest request) throws IllegalAccessException;
}
