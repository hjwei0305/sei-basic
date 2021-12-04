package com.changhong.sei.basic.api;

import com.changhong.sei.basic.dto.CorporationDto;
import com.changhong.sei.core.api.BaseEntityApi;
import com.changhong.sei.core.api.DataAuthEntityApi;
import com.changhong.sei.core.api.DataAuthEntityIncludeFrozenApi;
import com.changhong.sei.core.api.FindAllApi;
import com.changhong.sei.core.dto.ResultData;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * 实现功能: 公司API接口
 *
 * @author 王锦光 wangjg
 * @version 2020-01-26 16:16
 */
@FeignClient(name = "sei-basic", path = "corporation")
public interface CorporationApi extends BaseEntityApi<CorporationDto>,
        FindAllApi<CorporationDto>,
        DataAuthEntityApi<CorporationDto>,
        DataAuthEntityIncludeFrozenApi<CorporationDto> {
    /**
     * 根据公司代码查询公司
     *
     * @param code 公司代码
     * @return 公司
     */
    @GetMapping(path = "findByCode")
    @ApiOperation(value = "根据公司代码查询公司", notes = "根据公司代码查询公司")
    ResultData<CorporationDto> findByCode(@RequestParam("code") String code);

    /**
     * 根据ERP公司代码查询公司
     *
     * @param erpCode ERP公司代码
     * @return 公司
     */
    @GetMapping(path = "findByErpCode")
    @ApiOperation(value = "根据ERP公司代码查询公司", notes = "根据ERP公司代码查询公司")
    ResultData<List<CorporationDto>> findByErpCode(@RequestParam("erpCode") String erpCode);

    /**
     * 根据纳税人识别号查询公司
     *
     * @param taxNo 纳税人识别号(税号)
     * @return 公司
     */
    @GetMapping(path = "findByTaxNo")
    @ApiOperation(value = "根据纳税人识别号(税号)查询公司", notes = "根据纳税人识别号(税号)查询公司")
    ResultData<CorporationDto> findByTaxNo(@RequestParam("taxNo") String taxNo);


    /**
     * 根据组织机构Id查询公司
     *
     * @param organizationId 组织机构Id
     * @return 公司
     */
    @GetMapping(path = "findByOrganizationId")
    @ApiOperation(value = "根据组织机构Id查询公司", notes = "根据组织机构Id查询公司")
    ResultData<CorporationDto> findByOrganizationId(@RequestParam("organizationId") String organizationId);
}
