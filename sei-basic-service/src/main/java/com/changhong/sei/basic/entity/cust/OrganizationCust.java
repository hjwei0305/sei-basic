package com.changhong.sei.basic.entity.cust;

import com.changhong.sei.core.entity.BaseAuditableEntity;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.MappedSuperclass;

/**
 * 实现功能：
 *
 * @author 马超(Vision.Mac)
 * @version 1.0.00  2019-05-30 10:53
 */
@MappedSuperclass
@Access(AccessType.FIELD)
public class OrganizationCust extends BaseAuditableEntity {
}
