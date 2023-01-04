package com.changhong.sei.basic.service;

import com.changhong.sei.basic.connector.HRMSConnector;
import com.changhong.sei.basic.dao.CorporationDao;
import com.changhong.sei.basic.dao.OrganizationDao;
import com.changhong.sei.basic.dto.OrgDTO;
import com.changhong.sei.basic.dto.OrganizationDimension;
import com.changhong.sei.basic.entity.Corporation;
import com.changhong.sei.basic.entity.Employee;
import com.changhong.sei.basic.entity.Organization;
import com.changhong.sei.basic.service.client.SerialGenerator;
import com.changhong.sei.core.context.ContextUtil;
import com.changhong.sei.core.context.SessionUser;
import com.changhong.sei.core.dao.BaseTreeDao;
import com.changhong.sei.core.entity.BaseEntity;
import com.changhong.sei.core.service.BaseTreeService;
import com.changhong.sei.core.service.DataAuthEntityService;
import com.changhong.sei.core.service.bo.OperateResult;
import com.changhong.sei.core.service.bo.OperateResultWithData;
import com.changhong.sei.core.service.bo.ResponseData;
import com.changhong.sei.enums.UserAuthorityPolicy;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * *************************************************************************************************
 * <p/>
 * 实现功能：实现组织机构的业务逻辑服务
 * <p>
 * ------------------------------------------------------------------------------------------------
 * 版本          变更时间             变更人                     变更原因
 * ------------------------------------------------------------------------------------------------
 * 1.0.00      2017/4/19 15:49        秦有宝                      新建
 * <p/>
 * *************************************************************************************************
 */
