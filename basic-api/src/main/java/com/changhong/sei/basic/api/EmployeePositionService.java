package com.changhong.sei.basic.api;

import com.changhong.sei.basic.dto.EmployeeDto;
import com.changhong.sei.basic.dto.EmployeePositionDto;
import com.changhong.sei.basic.dto.PositionDto;
import com.changhong.sei.core.api.BaseRelationService;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 实现功能: 企业员工用户分配岗位的API接口
 *
 * @author 王锦光 wangjg
 * @version 2020-01-27 15:03
 */
@FeignClient(name = "sei-basic", path = "employeePosition")
@RestController
@RequestMapping(path = "employeePosition", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public interface EmployeePositionService extends BaseRelationService<EmployeePositionDto, EmployeeDto, PositionDto> {
}
