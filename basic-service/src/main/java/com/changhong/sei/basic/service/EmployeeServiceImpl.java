package com.changhong.sei.basic.service;

import com.changhong.sei.basic.api.EmployeeService;
import com.changhong.sei.basic.dto.*;
import com.changhong.sei.basic.entity.DataRole;
import com.changhong.sei.basic.entity.Employee;
import com.changhong.sei.basic.entity.FeatureRole;
import com.changhong.sei.basic.manager.EmployeeManager;
import com.changhong.sei.core.dto.ResultData;
import com.changhong.sei.core.dto.serach.PageResult;
import com.changhong.sei.core.dto.serach.QuickSearchParam;
import com.changhong.sei.core.dto.serach.Search;
import com.changhong.sei.core.manager.BaseEntityManager;
import com.changhong.sei.core.service.DefaultBaseEntityService;
import com.changhong.sei.core.service.DefaultFindByPageService;
import com.changhong.sei.core.utils.ResultDataUtil;
import io.swagger.annotations.Api;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 实现功能: 企业用户API服务实现
 *
 * @author 王锦光 wangjg
 * @version 2020-01-28 0:11
 */
@Service
@Api(value = "EmployeeService", tags = "企业用户API服务实现")
public class EmployeeServiceImpl implements DefaultBaseEntityService<Employee, EmployeeDto>,
        DefaultFindByPageService<Employee, EmployeeDto>,
        EmployeeService {
    @Autowired
    private EmployeeManager manager;
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
        PageResult<Employee> result = manager.findByEmployeeParam(employeeQueryParam);
        return convertToDtoPageResult(result);
    }

    /**
     * 根据员工的id列表获取员工
     *
     * @param ids 主键集合
     */
    @Override
    public ResultData<List<EmployeeDto>> findByIds(List<String> ids) {
        return ResultData.success(convertToDtos(manager.findByIds(ids)));
    }

    /**
     * 根据组合条件获取员工
     *
     * @param searchConfig 查询参数
     */
    @Override
    public ResultData<List<EmployeeDto>> findByFilters(Search searchConfig) {
        return ResultData.success(convertToDtos(manager.findByFilters(searchConfig)));
    }

    /**
     * 根据组织机构的id获取员工
     *
     * @param organizationId 组织机构的id
     * @return 员工用户查询结果
     */
    @Override
    public ResultData<List<EmployeeDto>> findByOrganizationId(String organizationId) {
        return ResultData.success(convertToDtos(manager.findByOrganizationId(organizationId)));
    }

    /**
     * 根据组织机构的id获取员工(不包含冻结)
     *
     * @param organizationId 组织机构的id
     * @return 员工清单
     */
    @Override
    public ResultData<List<EmployeeDto>> findByOrganizationIdWithoutFrozen(String organizationId) {
        return ResultData.success(convertToDtos(manager.findByOrganizationIdWithoutFrozen(organizationId)));
    }

    /**
     * 根据企业员工用户查询参数获取企业员工用户
     *
     * @param param 企业员工用户查询参数
     * @return 员工用户查询结果
     */
    @Override
    public ResultData<PageResult<EmployeeDto>> findByUserQueryParam(UserQueryParam param) {
        PageResult<Employee> result = manager.findByUserQueryParam(param);
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
        return ResultData.success(manager.getExecutorsByEmployeeIds(employeeIds));
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
        List<FeatureRole> roles = manager.getCanAssignedFeatureRoles(featureRoleGroupId, userId);
        List<FeatureRoleDto> dtos = roles.stream().map(EmployeeServiceImpl::convertToDto).collect(Collectors.toList());
        return ResultData.success(dtos);
    }

    /**
     * 转换功能角色数据实体为DTO
     * @param entity 功能角色数据实体
     * @return 功能角色DTO
     */
    private static FeatureRoleDto convertToDto(FeatureRole entity){
        if (Objects.isNull(entity)){
            return null;
        }
        ModelMapper custMapper = new ModelMapper();
        // 创建自定义映射规则
        PropertyMap<FeatureRole, FeatureRoleDto> propertyMap = new PropertyMap<FeatureRole, FeatureRoleDto>() {
            @Override
            protected void configure() {
                // 使用自定义转换规则
                map().setFeatureGroupId(source.getFeatureGroupId());
                map().setPublicOrgId(source.getPublicOrgId());
            }
        };
        // 添加映射器
        custMapper.addMappings(propertyMap);
        // 转换
        return custMapper.map(entity, FeatureRoleDto.class);
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
        List<DataRole> roles = manager.getCanAssignedDataRoles(dataRoleGroupId, userId);
        List<DataRoleDto> dtos = roles.stream().map(DataRoleServiceImpl::custConvertToDto).collect(Collectors.toList());
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
        return ResultData.success(convertToDto(manager.findAdminByTenantCode(tenantCode)));
    }

    /**
     * 通过员工编号获取员工
     *
     * @param code 员工编号
     * @return 员工
     */
    @Override
    public ResultData<EmployeeDto> findByCode(String code) {
        return ResultData.success(convertToDto(manager.findByCode(code)));
    }

    /**
     * 快速查询企业用户
     *
     * @param param 快速查询参数
     * @return 企业用户查询结果
     */
    @Override
    public ResultData<PageResult<EmployeeDto>> quickSearch(QuickSearchParam param) {
        return convertToDtoPageResult(manager.quickSearch(param));
    }

    /**
     * 快速查询企业用户作为流程执行人
     *
     * @param param 快速查询参数
     * @return 企业用户查询结果
     */
    @Override
    public ResultData<PageResult<Executor>> quickSearchExecutors(QuickSearchParam param) {
        return ResultData.success(manager.quickSearchExecutors(param));
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
        return ResultData.success(manager.getExecutorsByPostCatAndOrg(orgIds, postCatIds));
    }

    /**
     * 根据岗位id和员工用户查询参数获取所有可分配企业员工用户
     *
     * @param param 员工用户查询参数
     * @return 员工用户查询结果
     */
    @Override
    public ResultData<List<EmployeeDto>> listAllCanAssignEmployees(UserQueryParam param) {
        return ResultData.success(convertToDtos(manager.listAllCanAssignEmployees(param)));
    }

    /**
     * 通过用户id获取员工
     *
     * @param userId 用户id
     * @return 员工
     */
    @Override
    public ResultData<EmployeeDto> findByUserId(String userId) {
        ResultData<Employee> resultData = manager.findByUserId(userId);
        if (resultData.isFailed()){
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
        return ResultDataUtil.convertFromOperateResult(manager.copyToEmployees(copyParam));
    }

    @Override
    public BaseEntityManager<Employee> getManager() {
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
        return EmployeeServiceImpl.custConvertToDto(entity);
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
}
