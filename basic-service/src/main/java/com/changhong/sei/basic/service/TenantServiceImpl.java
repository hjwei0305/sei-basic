package com.changhong.sei.basic.service;

import com.changhong.sei.basic.dto.TenantDto;
import com.changhong.sei.basic.entity.Tenant;
import com.changhong.sei.basic.manager.TenantManager;
import com.changhong.sei.core.manager.BaseEntityManager;
import com.changhong.sei.core.service.DefaultBaseEntityService;
import com.changhong.sei.core.service.DefaultFindAllService;
import io.swagger.annotations.Api;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 实现功能: 租户的服务实现
 *
 * @author 王锦光 wangjg
 * @version 2020-01-19 14:51
 */
@Service
@Api(value = "TenantService", tags = "租户的API服务")
public class TenantServiceImpl implements DefaultBaseEntityService<Tenant, TenantDto>,
        DefaultFindAllService<Tenant, TenantDto> {
    @Autowired
    private TenantManager manager;
    @Autowired
    private ModelMapper modelMapper;
    @Override
    public BaseEntityManager<Tenant> getManager() {
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
    public Class<Tenant> getEntityClass() {
        return Tenant.class;
    }

    /**
     * 获取传输实体的类型
     *
     * @return 类型Class
     */
    @Override
    public Class<TenantDto> getDtoClass() {
        return TenantDto.class;
    }
}
