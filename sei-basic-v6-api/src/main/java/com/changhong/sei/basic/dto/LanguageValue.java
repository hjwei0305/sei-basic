package com.changhong.sei.basic.dto;

import com.changhong.sei.core.dto.BaseEntityDto;

/**
 * 实现功能: 语种的值
 *
 * @author 王锦光 wangjg
 * @version 2020-03-05 13:58
 */
public class LanguageValue extends BaseEntityDto {
    /**
     * 语种名称
     */
    private String name;

    /**
     * 语言代码
     */
    private String value;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public LanguageValue() {
    }

    public LanguageValue(String name, String value) {
        this.name = name;
        this.value = value;
    }
}
