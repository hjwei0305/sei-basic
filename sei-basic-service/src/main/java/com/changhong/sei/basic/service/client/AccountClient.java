package com.changhong.sei.basic.service.client;

import org.springframework.cloud.openfeign.FeignClient;

/**
 * 实现功能: 调用AUTH的用户账户服务
 *
 * @author 王锦光 wangjg
 * @version 2020-02-26 16:11
 */
@FeignClient(name = "sei-auth", path = "account")
public interface AccountClient {
}
