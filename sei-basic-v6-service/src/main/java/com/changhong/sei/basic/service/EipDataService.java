package com.changhong.sei.basic.service;

import com.changhong.sei.basic.dao.EipDataDao;
import com.changhong.sei.basic.entity.EipData;
import com.changhong.sei.core.dao.BaseEntityDao;
import com.changhong.sei.core.service.BaseEntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * (EipData)业务逻辑实现类
 *
 * @author sei
 * @since 2022-08-25 18:06:21
 */
@Service
public class EipDataService extends BaseEntityService<EipData> {
    @Autowired
    private EipDataDao dao;

    @Override
    protected BaseEntityDao<EipData> getDao() {
        return dao;
    }

}
