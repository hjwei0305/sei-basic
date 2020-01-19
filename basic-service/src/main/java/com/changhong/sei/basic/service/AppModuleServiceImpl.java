package com.changhong.sei.basic.service;

import com.changhong.sei.basic.api.AppModuleService;
import com.changhong.sei.basic.dto.AppModuleDto;
import com.changhong.sei.basic.entity.AppModule;
import com.changhong.sei.basic.manager.AppModuleManager;
import com.changhong.sei.core.dto.ResultData;
import com.changhong.sei.core.manager.BaseEntityManager;
import com.changhong.sei.core.service.DefaultBaseEntityService;
import com.changhong.sei.core.service.DefaultFindAllService;
import io.swagger.annotations.Api;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 实现功能:
 * 应用模块的服务实现
 *
 * @author 王锦光 wangjg
 * @version 2020-01-19 13:50
 */
@Service
@Api(value = "AppModuleService", tags = "应用模块的API服务")
public class AppModuleServiceImpl implements DefaultBaseEntityService<AppModule, AppModuleDto>,
        DefaultFindAllService<AppModule, AppModuleDto>,
        AppModuleService {
    @Autowired
    private AppModuleManager manager;
    @Autowired
    private ModelMapper modelMapper;
    @Override
    public BaseEntityManager<AppModule> getManager() {
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
    public Class<AppModule> getEntityClass() {
        return AppModule.class;
    }

    /**
     * 获取传输实体的类型
     *
     * @return 类型Class
     */
    @Override
    public Class<AppModuleDto> getDtoClass() {
        return AppModuleDto.class;
    }

    /**
     * 通过代码查询应用模块
     *
     * @param code 代码
     * @return 操作结果
     */
    @Override
    public ResultData<AppModuleDto> findByCode(String code) {
        AppModule appModule = manager.findByCode(code);
        return ResultData.success(convertToDto(appModule));
    }
}
