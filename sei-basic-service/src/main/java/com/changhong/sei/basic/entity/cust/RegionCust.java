package com.changhong.sei.basic.entity.cust;

import com.changhong.sei.core.entity.BaseAuditableEntity;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.MappedSuperclass;

/**
 * 行政区域扩展实体类
 *
 * @author Vision.Mac
 * @version 1.0.1 2019/2/15 23:35
 */
@MappedSuperclass
@Access(AccessType.FIELD)
public class RegionCust extends BaseAuditableEntity {

}
