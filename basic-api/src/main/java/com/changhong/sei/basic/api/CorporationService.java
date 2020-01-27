package com.changhong.sei.basic.api;

import com.changhong.sei.basic.dto.CorporationDto;
import com.changhong.sei.core.api.BaseEntityService;
import com.changhong.sei.core.api.DataAuthEntityService;
import com.changhong.sei.core.api.FindAllService;
import com.changhong.sei.core.dto.ResultData;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 实现功能: 公司API接口
 *
 * @author 王锦光 wangjg
 * @version 2020-01-26 16:16
 */
@FeignClient(name = "sei-basic", path = "corporation")
@RestController
@RequestMapping(path = "corporation", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public interface CorporationService extends BaseEntityService<CorporationDto>,
        FindAllService<CorporationDto>,
        DataAuthEntityService<CorporationDto> {
    /**
     * 根据公司代码查询公司
     *
     * @param code 公司代码
     * @return 公司
     */
    @GetMapping(path = "findByCode")
    @ApiOperation(value = "根据公司代码查询公司", notes = "根据公司代码查询公司")
    ResultData<CorporationDto> findByCode(@RequestParam("code")String code);

    /**
     * 根据ERP公司代码查询公司
     *
     * @param erpCode ERP公司代码
     * @return 公司
     */
    @GetMapping(path = "findByErpCode")
    @ApiOperation(value = "根据ERP公司代码查询公司", notes = "根据ERP公司代码查询公司")
    ResultData<List<CorporationDto>> findByErpCode(@RequestParam("erpCode") String erpCode);
}
