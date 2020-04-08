package com.changhong.sei.basic.entity.cust;

import com.changhong.sei.core.entity.BaseEntity;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.MappedSuperclass;

/**
 * 用户扩展实体类
 */
@MappedSuperclass
@Access(AccessType.FIELD)
public class UserCust extends BaseEntity {
}
