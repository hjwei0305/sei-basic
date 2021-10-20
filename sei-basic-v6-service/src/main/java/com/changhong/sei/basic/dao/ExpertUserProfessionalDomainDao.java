package com.changhong.sei.basic.dao;

import com.changhong.sei.basic.entity.ExpertUser;
import com.changhong.sei.basic.entity.ExpertUserProfessionalDomain;
import com.changhong.sei.basic.entity.ProfessionalDomain;
import com.changhong.sei.core.dao.BaseRelationDao;
import org.springframework.stereotype.Repository;

/**
 * 专家分配领域DAO
 * Author:jamson
 * date:2018/3/13
 */
@Repository
public interface ExpertUserProfessionalDomainDao extends BaseRelationDao<ExpertUserProfessionalDomain, ExpertUser, ProfessionalDomain> {
}
