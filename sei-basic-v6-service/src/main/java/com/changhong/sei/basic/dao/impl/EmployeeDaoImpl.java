package com.changhong.sei.basic.dao.impl;

import com.changhong.sei.basic.dao.EmployeeExtDao;
import com.changhong.sei.basic.dto.EmployeeBriefInfo;
import com.changhong.sei.basic.dto.EmployeeQueryParam;
import com.changhong.sei.basic.dto.search.EmployeeBriefInfoQueryParam;
import com.changhong.sei.basic.dto.search.EmployeeQuickQueryParam;
import com.changhong.sei.basic.entity.Employee;
import com.changhong.sei.basic.entity.Organization;
import com.changhong.sei.core.context.ContextUtil;
import com.changhong.sei.core.dao.impl.BaseEntityDaoImpl;
import com.changhong.sei.core.dao.impl.PageResultUtil;
import com.changhong.sei.core.dto.serach.PageResult;
import com.changhong.sei.core.entity.ITenant;
import com.changhong.sei.core.entity.search.QuerySql;
import com.changhong.sei.util.IdGenerator;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.*;

/**
 * *************************************************************************************************
 * <p/>
 * 实现功能：企业员工扩展接口实现
 * <p>
 * ------------------------------------------------------------------------------------------------
 * 版本          变更时间             变更人                     变更原因
 * ------------------------------------------------------------------------------------------------
 * 1.0.00      2017/5/26 13:58      秦有宝                     新建
 * <p/>
 * *************************************************************************************************
 */
public class EmployeeDaoImpl extends BaseEntityDaoImpl<Employee> implements EmployeeExtDao {

    public EmployeeDaoImpl(EntityManager entityManager) {
        super(Employee.class, entityManager);
    }

    /**
     * 保存企业员工用户
     *
     * @param entity 企业员工用户实体
     * @param isNew  是否是创建
     * @return 保存结果
     */
    @Override
    public Employee save(Employee entity, boolean isNew) {
        //是否含有业务审计属性实体
        if (entity != null) {
            Date now = new Date();
            String userId = ContextUtil.getUserId();
            String userAccount = ContextUtil.getUserAccount();
            String userName = ContextUtil.getUserName();
            if (isNew) {//创建
                entity.setCreatorId(userId);
                entity.setCreatorName(userName);
                entity.setCreatorAccount(userAccount);
                entity.setCreatedDate(now);
            }
            entity.setLastEditorId(userId);
            entity.setLastEditorName(userName);
            entity.setLastEditorAccount(userAccount);
            entity.setLastEditedDate(now);
        }
        //是否是租户实体(只是在租户代码为空时设置)
        if (entity != null && StringUtils.isBlank(((ITenant) entity).getTenantCode())) {
            //从上下文中获取租户代码
            ITenant tenant = (ITenant) entity;
            tenant.setTenantCode(ContextUtil.getTenantCode());
        }
        if (isNew) {
            entityManager.persist(entity);
            return entity;
        } else {
            return entityManager.merge(entity);
        }
    }

    /**
     * 根据查询参数获取企业员工(分页)
     *
     * @param employeeQueryParam 查询参数
     * @return 企业员工
     */
    @Override
    public PageResult<Employee> findByEmployeeParam(EmployeeQueryParam employeeQueryParam) {
        if (employeeQueryParam ==null){
            return new PageResult<>();
        }
        boolean idsNotEmpty = !CollectionUtils.isEmpty(employeeQueryParam.getIds());
        PageResult<Employee> pageResult = new PageResult<>();
        String sql = "select e from Employee e inner join User u on u.id=e.id where e.tenantCode=:tenantCode and u.frozen=false";
        if(idsNotEmpty){
            sql += " and e.id not in (:ids)";
        }
        //支持模糊搜索
        if (StringUtils.isNoneBlank(employeeQueryParam.getQuickSearchValue())){
            sql += " and ((u.userName like :quickSearchValue) or (u.account like :quickSearchValue) )";
        }
        Query query = entityManager.createQuery(sql);
        query.setParameter("tenantCode",ContextUtil.getTenantCode());
        if (StringUtils.isNoneBlank(employeeQueryParam.getQuickSearchValue())){
            query.setParameter("quickSearchValue", "%"+employeeQueryParam.getQuickSearchValue()+"%");
        }
        if(idsNotEmpty){
            query.setParameter("ids", employeeQueryParam.getIds());
        }
        //设置总条数
        int totaltRecords = query.getResultList().size();
        int totalPages = employeeQueryParam.getRows() == 0 ? 1 : (int) Math.ceil((double) totaltRecords / (double) employeeQueryParam.getRows());
        pageResult.setRecords(totaltRecords);
        pageResult.setTotal(totalPages);
        query.setFirstResult((employeeQueryParam.getPage()-1)* employeeQueryParam.getRows());
        query.setMaxResults(employeeQueryParam.getRows());
        List<Employee> datas = new ArrayList<>();
        for (Object e: query.getResultList()
             ) {
            datas.add((Employee)e);
        }
        pageResult.setPage(employeeQueryParam.getPage());
        pageResult.setRows(datas);
        return pageResult;
    }

