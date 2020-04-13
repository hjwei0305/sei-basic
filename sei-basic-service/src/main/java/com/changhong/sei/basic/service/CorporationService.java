package com.changhong.sei.basic.service;

import com.changhong.sei.basic.dao.CorporationDao;
import com.changhong.sei.basic.entity.Corporation;
import com.changhong.sei.basic.service.cust.CorporationServiceCust;
import com.changhong.sei.core.context.ContextUtil;
import com.changhong.sei.core.dao.BaseEntityDao;
import com.changhong.sei.core.service.BaseEntityService;
import com.changhong.sei.core.service.DataAuthEntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * *************************************************************************************************
 * <br>
 * 实现功能：公司业务逻辑实现
 * <br>
 * ------------------------------------------------------------------------------------------------
 * <br>
 * 版本          变更时间             变更人                     变更原因
 * <br>
 * ------------------------------------------------------------------------------------------------
 * <br>
 * 1.0.00      2017/6/2 17:26    余思豆(yusidou)                 新建
 * <br>
 * *************************************************************************************************<br>
 */
@Service
public class CorporationService extends BaseEntityService<Corporation> implements DataAuthEntityService {

    @Autowired
    private CorporationDao corporationDao;
    @Autowired
    private UserService userService;

    // 注入扩展业务逻辑
    @Autowired
    private CorporationServiceCust serviceCust;

    @Override
    protected BaseEntityDao<Corporation> getDao() {
        return corporationDao;
    }

    /**
     * 根据公司代码查询公司
     *
     * @param code 公司代码
     * @return 公司
     */
    public Corporation findByCode(String code) {
        Corporation corporation = corporationDao.findByCode(code);
        // 执行扩展业务逻辑
        return serviceCust.afterFindByCode(corporation);
    }

    /**
     * 根据ERP公司代码查询公司
     *
     * @param erpCode ERP公司代码
     * @return 公司
     */
    public List<Corporation> findByErpCode(String erpCode) {
        return corporationDao.findAllByErpCode(erpCode);
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
}
