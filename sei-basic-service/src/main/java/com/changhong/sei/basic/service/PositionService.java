package com.changhong.sei.basic.service;

import com.changhong.sei.basic.dao.OrganizationDao;
import com.changhong.sei.basic.dao.PositionDao;
import com.changhong.sei.basic.dto.Executor;
import com.changhong.sei.basic.dto.PositionCopyParam;
import com.changhong.sei.basic.dto.PositionQueryParam;
import com.changhong.sei.basic.entity.*;
import com.changhong.sei.basic.service.client.NumberGenerator;
import com.changhong.sei.core.dao.BaseEntityDao;
import com.changhong.sei.core.dto.serach.PageResult;
import com.changhong.sei.core.dto.serach.Search;
import com.changhong.sei.core.dto.serach.SearchFilter;
import com.changhong.sei.core.service.BaseEntityService;
import com.changhong.sei.core.service.bo.OperateResult;
import com.changhong.sei.core.service.bo.OperateResultWithData;
import com.changhong.sei.core.util.JsonUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * *************************************************************************************************
 * <p/>
 * 实现功能：岗位的业务逻辑服务
 * <p>
 * ------------------------------------------------------------------------------------------------
 * 版本          变更时间                  变更人                 变更原因
 * ------------------------------------------------------------------------------------------------
 * 1.0.00      2017/4/17 23:03            高银军                  新建
 * <p/>
 * *************************************************************************************************
 */
@Service
public class PositionService extends BaseEntityService<Position> {
    @Autowired
    private PositionDao positionDao;
    @Autowired
    private OrganizationDao organizationDao;
    @Autowired
    private OrganizationService organizationService;
    @Autowired
    private EmployeePositionService employeePositionService;
    @Autowired
    private FeatureRoleService featureRoleService;
    @Autowired
    private DataRoleService dataRoleService;
    @Autowired
    private PositionFeatureRoleService positionFeatureRoleService;
    @Autowired
    private PositionDataRoleService positionDataRoleService;
    @Autowired
    private NumberGenerator numberGenerator;

    @Override
    protected BaseEntityDao<Position> getDao() {
        return positionDao;
    }

    /**
     * 数据保存操作
     *
     * @param entity 岗位
     */
    @Override
    public OperateResultWithData<Position> save(Position entity) {
        //检查同一部门下的岗位名称是否存在
        if (positionDao.isOrgAndNameExist(entity.getOrganization().getId(), entity.getName(), entity.getId())) {
            //00048= 该组织机构下的岗位【{0}】已存在，请重新输入！
            return OperateResultWithData.operationFailure("00048", entity.getName());
        }
        if (StringUtils.isBlank(entity.getCode())) {
            entity.setCode(numberGenerator.getNumber(Position.class));
        }
        return super.save(entity);
    }

    /**
     * 删除数据保存数据之前额外操作回调方法 子类根据需要覆写添加逻辑即可
     *
     * @param s 待删除数据对象主键
     */
    @Override
    protected OperateResult preDelete(String s) {
        if (positionDataRoleService.isExistByParent(s)) {
            //00039 = 岗位存在已经分配的数据角色，禁止删除！
            return OperateResult.operationFailure("00039");
        }
        if (positionFeatureRoleService.isExistByParent(s)) {
            //岗位存在已经分配的功能角色，禁止删除！
            return OperateResult.operationFailure("00013");
        }
        if (employeePositionService.isExistByChild(s)) {
            //岗位存在已经分配的企业员工用户，禁止删除！
            return OperateResult.operationFailure("00017");
        }
        return super.preDelete(s);
    }

    /**
     * 根据岗位类别的id来查询岗位
     *
     * @param categoryId 岗位类别id
     * @return 岗位清单
     */
    public List<Position> findByCategoryId(String categoryId) {
        return positionDao.findByPositionCategoryId(categoryId);
    }

    /**
     * 根据组织机构的id获取岗位
     *
     * @param organizationId 组织机构的id
     * @return 岗位清单
     */
    public List<Position> findByOrganizationId(String organizationId) {
        return positionDao.findByOrganizationId(organizationId);
    }

