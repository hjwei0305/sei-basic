package com.changhong.sei.basic.controller;

import com.changhong.sei.basic.api.AppModuleApi;
import com.changhong.sei.basic.dto.AppModuleDto;
import com.changhong.sei.basic.entity.AppModule;
import com.changhong.sei.basic.service.AppModuleService;
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
 * 实现功能:
 * 应用模块的服务实现
 *
 * @author 王锦光 wangjg
 * @version 2020-01-19 13:50
 */
@RestController
@Api(value = "AppModuleApi", tags = "应用模块的API服务")
@RequestMapping(path = "appModule", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class AppModuleController implements DefaultBaseEntityController<AppModule, AppModuleDto>,
        AppModuleApi {
    @Autowired
    private AppModuleService service;
    @Autowired
    private ModelMapper modelMapper;
    @Override
    public BaseEntityService<AppModule> getService() {
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
        AppModule appModule = service.findByCode(code);
        return ResultData.success(convertToDto(appModule));
    }

    /**
     * 获取所有业务实体
     *
     * @return 业务实体清单
     */
    @Override
    public ResultData<List<AppModuleDto>> findAll() {
        return ResultData.success(convertToDtos(service.findAll()));
    }

    /**
     * 获取所有未冻结的业务实体
     *
     * @return 业务实体清单
     */
    @Override
    public ResultData<List<AppModuleDto>> findAllUnfrozen() {
        return ResultData.success(convertToDtos(service.findAllUnfrozen()));
    }
}
