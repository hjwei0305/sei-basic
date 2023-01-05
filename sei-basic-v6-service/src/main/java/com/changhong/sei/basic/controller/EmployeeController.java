package com.changhong.sei.basic.controller;

import com.changhong.sei.basic.api.EmployeeApi;
import com.changhong.sei.basic.dto.*;
import com.changhong.sei.basic.dto.search.EmployeeBriefInfoQueryParam;
import com.changhong.sei.basic.dto.search.EmployeeQuickQueryParam;
import com.changhong.sei.basic.entity.*;
import com.changhong.sei.basic.service.EmployeePositionService;
import com.changhong.sei.basic.service.EmployeeService;
import com.changhong.sei.basic.service.OrganizationService;
import com.changhong.sei.basic.service.PositionService;
import com.changhong.sei.core.context.ContextUtil;
import com.changhong.sei.core.controller.BaseEntityController;
import com.changhong.sei.core.dto.ResultData;
import com.changhong.sei.core.dto.serach.PageResult;
import com.changhong.sei.core.dto.serach.QuickQueryParam;
import com.changhong.sei.core.dto.serach.QuickSearchParam;
import com.changhong.sei.core.dto.serach.Search;
import com.changhong.sei.core.log.LogUtil;
import com.changhong.sei.core.service.BaseEntityService;
import com.changhong.sei.core.utils.ResultDataUtil;
import com.changhong.sei.enums.UserAuthorityPolicy;
import com.changhong.sei.enums.UserType;
import io.swagger.annotations.Api;
import org.apache.commons.lang3.StringUtils;
import org.modelmapper.PropertyMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 实现功能: 企业用户API服务实现
 *
 * @author 王锦光 wangjg
 * @version 2020-01-28 0:11
 */
