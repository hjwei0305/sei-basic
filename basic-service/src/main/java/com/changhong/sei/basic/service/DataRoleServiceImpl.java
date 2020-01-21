package com.changhong.sei.basic.service;

import com.changhong.sei.basic.api.DataRoleService;
import com.changhong.sei.basic.dto.DataRoleDto;
import com.changhong.sei.basic.entity.DataRole;
import com.changhong.sei.basic.manager.DataRoleManager;
import com.changhong.sei.core.dto.ResultData;
import com.changhong.sei.core.manager.BaseEntityManager;
import com.changhong.sei.core.service.DefaultBaseEntityService;
import io.swagger.annotations.Api;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 实现功能: 数据角色API服务实现
 *
 * @author 王锦光 wangjg
 * @version 2020-01-21 8:42
 */
@Service
@Api(value = "DataRoleService", tags = "数据角色API服务")
public class DataRoleServiceImpl implements DefaultBaseEntityService<DataRole, DataRoleDto>,
        DataRoleService {
    @Autowired
    private DataRoleManager manager;
    @Autowired
    private ModelMapper modelMapper;
    @Override
    public BaseEntityManager<DataRole> getManager() {
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
    public Class<DataRole> getEntityClass() {
        return DataRole.class;
    }

    /**
     * 获取传输实体的类型
     *
     * @return 类型Class
     */
    @Override
    public Class<DataRoleDto> getDtoClass() {
        return DataRoleDto.class;
    }

    /**
     * 通过角色组Id获取角色清单
     *
     * @param roleGroupId 角色组Id
     * @return 角色清单
     */
    @Override
    public ResultData<List<DataRoleDto>> findByDataRoleGroup(String roleGroupId) {
        List<DataRole> roles = manager.findByDataRoleGroup(roleGroupId);
        List<DataRoleDto> dtos = roles.stream().map(this::convertToDto).collect(Collectors.toList());
        return ResultData.success(dtos);
    }

    /**
     * 获取用户本人可以分配的角色
     *
     * @param roleGroupId 角色组Id
     * @return 可以分配的角色
     */
    @Override
    public ResultData<List<DataRoleDto>> getCanAssignedRoles(String roleGroupId) {
        List<DataRole> roles = manager.getCanAssignedRoles(roleGroupId);
        List<DataRoleDto> dtos = roles.stream().map(this::convertToDto).collect(Collectors.toList());
        return ResultData.success(dtos);
    }
}
