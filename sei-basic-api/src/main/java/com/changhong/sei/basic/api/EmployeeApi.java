package com.changhong.sei.basic.api;

import com.changhong.sei.basic.dto.*;
import com.changhong.sei.core.api.BaseEntityApi;
import com.changhong.sei.core.api.FindByPageApi;
import com.changhong.sei.core.dto.ResultData;
import com.changhong.sei.core.dto.serach.PageResult;
import com.changhong.sei.core.dto.serach.QuickSearchParam;
import com.changhong.sei.core.dto.serach.Search;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 实现功能: 企业用户API接口
 *
 * @author 王锦光 wangjg
 * @version 2020-01-27 20:04
 */
@FeignClient(name = "sei-basic", path = "employee")
@RequestMapping(path = "employee", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
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
     * 根据员工的id列表获取员工
     *
     * @param ids 主键集合
     */
    @PostMapping(path = "findByIds", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "根据员工的id列表获取员工", notes = "根据员工的id列表获取员工")
    ResultData<List<EmployeeDto>> findByIds(@RequestBody List<String> ids);

    /**
     * 根据组合条件获取员工
     */
    @PostMapping(path = "findByFilters", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
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
    @PostMapping(path = "findByUserQueryParam", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "获取企业员工用户", notes = "根据企业员工用户查询参数获取企业员工用户")
    ResultData<PageResult<EmployeeDto>> findByUserQueryParam(@RequestBody UserQueryParam param);

    /**
     * 根据企业员工的id列表获取执行人
     *
     * @param employeeIds 企业员工的id列表
     * @return 执行人清单
     */
    @PostMapping(path = "getExecutorsByEmployeeIds", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
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
    @PostMapping(path = "quickSearch", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "快速查询企业用户", notes = "用用户名或员工编号快速查询企业用户")
    ResultData<PageResult<EmployeeDto>> quickSearch(@RequestBody QuickSearchParam param);

    /**
     * 快速查询企业用户作为流程执行人
     *
     * @param param 快速查询参数
     * @return 企业用户查询结果
     */
    @PostMapping(path = "quickSearchExecutors", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "快速查询企业用户", notes = "用用户名或员工编号快速查询企业用户")
    ResultData<PageResult<Executor>> quickSearchExecutors(@RequestBody QuickSearchParam param);

    /**
     * 根据组织机构Id清单与岗位分类Id清单获取执行人
     *
     * @param orgIds     组织机构Id清单
     * @param postCatIds 岗位分类Id清单
     * @return 执行人清单
     */
    @GetMapping(path = "getExecutorsByPostCatAndOrg")
    @ApiOperation(value = "根据组织机构IDS与岗位分类IDS获取执行人", notes = "根据组织机构IDS与岗位分类IDS获取执行人")
    ResultData<List<Executor>> getExecutorsByPostCatAndOrg(@RequestParam("orgIds") List<String> orgIds, @RequestParam("postCatIds") List<String> postCatIds);

    /**
     * 根据岗位id和员工用户查询参数获取所有可分配企业员工用户
     *
     * @param param      员工用户查询参数
     * @return 员工用户查询结果
     */
    @PostMapping(path = "listAllCanAssignEmployees", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
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
    @PostMapping(path = "copyToEmployees", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "把一个企业用户的角色复制到多个企业用户", notes = "实现快速配置用户权限，把一个用户的角色复制到多个企业用户，可以复制功能角色和数据角色")
    ResultData copyToEmployees(@RequestBody EmployeeCopyParam copyParam);
}
