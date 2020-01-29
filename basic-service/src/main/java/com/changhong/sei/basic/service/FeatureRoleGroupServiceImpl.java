package com.changhong.sei.basic.service;

import com.changhong.sei.basic.api.FeatureRoleGroupService;
import com.changhong.sei.basic.dto.FeatureRoleGroupDto;
import com.changhong.sei.basic.entity.FeatureRoleGroup;
import com.changhong.sei.basic.manager.FeatureRoleGroupManager;
import com.changhong.sei.core.manager.BaseEntityManager;
import com.changhong.sei.core.service.DefaultBaseEntityService;
import com.changhong.sei.core.service.DefaultFindAllService;
import io.swagger.annotations.Api;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 实现功能: 功能角色组API服务实现
 *
 * @author 王锦光 wangjg
 * @version 2020-01-28 10:52
 */
@Service
@Api(value = "FeatureRoleGroupService", tags = "功能角色组API服务实现")
public class FeatureRoleGroupServiceImpl implements DefaultBaseEntityService<FeatureRoleGroup, FeatureRoleGroupDto>,
        DefaultFindAllService<FeatureRoleGroup, FeatureRoleGroupDto>,
        FeatureRoleGroupService {
    @Autowired
    private FeatureRoleGroupManager manager;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public BaseEntityManager<FeatureRoleGroup> getManager() {
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
    public Class<FeatureRoleGroup> getEntityClass() {
        return FeatureRoleGroup.class;
    }

    /**
     * 获取传输实体的类型
     *
     * @return 类型Class
     */
    @Override
    public Class<FeatureRoleGroupDto> getDtoClass() {
        return FeatureRoleGroupDto.class;
    }
}
