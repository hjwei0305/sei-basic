package com.changhong.sei.basic.manager;

import com.changhong.sei.basic.dao.CorporationDao;
import com.changhong.sei.basic.entity.Corporation;
import com.changhong.sei.basic.manager.cust.CorporationManagerCust;
import com.changhong.sei.core.context.ContextUtil;
import com.changhong.sei.core.dao.BaseEntityDao;
import com.changhong.sei.core.local.LocalUtil;
import com.changhong.sei.core.manager.BaseEntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
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
@Component
public class CorporationManager extends BaseEntityManager<Corporation> {

    @Autowired
    private CorporationDao corporationDao;

    // 注入扩展业务逻辑
    @Autowired
    private CorporationManagerCust managerCust;

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
        // 多语言
        LocalUtil.local(ContextUtil.getAppCode(), Corporation.class, corporation);
        // 执行扩展业务逻辑
        return managerCust.afterfindByCode(corporation);
    }

    /**
     * 根据ERP公司代码查询公司
     *
     * @param erpCode ERP公司代码
     * @return 公司
     */
    public List<Corporation> findByErpCode(String erpCode) {
        List<Corporation> corporations = corporationDao.findAllByErpCode(erpCode);
        // 多语言
        LocalUtil.localList(ContextUtil.getAppCode(), Corporation.class, corporations);
        return corporations;
    }
}
