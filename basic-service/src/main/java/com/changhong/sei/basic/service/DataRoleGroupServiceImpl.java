package com.changhong.sei.basic.service;

import com.changhong.sei.basic.api.DataRoleGroupService;
import com.changhong.sei.basic.dto.DataRoleGroupDto;
import com.changhong.sei.basic.entity.DataRoleGroup;
import com.changhong.sei.basic.manager.DataRoleGroupManager;
import com.changhong.sei.core.manager.BaseEntityManager;
import com.changhong.sei.core.service.DefaultBaseEntityService;
import com.changhong.sei.core.service.DefaultFindAllService;
import io.swagger.annotations.Api;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 实现功能: 数据角色组API服务
 *
 * @author 王锦光 wangjg
 * @version 2020-01-20 15:04
 */
@Service
@Api(value = "DataRoleGroupService", tags = "数据角色组API服务")
public class DataRoleGroupServiceImpl implements DefaultBaseEntityService<DataRoleGroup, DataRoleGroupDto>,
        DefaultFindAllService<DataRoleGroup, DataRoleGroupDto>,
        DataRoleGroupService {
    @Autowired
    private DataRoleGroupManager manager;
    @Autowired
    private ModelMapper modelMapper;
    @Override
    public BaseEntityManager<DataRoleGroup> getManager() {
        return manager;
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
}
