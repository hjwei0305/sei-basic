package com.changhong.sei.basic.dao.impl;

import com.changhong.sei.basic.dao.CorporationExtDao;
import com.changhong.sei.basic.entity.Corporation;
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
 * 产品扩展
 *
 * @author Vision.Mac
 * @version 1.0.1 2019/2/16 0:24
 */
public class CorporationDaoImpl extends BaseEntityDaoImpl<Corporation> implements CorporationExtDao {
    public CorporationDaoImpl(EntityManager entityManager) {
        super(Corporation.class, entityManager);
    }


    /**
     * 查询所有，根据创建日期正序排序
     *
     * @return
     */
    @Override
    public List<Corporation> findAll() {
        Sort sort = Sort.unsorted();
        if (IRank.class.isAssignableFrom(domainClass)) {
            //添加排序
            sort = sort.and(Sort.by(Sort.Direction.ASC, IRank.RANK));
        }
        //按时间正序
        sort = sort.and(Sort.by(Sort.Direction.ASC, "createdDate"));
        if (ITenant.class.isAssignableFrom(domainClass)) {
            Specification<Corporation> spec = (root, query, builder) -> {
                Path expression = root.get(ITenant.TENANT_CODE);
                return builder.equal(expression, ContextUtil.getTenantCode());
            };
            return super.findAll(spec, sort);
        } else {
            return super.findAll(sort);
        }
    }
}
