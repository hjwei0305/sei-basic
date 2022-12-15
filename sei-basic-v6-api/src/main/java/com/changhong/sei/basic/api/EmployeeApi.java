package com.changhong.sei.basic.api;

import com.changhong.sei.basic.dto.*;
import com.changhong.sei.basic.dto.search.EmployeeBriefInfoQueryParam;
import com.changhong.sei.basic.dto.search.EmployeeQuickQueryParam;
import com.changhong.sei.core.api.BaseEntityApi;
import com.changhong.sei.core.api.FindByPageApi;
import com.changhong.sei.core.dto.ResultData;
import com.changhong.sei.core.dto.serach.PageResult;
import com.changhong.sei.core.dto.serach.QuickQueryParam;
import com.changhong.sei.core.dto.serach.QuickSearchParam;
import com.changhong.sei.core.dto.serach.Search;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

/**
 * 实现功能: 企业用户API接口
 *
 * @author 王锦光 wangjg
 * @version 2020-01-27 20:04
 */
@FeignClient(name = "sei-basic", path = "employee")
public interface EmployeeApi extends BaseEntityApi<EmployeeDto>,
        FindByPageApi<EmployeeDto> {
    /**
     * 根据查询参数获取企业员工(分页)
     *
     * @param employeeQueryParam 查询参数
     * @return 企业员工
     */
    @PostMapping(path = "findByEmployeeParam")
    @ApiOperation(value = "根据查询参数获取企业员工", notes = "根据查询参数获取企业员工")
    ResultData<PageResult<EmployeeDto>> findByEmployeeParam(@RequestBody EmployeeQueryParam employeeQueryParam);

    /**
     * 分页查询企业用户
     *
     * @param queryParam 查询参数
     * @return 企业用户
     */
    @PostMapping(path = "queryEmployees", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "分页查询企业用户", notes = "通过快速查询参数，分页查询企业用户")
    ResultData<PageResult<EmployeeDto>> queryEmployees(@RequestBody EmployeeQuickQueryParam queryParam);

    /**
     * 根据员工的id列表获取员工
     *
     * @param ids 主键集合
     */
    @PostMapping(path = "findByIds", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "根据员工的id列表获取员工", notes = "根据员工的id列表获取员工")
    ResultData<List<EmployeeDto>> findByIds(@RequestBody List<String> ids);

    /**
     * 根据组合条件获取员工
     */
    @PostMapping(path = "findByFilters", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "根据组合条件获取员工", notes = "根据组合条件获取员工")
    ResultData<List<EmployeeDto>> findByFilters(@RequestBody Search searchConfig);

    /**
     * 根据组织机构的id获取员工
     *
     * @param organizationId 组织机构的id
     * @return 员工用户查询结果
     */
    @GetMapping(path = "findByOrganizationId")
    @ApiOperation(value = "根据组织机构的id获取员工", notes = "根据组织机构的id获取员工")
    ResultData<List<EmployeeDto>> findByOrganizationId(@RequestParam("organizationId") String organizationId);

    /**
     * 根据组织机构的id获取员工(不包含冻结)
     *
     * @param organizationId 组织机构的id
     * @return 员工清单
     */
    @GetMapping(path = "findByOrganizationIdWithoutFrozen")
    @ApiOperation(value = "根据组织机构的id获取员工(不包含冻结)", notes = "根据组织机构的id获取员工(不包含冻结)")
    ResultData<List<EmployeeDto>> findByOrganizationIdWithoutFrozen(@RequestParam("organizationId") String organizationId);

    /**
     * 根据企业员工用户查询参数获取企业员工用户
     *
     * @param param 企业员工用户查询参数
     * @return 员工用户查询结果
     */
    @PostMapping(path = "findByUserQueryParam", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "获取企业员工用户", notes = "根据企业员工用户查询参数获取企业员工用户")
    ResultData<PageResult<EmployeeDto>> findByUserQueryParam(@RequestBody UserQueryParam param);

    /**
     * 根据企业员工的id列表获取执行人
     *
     * @param employeeIds 企业员工的id列表
     * @return 执行人清单
     */
    @PostMapping(path = "getExecutorsByEmployeeIds", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "根据企业员工的id列表获取执行人", notes = "根据企业员工的id列表获取执行人")
    ResultData<List<Executor>> getExecutorsByEmployeeIds(@RequestBody List<String> employeeIds);

    /**
     * 查询可分配的功能角色
     *
     * @param featureRoleGroupId 功能角色组id
     * @param userId             用户id
     * @return 功能角色清单
     */
    @GetMapping(path = "getCanAssignedFeatureRoles")
    @ApiOperation(value = "根据功能角色组的id与岗位id获取可分配的功能角色", notes = "根据功能角色组的id与岗位id获取可分配的功能角色")
    ResultData<List<FeatureRoleDto>> getCanAssignedFeatureRoles(@RequestParam("featureRoleGroupId") String featureRoleGroupId, @RequestParam("userId") String userId);

    /**
     * 查询可分配的数据角色
     *
     * @param dataRoleGroupId 数据角色组id
     * @param userId          用户id
     * @return 数据角色清单
     */
    @GetMapping(path = "getCanAssignedDataRoles")
    @ApiOperation(value = "根据数据角色组的id与岗位id获取可分配的数据角色", notes = "根据数据角色组的id与岗位id获取可分配的数据角色")
    ResultData<List<DataRoleDto>> getCanAssignedDataRoles(@RequestParam("dataRoleGroupId") String dataRoleGroupId, @RequestParam("userId") String userId);

    /**
     * 通过租户代码获取租户管理员
     *
     * @param tenantCode 租户代码
     * @return 员工
     */
    @GetMapping(path = "findAdminByTenantCode")
    @ApiOperation(value = "通过租户代码获取租户管理员", notes = "通过租户代码获取租户管理员")
    ResultData<EmployeeDto> findAdminByTenantCode(@RequestParam("tenantCode") String tenantCode);

    /**
     * 通过员工编号获取员工
     *
     * @param code 员工编号
     * @return 员工
     */
    @GetMapping(path = "findByCode")
    @ApiOperation(value = "通过员工编号获取员工", notes = "通过员工编号获取员工")
    ResultData<EmployeeDto> findByCode(@RequestParam("code") String code);

    /**
     * 快速查询企业用户
     *
     * @param param 快速查询参数
     * @return 企业用户查询结果
     */
    @PostMapping(path = "quickSearch", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "快速查询企业用户", notes = "用用户名或员工编号快速查询企业用户")
    ResultData<PageResult<EmployeeDto>> quickSearch(@RequestBody QuickSearchParam param);

    /**
     * 快速查询企业用户作为流程执行人
     *
     * @param param 快速查询参数
     * @return 企业用户查询结果
     */
    @PostMapping(path = "quickSearchExecutors", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "快速查询企业用户", notes = "用用户名或员工编号快速查询企业用户")
    ResultData<PageResult<Executor>> quickSearchExecutors(@RequestBody QuickSearchParam param);

    /**
     * 根据组织机构Id清单与岗位分类Id清单获取执行人
     *
     * @param queryParam     执行人查询参数
     * @return 执行人清单
     */
    @PostMapping(path = "getExecutorsByPostCatAndOrg", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "根据组织机构IDS与岗位分类IDS获取执行人", notes = "根据组织机构IDS与岗位分类IDS获取执行人")
    ResultData<List<Executor>> getExecutorsByPostCatAndOrg(@RequestBody ExecutorQueryParam queryParam);

    /**
     * 根据岗位id和员工用户查询参数获取所有可分配企业员工用户
     *
     * @param param      员工用户查询参数
     * @return 员工用户查询结果
     */
    @PostMapping(path = "listAllCanAssignEmployees", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "根据企业员工用户查询参数获取企业员工用户", notes = "根据企业员工用户查询参数获取企业员工用户")
    ResultData<List<EmployeeDto>> listAllCanAssignEmployees(@RequestBody UserQueryParam param);

    /**
     * 通过用户id获取员工
     *
     * @param userId 用户id
     * @return 员工
     */
    @GetMapping(path = "findByUserId")
    @ApiOperation(value = "通过用户id获取员工", notes = "通过用户id获取员工")
    ResultData<EmployeeDto> findByUserId(@RequestParam("userId") String userId);

    /**
     * 把一个企业用户的角色复制到多个企业用户
     *
     * @param copyParam 复制参数
     * @return 操作结果
     */
    @PostMapping(path = "copyToEmployees", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "把一个企业用户的角色复制到多个企业用户", notes = "实现快速配置用户权限，把一个用户的角色复制到多个企业用户，可以复制功能角色和数据角色")
    ResultData copyToEmployees(@RequestBody EmployeeCopyParam copyParam);

    /**
     * 获取用户的组织机构代码清单
     * @param userId 用户Id
     * @return 组织机构代码清单
     */
    @GetMapping(path = "getEmployeeOrgCodes")
    @ApiOperation(value = "获取用户的组织机构代码清单", notes = "获取企业用户所在组织机构节点的所有父节点代码")
    ResultData<List<String>> getEmployeeOrgCodes(@RequestParam("userId") String userId);

    /**
     * 获取用户的组织机构Id清单
     * @param userId 用户Id
     * @return 组织机构Id清单
     */
    @GetMapping(path = "getEmployeeOrgIds")
    @ApiOperation(value = "获取用户的组织机构Id清单", notes = "获取企业用户所在组织机构节点的所有父节点Id清单")
    ResultData<List<String>> getEmployeeOrgIds(@RequestParam("userId") String userId);

    /**
     * 获取用户的岗位代码清单
     * @param userId 用户Id
     * @return 岗位代码清单
     */
    @GetMapping(path = "getEmployeePositionCodes")
    @ApiOperation(value = "获取用户的岗位代码清单", notes = "获取企业用户已分配的所有岗位代码")
    ResultData<List<String>> getEmployeePositionCodes(@RequestParam("userId") String userId);

    /**
     * 获取用户的岗位Id清单
     * @param userId 用户Id
     * @return 岗位Id清单
     */
    @GetMapping(path = "getEmployeePositionIds")
    @ApiOperation(value = "获取用户的岗位Id清单", notes = "获取用户的岗位Id清单")
    ResultData<List<String>> getEmployeePositionIds(@RequestParam("userId") String userId);

    /**
     * 保存一个租户的系统管理员
     * @param employeeDto 企业员工用户
     * @return 操作结果
     */
    @PostMapping(path = "saveTenantAdmin", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "保存一个租户的系统管理员", notes = "保存一个租户的系统管理员,需要确定租户代码")
    ResultData<EmployeeDto> saveTenantAdmin(@RequestBody EmployeeDto employeeDto);

    /**
     * 分页查询企业用户简要信息
     *
     * @param queryParam 查询参数
     * @return 企业用户简要信息
     */
    @PostMapping(path = "queryEmployeeBriefInfos", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "分页查询企业用户简要信息", notes = "通过快速查询参数，分页查询企业用户简要信息")
    ResultData<PageResult<EmployeeBriefInfo>> queryEmployeeBriefInfos(@RequestBody EmployeeBriefInfoQueryParam queryParam);

    /**
     * 变定时更新订单更
     * @param params
     * @return
     */
    @PostMapping(path = "updateEmployeeFormHrmsTask", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "每天从HRMS更新人员信息", notes = "每天从HRMS更新人员信息")
    ResultData updateEmployeeFormHrmsTask(@RequestBody Map<String, String> params);
}
