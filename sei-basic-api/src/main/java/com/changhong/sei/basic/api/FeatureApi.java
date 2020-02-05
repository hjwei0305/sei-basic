package com.changhong.sei.basic.api;

import com.changhong.sei.basic.dto.FeatureDto;
import com.changhong.sei.basic.dto.FeatureType;
import com.changhong.sei.core.api.BaseEntityApi;
import com.changhong.sei.core.api.FindByPageApi;
import com.changhong.sei.core.dto.ResultData;
import com.changhong.sei.core.dto.serach.Search;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 实现功能: 功能项API接口
 *
 * @author 王锦光 wangjg
 * @version 2020-01-19 20:49
 */
@FeignClient(name = "sei-basic", path = "feature")
@RequestMapping(path = "feature", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public interface FeatureApi extends BaseEntityApi<FeatureDto>,
        FindByPageApi<FeatureDto> {
    /**
     * 根据功能项组id查询功能项
     *
     * @param featureGroupId 功能项组的id
     * @return 查询的结果
     */
    @GetMapping(path = "findByFeatureGroupId")
    @ApiOperation(notes = "根据功能项组id查询功能项", value = "根据功能项组id查询功能项")
    ResultData<List<FeatureDto>> findByFeatureGroupId(@RequestParam("featureGroupId") String featureGroupId);

    /**
     * 根据功能项组id以及功能项类型查询功能项
     *
     * @param featureGroupId 功能项组的id
     * @param featureTypes   功能项类型清单
     * @return 查询的结果
     */
    @GetMapping(path = "findByFeatureGroupAndType")
    @ApiOperation(notes = "根据功能项组id以及功能项类型查询功能项", value = "根据功能项组id以及功能项类型查询功能项")
    ResultData<List<FeatureDto>> findByFeatureGroupAndType(
            @RequestParam("featureGroupId") String featureGroupId,
            @RequestParam("featureTypes") List<FeatureType> featureTypes
    );

    /**
     * 根据应用模块id查询功能项
     *
     * @param appModuleId 应用模块id
     * @return 功能项清单
     */
    @GetMapping(path = "findByAppModuleId")
    @ApiOperation(value = "根据应用模块id查询功能项", notes = "根据应用模块id查询功能项")
    ResultData<List<FeatureDto>> findByAppModuleId(@RequestParam("appModuleId") String appModuleId);

    /**
     * 根据过滤条件获取功能项
     *
     * @param search 过滤条件
     * @return 功能项列表
     */
    @PostMapping(path = "findByFilters", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "根据过滤条件获取功能项", notes = "根据过滤条件获取功能项")
    ResultData<List<FeatureDto>> findByFilters(@RequestBody Search search);

    /**
     * 根据功能项id查询子功能项
     *
     * @param featureId 功能项的id
     * @return 功能项列表
     */
    @GetMapping(path = "findChildByFeatureId")
    @ApiOperation(notes = "根据功能项id查询子功能项", value = "根据功能项id查询子功能项")
    ResultData<List<FeatureDto>> findChildByFeatureId(@RequestParam("featureId") String featureId);
}