    /**
     * 根据岗位查询参数获取获取岗位(分页)
     *
     * @param param 岗位查询参数
     * @return 岗位查询结果
     */
    public PageResult<Position> findByPositionQueryParam(PositionQueryParam param) {
        Search search = new Search(param);
        if (param.getIncludeSubNode()) {
            List<Organization> orgs = organizationService.getChildrenNodes4Unfrozen(param.getOrganizationId());
            List<String> orgIds = new ArrayList<>();
            orgs.forEach((r) -> orgIds.add(r.getId()));
            search.addFilter(new SearchFilter("organization.id", orgIds, SearchFilter.Operator.IN));
        } else {
            search.addFilter(new SearchFilter("organization.id", param.getOrganizationId(), SearchFilter.Operator.EQ));
        }
        return findByPage(search);
    }

    /**
     * 根据岗位查询参数获取获取全部岗位
     *
     * @param param
     * @return List<Position>
     */
    public List<Position> findAllByPositionQueryParam(PositionQueryParam param) {
        Search search = new Search(param);
        if (param.getIncludeSubNode()) {
            List<Organization> orgs = organizationService.getChildrenNodes4Unfrozen(param.getOrganizationId());
            List<String> orgIds = new ArrayList<>();
            orgs.forEach((r) -> orgIds.add(r.getId()));
            search.addFilter(new SearchFilter("organization.id", orgIds, SearchFilter.Operator.IN));
        } else {
            search.addFilter(new SearchFilter("organization.id", param.getOrganizationId(), SearchFilter.Operator.EQ));
        }
        return findByFilters(search);
    }

    /**
     * 根据岗位的id获取已分配的员工
     *
     * @param positionId 岗位的id
     * @return 员工清单
     */
    public List<Employee> listAllAssignedEmployeesByPositionId(String positionId) {
        List<Employee> employees = employeePositionService.getParentsFromChildId(positionId);
        for (Employee employee : employees) {
            employee.setUserName(employee.getUser().getUserName());
        }
        return employees;
    }

    /**
     * 查询可分配的功能角色
     *
     * @param featureRoleGroupId 功能角色组id
     * @param positionId         岗位id
     * @return 功能角色清单
     */
    public List<FeatureRole> getCanAssignedFeatureRoles(String featureRoleGroupId, String positionId) {
        Set<FeatureRole> result = new HashSet<>();
        //获取可分配的功能角色
        List<FeatureRole> canAssigned = featureRoleService.findByFeatureRoleGroup(featureRoleGroupId);
        //获取已经分配的功能角色
        List<FeatureRole> assigned = positionFeatureRoleService.getChildrenFromParentId(positionId);
        result.addAll(canAssigned);
        result.removeAll(assigned);
        return new ArrayList<>(result);
    }

    /**
     * 查询可分配的数据角色
     *
     * @param dataRoleGroupId 数据角色组id
     * @param positionId      岗位id
     * @return 数据角色清单
     */
    public List<DataRole> getCanAssignedDataRoles(String dataRoleGroupId, String positionId) {
        Set<DataRole> dataRoles = new HashSet<>();
        //获取可分配的数据角色
        List<DataRole> canAssigned = dataRoleService.findByDataRoleGroup(dataRoleGroupId);
        //获取已经分配的数据角色
        List<DataRole> assigned = positionDataRoleService.getChildrenFromParentId(positionId);
        dataRoles.addAll(canAssigned);
        dataRoles.removeAll(assigned);
        return new ArrayList<>(dataRoles);
    }

