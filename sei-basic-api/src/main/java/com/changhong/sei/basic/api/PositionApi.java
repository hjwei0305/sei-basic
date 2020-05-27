package com.changhong.sei.basic.api;

import com.changhong.sei.basic.dto.*;
import com.changhong.sei.basic.dto.search.PositionQuickQueryParam;
import com.changhong.sei.core.api.BaseEntityApi;
import com.changhong.sei.core.api.FindByPageApi;
import com.changhong.sei.core.dto.ResultData;
import com.changhong.sei.core.dto.serach.PageResult;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Collection;
import java.util.List;

/**
 * 实现功能: 岗位API接口
 *
 * @author 王锦光 wangjg
 * @version 2020-01-29 10:27
 */
@FeignClient(name = "sei-basic", path = "position")
public interface PositionApi extends BaseEntityApi<PositionDto>,
        FindByPageApi<PositionDto> {
    /**
     * 根据岗位id列表获取岗位
     */
    @PostMapping(path = "findByIds", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "根据岗位id列表获取岗位", notes = "根据岗位id列表获取岗位")
    ResultData<List<PositionDto>> findByIds(@RequestBody Collection<String> ids);

    /**
     * 根据岗位类别的id来查询岗位
     *
     * @param categoryId 岗位类别id
     * @return 岗位清单
     */
    @GetMapping(path = "findByCategoryId")
    @ApiOperation(value = "根据岗位类别的id来查询岗位", notes = "根据岗位类别的id来查询岗位")
    ResultData<List<PositionDto>> findByCategoryId(@RequestParam("categoryId") String categoryId);

    /**
     * 根据组织机构的id获取岗位
     *
     * @param organizationId 组织机构的id
     * @return 岗位清单
     */
    @GetMapping(path = "findByOrganizationId")
    @ApiOperation(value = "根据组织机构的id获取岗位", notes = "根据组织机构的id获取岗位")
    ResultData<List<PositionDto>> findByOrganizationId(@RequestParam("organizationId") String organizationId);

    /**
     * 根据岗位查询参数获取获取岗位(分页)
     *
     * @param param 岗位查询参数
     * @return 岗位查询结果
     */
    @PostMapping(path = "findByPositionQueryParam", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "根据岗位查询参数获取岗位", notes = "根据岗位查询参数获取获取岗位")
    ResultData<PageResult<PositionDto>> findByPositionQueryParam(@RequestBody PositionQueryParam param);

    /**
     * 根据岗位查询参数获取获取全部岗位
     *
     * @param param 岗位查询参数
     * @return 岗位查询结果
     */
    @PostMapping(path = "findAllByPositionQueryParam", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "根据岗位查询参数获取获取全部岗位", notes = "根据岗位查询参数获取获取全部岗位")
    ResultData<List<PositionDto>> findAllByPositionQueryParam(@RequestBody PositionQueryParam param);

    /**
     * 根据岗位的id获取执行人
     *
     * @param positionId 岗位的id
     * @return 执行人清单
     */
    @GetMapping(path = "getExecutorsByPositionId")
    @ApiOperation(value = "根据岗位的id获取执行人", notes = "根据岗位的id获取执行人")
    ResultData<List<Executor>> getExecutorsByPositionId(@RequestParam("positionId") String positionId);

    /**
     * 根据岗位的查询参数获取执行人
     *
     * @param findParam 查询参数
     * @return 执行人清单
     */
    @PostMapping(path = "getExecutorsByPositionIds", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "根据岗位的id列表获取执行人", notes = "根据岗位的id列表获取执行人")
    ResultData<List<Executor>> getExecutors(@RequestBody FindExecutorByPositionParam findParam);

    /**
     * 根据岗位类别的id获取执行人
     *
     * @param posCateId 岗位类别的id
     * @return 执行人清单
     */
    @GetMapping(path = "getExecutorsByPosCateId")
    @ApiOperation(value = "根据岗位类别的id获取执行人", notes = "根据岗位类别的id获取执行人")
    ResultData<List<Executor>> getExecutorsByPosCateId(@RequestParam("posCateId") String posCateId);

    /**
     * 根据岗位类别的id列表获取执行人
     *
     * @param posCateIds 岗位类别的id列表
     * @return 执行人清单
     */
    @PostMapping(path = "getExecutorsByPosCateIds", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "根据岗位类别的id列表获取执行人", notes = "根据岗位类别的id列表获取执行人")
    ResultData<List<Executor>> getExecutorsByPosCateIds(@RequestBody List<String> posCateIds);

    /**
     * 根据组织机构ID与岗位分类IDS获取执行人，组织向上至根节点直到有企业员工
     *
     * @param findParam 岗位类别参数
     * @return 执行人清单
     */
    @PostMapping(path = "getExecutorsByPostCatAndOrgToRoot", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "根据组织机构IDS与岗位分类IDS获取执行人，组织向上至根节点直到有企业员工", notes = "根据组织机构IDS与岗位分类IDS获取执行人，组织向上至根节点直到有企业员工")
    ResultData<List<Executor>> getExecutorsByPostCatAndOrgToRoot(@RequestBody FindExecutorByPositionCateParam findParam);

    /**
     * 根据岗位的id获取已分配的员工
     *
     * @param positionId 岗位id
     * @return 员工列表
     */
    @GetMapping(path = "listAllAssignedEmployeesByPositionId")
    @ApiOperation(value = "根据岗位的id获取已分配的员工", notes = "根据岗位的id获取已分配的员工")
    ResultData<List<EmployeeDto>> listAllAssignedEmployeesByPositionId(@RequestParam("positionId") String positionId);

    /**
     * 根据岗位的code获取已分配的员工Id
     *
     * @param orgCode      组织code
     * @param positionCode 岗位code
     * @return userId列表
     */
    @GetMapping(path = "getUserIdsByPositionCode")
    @ApiOperation(value = "根据岗位的code获取已分配的员工Id", notes = "根据岗位的code获取已分配的员工Id")
    ResultData<List<String>> getUserIdsByPositionCode(@RequestParam("orgCode") String orgCode, @RequestParam("positionCode") String positionCode);

    /**
     * 查询可分配的功能角色
     *
     * @param featureRoleGroupId 功能角色组id
     * @param positionId         岗位id
     * @return 功能角色清单
     */
    @GetMapping(path = "getCanAssignedFeatureRoles")
    @ApiOperation(value = "根据功能角色组的id与岗位id获取可分配的功能角色", notes = "根据功能角色组的id与岗位id获取可分配的功能角色")
    ResultData<List<FeatureRoleDto>> getCanAssignedFeatureRoles(@RequestParam("featureRoleGroupId") String featureRoleGroupId, @RequestParam("positionId") String positionId);

    /**
     * 查询可分配的数据角色
     *
     * @param dataRoleGroupId 数据角色组id
     * @param positionId      岗位id
     * @return 数据角色清单
     */
    @GetMapping(path = "getCanAssignedDataRoles")
    @ApiOperation(value = "根据数据角色组的id与岗位id获取可分配的数据角色", notes = "根据数据角色组的id与岗位id获取可分配的数据角色")
    ResultData<List<DataRoleDto>> getCanAssignedDataRoles(@RequestParam("dataRoleGroupId") String dataRoleGroupId, @RequestParam("positionId") String positionId);

    /**
     * 根据组织获取所有的岗位 根据岗位code排序
     *
     * @param orgId 组织Id
     * @return 岗位列表
     */
    @GetMapping(path = "findAllByOrganizationIdOrderByCode")
    @ApiOperation(notes = "根据组织获取所有的岗位 根据岗位code排序", value = "根据组织获取所有的岗位 根据岗位code排序")
    ResultData<List<PositionDto>> findAllByOrganizationIdOrderByCode(@RequestParam("orgId") String orgId);

    /**
     * 根据用户获取所有可分配岗位
     *
     * @param param 岗位查询参数
     * @return 岗位查询结果
     */
    @PostMapping(path = "listAllCanAssignPositions", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "根据用户获取所有可分配岗位", notes = "根据用户获取所有可分配岗位")
    ResultData<List<PositionDto>> listAllCanAssignPositions(@RequestBody PositionQueryParam param);

    /**
     * 把一个岗位复制到多个组织机构节点上
     *
     * @param copyParam 复制参数
     * @return 操作结果
     */
    @PostMapping(path = "copyToOrgNodes", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "把一个岗位复制到多个组织机构节点上", notes = "实现快速配置岗位，把一个岗位复制到多个组织机构节点上，可以复制功能角色")
    ResultData<?> copyToOrgNodes(@RequestBody PositionCopyParam copyParam);

    /**
     * 分页查询岗位
     *
     * @param queryParam 查询参数
     * @return 岗位DTO
     */
    @PostMapping(path = "queryPositions", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "分页查询岗位", notes = "通过快速查询参数，分页查询岗位")
    ResultData<PageResult<PositionDto>> queryPositions(@RequestBody PositionQuickQueryParam queryParam);
}
