package com.changhong.sei.basic.manager;

import com.changhong.sei.basic.dao.AuthorizeEntityTypeDao;
import com.changhong.sei.basic.entity.AuthorizeEntityType;
import com.changhong.sei.core.dao.BaseEntityDao;
import com.changhong.sei.core.manager.BaseEntityManager;
import com.changhong.sei.core.manager.bo.OperateResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * *************************************************************************************************
 * <p/>
 * 实现功能：权限对象类型业务逻辑实现
 * <p>
 * ------------------------------------------------------------------------------------------------
 * 版本          变更时间             变更人                     变更原因
 * ------------------------------------------------------------------------------------------------
 * 1.0.00      2017-05-04 14:04      王锦光(wangj)                新建
 * <p/>
 * *************************************************************************************************
 */
@Component
public class AuthorizeEntityTypeManager extends BaseEntityManager<AuthorizeEntityType> {
    @Autowired
    private AuthorizeEntityTypeDao dao;
    @Autowired
    private DataAuthorizeTypeManager dataAuthorizeTypeManager;
    @Override
    protected BaseEntityDao<AuthorizeEntityType> getDao() {
        return dao;
    }

    /**
     * 删除数据保存数据之前额外操作回调方法 子类根据需要覆写添加逻辑即可
     *
     * @param s 待删除数据对象主键
     */
    @Override
    protected OperateResult preDelete(String s) {
        //检查是否存在数据权限类型
        if (dataAuthorizeTypeManager.isExistsByProperty("authorizeEntityType.id",s)){
            //权限对象类型存在数据权限类型，禁止删除！
            return OperateResult.operationFailure("00019");
        }
        return super.preDelete(s);
    }
}
