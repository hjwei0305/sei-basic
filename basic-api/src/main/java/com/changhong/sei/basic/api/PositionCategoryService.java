package com.changhong.sei.basic.api;

import com.changhong.sei.basic.dto.PositionCategoryDto;
import com.changhong.sei.core.api.BaseEntityService;
import com.changhong.sei.core.api.FindAllService;
import com.changhong.sei.core.dto.ResultData;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 实现功能: 岗位类别API接口
 *
 * @author 王锦光 wangjg
 * @version 2020-01-29 9:48
 */
@FeignClient(name = "sei-basic", path = "positionCategory")
@RestController
@RequestMapping(path = "positionCategory", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public interface PositionCategoryService extends BaseEntityService<PositionCategoryDto>,
        FindAllService<PositionCategoryDto> {
    /**
     * 根据岗位类别id列表获取岗位类别
     */
    @PostMapping(path = "findByIds", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "根据岗位类别id列表获取岗位类别", notes = "根据岗位类别id列表获取岗位类别")
    ResultData<List<PositionCategoryDto>> findByIds(@RequestBody List<String> ids);
}
