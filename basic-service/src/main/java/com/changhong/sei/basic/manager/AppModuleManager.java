package com.changhong.sei.basic.manager;

import com.changhong.sei.basic.dao.AppModuleDao;
import com.changhong.sei.basic.entity.AppModule;
import com.changhong.sei.core.context.ContextUtil;
import com.changhong.sei.core.dao.BaseEntityDao;
import com.changhong.sei.core.manager.BaseEntityManager;
import com.changhong.sei.core.manager.bo.OperateResultWithData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * *************************************************************************************************
 * <br>
 * 实现功能：
 * 应用模块Entity定义
 * <br>
 * ------------------------------------------------------------------------------------------------
 * <br>
 * 版本          变更时间             变更人                     变更原因
 * <br>
 * ------------------------------------------------------------------------------------------------
 * <br>
 * 1.0.00     2017/4/19  19:00     余思豆(yusidou)                 新建
 * <br>
 * *************************************************************************************************<br>
 */
@Component
public class AppModuleManager extends BaseEntityManager<AppModule> {

    @Autowired
    private AppModuleDao appModuleDao;

    @Override
    protected BaseEntityDao<AppModule> getDao() {
        return appModuleDao;
    }

    /**
     * 通过代码查询应用模块
     *
     * @param code 应用模块代码
     * @return 应用模块
     */
    public AppModule findByCode(String code) {
        AppModule appModule = appModuleDao.findByCode(code);
        if (Objects.isNull(appModule)){
            return null;
        }
        appModule.setWebBaseAddress(ContextUtil.getProperty(appModule.getWebBaseAddress()));
        appModule.setApiBaseAddress(ContextUtil.getProperty(appModule.getApiBaseAddress()));
        return appModule;
    }
}
