package com.changhong.sei.basic.api;

import com.changhong.sei.basic.dto.DataRoleDto;
import com.changhong.sei.basic.dto.EssEmployeePositionDto;
import com.changhong.sei.core.api.BaseEntityApi;
import com.changhong.sei.core.dto.ResultData;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.List;

/**
 * (EssEmployeePosition)API
 *
 * @author sei
 * @since 2022-11-15 18:17:48
 * TODO @FeignClient(name = "请修改为项目服务名")
 */
@Valid
@FeignClient(name = "sei-basic-v6", path = EssEmployeePositionApi.PATH)
public interface EssEmployeePositionApi extends BaseEntityApi<EssEmployeePositionDto> {
    String PATH = "essEmployeePosition";
}
