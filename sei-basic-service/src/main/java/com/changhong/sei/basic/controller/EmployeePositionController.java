package com.changhong.sei.basic.controller;

import com.changhong.sei.basic.api.EmployeePositionApi;
import com.changhong.sei.basic.dto.EmployeeDto;
import com.changhong.sei.basic.dto.EmployeePositionDto;
import com.changhong.sei.basic.dto.PositionDto;
import com.changhong.sei.basic.entity.Employee;
import com.changhong.sei.basic.entity.EmployeePosition;
import com.changhong.sei.basic.entity.Position;
import com.changhong.sei.basic.service.EmployeePositionService;
import com.changhong.sei.core.controller.DefaultRelationController;
import com.changhong.sei.core.service.BaseRelationService;
import io.swagger.annotations.Api;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

/**
 * 实现功能: 企业员工用户分配岗位的API服务实现
 *
 * @author 王锦光 wangjg
 * @version 2020-01-27 16:07
 */
@RestController
@Api(value = "EmployeePositionService", tags = "企业员工用户分配岗位的API服务实现")
public class EmployeePositionController implements DefaultRelationController<EmployeePosition, Employee, Position, EmployeePositionDto, EmployeeDto, PositionDto>,
        EmployeePositionApi {
    @Autowired
    private EmployeePositionService service;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public BaseRelationService<EmployeePosition, Employee, Position> getService() {
        return service;
    }

    @Override
    public ModelMapper getModelMapper() {
        return modelMapper;
    }

    /**
     * 获取关系型数据实体的类型
     *
     * @return 类型Class
     */
    @Override
    public Class<EmployeePosition> getRelationEntityClass() {
        return EmployeePosition.class;
    }

    /**
     * 获取关系型传输实体的类型
     *
     * @return 类型Class
     */
    @Override
    public Class<EmployeePositionDto> getRelationDtoClass() {
        return EmployeePositionDto.class;
    }

    /**
     * 获取父数据实体的类型
     *
     * @return 类型Class
     */
    @Override
    public Class<Employee> getParentEntityClass() {
        return Employee.class;
    }

    /**
     * 获取父传输实体的类型
     *
     * @return 类型Class
     */
    @Override
    public Class<EmployeeDto> getParentDtoClass() {
        return EmployeeDto.class;
    }

    /**
     * 获取子数据实体的类型
     *
     * @return 类型Class
     */
    @Override
    public Class<Position> getChildEntityClass() {
        return Position.class;
    }

    /**
     * 获取子传输实体的类型
     *
     * @return 类型Class
     */
    @Override
    public Class<PositionDto> getChildDtoClass() {
        return PositionDto.class;
    }
}
