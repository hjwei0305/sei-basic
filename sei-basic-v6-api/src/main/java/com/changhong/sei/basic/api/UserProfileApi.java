package com.changhong.sei.basic.api;

import com.changhong.sei.basic.dto.LanguageValue;
import com.changhong.sei.basic.dto.UserInfoDto;
import com.changhong.sei.basic.dto.UserProfileDto;
import com.changhong.sei.core.api.BaseEntityApi;
import com.changhong.sei.core.dto.ResultData;
import com.changhong.sei.notify.dto.UserNotifyInfo;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 实现功能: 用户配置API接口
 *
 * @author 王锦光 wangjg
 * @version 2020-01-30 9:47
 */
@FeignClient(name = "sei-basic", path = "userProfile")
public interface UserProfileApi extends BaseEntityApi<UserProfileDto> {
    /**
     * 获取支持的语言
     */
    @GetMapping(path = "getLanguages")
    @ApiOperation(value = "获取支持的语言", notes = "获取支持的语言")
    ResultData<List<LanguageValue>> getLanguages();

    /**
     * 查询一个用户配置
     *
     * @param userId 用户id
     * @return 用户配置
     */
    @GetMapping(path = "findByUserId")
    @ApiOperation(value = "查询一个用户配置", notes = "查询一个用户配置")
    ResultData<UserProfileDto> findByUserId(@RequestParam("userId") String userId);

    /**
     * 查询一个用户配置
     *
     * @return 用户配置
     */
    @GetMapping(path = "getUserInfo")
    @ApiOperation(value = "查询一个用户配置", notes = "查询一个用户配置")
    ResultData<UserInfoDto> getUserInfo();

    /**
     * 根据用户id列表获取通知信息
     *
     * @param userIds 用户id集合
     */
    @PostMapping(path = "findNotifyInfoByUserIds", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "获取通知信息列表", notes = "根据用户id列表获取通知信息列表")
    ResultData<List<UserNotifyInfo>> findNotifyInfoByUserIds(@RequestBody List<String> userIds);

    /**
     * 获取当前用户的记账用户
     *
     * @return 记账用户
     */
    @GetMapping(path = "findAccountor")
    @ApiOperation(value = "获取当前用户的记账用户", notes = "获取当前用户的记账用户")
    ResultData<String> findAccountor();

    /**
     * 获取当前用户的偏好配置
     *
     * @return 偏好配置. 如:{portrait:'data:image/png;base64,XXX', guide:'true'}
     */
    @GetMapping(path = "getPreferences")
    @ApiOperation(value = "获取当前用户的偏好配置", notes = "获取当前用户的偏好配置. 如: {portrait:'data:image/png;base64,XXX', guide:'true'}")
    ResultData<String> getPreferences();

    /**
     * 设置用户偏好配置
     *
     * @param preference 偏好配置类型
     * @param value      偏好配置
     * @return 返回操作结果
     */
    @ApiImplicitParam(name = "preference", value = "偏好配置类型. portrait-头像;guide-系统引导", paramType = "path")
    @ApiOperation(value = "设置用户偏好配置", notes = "设置用户偏好配置")
    @PostMapping(path = "setUserPreference/{preference}", consumes = MediaType.APPLICATION_JSON_VALUE)
    ResultData<Void> setUserPreference(@PathVariable("preference") String preference, @RequestBody Object value);
}
