package com.changhong.sei.basic.api;

import com.changhong.sei.basic.dto.FeatureGroupDto;
import com.changhong.sei.core.api.BaseEntityApi;
import com.changhong.sei.core.api.FindAllApi;
import com.changhong.sei.core.dto.ResultData;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * 实现功能: 功能项组API接口
 *
 * @author 王锦光 wangjg
 * @version 2020-01-19 16:10
 */
@FeignClient(name = "sei-basic", path = "featureGroup")
public interface FeatureGroupApi extends BaseEntityApi<FeatureGroupDto>,
        FindAllApi<FeatureGroupDto> {
    /**
     * 模糊查询
     *
     * @return 实体清单
     */
    @GetMapping(path = "findByNameLike")
    @ApiOperation(value = "模糊查询", notes = "通过名称模糊查询")
    ResultData<List<FeatureGroupDto>> findByNameLike(@RequestParam("name") String name);

    /**
     * 根据应用模块id查询功能项组
     *
     * @param appModuleId 应用模块id
     * @return 功能项组
     */
    @GetMapping(path = "findByAppModuleId")
    @ApiOperation(value = "根据应用模块id查询功能项组", notes = "根据应用模块id查询功能项组")
    ResultData<List<FeatureGroupDto>> findByAppModuleId(@RequestParam("appModuleId") String appModuleId);
}
