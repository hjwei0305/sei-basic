package com.changhong.sei.basic.controller;

import com.changhong.sei.basic.api.CorporationApi;
import com.changhong.sei.basic.dto.CorporationDto;
import com.changhong.sei.basic.entity.Corporation;
import com.changhong.sei.basic.service.CorporationService;
import com.changhong.sei.core.controller.DefaultBaseEntityController;
import com.changhong.sei.core.dto.ResultData;
import com.changhong.sei.core.dto.auth.AuthEntityData;
import com.changhong.sei.core.service.BaseEntityService;
import io.swagger.annotations.Api;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 实现功能: 公司API服务实现
 *
 * @author 王锦光 wangjg
 * @version 2020-01-26 16:26
 */
@RestController
@Api(value = "CorporationApi", tags = "公司API服务")
@RequestMapping(path = "corporation", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class CorporationController implements DefaultBaseEntityController<Corporation, CorporationDto>,
        CorporationApi {
    @Autowired
    private CorporationService service;
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
        Corporation corporation = service.findByCode(code);
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
        List<Corporation> corporations = service.findByErpCode(erpCode);
        List<CorporationDto> dtos = corporations.stream().map(this::convertToDto).collect(Collectors.toList());
        return ResultData.success(dtos);
    }

    @Override
    public BaseEntityService<Corporation> getService() {
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

    /**
     * 通过业务实体Id清单获取数据权限实体清单
     *
     * @param ids 业务实体Id清单
     * @return 数据权限实体清单
     */
    @Override
    public ResultData<List<AuthEntityData>> getAuthEntityDataByIds(List<String> ids) {
        return ResultData.success(service.getAuthEntityDataByIds(ids));
    }

    /**
     * 获取所有数据权限实体清单
     *
     * @return 数据权限实体清单
     */
    @Override
    public ResultData<List<AuthEntityData>> findAllAuthEntityData() {
        return ResultData.success(service.findAllAuthEntityData());
    }

    /**
     * 获取当前用户有权限的业务实体清单
     *
     * @param featureCode 功能项代码
     * @return 有权限的业务实体清单
     */
    @Override
    public ResultData<List<CorporationDto>> getUserAuthorizedEntities(String featureCode) {
        return ResultData.success(convertToDtos(service.getUserAuthorizedEntities(featureCode)));
    }

    /**
     * 获取所有业务实体
     *
     * @return 业务实体清单
     */
    @Override
    public ResultData<List<CorporationDto>> findAll() {
        return ResultData.success(convertToDtos(service.findAll()));
    }

    /**
     * 获取所有未冻结的业务实体
     *
     * @return 业务实体清单
     */
    @Override
    public ResultData<List<CorporationDto>> findAllUnfrozen() {
        return ResultData.success(convertToDtos(service.findAllUnfrozen()));
    }
}