    /**
     * 根据用户获取所有可分配岗位
     *
     * @param param 岗位查询参数
     * @return
     */
    public List<Position> listAllCanAssignPositions(PositionQueryParam param) {
        //获取已经分配的岗位列表
        String userId = param.getUserId();
        List<Position> assignedPositions = employeePositionService.getChildrenFromParentId(userId);
        Set<String> assignedPositionIds = assignedPositions.stream().map(Position::getId).collect(Collectors.toSet());
        //是否包含子节点
        Search search = new Search(param);
        if (param.getIncludeSubNode()) {
            List<Organization> orgs = organizationDao.getChildrenNodes4Unfrozen(param.getOrganizationId());
            List<String> orgIds = new ArrayList<>();
            //添加当前节点组织id，避免没有子节点下查询全部组织的id
            orgIds.add(param.getOrganizationId());
            orgs.forEach((r) -> orgIds.add(r.getId()));
            search.addFilter(new SearchFilter("organization.id", orgIds, SearchFilter.Operator.IN));
        } else {
            search.addFilter(new SearchFilter("organization.id", param.getOrganizationId(), SearchFilter.Operator.EQ));
        }
        //获取组织下所有岗位列表
        List<Position> allPositions = findByFilters(search);
        //去除掉已经分配的
        List<Position> result = allPositions.stream().filter(e -> !assignedPositionIds.contains(e.getId())).collect(Collectors.toList());
        return result;
    }

    /**
     * 把一个岗位复制到多个组织机构节点上
     *
     * @param copyParam 复制参数
     * @return 操作结果
     */
    public OperateResult copyToOrgNodes(PositionCopyParam copyParam) {
        // 获取源岗位
        Position position = positionDao.findOne(copyParam.getPositionId());
        if (Objects.isNull(position)){
            // 岗位【{0}】不存在！
            return OperateResult.operationFailure("00090", copyParam.getPositionId());
        }
        AtomicInteger atomicInteger = new AtomicInteger(0);
        List<String> orgIds = copyParam.getTargetOrgIds();
        if (CollectionUtils.isEmpty(orgIds)){
            // 岗位【{0}-{1}】成功复制到【{2}】个组织机构节点！
            return OperateResult.operationSuccess("00091", position.getCode(), position.getName(), atomicInteger.intValue());
        }
        // 循环复制岗位
        for (String orgId: orgIds){
            Position targetPosition = copyPosition(position, orgId, copyParam.getCopyFeatureRole());
            if (Objects.nonNull(targetPosition)){
                atomicInteger.incrementAndGet();
            }
        }
        // 岗位【{0}-{1}】成功复制到【{2}】个组织机构节点！
        return OperateResult.operationSuccess("00091", position.getCode(), position.getName(), atomicInteger.intValue());
    }

    /**
     * 复制一个组织机构到指定的组织机构节点
     * @param position 源岗位
     * @param orgId 组织结构节点Id
     * @param copyFeatureRole 需要复制功能角色
     * @return 复制的目标岗位
     */
    private Position copyPosition(Position position, String orgId, boolean copyFeatureRole){
        // 如果组织机构Id相同，直接跳过
        String sourceOrgId = position.getOrganization().getId();
        if (StringUtils.equals(orgId, sourceOrgId)){
            return null;
        }
        // 通过名称获取组织机构已经存在的岗位
        List<Position> samePositions = positionDao.findByOrganizationIdAndName(orgId, position.getName());
        if (CollectionUtils.isNotEmpty(samePositions)){
            return null;
        }
        // 克隆一个岗位,并初始Id
        Position copyPosition = JsonUtils.cloneByJson(position);
        copyPosition.setId(null);
        copyPosition.setCode(null);
        copyPosition.setOrganizationId(orgId);
        OperateResultWithData<Position> saveResult = save(copyPosition);
        if (saveResult.notSuccessful()){
            return null;
        }
        Position targetPosition = saveResult.getData();
        // 复制功能角色
        if (copyFeatureRole){
            List<FeatureRole> roles = positionFeatureRoleService.getChildrenFromParentId(position.getId());
            if (CollectionUtils.isNotEmpty(roles)){
                List<String> roleIds = roles.stream().map(FeatureRole::getId).collect(Collectors.toList());
                positionFeatureRoleService.insertRelations(targetPosition.getId(), roleIds);
            }
        }
        return targetPosition;
    }

    /**
     * 根据岗位的id获取执行人
     *
     * @param positionId 岗位的id
     * @return 执行人清单
     */
    public List<Executor> getExecutorsByPositionId(String positionId) {
        Position position = findOne(positionId);
        if (Objects.isNull(position)) {
            return new ArrayList<>();
        }
        List<Employee> employees = employeePositionService.getParentsFromChildId(positionId);
        return constructExecutors(employees, position);

    }


