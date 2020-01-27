package com.changhong.sei.basic.service;

import com.changhong.sei.basic.api.CountryService;
import com.changhong.sei.basic.dto.CountryDto;
import com.changhong.sei.basic.entity.Country;
import com.changhong.sei.basic.manager.CountryManager;
import com.changhong.sei.core.dto.ResultData;
import com.changhong.sei.core.manager.BaseEntityManager;
import com.changhong.sei.core.service.DefaultBaseEntityService;
import com.changhong.sei.core.service.DefaultFindAllService;
import io.swagger.annotations.Api;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 实现功能: 国家API服务实现
 *
 * @author 王锦光 wangjg
 * @version 2020-01-26 19:41
 */
@Service
@Api(value = "CountryService", tags = "国家API服务")
public class CountryServiceImpl implements DefaultBaseEntityService<Country, CountryDto>,
        DefaultFindAllService<Country, CountryDto>,
        CountryService {
    @Autowired
    private CountryManager manager;
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
        Country country = manager.findByCode(code);
        return ResultData.success(convertToDto(country));
    }

    @Override
    public BaseEntityManager<Country> getManager() {
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
}
