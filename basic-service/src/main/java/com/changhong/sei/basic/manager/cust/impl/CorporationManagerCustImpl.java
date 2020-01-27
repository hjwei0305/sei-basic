package com.changhong.sei.basic.manager.cust.impl;

import com.changhong.sei.basic.entity.Corporation;
import com.changhong.sei.basic.manager.cust.CorporationManagerCustBase;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

/**
 * 实现功能: 公司业务逻辑扩展接口自定义实现
 *
 * @author 王锦光 wangjg
 * @version 2020-01-26 14:35
 */
@Component
@Primary
public class CorporationManagerCustImpl extends CorporationManagerCustBase {
    /**
     * 根据公司代码查询公司后，对公司的数据处理（无事务控制）
     *
     * @param corporation 查询结果
     * @return 扩展处理结果
     */
    @Override
    public Corporation afterFindByCode(Corporation corporation) {
        // 公司业务逻辑扩展接口自定义实现
        System.out.println("执行公司业务逻辑扩展接口自定义实现！");
        return corporation;
    }
}
