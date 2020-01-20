package com.changhong.sei.basic.dao.impl;

import com.changhong.sei.basic.dao.AuthorizeEntityTypeExtDao;
import com.changhong.sei.basic.entity.AuthorizeEntityType;
import com.changhong.sei.core.context.ContextUtil;
import com.changhong.sei.core.dao.impl.BaseEntityDaoImpl;
import com.changhong.sei.core.dto.IRank;
import com.changhong.sei.core.entity.ITenant;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.EntityManager;
import javax.persistence.criteria.Path;
import java.util.List;

/**
 * 重新实现查询所有方法，根据创建日期正序排序
 *
 * @author 陈旭东
 * @create 2019-04-19 10:57
 **/
public class AuthorizeEntityTypeDaoImpl extends BaseEntityDaoImpl<AuthorizeEntityType> implements AuthorizeEntityTypeExtDao {


    public AuthorizeEntityTypeDaoImpl(EntityManager entityManager) {
        super(AuthorizeEntityType.class, entityManager);
    }


    /**
     * 基于主键集合查询集合数据对象,根据创建日期正序
     */
    @Override
    public List<AuthorizeEntityType> findAll() {
        //按时间正序
        Sort sort = Sort.by(Sort.Direction.ASC,"createdDate");

        //BaseDaoImpl原始代码
        if (IRank.class.isAssignableFrom(domainClass)) {
            //添加排序
            sort = sort.and(Sort.by(Sort.Direction.ASC, IRank.RANK));
        }
        if (ITenant.class.isAssignableFrom(domainClass)) {
            Specification<AuthorizeEntityType> spec = (root, query, builder) -> {
                Path expression = root.get(ITenant.TENANT_CODE);
                return builder.equal(expression, ContextUtil.getTenantCode());
            };
            return super.findAll(spec, sort);
        } else {
            return super.findAll(sort);
        }
    }
}
