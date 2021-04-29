package com.changhong.sei.basic.api;

import com.changhong.sei.basic.dto.OrganizationDimension;
import com.changhong.sei.basic.dto.OrganizationDto;
import com.changhong.sei.core.api.BaseTreeApi;
import com.changhong.sei.core.api.DataAuthTreeEntityApi;
import com.changhong.sei.core.api.DataAuthTreeEntityIncludeFrozenApi;
import com.changhong.sei.core.dto.ResultData;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Collection;
import java.util.List;

/**
 * 实现功能: 组织机构API接口
 *
 * @author 王锦光 wangjg
 * @version 2020-01-20 16:25
 */
@FeignClient(name = "sei-basic", path = "organization")
public interface OrganizationApi extends BaseTreeApi<OrganizationDto>,
        DataAuthTreeEntityApi<OrganizationDto>,
        DataAuthTreeEntityIncludeFrozenApi<OrganizationDto> {
    /**
     * 通过代码获取组织机构
     *
     * @param code 代码
     * @return 组织机构
     */
    @GetMapping(path = "findByCode")
    @ApiOperation(value = "通过代码获取组织机构", notes = "通过代码获取组织机构")
    ResultData<OrganizationDto> findByCode(@RequestParam("code") String code);

    /**
     * 通过租户代码获取组织机构根节点
     *
     * @param tenantCode 租户代码
     * @return 组织机构
     */
    @GetMapping(path = "findRootByTenantCode")
    @ApiOperation(value = "通过租户代码获取组织机构根节点", notes = "通过租户代码获取组织机构根节点")
    ResultData<OrganizationDto> findRootByTenantCode(@RequestParam("tenantCode") String tenantCode);

    /**
     * 获取组织机构树
     *
     * @return 组织机构树
     */
    @GetMapping(path = "findOrgTree")
    @ApiOperation(value = "获取组织机构树", notes = "获取组织机构树")
    ResultData<OrganizationDto> findOrgTree();

    /**
     * 获取组织机构树(不包含冻结)
     *
     * @return 组织机构树清单
     */
    @GetMapping(path = "findOrgTreeWithoutFrozen")
    @ApiOperation(value = "获取组织机构树(不包含冻结)", notes = "获取组织机构树(不包含冻结)")
    ResultData<List<OrganizationDto>> findOrgTreeWithoutFrozen();

    /**
     * 获取所有组织机构树
     *
     * @return 所有组织机构树
     */
    @GetMapping(path = "findAllOrgs")
    @ApiOperation(value = "获取所有组织机构树", notes = "获取所有组织机构树")
    ResultData<List<OrganizationDto>> findAllOrgs();

    /**
     * 根据指定的节点id获取树
     *
     * @param nodeId 节点ID
     * @return 返回已指定节点ID为根的树
     */
    @GetMapping(path = "getTree4Unfrozen")
    @ApiOperation(value = "根据指定的节点id获取树", notes = "根据指定的节点id获取树")
    ResultData<OrganizationDto> getTree4Unfrozen(@RequestParam("nodeId") String nodeId);

    /**
     * 通过组织机构id获取组织机构清单
     *
     * @param nodeId 组织机构id
     * @return 组织机构清单（非树形）
     */
    @GetMapping(path = "getChildrenNodes4Unfrozen")
    @ApiOperation(value = "获取非树形组织机构清单", notes = "通过组织机构id获取非树形组织机构清单")
    ResultData<List<OrganizationDto>> getChildrenNodes4Unfrozen(@RequestParam("nodeId") String nodeId);

    /**
     * 获取组织机构维度清单
     *
     * @return 组织机构维度清单
     */
    @GetMapping(path = "findOrganizationDimension")
    @ApiOperation(value = "获取组织机构维度清单", notes = "获取组织机构维度清单")
    ResultData<List<OrganizationDimension>> findOrganizationDimension();

    /**
     * 通过id集合获取组织机构清单
     *
     * @param orgIds id集合
     * @return 组织机构
     */
    @PostMapping(path = "findOrganizationByIds")
    @ApiOperation(value = "通过id集合获取组织机构清单", notes = "通过id集合获取组织机构清单")
    ResultData<List<OrganizationDto>> findOrganizationByIds(@RequestBody Collection<String> orgIds);

    /**
     * 获取当前用户有权限的树形节点代码清单
     *
     * @param featureCode 功能项代码
     * @return 树形节点代码清单
     */
    @GetMapping(path = "getUserAuthorizedTreeNodeCodes")
    @ApiOperation(value = "获取当前用户有权限的树形节点代码清单", notes = "获取当前用户有权限的树形业务实体中的所有节点代码清单")
    ResultData<List<String>> getUserAuthorizedTreeNodeCodes(@RequestParam(value = "featureCode", required = false, defaultValue = "") String featureCode);


    /**
     * 根据公司获取用户有权限的组织机构树
     *
     * @param corporationCode 公司代码
     * @param featureCode     功能项代码
     * @return 组织机构树
     */
    @GetMapping(path = "getOrgAuthTreeByCorp")
    @ApiOperation(value = "根据组织机构Id查询公司", notes = "根据组织机构Id查询公司")
    ResultData<List<OrganizationDto>> getOrgAuthTreeByCorp(@RequestParam("corporationCode") String corporationCode,
                                                     @RequestParam(value = "featureCode", required = false, defaultValue = "") String featureCode);

}