@Service
public class OrganizationService extends BaseTreeService<Organization>
        implements DataAuthEntityService {
    @Autowired
    private OrganizationDao organizationDao;
    @Autowired
    private SerialGenerator serialGenerator;
    @Autowired
    private PositionService positionService;
    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private FeatureRoleService featureRoleService;
    @Autowired
    private DataRoleService dataRoleService;
    @Autowired
    private UserService userService;
    @Autowired
    private CorporationDao corporationDao;

    @Override
    protected BaseTreeDao<Organization> getDao() {
        return organizationDao;
    }

    @Override
    @Transactional
    public OperateResultWithData<Organization> save(Organization entity) {
        if (StringUtils.isBlank(entity.getCode())) {
            entity.setCode(serialGenerator.getNumber(Organization.class));
        }
        if (!entity.isNew()) {
            //冻结，级联冻结所有子节点
            if (entity.getFrozen()) {
                List<Organization> childrenNodes = organizationDao.getChildrenNodesNoneOwn(entity.getId());
                for (Organization organization : childrenNodes) {
                    organization.setFrozen(true);
                    organizationDao.save(organization);
                }
            } else {
                if (StringUtils.isNotBlank(entity.getParentId())) {
                    //解冻，判断父节点是否冻结
                    Organization parentNode = organizationDao.findOne(entity.getParentId());
                    if (parentNode.getFrozen()) {
                        //00047 = 禁止解冻，请先解冻【{0}】的所有父节点！
                        return OperateResultWithData.operationFailure("00047", entity.getName());
                    }
                }
            }
        } else {
            //新增时，判断父节点是否冻结，是则子节点保存也需要冻结
            if (StringUtils.isNotBlank(entity.getParentId())) {
                //解冻，判断父节点是否冻结
                Organization parentNode = organizationDao.findOne(entity.getParentId());
                if (parentNode.getFrozen()) {
                    if (!entity.getFrozen()) {
                        //00047 = 禁止解冻，请先解冻【{0}】的所有父节点！
                        return OperateResultWithData.operationFailure("00069", entity.getName());
                    }
                }
            }
        }
        return super.save(entity);
    }

    /**
     * 重写移动，如果移动到已冻结父节点，父节点需要冻结
     *
     * @param nodeId
     * @param targetParentId
     * @return
     */
    @Override
    public OperateResult move(String nodeId, String targetParentId) {
        Organization parentNode = organizationDao.findOne(targetParentId);
        if (Objects.nonNull(parentNode)) {
            if (parentNode.getFrozen()) {
                //00047 = 禁止解冻，请先解冻【{0}】的所有父节点！
                return OperateResult.operationFailure("00070");
            }
        }
        return super.move(nodeId, targetParentId);
    }

    @Override
    public Organization findOne(String s) {
        if (ContextUtil.getSessionUser().getAuthorityPolicy() == UserAuthorityPolicy.GlobalAdmin) {
            Organization organization = findRootById(s);
            if (organization != null) {
                return organization;
            }
        }
        return super.findOne(s);
    }

    /**
     * 通过获取组织机构根节点
     *
     * @param id 组织机构id
     * @return 组织机构
     */
    private Organization findRootById(String id) {
        return organizationDao.findByParentIdIsNullAndId(id);
    }

    /**
     * 通过代码获取组织机构
     *
     * @param code 代码
     * @return 组织机构
     */
    public Organization findByCode(String code) {
        return organizationDao.findByCodeAndTenantCode(code, ContextUtil.getTenantCode());
    }

    /**
     * 通过获取组织机构根节点
     *
     * @param tenantCode 租户代码
     * @return 组织机构
     */
    public Organization findRootByTenantCode(String tenantCode) {
        return organizationDao.findByParentIdIsNullAndTenantCode(tenantCode);
    }

    /**
     * 获取组织机构树(不包含冻结)
     *
     * @return 组织机构树清单
     */
    public List<Organization> findOrgTreeWithoutFrozen() {
        List<Organization> orgList = organizationDao.findAllUnfrozen();
        return buildTree(orgList);
    }

    /**
     * 通过获取组织机构树
     *
     * @return 组织机构清单
     */
    public Organization findOrgTree() {
        String tenantCode = ContextUtil.getTenantCode();

        List<Organization> orgRoots = getAllRootNode();
        Optional<Organization> optional = orgRoots.stream().filter(tempRoot -> StringUtils.equals(tenantCode, tempRoot.getTenantCode())).sorted(Comparator.comparing(Organization::getCode)).findFirst();
        if (optional.isPresent()) {
            //获取树根节点
            Organization treeRoot = optional.get();
            return getTree(treeRoot.getId());
        }
        return null;
    }

    /**
     * 获取所有组织机构树
     *
     * @return 所有组织机构树
     */
    public List<Organization> findAllOrgs() {
        List<Organization> orgTree = new ArrayList<>();
        List<Organization> orgRoots = getAllRootNode();
        for (Organization orgRoot : orgRoots) {
            orgTree.add(getTree(orgRoot.getId()));
        }
        return orgTree;
    }

    /**
     * 根据指定的节点id获取树
     *
     * @param nodeId 节点ID
     * @return 返回已指定节点ID为根的树
     */
    public Organization getTree4Unfrozen(String nodeId) {
        return StringUtils.isNotBlank(nodeId) ? organizationDao.getTree4Unfrozen(nodeId) : null;
    }

    /**
     * 通过组织机构id获取组织机构清单
     *
     * @param nodeId 组织机构id
     * @return 组织机构清单（非树形）
     */
    public List<Organization> getChildrenNodes4Unfrozen(String nodeId) {
        return organizationDao.getChildrenNodes4Unfrozen(nodeId);
    }

    /**
     * 通过组织机构id清单获取下级组织机构清单
     *
     * @param nodeIds 组织机构id清单
     * @return 组织机构清单（非树形）
     */
    public List<Organization> getChildrenNodes4UnfrozenByIds(Set<String> nodeIds) {
        if (CollectionUtils.isNotEmpty(nodeIds)) {
            List<Organization> list = new ArrayList<>();
            for (String nodeId : nodeIds) {
                list.addAll(organizationDao.getChildrenNodes4Unfrozen(nodeId));
            }
            list = list.stream().distinct().collect(Collectors.toList());
            return list;
        } else {
            return new ArrayList<>();
        }
    }

    /**
     * 删除数据保存数据之前额外操作回调方法 子类根据需要覆写添加逻辑即可
     *
     * @param s 待删除数据对象主键
     */
    @Override
    protected OperateResult preDelete(String s) {
        if (positionService.isExistsByProperty("organization.id", s)) {
            //00042 = 该组织机构下存在岗位，禁止删除！
            return OperateResult.operationFailure("00042");
        }
        if (employeeService.isExistsByProperty("organization.id", s)) {
            //00043 = 该组织机构下存在企业员工，禁止删除！
            return OperateResult.operationFailure("00043");
        }
        if (featureRoleService.isExistsByProperty("publicOrg.id", s)) {
            //00044 = 该组织机构下存在功能角色，禁止删除！
            return OperateResult.operationFailure("00044");
        }
        if (dataRoleService.isExistsByProperty("publicOrg.id", s)) {
            //00045 = 该组织机构下存在数据角色，禁止删除！
            return OperateResult.operationFailure("00045");
        }
        return super.preDelete(s);
    }

    /**
     * 获取一般用户有权限的业务实体Id清单
     *
     * @param featureCode 功能项代码
     * @param userId      用户Id
     * @return 业务实体Id清单
     */
    @Override
    protected List<String> getNormalUserAuthorizedEntityIds(String featureCode, String userId) {
        Set<String> authorizedEntityIds = new HashSet<>();
        List<String> entityIds = super.getNormalUserAuthorizedEntityIds(featureCode, userId);
        if (Objects.nonNull(entityIds) && !entityIds.isEmpty()) {
            authorizedEntityIds.addAll(entityIds);
        }
        // 获取企业用户的组织机构
        Employee employee = employeeService.findOne(userId);
        if (Objects.nonNull(employee) && Objects.nonNull(employee.getOrganization())) {
            // 获取所有子节点清单(包含冻结的节点)
            List<Organization> children = organizationDao.getChildrenNodes(employee.getOrganization().getId());
            List<String> childIds = children.stream().map(BaseEntity::getId).collect(Collectors.toList());
            authorizedEntityIds.addAll(childIds);
        }
        return new ArrayList<>(authorizedEntityIds);
    }

    /**
     * 获取组织机构维度清单
     *
     * @return 组织机构维度清单
     */
    public List<OrganizationDimension> findOrganizationDimension() {
        List<OrganizationDimension> organizationDimensions = new ArrayList<>();
        OrganizationDimension organizationDimension = new OrganizationDimension();
        organizationDimension.setId("0");
        organizationDimension.setName("业务部门");
        organizationDimensions.add(organizationDimension);
        return organizationDimensions;
    }

    /**
     * 通过id集合获取组织机构清单
     *
     * @param orgIds id集合
     * @return 组织机构
     */
    public List<Organization> findOrganizationByIds(Collection<String> orgIds) {
        if (CollectionUtils.isNotEmpty(orgIds)) {
            return organizationDao.findByTenantCodeAndIdIn(ContextUtil.getTenantCode(), orgIds);
        } else {
            return null;
        }
    }


    /**
     * 从平台基础应用获取一般用户有权限的数据实体Id清单
     * 对于数据权限对象的业务实体，需要override，使用BASIC提供的通用工具来获取
     *
     * @param entityClassName 权限对象实体类型
     * @param featureCode     功能项代码
     * @param userId          用户Id
     * @return 数据实体Id清单
     */
    @Override
    public List<String> getNormalUserAuthorizedEntitiesFromBasic(String entityClassName, String featureCode, String userId) {
        return userService.getNormalUserAuthorizedEntities(entityClassName, featureCode, userId);
    }


    /**
     * 根据公司获取用户有权限的组织机构树
     *
     * @param corporationCode 公司代码
     * @return 组织机构树
     */
    public ResponseData<List<Organization>> getOrgAuthTreeByCorp(String corporationCode, String featureCode) {
        Corporation corporation = corporationDao.findByCodeAndTenantCode(corporationCode, ContextUtil.getTenantCode());
        if (Objects.isNull(corporation)) {
            //00121 = 公司【{0}】不存在，请检查！
            return ResponseData.operationFailure("00121", corporationCode);
        }
        if (StringUtils.isBlank(corporation.getOrganizationId())) {
            //00122 = 公司【{0}】对应的组织机构未配置，请检查！
            return ResponseData.operationFailure("00122", corporationCode);
        }
        List<Organization> allList = organizationDao.getChildrenNodes(corporation.getOrganizationId());
        List<Organization> resultList = Collections.emptyList();
        if (CollectionUtils.isNotEmpty(allList)) {
            //过滤掉冻结部分
            allList = allList.parallelStream().filter(i -> !i.getFrozen()).collect(Collectors.toList());
            //获取当前用户
            SessionUser sessionUser = ContextUtil.getSessionUser();
            //如果是匿名用户无数据
            if (!sessionUser.isAnonymous()) {
                UserAuthorityPolicy authorityPolicy = sessionUser.getAuthorityPolicy();
                switch (authorityPolicy) {
                    case GlobalAdmin:
                        //如果是全局管理，无数据
                        break;
                    case TenantAdmin:
                        //如果是租户管理员，返回节点下的所有未冻结数据
                        resultList = buildTree(allList);
                        break;
                    case NormalUser:
                    default:
                        //如果是一般用户，先获取有权限的角色对应的业务实体Id清单
                        List<String> entityIds = getNormalUserAuthorizedEntityIds(featureCode, sessionUser.getUserId());
                        if (CollectionUtils.isEmpty(entityIds) || CollectionUtils.isEmpty(allList)) {
                            resultList = Collections.emptyList();
                        } else {
                            List<Organization> entities = allList.parallelStream().filter((p) -> entityIds.contains(p.getId())).collect(Collectors.toList());
                            resultList = buildTree(entities);
                        }
                        break;
                }
            }
        }
        return ResponseData.operationSuccessWithData(resultList);
    }

    /**
     * 同步/更新组织信息
     */
    @Transactional(rollbackFor = Exception.class)
    public void synOrg(){
        List<Organization> allRootNode = this.getAllRootNode();
        String parentId=  allRootNode.get(0).getId();
        List<OrgDTO.DataDTO> hrmsOrgList = HRMSConnector.getOrg();
        List<Organization> organizationList = findAllUnfrozen();
        ArrayList<Organization> saveList = new ArrayList<>();
        for (OrgDTO.DataDTO dataDTO : hrmsOrgList) {
            // 对比信息
            List<Organization> pmOrganizes = organizationList.stream()
                    .filter(org -> org.getCode().equals(dataDTO.getCode()))
                    .collect(Collectors.toList());
            if(pmOrganizes.size()>0){
                // 更新
                //00000001 新宝股份
                //00011334 东菱科技
                //00016720 凯恒电机
                Organization organization  = pmOrganizes.get(0);
                if(organization.getCode().equals("00000001") ||organization.getCode().equals("00011334") ||organization.getCode().equals("00016720")){
                    organization.setParentId(parentId);
                }
                organization.setShortName(dataDTO.getOrgname());
                organization.setName(dataDTO.getExtorgname());
                organization.setFrozen(false);
                saveList.add(organization);
            }else {
                // 新增
                Organization organization = new Organization();
                organization.setShortName(dataDTO.getOrgname());
                organization.setCode(dataDTO.getCode());
                organization.setName(dataDTO.getExtorgname());
                organization.setFrozen(false);
                saveList.add(organization);
            }
        }
        //更新已经作废的组织
        for (Organization organization : organizationList) {
            int size = hrmsOrgList.stream().filter(a -> a.getCode().equals(organization.getCode())).collect(Collectors.toList()).size();
            if(size==0){
                if(!organization.getCode().equals("DONLIM")){
                    //排除根节点
                    organization.setFrozen(true);
                    saveList.add(organization);
                }

            }
        }
        save(saveList);
        saveParentId();
    }

    private void saveParentId() {
        List<Organization> allList = findAllUnfrozen();
        ArrayList<Organization> saveList = new ArrayList<>();
        allList.stream().forEach(org -> {
            if(org.getName().contains("-")){
                List<Organization> parentOrg = allList.stream()
                        .filter(allOrg -> allOrg.getName().equals(org.getName().substring(0,org.getName().lastIndexOf("-")))).collect(Collectors.toList());
                if(parentOrg.size() > 0){
                    if(org.getCode().equals("00000001") ||org.getCode().equals("00011334") ||org.getCode().equals("00016720")){

                    }else{
                        org.setParentId(parentOrg.get(0).getId());
                    }
                    saveList.add(org);
                }

            }
        });

        save(saveList);
    }

    @Transactional(rollbackFor = Exception.class)
    public void Org(){
//        List<OrgDTO.DataDTO> orgList = HRMSConnector.getOrg().subList(0,2);
        List<Organization> allList = findAll();
//        List<Organization> allList = new ArrayList<>();
        ArrayList<Organization> saveList = new ArrayList<>();
        for (Organization dataDTO : allList) {
                // 更新
                Organization organization  = dataDTO;
                if(organization.getCodePath().startsWith("|00000001|") && !organization.getCodePath().startsWith("|DONLIM|")){
                    organization.setNodeLevel(organization.getNodeLevel()+1);
                    organization.setCodePath("|DONLIM"+organization.getCodePath());
                    organization.setNamePath("/广东新宝电器股份有限公司"+organization.getNamePath());
//                    organization.setFrozen(false);
                    saveList.add(organization);
                }
            }
        save(saveList);
    }
}
