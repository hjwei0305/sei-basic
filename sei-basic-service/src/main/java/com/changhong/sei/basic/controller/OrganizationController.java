package com.changhong.sei.basic.controller;

import com.changhong.sei.basic.api.OrganizationApi;
import com.changhong.sei.basic.dto.OrganizationDimension;
import com.changhong.sei.basic.dto.OrganizationDto;
import com.changhong.sei.basic.entity.Organization;
import com.changhong.sei.basic.service.OrganizationService;
import com.changhong.sei.core.controller.BaseTreeController;
import com.changhong.sei.core.dto.ResultData;
import com.changhong.sei.core.dto.auth.AuthTreeEntityData;
import com.changhong.sei.core.service.BaseTreeService;
import com.changhong.sei.core.service.bo.ResponseData;
import com.changhong.sei.core.utils.ResultDataUtil;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 实现功能: 组织机构API服务实现
 *
 * @author 王锦光 wangjg
 * @version 2020-01-20 17:06
 */
@RestController
@Api(value = "OrganizationApi", tags = "组织机构API服务")
@RequestMapping(path = "organization", produces = MediaType.APPLICATION_JSON_VALUE)
public class OrganizationController extends BaseTreeController<Organization, OrganizationDto>
        implements OrganizationApi {
    @Autowired
    private OrganizationService service;

    /**
     * 通过代码获取组织机构
     *
     * @param code 代码
     * @return 组织机构
     */
    @Override
    public ResultData<OrganizationDto> findByCode(String code) {
        Organization organization = service.findByCode(code);
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
        Organization organization = service.findRootByTenantCode(tenantCode);
        return ResultData.success(convertToDto(organization));
    }

    /**
     * 获取组织机构树
     *
     * @return 组织机构树
     */
    @Override
    public ResultData<OrganizationDto> findOrgTree() {
        Organization organization = service.findOrgTree();
        return ResultData.success(convertToDto(organization));
    }

    /**
     * 获取组织机构树(不包含冻结)
     *
     * @return 组织机构树清单
     */
    @Override
    public ResultData<List<OrganizationDto>> findOrgTreeWithoutFrozen() {
        List<Organization> organizations = service.findOrgTreeWithoutFrozen();
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
        List<Organization> organizations = service.findAllOrgs();
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
        Organization organization = service.getTree4Unfrozen(nodeId);
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
        List<Organization> organizations = service.getChildrenNodes4Unfrozen(nodeId);
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
        return ResultData.success(service.findOrganizationDimension());
    }

    /**
     * 通过id集合获取组织机构清单
     *
     * @param orgIds id集合
     * @return 组织机构
     */
    @Override
    public ResultData<List<OrganizationDto>> findOrganizationByIds(Collection<String> orgIds) {
        List<Organization> organizations = service.findOrganizationByIds(orgIds);
        List<OrganizationDto> dtos = organizations.stream().map(this::convertToDto).collect(Collectors.toList());
        return ResultData.success(dtos);
    }

    /**
     * 获取当前用户有权限的树形节点代码清单
     *
     * @param featureCode 功能项代码
     * @return 树形节点代码清单
     */
    @Override
    public ResultData<List<String>> getUserAuthorizedTreeNodeCodes(String featureCode) {
        return ResultData.success(service.getUserAuthorizedTreeNodeCodes(featureCode));
    }

    /**
     * 通过业务实体Id清单获取数据权限树形实体清单
     *
     * @param ids 业务实体Id清单
     * @return 数据权限树形实体清单
     */
    @Override
    public ResultData<List<AuthTreeEntityData>> getAuthTreeEntityDataByIds(List<String> ids) {
        return ResultData.success(service.getAuthTreeEntityDataByIds(ids));
    }

    /**
     * 获取所有数据权限树形实体清单
     *
     * @return 数据权限树形实体清单
     */
    @Override
    public ResultData<List<AuthTreeEntityData>> findAllAuthTreeEntityData() {
        return ResultData.success(service.findAllAuthTreeEntityData());
    }

    /**
     * 获取当前用户有权限的树形业务实体清单
     *
     * @param featureCode 功能项代码
     * @return 有权限的树形业务实体清单
     */
    @Override
    public ResultData<List<OrganizationDto>> getUserAuthorizedTreeEntities(String featureCode) {
        return ResultData.success(convertToDtos(service.getUserAuthorizedTreeEntities(featureCode)));
    }

    @Override
    public BaseTreeService<Organization> getService() {
        return service;
    }

    /**
     * 获取当前用户有权限的树形业务实体清单(包含已冻结)
     *
     * @param featureCode 功能项代码
     * @return 有权限的树形业务实体清单
     */
    @Override
    public ResultData<List<OrganizationDto>> getUserAuthTreeEntitiesIncludeFrozen(String featureCode) {
        return ResultData.success(convertToDtos(service.getUserAuthorizedTreeEntities(featureCode, Boolean.TRUE)));
    }

    /**
     * 根据公司获取用户有权限的组织机构树
     *
     * @param corporationCode 公司代码
     * @return 组织机构树
     */
    @Override
    public ResultData<List<OrganizationDto>> getOrgAuthTreeByCorp(String corporationCode, String featureCode) {
        ResponseData<List<Organization>> responseData = service.getOrgAuthTreeByCorp(corporationCode, featureCode);
        return ResultDataUtil.convertFromResponseData(responseData, convertToDtos(responseData.getData()));
    }
}
