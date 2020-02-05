package com.changhong.sei.basic.dao;

import com.changhong.sei.basic.entity.Corporation;
import com.changhong.sei.core.dao.BaseEntityDao;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * *************************************************************************************************
 * <br>
 * 实现功能：公司的数据访问
 * <br>
 * ------------------------------------------------------------------------------------------------
 * <br>
 * 版本          变更时间             变更人                     变更原因
 * <br>
 * ------------------------------------------------------------------------------------------------
 * <br>
 * 1.0.00      2017/6/2 17:22    余思豆(yusidou)                 新建
 * <br>
 * *************************************************************************************************<br>
 */
@Repository
public interface CorporationDao extends BaseEntityDao<Corporation>, CorporationExtDao {

    /**
     * 根据公司代码查询公司
     *
     * @param code 公司代码
     * @return 公司
     */
    Corporation findByCode(String code);

    /**
     * 根据ERP公司代码查询公司
     *
     * @param erpCode ERP公司代码
     * @return 公司
     */
    List<Corporation> findAllByErpCode(String erpCode);
}
