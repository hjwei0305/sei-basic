package com.changhong.sei.basic.api;

import com.changhong.sei.basic.dto.ExpertUserDto;
import com.changhong.sei.basic.dto.ExpertUserVo;
import com.changhong.sei.core.dto.ResultData;
import com.changhong.sei.core.dto.serach.PageResult;
import com.changhong.sei.core.dto.serach.Search;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

/**
 * 实现功能: 专家用户API接口
 *
 * @author 王锦光 wangjg
 * @version 2020-01-28 9:50
 */
@FeignClient(name = "sei-basic", path = "expertUser")
@RestController
@RequestMapping(path = "expertUser", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public interface ExpertUserService {
    /**
     * 分页查询业务实体
     *
     * @param search 查询参数
     * @return 分页查询结果
     */
    @PostMapping(path = "findVoByPage", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "分页查询业务实体", notes = "分页查询业务实体")
    ResultData<PageResult<ExpertUserVo>> findVoByPage(@RequestBody Search search);

    /**
     * 通过ID将该实体冻结/解冻
     *
     * @param id     实体ID
     * @param frozen 是否冻结，是true,否false
     * @return 操作结果
     */
    @PostMapping(path = "freeze")
    @ApiOperation(value = "通过ID将该实体冻结/解冻", notes = "通过ID将该实体冻结/解冻")
    ResultData freeze(@RequestParam("id") String id, @RequestParam("frozen") Boolean frozen);

    /**
     * 通过专家用户中专家的ID将该实体冻结/解冻
     *
     * @param expertId 实体ID
     * @param frozen   是否冻结，是true,否false
     * @return 操作结果
     */
    @PostMapping(path = "freezeByExpertId")
    @ApiOperation(value = "通过专家用户中专家的ID将该实体冻结/解冻", notes = "通过专家用户中专家的ID将该实体冻结/解冻")
    ResultData freezeByExpertId(@RequestParam("expertId") String expertId, @RequestParam("frozen")Boolean frozen);

    /**
     * 保存专家用户
     *
     * @param expertUserVo 专家用户
     * @return 操作结果
     */
    @PostMapping(path = "save", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "保存专家用户", notes = "保存专家用户")
    ResultData save(@RequestBody ExpertUserVo expertUserVo);

    /**
     * 根据专家用户中的专家的ID删除业务实体
     *
     * @param expertId 专家用户中的专家的ID
     * @return 操作结果
     */
    @DeleteMapping(path = "deleteByExpertId")
    @ApiOperation(value = "根据专家用户中的专家的ID删除业务实体", notes = "根据专家用户中的专家的ID删除业务实体")
    ResultData deleteByExpertId(@RequestParam("expertId") String expertId);

    /**
     * 通过Id获取一个业务实体
     *
     * @param id 业务实体Id
     * @return 业务实体
     */
    @GetMapping(path = "findOne")
    @ApiOperation(value = "获取一个业务实体", notes = "通过Id获取一个业务实体")
    ResultData<ExpertUserDto> findOne(@RequestParam("id") String id);
}
