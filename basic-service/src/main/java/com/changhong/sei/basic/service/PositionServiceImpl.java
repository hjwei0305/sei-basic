package com.changhong.sei.basic.service;

import com.changhong.sei.basic.api.PositionService;
import com.changhong.sei.basic.dto.*;
import com.changhong.sei.basic.entity.DataRole;
import com.changhong.sei.basic.entity.Employee;
import com.changhong.sei.basic.entity.FeatureRole;
import com.changhong.sei.basic.entity.Position;
import com.changhong.sei.basic.manager.PositionManager;
import com.changhong.sei.core.dto.ResultData;
import com.changhong.sei.core.dto.serach.PageResult;
import com.changhong.sei.core.manager.BaseEntityManager;
import com.changhong.sei.core.service.DefaultBaseEntityService;
import com.changhong.sei.core.service.DefaultDataAuthEntityService;
import com.changhong.sei.core.service.DefaultFindByPageService;
import com.changhong.sei.core.utils.ResultDataUtil;
import io.swagger.annotations.Api;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 实现功能: 岗位API服务实现
 *
 * @author 王锦光 wangjg
 * @version 2020-01-29 10:47
 */
@Service
@Api(value = "PositionService", tags = "岗位API服务实现")
public class PositionServiceImpl implements DefaultBaseEntityService<Position, PositionDto>,
        DefaultFindByPageService<Position, PositionDto>,
        DefaultDataAuthEntityService<Position, PositionDto>,
        PositionService {
    @Autowired
    private PositionManager manager;
    @Autowired
    private ModelMapper modelMapper;
    @Override
    public BaseEntityManager<Position> getManager() {
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
    public Class<Position> getEntityClass() {
        return Position.class;
    }

    /**
     * 获取传输实体的类型
     *
     * @return 类型Class
     */
    @Override
    public Class<PositionDto> getDtoClass() {
        return PositionDto.class;
    }

    /**
     * 根据岗位id列表获取岗位
     *
     * @param ids
     */
    @Override
    public ResultData<List<PositionDto>> findByIds(Collection<String> ids) {
        return ResultData.success(convertToDtos(manager.findByIds(ids)));
    }

    /**
     * 根据岗位类别的id来查询岗位
     *
     * @param categoryId 岗位类别id
     * @return 岗位清单
     */
    @Override
    public ResultData<List<PositionDto>> findByCategoryId(String categoryId) {
        return ResultData.success(convertToDtos(manager.findByCategoryId(categoryId)));
    }

    /**
     * 根据组织机构的id获取岗位
     *
     * @param organizationId 组织机构的id
     * @return 岗位清单
     */
    @Override
    public ResultData<List<PositionDto>> findByOrganizationId(String organizationId) {
        return ResultData.success(convertToDtos(manager.findByOrganizationId(organizationId)));
    }

    /**
     * 根据岗位查询参数获取获取岗位(分页)
     *
     * @param param 岗位查询参数
     * @return 岗位查询结果
     */
    @Override
    public ResultData<PageResult<PositionDto>> findByPositionQueryParam(PositionQueryParam param) {
        return convertToDtoPageResult(manager.findByPositionQueryParam(param));
    }

    /**
     * 根据岗位查询参数获取获取全部岗位
     *
     * @param param 岗位查询参数
     * @return 岗位查询结果
     */
    @Override
    public ResultData<List<PositionDto>> findAllByPositionQueryParam(PositionQueryParam param) {
        return ResultData.success(convertToDtos(manager.findAllByPositionQueryParam(param)));
    }

    /**
     * 根据岗位的id获取执行人
     *
     * @param positionId 岗位的id
     * @return 执行人清单
     */
    @Override
    public ResultData<List<Executor>> getExecutorsByPositionId(String positionId) {
        return ResultData.success(manager.getExecutorsByPositionId(positionId));
    }

    /**
     * 根据岗位的id列表获取执行人
     *
     * @param positionIds 岗位的id列表
     * @return 执行人清单
     */
    @Override
    public ResultData<List<Executor>> getExecutorsByPositionIds(List<String> positionIds) {
        return ResultData.success(manager.getExecutorsByPositionIds(positionIds));
    }

    /**
     * 通过岗位ids、组织维度ids和组织机构id来获取执行人
     *
     * @param positionIds 岗位的id列表
     * @param orgDimIds   组织维度的id列表
     * @param orgId       组织机构id
     * @return 执行人清单
     */
    @Override
    public ResultData<List<Executor>> getExecutors(List<String> positionIds, List<String> orgDimIds, String orgId) {
        return ResultData.success(manager.getExecutors(positionIds, orgDimIds, orgId));
    }

    /**
     * 根据岗位类别的id获取执行人
     *
     * @param posCateId 岗位类别的id
     * @return 执行人清单
     */
    @Override
    public ResultData<List<Executor>> getExecutorsByPosCateId(String posCateId) {
        return ResultData.success(manager.getExecutorsByPosCateId(posCateId));
    }

    /**
     * 根据岗位类别的id列表获取执行人
     *
     * @param posCateIds 岗位类别的id列表
     * @return 执行人清单
     */
    @Override
    public ResultData<List<Executor>> getExecutorsByPosCateIds(List<String> posCateIds) {
        return ResultData.success(manager.getExecutorsByPosCateIds(posCateIds));
    }

    /**
     * 根据组织机构ID与岗位分类IDS获取执行人，组织向上至根节点直到有企业员工
     *
     * @param orgId      组织机构ID
     * @param postCatIds 岗位类别的id列表
     * @return 执行人清单
     */
    @Override
    public ResultData<List<Executor>> getExecutorsByPostCatAndOrgToRoot(String orgId, List<String> postCatIds) {
        return ResultData.success(manager.getExecutorsByPostCatAndOrgToRoot(orgId, postCatIds));
    }

    /**
     * 根据岗位的id获取已分配的员工
     *
     * @param positionId 岗位id
     * @return 员工列表
     */
    @Override
    public ResultData<List<EmployeeDto>> listAllAssignedEmployeesByPositionId(String positionId) {
        List<Employee> employees = manager.listAllAssignedEmployeesByPositionId(positionId);
        List<EmployeeDto> dtos = employees.stream().map(EmployeeServiceImpl::custConvertToDto).collect(Collectors.toList());
        return ResultData.success(dtos);
    }

    /**
     * 查询可分配的功能角色
     *
     * @param featureRoleGroupId 功能角色组id
     * @param positionId         岗位id
     * @return 功能角色清单
     */
    @Override
    public ResultData<List<FeatureRoleDto>> getCanAssignedFeatureRoles(String featureRoleGroupId, String positionId) {
        List<FeatureRole> roles = manager.getCanAssignedFeatureRoles(featureRoleGroupId, positionId);
        List<FeatureRoleDto> dtos = roles.stream().map(FeatureRoleServiceImpl::custConvertToDto).collect(Collectors.toList());
        return ResultData.success(dtos);
    }

    /**
     * 查询可分配的数据角色
     *
     * @param dataRoleGroupId 数据角色组id
     * @param positionId      岗位id
     * @return 数据角色清单
     */
    @Override
    public ResultData<List<DataRoleDto>> getCanAssignedDataRoles(String dataRoleGroupId, String positionId) {
        List<DataRole> roles = manager.getCanAssignedDataRoles(dataRoleGroupId, positionId);
        List<DataRoleDto> dtos = roles.stream().map(DataRoleServiceImpl::custConvertToDto).collect(Collectors.toList());
        return ResultData.success(dtos);
    }

    /**
     * 根据组织获取所有的岗位 根据岗位code排序
     *
     * @param orgId 组织Id
     * @return 岗位列表
     */
    @Override
    public ResultData<List<PositionDto>> findAllByOrganizationIdOrderByCode(String orgId) {
        return ResultData.success(convertToDtos(manager.findAllByOrganizationIdOrderByCode(orgId)));
    }

    /**
     * 根据用户获取所有可分配岗位
     *
     * @param param 岗位查询参数
     * @return 岗位查询结果
     */
    @Override
    public ResultData<List<PositionDto>> listAllCanAssignPositions(PositionQueryParam param) {
        return ResultData.success(convertToDtos(manager.listAllCanAssignPositions(param)));
    }

    /**
     * 把一个岗位复制到多个组织机构节点上
     *
     * @param copyParam 复制参数
     * @return 操作结果
     */
    @Override
    public ResultData copyToOrgNodes(PositionCopyParam copyParam) {
        return ResultDataUtil.convertFromOperateResult(manager.copyToOrgNodes(copyParam));
    }
}
