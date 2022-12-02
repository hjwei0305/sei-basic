package com.changhong.sei.basic.api;

import com.changhong.sei.basic.dto.SysUserDto;
import com.changhong.sei.core.api.BaseEntityApi;
import com.changhong.sei.core.dto.ResultData;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.Map;


/**
 * 系统用户(SysUser)API
 *
 * @author sei
 * @since 2022-12-02 14:35:15
 * TODO @FeignClient(name = "请修改为项目服务名")
 */
@Valid
@FeignClient(name = "sei-basic-v6", path = SysUserApi.PATH)
public interface SysUserApi extends BaseEntityApi<SysUserDto> {
    String PATH = "sysUser";

    @PostMapping(path = "updateEmpTask", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "定时更新员工信息", notes = "定时更新员工信息")
    ResultData updateEmpTask(@RequestBody Map<String, String> params);

    @GetMapping(path = "findByEmployeeCode", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "根据工号查询", notes = "根据工号查询")
    ResultData findByEmployeeCode(@RequestParam("employeeCode") String employeeCode);
}