    /**
     * 根据岗位的id列表获取执行人
     *
     * @param positionIds 岗位的id列表
     * @return 执行人清单
     */
    public List<Executor> getExecutorsByPositionIds(List<String> positionIds) {
        List<Executor> executors = new ArrayList<>();
        for (String positionId : positionIds) {
            executors.addAll(getExecutorsByPositionId(positionId));
        }
        //去除重复执行人
        Set<Executor> executorSet = new HashSet<>(executors);
        return new ArrayList<>(executorSet);
    }

    /**
     * 通过岗位ids、组织维度ids和组织机构id来获取执行人
     *
     * @param positionIds 岗位的id列表
     * @param orgDimIds   组织维度的id列表
     * @param orgId       组织机构id
     * @return 执行人清单
     */
    public List<Executor> getExecutors(List<String> positionIds, List<String> orgDimIds, String orgId) {
        List<Executor> executors = getExecutorsByPositionIds(positionIds).stream().filter(r -> r.getOrganizationId().equals(orgId)).collect(Collectors.toList());
        return executors;
    }

    /**
     * 根据岗位类别的id获取执行人
     *
     * @param posCateId 岗位类别的id
     * @return 执行人清单
     */
    public List<Executor> getExecutorsByPosCateId(String posCateId) {
        List<Position> positions = positionDao.findByPositionCategoryId(posCateId);
        List<String> ids = new ArrayList<>();
        positions.forEach((r) -> {
            String id = r.getId();
            ids.add(id);
        });
        return getExecutorsByPositionIds(ids);
    }

    /**
     * 根据岗位类别的id列表获取执行人
     *
     * @param posCateIds 岗位类别的id列表
     * @return 执行人清单
     */
    public List<Executor> getExecutorsByPosCateIds(List<String> posCateIds) {
        if (Objects.isNull(posCateIds) || posCateIds.isEmpty()) {
            return Collections.emptyList();
        }
        SearchFilter filter = new SearchFilter(Position.POSITION_CATEGORY_ID, posCateIds, SearchFilter.Operator.IN);
        List<Position> positions = findByFilter(filter);
        if (Objects.isNull(positions)) {
            return new ArrayList<>();
        }
        List<String> ids = new ArrayList<>();
        positions.forEach((r) -> {
            String id = r.getId();
            ids.add(id);
        });
        return getExecutorsByPositionIds(ids);
    }

    /**
     * 根据组织机构ID与岗位分类IDS获取执行人，组织向上至根节点直到有企业员工
     *
     * @param orgId      组织机构ID
     * @param postCatIds 岗位类别的id列表
     * @return
     */
    public List<Executor> getExecutorsByPostCatAndOrgToRoot(String orgId, List<String> postCatIds) {
        if (Objects.isNull(orgId) || orgId.isEmpty() || Objects.isNull(postCatIds) || postCatIds.isEmpty()) {
            return Collections.emptyList();
        }
        List<Executor> es = new ArrayList<>();
        //1.根据岗位类别筛选出所有岗位
        List<Position> posts = findByFilter(new SearchFilter(Position.POSITION_CATEGORY_ID, postCatIds, SearchFilter.Operator.IN));
        if (Objects.nonNull(posts) && !posts.isEmpty()) {
            Organization organization = organizationService.findOne(orgId);
            //2.循环组织向上查找
            List<EmployeePosition> employeePositions = getEmployeesByPostionsAndOrgsToRoot(organization, posts);
            if (Objects.nonNull(employeePositions) && !employeePositions.isEmpty()) {
                es = construstExecutor(employeePositions);
            }
        }
        return es;
    }

