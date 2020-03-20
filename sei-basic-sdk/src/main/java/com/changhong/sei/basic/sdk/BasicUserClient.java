package com.changhong.sei.basic.sdk;

import com.changhong.sei.core.dto.ResultData;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * 实现功能: 基础应用中通用的用户API接口
 *
 * @author 王锦光 wangjg
 * @version 2020-03-20 10:32
 */
@FeignClient(name = "sei-basic", path = "user")
public interface BasicUserClient {
    /**
     * 获取一般用户有权限的业务实体Id清单
     * @param entityClassName 权限对象类名
     * @param featureCode 功能项代码
     * @param userId 用户Id
     * @return 业务实体Id清单
     */
    @GetMapping(path = "getNormalUserAuthorizedEntities")
    ResultData<List<String>> getNormalUserAuthorizedEntities(@RequestParam("entityClassName") String entityClassName,
                                                             @RequestParam(value = "featureCode", required = false, defaultValue = "") String featureCode,
                                                             @RequestParam("userId") String userId);
}
