package com.changhong.sei.basic.service;

import com.changhong.sei.basic.dao.EssEmployeePositionDao;
import com.changhong.sei.basic.entity.EssEmployeePosition;
import com.changhong.sei.core.dao.BaseEntityDao;
import com.changhong.sei.core.service.BaseEntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * (EssEmployeePosition)业务逻辑实现类
 *
 * @author sei
 * @since 2022-11-15 18:13:31
 */
@Service
public class EssEmployeePositionService extends BaseEntityService<EssEmployeePosition> {
    @Autowired
    private EssEmployeePositionDao dao;

    @Override
    protected BaseEntityDao<EssEmployeePosition> getDao() {
        return dao;
    }

}
