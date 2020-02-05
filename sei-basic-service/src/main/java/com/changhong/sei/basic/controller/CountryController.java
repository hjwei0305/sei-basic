package com.changhong.sei.basic.controller;

import com.changhong.sei.basic.api.CountryApi;
import com.changhong.sei.basic.dto.CountryDto;
import com.changhong.sei.basic.entity.Country;
import com.changhong.sei.basic.service.CountryService;
import com.changhong.sei.core.controller.DefaultBaseEntityController;
import com.changhong.sei.core.dto.ResultData;
import com.changhong.sei.core.service.BaseEntityService;
import io.swagger.annotations.Api;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 实现功能: 国家API服务实现
 *
 * @author 王锦光 wangjg
 * @version 2020-01-26 19:41
 */
@RestController
@Api(value = "CountryService", tags = "国家API服务")
public class CountryController implements DefaultBaseEntityController<Country, CountryDto>,
        CountryApi {
    @Autowired
    private CountryService service;
    @Autowired
    private ModelMapper modelMapper;
    /**
     * 根据代码查询国家
     *
     * @param code 代码
     * @return 国家信息
     */
    @Override
    public ResultData<CountryDto> findByCode(String code) {
        Country country = service.findByCode(code);
        return ResultData.success(convertToDto(country));
    }

    @Override
    public BaseEntityService<Country> getService() {
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
    public Class<Country> getEntityClass() {
        return Country.class;
    }

    /**
     * 获取传输实体的类型
     *
     * @return 类型Class
     */
    @Override
    public Class<CountryDto> getDtoClass() {
        return CountryDto.class;
    }

    /**
     * 获取所有业务实体
     *
     * @return 业务实体清单
     */
    @Override
    public ResultData<List<CountryDto>> findAll() {
        return ResultData.success(convertToDtos(service.findAll()));
    }

    /**
     * 获取所有未冻结的业务实体
     *
     * @return 业务实体清单
     */
    @Override
    public ResultData<List<CountryDto>> findAllUnfrozen() {
        return ResultData.success(convertToDtos(service.findAllUnfrozen()));
    }
}
