package com.changhong.sei.basic.api;

import com.changhong.sei.basic.dto.UserEmailAlertDto;
import com.changhong.sei.core.api.BaseEntityApi;
import com.changhong.sei.core.dto.ResultData;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * 实现功能: 用户邮件提醒API接口
 *
 * @author 王锦光 wangjg
 * @version 2020-01-22 10:21
 */
@FeignClient(name = "sei-basic", path = "userEmailAlert")
public interface UserEmailAlertApi extends BaseEntityApi<UserEmailAlertDto> {
    /**
     * 通过用户ID列表获取用户邮件通知列表
     * @param userIdS 用户ID列表
     * @return 操作结果
     */
    @PostMapping(path = "findByUserIds", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "根据用户ID查找", notes = "根据用户ID查找")
    ResultData<List<UserEmailAlertDto>> findByUserIds(@RequestBody List<String> userIdS);

    /**
     * 通过用户ID列表更新最新提醒时间
     * @param userIds 用户ID列表
     * @return 操作结果
     */
    @PostMapping(path = "updateLastTimes", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "通过用户ID列表更新最新提醒时间", notes = "通过用户ID列表更新最新提醒时间")
    ResultData updateLastTimes(@RequestBody List<String> userIds);

    /**
     * 获取当前用户邮件通知列表
     *
     * @return 操作结果
     */
    @GetMapping(path = "findMyEmailAlert")
    @ApiOperation(value = "获取当前用户邮件通知列表", notes = "获取当前用户邮件通知列表")
    ResultData<List<UserEmailAlertDto>> findByUserIds();
}
