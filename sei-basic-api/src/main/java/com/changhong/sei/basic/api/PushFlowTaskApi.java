package com.changhong.sei.basic.api;

import com.changhong.sei.core.dto.flow.FlowTask;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * *************************************************************************************************
 * <p/>
 * 实现功能： 流程推送任务的接口
 * <p>
 * ------------------------------------------------------------------------------------------------
 * 版本          变更时间             变更人                     变更原因
 * ------------------------------------------------------------------------------------------------
 * 1.0.00      2019/3/11            何灿坤                      新建
 * <p/>
 * *************************************************************************************************
 */

@FeignClient(name = "sei-basic", path = "task")
public interface PushFlowTaskApi {
    /**
     * 推送流程模块待办
     *
     * @param taskList 需要推送的待办
     */
    @PostMapping(path = "pushNewTask", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "推送流程模块待办", notes = "推送流程模块待办")
    default void pushNewTask(@RequestBody List<FlowTask> taskList){
    }

    /**
     * 推送流程模块已办（待办转已办）
     *
     * @param taskList 需要推送的已办（待办转已办）
     */
    @PostMapping(path = "pushOldTask", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "推送流程模块已办（待办转已办）", notes = "推送流程模块已办（待办转已办）")
    default void pushOldTask(@RequestBody List<FlowTask> taskList){
    }

    /**
     * 推送流程模块需要删除的待办
     *
     * @param taskList 需要删除的待办
     */
    @PostMapping(path = "pushDelTask", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "推送流程模块需要删除的待办", notes = "推送流程模块需要删除的待办")
    default void pushDelTask(@RequestBody List<FlowTask> taskList){
    }

    /**
     * 推送流程模块归档（正常结束）的待办
     *
     * @param task 需要归档的任务
     */
    @PostMapping(path = "pushEndTask", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "推送流程模块归档（正常结束）的待办", notes = "推送流程模块归档（正常结束）的待办")
    default void pushEndTask(@RequestBody FlowTask task){
    }
}
