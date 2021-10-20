package com.changhong.sei.basic.service.cust;

import com.changhong.sei.basic.dto.CorporationDto;
import com.changhong.sei.basic.entity.Corporation;
import org.modelmapper.PropertyMap;
import org.springframework.stereotype.Component;

/**
 * 实现功能: 公司业务逻辑扩展接口默认实现基类
 *
 * @author 王锦光 wangjg
 * @version 2020-01-26 14:34
 */
public class CorporationServiceCustBase implements CorporationServiceCust {
    /**
     * 根据公司代码查询公司后，对公司的数据处理（无事务控制）
     *
     * @param corporation 查询结果
     * @return 扩展处理结果
     */
    @Override
    public Corporation afterFindByCode(Corporation corporation) {
        // 公司业务逻辑扩展接口默认实现逻辑
        System.out.println("执行公司业务逻辑扩展接口默认实现！");
        return corporation;
    }

    /**
     * 自定义扩展实体到DTO的属性转换器
     */
    @Override
    public void customEntityToDto(Corporation entity, CorporationDto dto) {

    }
}
