package com.changhong.sei.basic.entity;

import com.changhong.sei.core.entity.BaseAuditableEntity;
import com.changhong.sei.core.entity.RelationEntity;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

/**
 * 实现功能：用户收藏的菜单
 */
@Entity
@Access(AccessType.FIELD)
@Table(name = "user_menu")
@DynamicUpdate
@DynamicInsert
public class UserMenu extends BaseAuditableEntity implements RelationEntity<User, Menu> {
    /**
     * 用户
     */
    @ManyToOne
    @JoinColumn(name = "user_id",nullable = false)
    private User parent;

    /**
     * 菜单
     */
    @ManyToOne
    @JoinColumn(name = "menu_id",nullable = false)
    private Menu child;

    @Override
    public User getParent() {
        return parent;
    }

    @Override
    public void setParent(User parent) {
        this.parent = parent;
    }

    @Override
    public Menu getChild() {
        return child;
    }

    @Override
    public void setChild(Menu child) {
        this.child = child;
    }
}