    /**
     * 根据岗位ids以及组织(递归至根组织直到有企业员工)查找企业员工
     *
     * @param organization
     * @param posts
     * @return
     */
    private List<EmployeePosition> getEmployeesByPostionsAndOrgsToRoot(Organization organization, List<Position> posts) {
        if (Objects.isNull(organization)) {
            return Collections.emptyList();
        }
        List<EmployeePosition> employeePositions = null;
        //冻结的组织直接继续向上找组织
        if (!organization.getFrozen()) {
            //1.筛查指定组织的岗位
            List<Position> resultPositions = posts.stream().filter(e -> organization.getId().contains(e.getOrganization().getId())).collect(Collectors.toList());
            if (!resultPositions.isEmpty()) {
                //2.根据指定岗位寻找企业员工
                Set<String> resultPositionIds = resultPositions.stream().map(Position::getId).collect(Collectors.toSet());
                SearchFilter filter = new SearchFilter(EmployeePosition.CHILD_FIELD + ".id", new ArrayList<>(resultPositionIds), SearchFilter.Operator.IN);
                employeePositions = employeePositionService.findByFilter(filter);
            }
        }
        //筛选掉冻结的用户的信息
        if (Objects.nonNull(employeePositions)) {
            employeePositions = employeePositions.stream().filter(e -> e.getParent().getUser().getFrozen().equals(Boolean.FALSE)).collect(Collectors.toList());
        }
        //为空则查找父组织递归，直到组织父节点为根节点
        if (Objects.isNull(employeePositions) || employeePositions.isEmpty()) {
            String parentId = organization.getParentId();
            //判断是否已经到了根节点
            if (StringUtils.isNotBlank(parentId)) {
                Organization parentOrganization = organizationService.findOne(organization.getParentId());
                employeePositions = getEmployeesByPostionsAndOrgsToRoot(parentOrganization, posts);
            }
        }
        return employeePositions;
    }

    /**
     * 通过企业用户构造流程任务执行人
     *
     * @param employeePositions 企业员工用户分配岗位列表
     */
    private List<Executor> construstExecutor(List<EmployeePosition> employeePositions) {
        List<Executor> executors = new ArrayList<>();
        for (EmployeePosition employeePosition : employeePositions) {
            Employee employee = employeePosition.getParent();
            //排除冻结的用户
            if (employee.getUser().getFrozen()) {
                continue;
            }
            Executor executor = new Executor();
            executor.setId(employee.getId());
            executor.setCode(employee.getCode());
            executor.setName(employee.getUserName());
            //设置组织机构
            Organization organization = employee.getOrganization();
            if (Objects.nonNull(organization)) {
                executor.setOrganizationId(organization.getId());
                executor.setOrganizationCode(organization.getCode());
                executor.setOrganizationName(organization.getName());
                executor.setRemark(organization.getName());
            }
            //设置岗位
            Position position = employeePosition.getChild();
            executor.setPositionId(position.getId());
            executor.setPositionName(position.getName());
            executor.setPositionCode(position.getCode());
            executor.setRemark(position.getOrganization().getName() + "-" + position.getName());
            executors.add(executor);
        }
        return executors;
    }

    /**
     * 根据组织获取所有的岗位 根据岗位code排序
     *
     * @param orgId 组织Id
     * @return 岗位列表
     */
    public List<Position> findAllByOrganizationIdOrderByCode(String orgId) {
        if (Objects.isNull(orgId) || orgId.isEmpty()) {
            return Collections.emptyList();
        }
        return positionDao.findAllByOrganizationIdOrderByCode(orgId);
    }

    /**
     * 通过员工与岗位构造执行人
     *
     * @param employees
     * @param position
     * @return
     */
    private List<Executor> constructExecutors(List<Employee> employees, Position position) {
        List<Executor> executors = new ArrayList<>();
        for (Employee r : employees) {
            //排除冻结的用户
            if (r.getUser().getFrozen()) {
                continue;
            }
            Executor executor = new Executor();
            executor.setId(r.getId());
            executor.setCode(r.getCode());
            executor.setName(r.getUser().getUserName());
            executor.setOrganizationId(r.getOrganization().getId());
            executor.setOrganizationName(r.getOrganization().getName());
            executor.setOrganizationCode(r.getOrganization().getCode());
            executor.setPositionId(position.getId());
            executor.setPositionName(position.getName());
            executor.setPositionCode(position.getCode());
            executor.setRemark(r.getOrganization().getName() + "-" + position.getName());
            executors.add(executor);
        }
        return executors;
    }
}
