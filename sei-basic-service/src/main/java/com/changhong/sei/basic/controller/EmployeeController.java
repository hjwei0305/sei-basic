package com.changhong.sei.basic.controller;

import com.changhong.sei.basic.api.EmployeeApi;
import com.changhong.sei.basic.dto.*;
import com.changhong.sei.basic.entity.*;
import com.changhong.sei.basic.service.EmployeePositionService;
import com.changhong.sei.basic.service.EmployeeService;
import com.changhong.sei.basic.service.OrganizationService;
import com.changhong.sei.core.context.ContextUtil;
import com.changhong.sei.core.controller.DefaultBaseEntityController;
import com.changhong.sei.core.dto.ResultData;
import com.changhong.sei.core.dto.serach.PageResult;
import com.changhong.sei.core.dto.serach.QuickSearchParam;
import com.changhong.sei.core.dto.serach.Search;
import com.changhong.sei.core.service.BaseEntityService;
import com.changhong.sei.core.utils.ResultDataUtil;
import com.chonghong.sei.enums.UserAuthorityPolicy;
import com.chonghong.sei.enums.UserType;
import io.swagger.annotations.Api;
import javafx.geometry.Pos;
import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 实现功能: 企业用户API服务实现
 *
 * @author 王锦光 wangjg
 * @version 2020-01-28 0:11
 */
@RestController
@Api(value = "EmployeeService", tags = "企业用户API服务实现")
public class EmployeeController implements DefaultBaseEntityController<Employee, EmployeeDto>,
        EmployeeApi {
    @Autowired
    private EmployeeService service;
    @Autowired
    private OrganizationService organizationService;
    @Autowired
    private EmployeePositionService employeePositionService;
    @Autowired
    private ModelMapper modelMapper;
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
        List<FeatureRoleDto> dtos = roles.stream().map(FeatureRoleController::custConvertToDto).collect(Collectors.toList());
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
        List<DataRoleDto> dtos = roles.stream().map(DataRoleController::custConvertToDto).collect(Collectors.toList());
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
     * @param orgIds     组织机构Id清单
     * @param postCatIds 岗位分类Id清单
     * @return 执行人清单
     */
    @Override
    public ResultData<List<Executor>> getExecutorsByPostCatAndOrg(List<String> orgIds, List<String> postCatIds) {
        return ResultData.success(service.getExecutorsByPostCatAndOrg(orgIds, postCatIds));
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
    public ResultData copyToEmployees(EmployeeCopyParam copyParam) {
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
     * 保存一个租户的系统管理员
     *
     * @param employeeDto 企业员工用户
     * @return 操作结果
     */
    @Override
    public ResultData saveTenantAdmin(EmployeeDto employeeDto) {
        // 检查租户代码
        if (StringUtils.isBlank(employeeDto.getTenantCode())){
            // 保存一个租户的系统管理员，需要确定租户代码！
            return ResultData.fail(ContextUtil.getMessage("00094"));
        }
        // 设置租户的系统管理员属性
        employeeDto.setUserType(UserType.Employee);
        employeeDto.setUserAuthorityPolicy(UserAuthorityPolicy.TenantAdmin);
        employeeDto.setCreateAdmin(Boolean.TRUE);
        return DefaultBaseEntityController.super.save(employeeDto);
    }

    @Override
    public BaseEntityService<Employee> getService() {
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
    public Class<Employee> getEntityClass() {
        return Employee.class;
    }

    /**
     * 获取传输实体的类型
     *
     * @return 类型Class
     */
    @Override
    public Class<EmployeeDto> getDtoClass() {
        return EmployeeDto.class;
    }

    /**
     * 将数据实体转换成DTO
     *
     * @param entity 业务实体
     * @return DTO
     */
    @Override
    public EmployeeDto convertToDto(Employee entity) {
        return EmployeeController.custConvertToDto(entity);
    }

    /**
     * 将数据实体转换成DTO
     * @param entity 数据实体
     * @return DTO
     */
    static EmployeeDto custConvertToDto(Employee entity){
        if (Objects.isNull(entity)){
            return null;
        }
        ModelMapper custMapper = new ModelMapper();
        // 创建自定义映射规则
        PropertyMap<Employee, EmployeeDto> propertyMap = new PropertyMap<Employee, EmployeeDto>() {
            @Override
            protected void configure() {
                // 使用自定义转换规则
                map().setId(source.getId());
                map().setOrganizationId(source.getOrganizationId());
            }
        };
        // 添加映射器
        custMapper.addMappings(propertyMap);
        // 转换
        return custMapper.map(entity, EmployeeDto.class);
    }

    /**
     * 将DTO转换成数据实体
     *
     * @param dto 业务实体
     * @return 数据实体
     */
    @Override
    public Employee convertToEntity(EmployeeDto dto) {
        if (Objects.isNull(dto)){
            return null;
        }
        ModelMapper custMapper = new ModelMapper();
        // 创建自定义映射规则
        PropertyMap<EmployeeDto, Employee> propertyMap = new PropertyMap<EmployeeDto, Employee>() {
            @Override
            protected void configure() {
                // 使用自定义转换规则
                skip().setUser(null);
            }
        };
        // 添加映射器
        custMapper.addMappings(propertyMap);
        // 转换
        return custMapper.map(dto, Employee.class);
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
