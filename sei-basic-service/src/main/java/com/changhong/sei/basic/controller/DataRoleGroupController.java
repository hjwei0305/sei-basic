package com.changhong.sei.basic.controller;

import com.changhong.sei.basic.api.DataRoleGroupApi;
import com.changhong.sei.basic.dto.DataRoleGroupDto;
import com.changhong.sei.basic.entity.DataRoleGroup;
import com.changhong.sei.basic.service.DataRoleGroupService;
import com.changhong.sei.core.controller.DefaultBaseEntityController;
import com.changhong.sei.core.dto.ResultData;
import com.changhong.sei.core.service.BaseEntityService;
import io.swagger.annotations.Api;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 实现功能: 数据角色组API服务
 *
 * @author 王锦光 wangjg
 * @version 2020-01-20 15:04
 */
@RestController
@Api(value = "DataRoleGroupApi", tags = "数据角色组API服务")
@RequestMapping(path = "dataRoleGroup", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class DataRoleGroupController implements DefaultBaseEntityController<DataRoleGroup, DataRoleGroupDto>,
        DataRoleGroupApi {
    @Autowired
    private DataRoleGroupService service;
    @Autowired
    private ModelMapper modelMapper;
    @Override
    public BaseEntityService<DataRoleGroup> getService() {
        return service;
    }

    @Override
    public ModelMapper getModelMapper() {
        return modelMapper;
    }

    /**
     * 获取数据实体的类型
     *
     * @return 类型Class
     */
    @Override
    public Class<DataRoleGroup> getEntityClass() {
        return DataRoleGroup.class;
    }

    /**
     * 获取传输实体的类型
     *
     * @return 类型Class
     */
    @Override
    public Class<DataRoleGroupDto> getDtoClass() {
        return DataRoleGroupDto.class;
    }

    /**
     * 获取所有业务实体
     *
     * @return 业务实体清单
     */
    @Override
    public ResultData<List<DataRoleGroupDto>> findAll() {
        return ResultData.success(convertToDtos(service.findAll()));
    }

    /**
     * 获取所有未冻结的业务实体
     *
     * @return 业务实体清单
     */
    @Override
    public ResultData<List<DataRoleGroupDto>> findAllUnfrozen() {
        return ResultData.success(convertToDtos(service.findAllUnfrozen()));
    }
}