@RestController
@Api(value = "EmployeeApi", tags = "企业用户API服务实现")
@RequestMapping(path = "employee", produces = MediaType.APPLICATION_JSON_VALUE)
public class EmployeeController extends BaseEntityController<Employee, EmployeeDto>
        implements EmployeeApi {
    @Autowired
    private EmployeeService service;
    @Autowired
    private OrganizationService organizationService;
    @Autowired
    private EmployeePositionService employeePositionService;
    @Override
    public BaseEntityService<Employee> getService() {
        return service;
    }
    @Autowired
    public PositionService positionService;
    /**
     * 自定义设置Entity转换为DTO的转换器
     */
    @Override
    protected void customConvertToDtoMapper() {
        // 创建自定义映射规则
        PropertyMap<Employee, EmployeeDto> propertyMap = new PropertyMap<Employee, EmployeeDto>() {
            @Override
            protected void configure() {
                // 使用自定义转换规则
                map().setId(source.getId());
                map().setOrganizationId(source.getOrganizationId());
                map().setFrozen(source.getUser().getFrozen());
                map().setUserAccount(source.getUser().getAccount());
            }
        };
        // 添加映射器
        dtoModelMapper.addMappings(propertyMap);
    }

    /**
     * 将数据实体转换成DTO
     * @param entity 数据实体
     * @return DTO
     */
    static EmployeeDto convertToDtoStatic(Employee entity){
        if (Objects.isNull(entity)){
            return null;
        }
        // 转换
        return dtoModelMapper.map(entity, EmployeeDto.class);
    }

    /**
     * 根据查询参数获取企业员工(分页)
     *
     * @param employeeQueryParam 查询参数
     * @return 企业员工
     */
    @Override
    public ResultData<PageResult<EmployeeDto>> findByEmployeeParam(EmployeeQueryParam employeeQueryParam) {
        PageResult<Employee> result = service.findByEmployeeParam(employeeQueryParam);
        return convertToDtoPageResult(result);
    }

    /**
     * 分页查询企业用户
     *
     * @param queryParam 查询参数
     * @return 企业用户
     */
    @Override
    public ResultData<PageResult<EmployeeDto>> queryEmployees(EmployeeQuickQueryParam queryParam) {
        return convertToDtoPageResult(service.queryEmployees(queryParam));
    }

    /**
     * 根据员工的id列表获取员工
     *
     * @param ids 主键集合
     */
    @Override
    public ResultData<List<EmployeeDto>> findByIds(List<String> ids) {
        return ResultData.success(convertToDtos(service.findByIds(ids)));
    }

    /**
     * 根据组合条件获取员工
     *
     * @param searchConfig 查询参数
     */
    @Override
    public ResultData<List<EmployeeDto>> findByFilters(Search searchConfig) {
        return ResultData.success(convertToDtos(service.findByFilters(searchConfig)));
    }

    /**
     * 根据组织机构的id获取员工
     *
     * @param organizationId 组织机构的id
     * @return 员工用户查询结果
     */
    @Override
    public ResultData<List<EmployeeDto>> findByOrganizationId(String organizationId) {
        return ResultData.success(convertToDtos(service.findByOrganizationId(organizationId)));
    }

    /**
     * 根据组织机构的id获取员工(不包含冻结)
     *
     * @param organizationId 组织机构的id
     * @return 员工清单
     */
    @Override
    public ResultData<List<EmployeeDto>> findByOrganizationIdWithoutFrozen(String organizationId) {
        return ResultData.success(convertToDtos(service.findByOrganizationIdWithoutFrozen(organizationId)));
    }

    /**
     * 根据企业员工用户查询参数获取企业员工用户
     *
     * @param param 企业员工用户查询参数
     * @return 员工用户查询结果
     */
    @Override
    public ResultData<PageResult<EmployeeDto>> findByUserQueryParam(UserQueryParam param) {
        PageResult<Employee> result = service.findByUserQueryParam(param);
        return convertToDtoPageResult(result);
    }

    /**
     * 根据企业员工的id列表获取执行人
     *
     * @param employeeIds 企业员工的id列表
     * @return 执行人清单
     */
    @Override
    public ResultData<List<Executor>> getExecutorsByEmployeeIds(List<String> employeeIds) {
        return ResultData.success(service.getExecutorsByEmployeeIds(employeeIds));
    }

    /**
     * 查询可分配的功能角色
     *
     * @param featureRoleGroupId 功能角色组id
     * @param userId             用户id
     * @return 功能角色清单
     */
    @Override
    public ResultData<List<FeatureRoleDto>> getCanAssignedFeatureRoles(String featureRoleGroupId, String userId) {
        List<FeatureRole> roles = service.getCanAssignedFeatureRoles(featureRoleGroupId, userId);
        List<FeatureRoleDto> dtos = roles.stream().map(FeatureRoleController::convertToDtoStatic).collect(Collectors.toList());
        return ResultData.success(dtos);
    }

    /**
     * 查询可分配的数据角色
     *
     * @param dataRoleGroupId 数据角色组id
     * @param userId          用户id
     * @return 数据角色清单
     */
    @Override
    public ResultData<List<DataRoleDto>> getCanAssignedDataRoles(String dataRoleGroupId, String userId) {
        List<DataRole> roles = service.getCanAssignedDataRoles(dataRoleGroupId, userId);
        List<DataRoleDto> dtos = roles.stream().map(DataRoleController::convertToDtoStatic).collect(Collectors.toList());
        return ResultData.success(dtos);
    }

    /**
     * 通过租户代码获取租户管理员
     *
     * @param tenantCode 租户代码
     * @return 员工
     */
    @Override
    public ResultData<EmployeeDto> findAdminByTenantCode(String tenantCode) {
        return ResultData.success(convertToDto(service.findAdminByTenantCode(tenantCode)));
    }

    /**
     * 通过员工编号获取员工
     *
     * @param code 员工编号
     * @return 员工
     */
    @Override
    public ResultData<EmployeeDto> findByCode(String code) {
        return ResultData.success(convertToDto(service.findByCode(code)));
    }

    /**
     * 快速查询企业用户
     *
     * @param param 快速查询参数
     * @return 企业用户查询结果
     */
    @Override
    public ResultData<PageResult<EmployeeDto>> quickSearch(QuickSearchParam param) {
        return convertToDtoPageResult(service.quickSearch(param));
    }

    /**
     * 快速查询企业用户作为流程执行人
     *
     * @param param 快速查询参数
     * @return 企业用户查询结果
     */
    @Override
    public ResultData<PageResult<Executor>> quickSearchExecutors(QuickSearchParam param) {
        return ResultData.success(service.quickSearchExecutors(param));
    }

    /**
     * 根据组织机构Id清单与岗位分类Id清单获取执行人
     *
     * @param queryParam     执行人查询参数
     * @return 执行人清单
     */
    @Override
    public ResultData<List<Executor>> getExecutorsByPostCatAndOrg(ExecutorQueryParam queryParam) {
        return ResultData.success(service.getExecutorsByPostCatAndOrg(queryParam.getOrgIds(), queryParam.getPostCatIds()));
    }

    /**
     * 根据岗位id和员工用户查询参数获取所有可分配企业员工用户
     *
     * @param param 员工用户查询参数
     * @return 员工用户查询结果
     */
    @Override
    public ResultData<List<EmployeeDto>> listAllCanAssignEmployees(UserQueryParam param) {
        return ResultData.success(convertToDtos(service.listAllCanAssignEmployees(param)));
    }

    /**
     * 通过用户id获取员工
     *
     * @param userId 用户id
     * @return 员工
     */
    @Override
    public ResultData<EmployeeDto> findByUserId(String userId) {
        ResultData<Employee> resultData = service.findByUserId(userId);
        if (resultData.failed()){
            return ResultData.fail(resultData.getMessage());
        }
        return ResultData.success(convertToDto(resultData.getData()));
    }

    /**
     * 把一个企业用户的角色复制到多个企业用户
     *
     * @param copyParam 复制参数
     * @return 操作结果
     */
    @Override
    public ResultData<?> copyToEmployees(EmployeeCopyParam copyParam) {
        return ResultDataUtil.convertFromOperateResult(service.copyToEmployees(copyParam));
    }

    /**
     * 获取用户的组织机构代码清单
     *
     * @param userId 用户Id
     * @return 组织机构代码清单
     */
    @Override
    public ResultData<List<String>> getEmployeeOrgCodes(String userId) {
        List<String> orgCodes = new ArrayList<>();
        // 获取企业员工的组织
        Employee employee = service.findOne(userId);
        if (Objects.isNull(employee) || Objects.isNull(employee.getOrganization())){
            return ResultData.success(orgCodes);
        }
        // 获取组织机构节点清单
        List<Organization> organizations = organizationService.getParentNodes(employee.getOrganization(), true);
        return ResultData.success(organizations.stream().map(Organization::getCode).collect(Collectors.toList()));
    }

    /**
     * 获取用户的组织机构Id清单
     *
     * @param userId 用户Id
     * @return 组织机构Id清单
     */
    @Override
    public ResultData<List<String>> getEmployeeOrgIds(String userId) {
        List<String> orgCodes = new ArrayList<>();
        // 获取企业员工的组织
        Employee employee = service.findOne(userId);
        if (Objects.isNull(employee) || Objects.isNull(employee.getOrganization())){
            return ResultData.success(orgCodes);
        }
        // 获取组织机构节点清单
        List<Organization> organizations = organizationService.getParentNodes(employee.getOrganization(), true);
        return ResultData.success(organizations.stream().map(Organization::getId).collect(Collectors.toList()));
    }

    /**
     * 获取用户的岗位代码清单
     *
     * @param userId 用户Id
     * @return 岗位代码清单
     */
    @Override
    public ResultData<List<String>> getEmployeePositionCodes(String userId) {
        // 获取企业用户的岗位
        List<Position> positions = employeePositionService.getChildrenFromParentId(userId);
        return ResultData.success(positions.stream().map(Position::getCode).collect(Collectors.toList()));
    }

    /**
     * 获取用户的岗位Id清单
     *
     * @param userId 用户Id
     * @return 岗位Id清单
     */
    @Override
    public ResultData<List<String>> getEmployeePositionIds(String userId) {
        // 获取企业用户的岗位
        List<Position> positions = employeePositionService.getChildrenFromParentId(userId);
        return ResultData.success(positions.stream().map(Position::getId).collect(Collectors.toList()));
    }

    /**
     * 保存一个租户的系统管理员
     *
     * @param employeeDto 企业员工用户
     * @return 操作结果
     */
    @Override
    public ResultData<EmployeeDto> saveTenantAdmin(EmployeeDto employeeDto) {
        // 检查租户代码
        if (StringUtils.isBlank(employeeDto.getTenantCode())){
            // 保存一个租户的系统管理员，需要确定租户代码！
            return ResultData.fail(ContextUtil.getMessage("00094"));
        }
        // 设置租户的系统管理员属性
        employeeDto.setUserType(UserType.Employee);
        employeeDto.setUserAuthorityPolicy(UserAuthorityPolicy.TenantAdmin);
        employeeDto.setCreateAdmin(Boolean.TRUE);
        return super.save(employeeDto);
    }

    /**
     * 分页查询企业用户简要信息
     *
     * @param queryParam 查询参数
     * @return 企业用户简要信息
     */
    @Override
    public ResultData<PageResult<EmployeeBriefInfo>> queryEmployeeBriefInfos(EmployeeBriefInfoQueryParam queryParam) {
        return ResultData.success(service.queryEmployeeBriefInfos(queryParam));
    }



    @Override
    public ResultData updateEmployeeFormHrmsTask(Map<String, String> params) {
        LogUtil.bizLog("同步HRMS人员信息开始！");
        try{
            /*organizationService.synOrg();
            positionService.initPostion();*/
            service.initEmployee();
            employeePositionService.initUserPosition();
        }catch (Exception e){
            LogUtil.bizLog("同步HRMS人员信息出错！"+e.toString());
        }

        LogUtil.bizLog("同步HRMS人员信息结束！");
        return ResultData.success();
    }


    @Override
    public ResultData updateOrgFormHrmsTask() {
        LogUtil.bizLog("同步HRMS组织信息开始！");
        try{
            organizationService.synOrg();
        }catch (Exception e){
            LogUtil.bizLog("同步HRMS组织信息出错！"+e.toString());
        }

        LogUtil.bizLog("同步HRMS组织信息结束！");
        return ResultData.success();
    }

    @Override
    public ResultData updatePosition() {
        LogUtil.bizLog("同步岗位信息开始！");
        try{
            positionService.initPostion();
            employeePositionService.initUserPosition();
        }catch (Exception e){
            LogUtil.bizLog("同步岗位信息出错！"+e.toString());
        }

        LogUtil.bizLog("同步岗位信息结束！");
        return ResultData.success();
    }

    /**
     * 分页查询业务实体
     *
     * @param search 查询参数
     * @return 分页查询结果
     */
    @Override
    public ResultData<PageResult<EmployeeDto>> findByPage(Search search) {
        return convertToDtoPageResult(service.findByPage(search));
    }
}
