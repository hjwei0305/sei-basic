package com.changhong.sei.basic.service;

import com.changhong.sei.basic.dao.AppModuleDao;
import com.changhong.sei.basic.dao.FeatureGroupDao;
import com.changhong.sei.basic.entity.AppModule;
import com.changhong.sei.core.context.ContextUtil;
import com.changhong.sei.core.dao.BaseEntityDao;
import com.changhong.sei.core.service.BaseEntityService;
import com.changhong.sei.core.service.bo.OperateResult;
import org.springframework.beans.factory.annotation.Autowired;
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
@Service
public class AppModuleService extends BaseEntityService<AppModule> {

    @Autowired
    private AppModuleDao appModuleDao;
    @Autowired
    private FeatureGroupDao featureGroupDao;
    @Autowired
    private TenantAppModuleService tenantAppModuleService;

    @Override
    protected BaseEntityDao<AppModule> getDao() {
        return appModuleDao;
    }

    /**
     * 删除数据保存数据之前额外操作回调方法 子类根据需要覆写添加逻辑即可
     *
     * @param s 待删除数据对象主键
     */
    @Override
    protected OperateResult preDelete(String s) {
        if (featureGroupDao.isExistsByProperty("appModule.id", s)) {
            //00018 = 该应用模块下存在功能项组，禁止删除！
            return OperateResult.operationFailure("00018");
        }
        if (tenantAppModuleService.isExistsByProperty("child.id", s)) {
            //该应用模块已分配给租户，禁止删除！
            return OperateResult.operationFailure("00046");
        }
        return super.preDelete(s);
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
