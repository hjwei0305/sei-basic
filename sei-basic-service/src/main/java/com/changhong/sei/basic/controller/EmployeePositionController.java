package com.changhong.sei.basic.controller;

import com.changhong.sei.basic.api.EmployeePositionApi;
import com.changhong.sei.basic.dto.EmployeeDto;
import com.changhong.sei.basic.dto.EmployeePositionDto;
import com.changhong.sei.basic.dto.PositionDto;
import com.changhong.sei.basic.entity.Employee;
import com.changhong.sei.basic.entity.EmployeePosition;
import com.changhong.sei.basic.entity.Position;
import com.changhong.sei.basic.service.EmployeePositionService;
import com.changhong.sei.core.controller.BaseRelationController;
import com.changhong.sei.core.service.BaseRelationService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 实现功能: 企业员工用户分配岗位的API服务实现
 *
 * @author 王锦光 wangjg
 * @version 2020-01-27 16:07
 */
@RestController
@Api(value = "EmployeePositionApi", tags = "企业员工用户分配岗位的API服务实现")
@RequestMapping(path = "employeePosition", produces = MediaType.APPLICATION_JSON_VALUE)
public class EmployeePositionController extends BaseRelationController<EmployeePosition, Employee, Position, EmployeePositionDto, EmployeeDto, PositionDto>
        implements EmployeePositionApi {
    @Autowired
    private EmployeePositionService service;

    @Override
    public BaseRelationService<EmployeePosition, Employee, Position> getService() {
        return service;
    }

    /**
     * 将父数据实体转换成DTO
     *
     * @param entity 业务实体
     * @return DTO
     */
    @Override
    public EmployeeDto convertParentToDto(Employee entity) {
        return EmployeeController.convertToDtoStatic(entity);
    }

    /**
     * 将子数据实体转换成DTO
     *
     * @param entity 业务实体
     * @return DTO
     */
    @Override
    public PositionDto convertChildToDto(Position entity) {
        return PositionController.convertToDtoStatic(entity);
    }
}
