package com.changhong.sei.basic.service;

import com.changhong.sei.basic.api.OrganizationService;
import com.changhong.sei.basic.dto.OrganizationDimension;
import com.changhong.sei.basic.dto.OrganizationDto;
import com.changhong.sei.basic.entity.Organization;
import com.changhong.sei.basic.manager.OrganizationManager;
import com.changhong.sei.core.dto.ResultData;
import com.changhong.sei.core.dto.auth.AuthTreeEntityData;
import com.changhong.sei.core.manager.BaseTreeManager;
import com.changhong.sei.core.service.DefaultTreeService;
import io.swagger.annotations.Api;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 实现功能: 组织机构API服务实现
 *
 * @author 王锦光 wangjg
 * @version 2020-01-20 17:06
 */
@Service
@Api(value = "OrganizationService", tags = "组织机构API服务")
public class OrganizationServiceImpl implements DefaultTreeService<Organization, OrganizationDto>,
        OrganizationService {
    @Autowired
    private OrganizationManager manager;
    @Autowired
    private ModelMapper modelMapper;
    /**
     * 通过代码获取组织机构
     *
     * @param code 代码
     * @return 组织机构
     */
    @Override
    public ResultData<OrganizationDto> findByCode(String code) {
        Organization organization = manager.findByCode(code);
        return ResultData.success(convertToDto(organization));
    }

    /**
     * 通过租户代码获取组织机构根节点
     *
     * @param tenantCode 租户代码
     * @return 组织机构
     */
    @Override
    public ResultData<OrganizationDto> findRootByTenantCode(String tenantCode) {
        Organization organization = manager.findRootByTenantCode(tenantCode);
        return ResultData.success(convertToDto(organization));
    }

    /**
     * 获取组织机构树
     *
     * @return 组织机构树
     */
    @Override
    public ResultData<OrganizationDto> findOrgTree() {
        Organization organization = manager.findOrgTree();
        return ResultData.success(convertToDto(organization));
    }

    /**
     * 获取组织机构树(不包含冻结)
     *
     * @return 组织机构树清单
     */
    @Override
    public ResultData<List<OrganizationDto>> findOrgTreeWithoutFrozen() {
        List<Organization> organizations = manager.findOrgTreeWithoutFrozen();
        List<OrganizationDto> dtos = organizations.stream().map(this::convertToDto).collect(Collectors.toList());
        return ResultData.success(dtos);
    }

    /**
     * 获取所有组织机构树
     *
     * @return 所有组织机构树
     */
    @Override
    public ResultData<List<OrganizationDto>> findAllOrgs() {
        List<Organization> organizations = manager.findAllOrgs();
        List<OrganizationDto> dtos = organizations.stream().map(this::convertToDto).collect(Collectors.toList());
        return ResultData.success(dtos);
    }

    /**
     * 根据指定的节点id获取树
     *
     * @param nodeId 节点ID
     * @return 返回已指定节点ID为根的树
     */
    @Override
    public ResultData<OrganizationDto> getTree4Unfrozen(String nodeId) {
        Organization organization = manager.getTree4Unfrozen(nodeId);
        return ResultData.success(convertToDto(organization));
    }

    /**
     * 通过组织机构id获取组织机构清单
     *
     * @param nodeId 组织机构id
     * @return 组织机构清单（非树形）
     */
    @Override
    public ResultData<List<OrganizationDto>> getChildrenNodes4Unfrozen(String nodeId) {
        List<Organization> organizations = manager.getChildrenNodes4Unfrozen(nodeId);
        List<OrganizationDto> dtos = organizations.stream().map(this::convertToDto).collect(Collectors.toList());
        return ResultData.success(dtos);
    }

    /**
     * 获取组织机构维度清单
     *
     * @return 组织机构维度清单
     */
    @Override
    public ResultData<List<OrganizationDimension>> findOrganizationDimension() {
        return ResultData.success(manager.findOrganizationDimension());
    }

    /**
     * 通过id集合获取组织机构清单
     *
     * @param orgIds id集合
     * @return 组织机构
     */
    @Override
    public ResultData<List<OrganizationDto>> findOrganizationByIds(Collection<String> orgIds) {
        List<Organization> organizations = manager.findOrganizationByIds(orgIds);
        List<OrganizationDto> dtos = organizations.stream().map(this::convertToDto).collect(Collectors.toList());
        return ResultData.success(dtos);
    }

    /**
     * 通过业务实体Id清单获取数据权限树形实体清单
     *
     * @param ids 业务实体Id清单
     * @return 数据权限树形实体清单
     */
    @Override
    public ResultData<List<AuthTreeEntityData>> getAuthTreeEntityDataByIds(List<String> ids) {
        return ResultData.success(manager.getAuthTreeEntityDataByIds(ids));
    }

    /**
     * 获取所有数据权限树形实体清单
     *
     * @return 数据权限树形实体清单
     */
    @Override
    public ResultData<List<AuthTreeEntityData>> findAllAuthTreeEntityData() {
        return ResultData.success(manager.findAllAuthTreeEntityData());
    }

    /**
     * 获取当前用户有权限的树形业务实体清单
     *
     * @param featureCode 功能项代码
     * @return 有权限的树形业务实体清单
     */
    @Override
    public ResultData<List<OrganizationDto>> getUserAuthorizedTreeEntities(String featureCode) {
        List<Organization> organizations = manager.getUserAuthorizedTreeEntities(featureCode);
        List<OrganizationDto> dtos = organizations.stream().map(this::convertToDto).collect(Collectors.toList());
        return ResultData.success(dtos);
    }

    @Override
    public BaseTreeManager<Organization> getManager() {
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
    public Class<Organization> getEntityClass() {
        return Organization.class;
    }

    /**
     * 获取传输实体的类型
     *
     * @return 类型Class
     */
    @Override
    public Class<OrganizationDto> getDtoClass() {
        return OrganizationDto.class;
    }
}
