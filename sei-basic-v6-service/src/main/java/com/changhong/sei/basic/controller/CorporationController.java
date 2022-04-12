package com.changhong.sei.basic.controller;

import com.changhong.sei.basic.api.CorporationApi;
import com.changhong.sei.basic.dto.CorporationDto;
import com.changhong.sei.basic.entity.Corporation;
import com.changhong.sei.basic.service.CorporationService;
import com.changhong.sei.basic.service.cust.CorporationServiceCust;
import com.changhong.sei.core.controller.BaseEntityController;
import com.changhong.sei.core.dto.ResultData;
import com.changhong.sei.core.dto.auth.AuthEntityData;
import com.changhong.sei.core.service.BaseEntityService;
import io.swagger.annotations.Api;
import org.apache.commons.collections.CollectionUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 实现功能: 公司API服务实现
 *
 * @author 王锦光 wangjg
 * @version 2020-01-26 16:26
 */
@RestController
@Api(value = "CorporationApi", tags = "公司API服务")
@RequestMapping(path = "corporation", produces = MediaType.APPLICATION_JSON_VALUE)
public class CorporationController extends BaseEntityController<Corporation, CorporationDto>
        implements CorporationApi {
    @Autowired
    private CorporationService service;
    // 注入扩展业务逻辑
    @Autowired
    private CorporationServiceCust serviceCust;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public BaseEntityService<Corporation> getService() {
        return service;
    }


    /**
     * 将数据实体转换成DTO
     *
     * @param entity 业务实体
     * @return DTO
     */
    @Override
    public CorporationDto convertToDto(Corporation entity) {
        if (Objects.isNull(entity)) {
            return null;
        }
        CorporationDto dto = modelMapper.map(entity, getDtoClass());
        // 自定义扩展实体转DTO属性赋值
        serviceCust.customEntityToDto(entity, dto);
        return dto;
    }

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

    /**
     * 根据纳税人识别号查询公司
     *
     * @param taxNo 纳税人识别号(税号)
     * @return 公司
     */
    @Override
    public ResultData<CorporationDto> findByTaxNo(String taxNo) {
        Corporation corporation = service.findByTaxNo(taxNo);
        return ResultData.success(convertToDto(corporation));
    }

    /**
     * 根据纳税人识别号查询公司
     *
     * @param taxNos 纳税人识别号(税号)
     * @return 公司
     * @deprecated 更新ias后删除 预计在2022-05-01前更新删除
     */
    @Override
    @Deprecated
    public ResultData<List<CorporationDto>> findByTaxNos(Set<String> taxNos) {
        List<CorporationDto> list;
        List<Corporation> corporations = service.findByTaxNos(taxNos);
        if (CollectionUtils.isNotEmpty(corporations)) {
            list = corporations.stream().map(this::convertToDto).collect(Collectors.toList());
        } else {
            list = new ArrayList<>();
        }
        return ResultData.success(list);
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

    /**
     * 获取当前用户有权限的业务实体清单(包含已冻结)
     *
     * @param featureCode 功能项代码
     * @return 有权限的业务实体清单
     */
    @Override
    public ResultData<List<CorporationDto>> getUserAuthEntitiesIncludeFrozen(String featureCode) {
        return ResultData.success(convertToDtos(service.getUserAuthorizedEntities(featureCode, Boolean.TRUE)));
    }


    /**
     * 根据组织机构Id查询公司
     *
     * @param organizationId 组织机构Id
     * @return 公司
     */
    @Override
    public ResultData<CorporationDto> findByOrganizationId(String organizationId) {
        return ResultData.success(convertToDto(service.findByOrganizationId(organizationId)));
    }
}
