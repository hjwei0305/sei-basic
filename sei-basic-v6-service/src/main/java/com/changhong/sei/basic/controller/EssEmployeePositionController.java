package com.changhong.sei.basic.controller;

import com.changhong.sei.basic.api.EssEmployeePositionApi;
import com.changhong.sei.basic.connector.HRMSConnector;
import com.changhong.sei.basic.dao.EmployeeDao;
import com.changhong.sei.basic.dto.DataRoleDto;
import com.changhong.sei.basic.dto.EssEmployeePositionDto;
import com.changhong.sei.basic.dto.OrgDTO;
import com.changhong.sei.basic.entity.*;
import com.changhong.sei.basic.service.EssEmployeePositionService;
import com.changhong.sei.basic.service.OrganizationService;
import com.changhong.sei.basic.service.PositionService;
import com.changhong.sei.core.controller.BaseEntityController;
import com.changhong.sei.core.dto.ParentRelationParam;
import com.changhong.sei.core.dto.ResultData;
import com.changhong.sei.core.service.BaseEntityService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

/**
 * (EssEmployeePosition)控制类
 *
 * @author sei
 * @since 2022-11-15 18:13:31
 */
@RestController
@Api(value = "EssEmployeePositionApi", tags = "服务")
@RequestMapping(path = EssEmployeePositionApi.PATH, produces = MediaType.APPLICATION_JSON_VALUE)
public class EssEmployeePositionController extends BaseEntityController<EssEmployeePosition, EssEmployeePositionDto> implements EssEmployeePositionApi {
    /**
     * 服务对象
     */
    @Autowired
    private EssEmployeePositionService service;
    @Autowired
    private PositionService positionService;
    @Autowired
    private OrganizationService organizationService;
    @Autowired
    private EmployeeDao employeeDao;
    @Autowired
    private EmployeePositionController controller;

    @Override
    public BaseEntityService<EssEmployeePosition> getService() {
        return service;
    }
}
