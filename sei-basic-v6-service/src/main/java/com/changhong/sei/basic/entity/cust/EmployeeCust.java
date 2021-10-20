package com.changhong.sei.basic.entity.cust;

import com.changhong.sei.core.entity.BaseAuditableEntity;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.MappedSuperclass;

/**
 * 企业用户扩展实体类
 */
@MappedSuperclass
@Access(AccessType.FIELD)
public class EmployeeCust extends BaseAuditableEntity {
}
