package com.changhong.sei.basic.service;

import com.changhong.sei.basic.dao.ProfessionalDomainDao;
import com.changhong.sei.basic.entity.ProfessionalDomain;
import com.changhong.sei.core.context.ContextUtil;
import com.changhong.sei.core.dao.BaseTreeDao;
import com.changhong.sei.core.service.BaseTreeService;
import com.changhong.sei.core.service.bo.OperateResultWithData;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 领域业务层
 * Author:jamson
 * date:2018/3/13
 */
@Service
public class ProfessionalDomainService extends BaseTreeService<ProfessionalDomain> {
    @Autowired
    private ProfessionalDomainDao professionalDomainDao;

    @Override
    protected BaseTreeDao<ProfessionalDomain> getDao() {
        return professionalDomainDao;
    }

    /**
     * 获取整个领域树
     *
     * @return 领域树形对象集合
     */
    public List<ProfessionalDomain> getDomainTree() {
        List<ProfessionalDomain> rootTree = getAllRootNode();
        List<ProfessionalDomain> rootDomainTree = new ArrayList<>();
        for (ProfessionalDomain aRootTree : rootTree) {
            ProfessionalDomain professionalDomain = getTree(aRootTree.getId());
            rootDomainTree.add(professionalDomain);
        }
        return rootDomainTree;
    }

    /**
     * 重写保存 添加检验code是否重复
     *
     * @param entity 实体类
     */
    @Override
    public OperateResultWithData<ProfessionalDomain> save(ProfessionalDomain entity) {
        String code = entity.getCode();
        if (StringUtils.isBlank(code)) {
            // 领域代码不能为空
            return OperateResultWithData.operationFailure("00064");
        }
        ProfessionalDomain dbEntity = professionalDomainDao.findByCodeAndTenantCode(code, ContextUtil.getTenantCode());
        if (Objects.nonNull(dbEntity) && !dbEntity.getId().equals(entity.getId())) {
            // 领域代码不能重复
            return OperateResultWithData.operationFailure("00065");
        }
        return super.save(entity);
    }
}
