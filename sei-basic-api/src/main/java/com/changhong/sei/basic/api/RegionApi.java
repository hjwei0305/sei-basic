package com.changhong.sei.basic.api;

import com.changhong.sei.basic.dto.RegionDto;
import com.changhong.sei.core.api.BaseTreeApi;
import com.changhong.sei.core.api.FindByPageApi;
import com.changhong.sei.core.dto.ResultData;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * 实现功能: 行政区域API接口
 *
 * @author 王锦光 wangjg
 * @version 2020-01-29 22:58
 */
@FeignClient(name = "sei-basic", path = "region")
public interface RegionApi extends BaseTreeApi<RegionDto>,
        FindByPageApi<RegionDto> {
    /**
     * 获取所有行政区域树
     *
     * @return 行政区域树形对象集合
     */
    @GetMapping(path = "getRegionTree")
    @ApiOperation(notes = "查询所有的行政区域树", value = "查询所有的行政区域树")
    ResultData<List<RegionDto>> getRegionTree();

    /**
     * 通过国家id查询行政区域树
     *
     * @param countryId 国家id
     * @return 行政区域树清单
     */
    @GetMapping(path = "getRegionTreeByCountry")
    @ApiOperation(value = "行政区域树", notes = "通过国家id查询行政区域树")
    ResultData<List<RegionDto>> getRegionTreeByCountry(@RequestParam("countryId") String countryId);

    /**
     * 通过国家id查询省
     */
    @GetMapping(path = "getProvinceByCountry")
    @ApiOperation(value = "通过国家id查询省", notes = "通过国家id查询省")
    ResultData<List<RegionDto>> getProvinceByCountry(@RequestParam("countryId") String countryId);

    /**
     * 通过省id查询市
     */
    @GetMapping(path = "getCityByProvince")
    @ApiOperation(value = "通过省id查询市", notes = "通过省id查询市")
    ResultData<List<RegionDto>> getCityByProvince(@RequestParam("provinceId") String provinceId);
}
