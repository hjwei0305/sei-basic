package com.changhong.sei.basic.dao.impl;

import com.changhong.sei.basic.dao.PositionExtDao;
import com.changhong.sei.basic.dto.search.PositionQuickQueryParam;
import com.changhong.sei.basic.entity.Organization;
import com.changhong.sei.basic.entity.Position;
import com.changhong.sei.core.dao.impl.BaseEntityDaoImpl;
import com.changhong.sei.core.dao.impl.PageResultUtil;
import com.changhong.sei.core.dto.serach.PageResult;
import com.changhong.sei.core.dto.serach.Search;
import com.changhong.sei.core.dto.serach.SearchFilter;
import com.changhong.sei.core.entity.search.QuerySql;
import com.changhong.sei.util.IdGenerator;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.EntityManager;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * *************************************************************************************************
 * <p/>
 * 实现功能：岗位扩展接口实现
 * <p>
 * ------------------------------------------------------------------------------------------------
 * 版本          变更时间             变更人                     变更原因
 * ------------------------------------------------------------------------------------------------
 * 1.0.00      2017/6/30 13:48      秦有宝                     新建
 * <p/>
 * *************************************************************************************************
 */
public class PositionDaoImpl extends BaseEntityDaoImpl<Position> implements PositionExtDao {

    public PositionDaoImpl(EntityManager entityManager) {
        super(Position.class, entityManager);
    }

    /**
     * 检查同一部门下的岗位名称是否存在
     *
     * @param organizationId 组织机构的id
     * @param name           岗位名称
     * @param id             实体id
     * @return 是否存在
     */
    @Override
    public Boolean isOrgAndNameExist(String organizationId, String name, String id) {
        if(StringUtils.isBlank(id)){
            id = IdGenerator.uuid();
        }
        Search search = new Search();
        search.addFilter(new SearchFilter("organization.id", organizationId, SearchFilter.Operator.EQ));
        search.addFilter(new SearchFilter("name", name, SearchFilter.Operator.EQ));
        search.addFilter(new SearchFilter("id", id, SearchFilter.Operator.NE));
        List results = findByFilters(search);
        return !results.isEmpty();
    }

    /**
     * 分页查询岗位
     *
     * @param queryParam 查询参数
     * @param excludeIds 排除的岗位Id清单
     * @param tenantCode 租户代码
     * @param organization 组织机构
     * @return 岗位
     */
    @Override
    public PageResult<Position> queryPositions(PositionQuickQueryParam queryParam, List<String> excludeIds, String tenantCode, Organization organization) {
        String select = "select p ";
        String fromAndWhere = "from Position p where p.tenantCode=:tenantCode ";
        Map<String, Object> sqlParams = new HashMap<>();
        sqlParams.put("tenantCode", tenantCode);
        String quickSearchValue = queryParam.getQuickSearchValue();
        // 判断组织机构是否包含子节点
        if (Objects.nonNull(organization)) {
            if (queryParam.getIncludeSubNode()) {
                String startWithCode = organization.getCodePath();
                fromAndWhere += "and p.organization.codePath like :startWithCode ";
                sqlParams.put("startWithCode", startWithCode+"%");
            } else {
                fromAndWhere += "and p.organizationId = :orgId ";
                sqlParams.put("orgId", organization.getId());
            }
        }
        // 限制排除的岗位
        if (CollectionUtils.isNotEmpty(excludeIds)) {
            fromAndWhere += "and (p.id not in :excludeIds) ";
            sqlParams.put("excludeIds", excludeIds);
        }
        // 限制关键字
        if (!StringUtils.isBlank(quickSearchValue)){
            fromAndWhere += "and (p.code like :quickSearchValue or p.name like :quickSearchValue) ";
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