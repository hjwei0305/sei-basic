package com.changhong.sei.basic.service;

import com.changhong.sei.basic.api.CorporationService;
import com.changhong.sei.basic.dto.CorporationDto;
import com.changhong.sei.basic.entity.Corporation;
import com.changhong.sei.basic.manager.CorporationManager;
import com.changhong.sei.core.dto.ResultData;
import com.changhong.sei.core.manager.BaseEntityManager;
import com.changhong.sei.core.service.DefaultBaseEntityService;
import com.changhong.sei.core.service.DefaultDataAuthEntityService;
import com.changhong.sei.core.service.DefaultFindAllService;
import io.swagger.annotations.Api;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 实现功能: 公司API服务实现
 *
 * @author 王锦光 wangjg
 * @version 2020-01-26 16:26
 */
@Service
@Api(value = "CorporationService", tags = "公司API服务")
public class CorporationServiceImpl implements DefaultBaseEntityService<Corporation, CorporationDto>,
        DefaultFindAllService<Corporation, CorporationDto>,
        DefaultDataAuthEntityService<Corporation, CorporationDto>,
        CorporationService {
    @Autowired
    private CorporationManager manager;
    @Autowired
    private ModelMapper modelMapper;
    /**
     * 根据公司代码查询公司
     *
     * @param code 公司代码
     * @return 公司
     */
    @Override
    public ResultData<CorporationDto> findByCode(String code) {
        Corporation corporation = manager.findByCode(code);
        return ResultData.success(convertToDto(corporation));
    }

    /**
     * 根据ERP公司代码查询公司
     *
     * @param erpCode ERP公司代码
     * @return 公司
     */
    @Override
    public ResultData<List<CorporationDto>> findByErpCode(String erpCode) {
        List<Corporation> corporations = manager.findByErpCode(erpCode);
        List<CorporationDto> dtos = corporations.stream().map(this::convertToDto).collect(Collectors.toList());
        return ResultData.success(dtos);
    }

    @Override
    public BaseEntityManager<Corporation> getManager() {
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
    public Class<Corporation> getEntityClass() {
        return Corporation.class;
    }

    /**
     * 获取传输实体的类型
     *
     * @return 类型Class
     */
    @Override
    public Class<CorporationDto> getDtoClass() {
        return CorporationDto.class;
    }
}
