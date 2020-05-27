package com.changhong.sei.basic.controller;

import com.changhong.sei.basic.api.PositionApi;
import com.changhong.sei.basic.dto.*;
import com.changhong.sei.basic.dto.search.PositionQuickQueryParam;
import com.changhong.sei.basic.entity.DataRole;
import com.changhong.sei.basic.entity.Employee;
import com.changhong.sei.basic.entity.FeatureRole;
import com.changhong.sei.basic.entity.Position;
import com.changhong.sei.basic.service.PositionService;
import com.changhong.sei.core.context.ContextUtil;
import com.changhong.sei.core.controller.DefaultBaseEntityController;
import com.changhong.sei.core.dto.ResultData;
import com.changhong.sei.core.dto.serach.PageResult;
import com.changhong.sei.core.dto.serach.Search;
import com.changhong.sei.core.dto.serach.SearchFilter;
import com.changhong.sei.core.service.BaseEntityService;
import com.changhong.sei.core.utils.ResultDataUtil;
import io.swagger.annotations.Api;
import org.apache.commons.collections.CollectionUtils;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 实现功能: 岗位API服务实现
 *
 * @author 王锦光 wangjg
 * @version 2020-01-29 10:47
 */
@RestController
@Api(value = "PositionApi", tags = "岗位API服务实现")
@RequestMapping(path = "position", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class PositionController implements DefaultBaseEntityController<Position, PositionDto>,
        PositionApi {
    @Autowired
    private PositionService service;

    @Override
    public BaseEntityService<Position> getService() {
        return service;
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
        return ResultData.success(convertToDtos(service.findByIds(ids)));
    }

    /**
     * 根据岗位类别的id来查询岗位
     *
     * @param categoryId 岗位类别id
     * @return 岗位清单
     */
    @Override
    public ResultData<List<PositionDto>> findByCategoryId(String categoryId) {
        return ResultData.success(convertToDtos(service.findByCategoryId(categoryId)));
    }

    /**
     * 根据组织机构的id获取岗位
     *
     * @param organizationId 组织机构的id
     * @return 岗位清单
     */
    @Override
    public ResultData<List<PositionDto>> findByOrganizationId(String organizationId) {
        return ResultData.success(convertToDtos(service.findByOrganizationId(organizationId)));
    }

    /**
     * 根据岗位查询参数获取获取岗位(分页)
     *
     * @param param 岗位查询参数
     * @return 岗位查询结果
     */
    @Override
    public ResultData<PageResult<PositionDto>> findByPositionQueryParam(PositionQueryParam param) {
        return convertToDtoPageResult(service.findByPositionQueryParam(param));
    }

    /**
     * 根据岗位查询参数获取获取全部岗位
     *
     * @param param 岗位查询参数
     * @return 岗位查询结果
     */
    @Override
    public ResultData<List<PositionDto>> findAllByPositionQueryParam(PositionQueryParam param) {
        return ResultData.success(convertToDtos(service.findAllByPositionQueryParam(param)));
    }

    /**
     * 根据岗位的id获取执行人
     *
     * @param positionId 岗位的id
     * @return 执行人清单
     */
    @Override
    public ResultData<List<Executor>> getExecutorsByPositionId(String positionId) {
        return ResultData.success(service.getExecutorsByPositionId(positionId));
    }

    /**
     * 根据岗位的查询参数获取执行人
     *
     * @param findParam 查询参数
     * @return 执行人清单
     */
    @Override
    public ResultData<List<Executor>> getExecutors(FindExecutorByPositionParam findParam) {
        return ResultData.success(service.getExecutors(findParam.getPositionIds(), findParam.getOrgId()));
    }

    /**
     * 根据岗位类别的id获取执行人
     *
     * @param posCateId 岗位类别的id
     * @return 执行人清单
     */
    @Override
    public ResultData<List<Executor>> getExecutorsByPosCateId(String posCateId) {
        return ResultData.success(service.getExecutorsByPosCateId(posCateId));
    }

    /**
     * 根据岗位类别的id列表获取执行人
     *
     * @param posCateIds 岗位类别的id列表
     * @return 执行人清单
     */
    @Override
    public ResultData<List<Executor>> getExecutorsByPosCateIds(List<String> posCateIds) {
        return ResultData.success(service.getExecutorsByPosCateIds(posCateIds));
    }

    /**
     * 根据组织机构ID与岗位分类IDS获取执行人，组织向上至根节点直到有企业员工
     *
     * @param findParam 岗位类别参数
     * @return 执行人清单
     */
    @Override
    public ResultData<List<Executor>> getExecutorsByPostCatAndOrgToRoot(FindExecutorByPositionCateParam findParam) {
        return ResultData.success(service.getExecutorsByPostCatAndOrgToRoot(findParam.getOrgId(), findParam.getPostCatIds()));
    }

    /**
     * 根据岗位的id获取已分配的员工
     *
     * @param positionId 岗位id
     * @return 员工列表
     */
    @Override
    public ResultData<List<EmployeeDto>> listAllAssignedEmployeesByPositionId(String positionId) {
        List<Employee> employees = service.listAllAssignedEmployeesByPositionId(positionId);
        List<EmployeeDto> dtos = employees.stream().map(EmployeeController::custConvertToDto).collect(Collectors.toList());
        return ResultData.success(dtos);
    }

    /**
     * 根据岗位的code获取已分配的员工Id
     *
     * @param positionCode 岗位code
     * @return userId列表
     */
    @Override
    public ResultData<List<String>> getUserIdsByPositionCode(String orgCode, String positionCode) {
        return service.getUserIdsByPositionCode(orgCode, positionCode);
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
        List<FeatureRole> roles = service.getCanAssignedFeatureRoles(featureRoleGroupId, positionId);
        List<FeatureRoleDto> dtos = roles.stream().map(FeatureRoleController::custConvertToDto).collect(Collectors.toList());
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
        List<DataRole> roles = service.getCanAssignedDataRoles(dataRoleGroupId, positionId);
        List<DataRoleDto> dtos = roles.stream().map(DataRoleController::custConvertToDto).collect(Collectors.toList());
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
        return ResultData.success(convertToDtos(service.findAllByOrganizationIdOrderByCode(orgId)));
    }

    /**
     * 根据用户获取所有可分配岗位
     *
     * @param param 岗位查询参数
     * @return 岗位查询结果
     */
    @Override
    public ResultData<List<PositionDto>> listAllCanAssignPositions(PositionQueryParam param) {
        return ResultData.success(convertToDtos(service.listAllCanAssignPositions(param)));
    }

    /**
     * 把一个岗位复制到多个组织机构节点上
     *
     * @param copyParam 复制参数
     * @return 操作结果
     */
    @Override
    public ResultData<?> copyToOrgNodes(PositionCopyParam copyParam) {
        return ResultDataUtil.convertFromOperateResult(service.copyToOrgNodes(copyParam));
    }

    /**
     * 分页查询岗位
     *
     * @param queryParam 查询参数
     * @return 岗位DTO
     */
    @Override
    public ResultData<PageResult<PositionDto>> queryPositions(PositionQuickQueryParam queryParam) {
        return convertToDtoPageResult(service.queryPositions(queryParam, ContextUtil.getTenantCode()));
    }

    /**
     * 将数据实体转换成DTO
     *
     * @param entity 业务实体
     * @return DTO
     */
    @Override
    public PositionDto convertToDto(Position entity) {
        return PositionController.custConvertToDto(entity);
    }

    /**
     * 自定义将数据实体转换成DTO
     *
     * @param entity 业务实体
     * @return DTO
     */
    static PositionDto custConvertToDto(Position entity) {
        if (Objects.isNull(entity)) {
            return null;
        }
        ModelMapper custMapper = new ModelMapper();
        // 创建自定义映射规则
        PropertyMap<Position, PositionDto> propertyMap = new PropertyMap<Position, PositionDto>() {
            @Override
            protected void configure() {
                // 自定义转换规则
                map().setPositionCategoryId(source.getPositionCategoryId());
                map().setOrganizationId(source.getOrganizationId());
            }
        };
        // 添加映射器
        custMapper.addMappings(propertyMap);
        // 转换
        return custMapper.map(entity, PositionDto.class);
    }

    /**
     * 分页查询业务实体
     *
     * @param search 查询参数
     * @return 分页查询结果
     */
    @Override
    public ResultData<PageResult<PositionDto>> findByPage(Search search) {
        return convertToDtoPageResult(service.findByPage(search));
    }
}
