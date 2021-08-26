package com.changhong.sei.basic.dao.impl;

import com.changhong.sei.basic.dao.EmployeeExtDao;
import com.changhong.sei.basic.dao.UserExtDao;
import com.changhong.sei.basic.dto.EmployeeQueryParam;
import com.changhong.sei.basic.dto.search.EmployeeQuickQueryParam;
import com.changhong.sei.basic.dto.search.UserQuickQueryParam;
import com.changhong.sei.basic.entity.Employee;
import com.changhong.sei.basic.entity.Organization;
import com.changhong.sei.basic.entity.User;
import com.changhong.sei.core.context.ContextUtil;
import com.changhong.sei.core.dao.impl.BaseEntityDaoImpl;
import com.changhong.sei.core.dao.impl.PageResultUtil;
import com.changhong.sei.core.dto.serach.PageResult;
import com.changhong.sei.core.entity.ITenant;
import com.changhong.sei.core.entity.search.QuerySql;
import com.changhong.sei.util.IdGenerator;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import javax.jws.soap.SOAPBinding;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.*;

/**
 * *************************************************************************************************
 * <p/>
 * 实现功能：用户扩展接口实现
 * <p>
 * ------------------------------------------------------------------------------------------------
 * 版本          变更时间             变更人                     变更原因
 * ------------------------------------------------------------------------------------------------
 * 1.0.00      2017/5/26 13:58      秦有宝                     新建
 * <p/>
 * *************************************************************************************************
 */
public class UserDaoImpl extends BaseEntityDaoImpl<User> implements UserExtDao {

    public UserDaoImpl(EntityManager entityManager) {
        super(User.class, entityManager);
    }
    /**
     * 分页查询用户
     *
     * @param queryParam 查询参数
     * @param excludeUserIds 排除的用户Id清单
     * @param tenantCode 租户代码
     * @return 用户
     */
    @Override
    public PageResult<User> queryUsers(UserQuickQueryParam queryParam, List<String> excludeUserIds, String tenantCode) {
        String select = "select u ";
        String fromAndWhere = "from User u where u.frozen=false and u.tenantCode=:tenantCode ";
        Map<String, Object> sqlParams = new HashMap<>();
        sqlParams.put("tenantCode", tenantCode);
        String quickSearchValue = queryParam.getQuickSearchValue();
        // 限制排除的用户
        if (CollectionUtils.isNotEmpty(excludeUserIds)) {
            fromAndWhere += "and (u.id not in :excludeIds) ";
            sqlParams.put("excludeIds", excludeUserIds);
        }
        // 限制关键字
        if (!StringUtils.isBlank(quickSearchValue)){
            fromAndWhere += "and (u.userName like :quickSearchValue or u.account like :quickSearchValue) ";
            sqlParams.put("quickSearchValue", "%"+quickSearchValue+"%");
        }
        QuerySql querySql = new QuerySql(select,fromAndWhere);
        if (CollectionUtils.isEmpty(queryParam.getSortOrders())) {
            querySql.setOrderBy("order by u.userName");
        }
        return PageResultUtil.getResult(entityManager,querySql,sqlParams, queryParam);
    }

    /**
     * 检查用户主账号是否存在
     *
     * @param account 员工编号
     * @param id      实体id
     * @return 是否存在
     */
    @Override
    public Boolean isAccountExist(String account, String id) {
        if(StringUtils.isBlank(id)){
            id = IdGenerator.uuid();
        }
        String sql = "select r.id from User r " +
                "where r.account is not null " +
                "and r.account=:account " +
                "and r.tenantCode=:tenantCode " +
                "and r.id<>:id ";
        Query query = entityManager.createQuery(sql);
        query.setParameter("account", account);
        query.setParameter("id",id);
        query.setParameter("tenantCode",ContextUtil.getTenantCode());
        List results = query.getResultList();
        return !results.isEmpty();
    }
}

