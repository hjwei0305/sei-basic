package com.changhong.sei.basic.api;

import com.changhong.sei.basic.dto.UserProfileDto;
import com.changhong.sei.core.api.BaseEntityService;
import com.changhong.sei.core.dto.IDataValue;
import com.changhong.sei.core.dto.ResultData;
import com.changhong.sei.notify.dto.UserNotifyInfo;
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
@RestController
@RequestMapping(path = "userProfile", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public interface UserProfileService extends BaseEntityService<UserProfileDto> {
    /**
     * 获取支持的语言
     */
    @GetMapping(path = "getLanguages")
    @ApiOperation(value = "获取支持的语言", notes = "获取支持的语言")
    ResultData<List<IDataValue>> getLanguages();

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
     * 根据用户id列表获取通知信息
     *
     * @param userIds 用户id集合
     */
    @PostMapping(path = "findNotifyInfoByUserIds", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
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
}