    /**
     * 检查员工编号是否存在
     *
     * @param code 员工编号
     * @param id 实体id
     * @return 是否存在
     */
    @Override
    public Boolean isCodeExist(String code, String id) {
        if(StringUtils.isBlank(id)){
            id = IdGenerator.uuid();
        }
        String sql = "select r.id from Employee r " +
                "where r.code=:code " +
                "and r.tenantCode=:tenantCode " +
                "and r.id<>:id ";
        Query query = entityManager.createQuery(sql);
        query.setParameter("code",code);
        query.setParameter("id",id);
        query.setParameter("tenantCode",ContextUtil.getTenantCode());
        List results = query.getResultList();
        return !results.isEmpty();
    }

    /**
     * 分页查询企业用户
     *
     * @param queryParam 查询参数
     * @param organization 查询的组织节点
     * @param excludeEmployeeIds 需要排数的用户Id清单
     * @return 企业用户
     */
    @Override
    public PageResult<Employee> queryEmployees(EmployeeQuickQueryParam queryParam,
                                               Organization organization,
                                               List<String> excludeEmployeeIds) {
        String select = "select e ";
        String fromAndWhere = "from Employee e inner join Organization o " +
                "on e.organizationId = o.id ";
        Map<String, Object> sqlParams = new HashMap<>();
        String quickSearchValue = queryParam.getQuickSearchValue();
        // 判断是否包含子节点
        if (queryParam.getIncludeSubNode()) {
            String startWithCode = organization.getCodePath();
            fromAndWhere += "where o.codePath like :startWithCode ";
            sqlParams.put("startWithCode", startWithCode+"%");
        } else {
            fromAndWhere += "where o.id = :orgId ";
            sqlParams.put("orgId", organization.getId());
        }
        // 是否包含冻结的用户
        if (!queryParam.getIncludeFrozen()) {
            fromAndWhere += "and (e.user.frozen = false) ";
        }
        // 限制排除的企业用户
        if (CollectionUtils.isNotEmpty(excludeEmployeeIds)) {
            fromAndWhere += "and (e.id not in :excludeIds) ";
            sqlParams.put("excludeIds", excludeEmployeeIds);
        }
        // 限制关键字
        if (!StringUtils.isBlank(quickSearchValue)){
            fromAndWhere += "and (e.code like :quickSearchValue or e.user.userName like :quickSearchValue) ";
            sqlParams.put("quickSearchValue", "%"+quickSearchValue+"%");
        }
        QuerySql querySql = new QuerySql(select,fromAndWhere);
        // 默认排序
        if (CollectionUtils.isEmpty(queryParam.getSortOrders())) {
            querySql.setOrderBy("order by e.code");
        }
        return PageResultUtil.getResult(entityManager,querySql,sqlParams, queryParam);
    }

    /**
     * 分页查询企业用户简要信息
     *
     * @param queryParam 查询参数
     * @param tenantCode 租户代码
     * @return 企业用户简要信息
     */
    @Override
    public PageResult<EmployeeBriefInfo> queryEmployeeBriefInfos(EmployeeBriefInfoQueryParam queryParam, String tenantCode) {
        String select = "select new com.changhong.sei.basic.dto.EmployeeBriefInfo(e.id, e.code, u.userName, o.name) ";
        String fromAndWhere = "from Employee e inner join Organization o on e.organizationId = o.id " +
                "inner join User u on e.id = u.id " +
                "where e.tenantCode = :tenantCode ";
        Map<String, Object> sqlParams = new HashMap<>();
        sqlParams.put("tenantCode", tenantCode);
        String quickSearchValue = queryParam.getQuickSearchValue();
        // 是否包含冻结的用户
        if (!queryParam.getIncludeFrozen()) {
            fromAndWhere += "and (u.frozen = false) ";
        }
        // 限制关键字
        if (!StringUtils.isBlank(quickSearchValue)){
            fromAndWhere += "and (e.code like :quickSearchValue or u.userName like :quickSearchValue) ";
            sqlParams.put("quickSearchValue", "%"+quickSearchValue+"%");
        }
        QuerySql querySql = new QuerySql(select,fromAndWhere);
        // 默认排序
        if (CollectionUtils.isEmpty(queryParam.getSortOrders())) {
            querySql.setOrderBy("order by e.code");
        }
        return PageResultUtil.getResult(entityManager,querySql,sqlParams, queryParam);
    }
}

