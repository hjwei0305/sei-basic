package com.changhong.sei.basic.dao;

import com.changhong.sei.basic.entity.AuthorizeEntityType;

import java.util.List;

/**
 * @author 陈旭东
 * @create 2019-04-19 10:57
 **/
public interface AuthorizeEntityTypeExtDao {
    /**
     * 查询所有，根据创建日期正序排序
     *
     * @return
     */
    List<AuthorizeEntityType> findAll();
}
