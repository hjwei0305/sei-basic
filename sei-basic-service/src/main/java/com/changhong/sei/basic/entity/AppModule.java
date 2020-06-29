package com.changhong.sei.basic.entity;

import com.changhong.sei.core.dto.IRank;
import com.changhong.sei.core.dto.annotation.DataHistory;
import com.changhong.sei.core.dto.annotation.EnableDataHistory;
import com.changhong.sei.core.entity.BaseAuditableEntity;
import com.changhong.sei.core.entity.ICodeUnique;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

/**
 * *************************************************************************************************
 * <br>
 * 实现功能： 应用模块Entity定义
 * <br>
 * ------------------------------------------------------------------------------------------------
 * <br>
 * 版本          变更时间             变更人                     变更原因
 * <br>
 * ------------------------------------------------------------------------------------------------
 * <br>
 * 1.0.00      2017/4/19 15:52      余思豆(yusidou)                 新建
 * <br>
 * *************************************************************************************************<br>
 */
@Access(AccessType.FIELD)
@Entity
@Table(name = "app_module")
@DynamicInsert
@DynamicUpdate
@EnableDataHistory(name = "应用模块")
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class AppModule extends BaseAuditableEntity
        implements ICodeUnique, IRank {

    private static final long serialVersionUID = -2934390420466176047L;
    /**
     * 名称
     */
    @DataHistory(name = "名称")
    @Column(name = "name", length = 30, nullable = false)
    private String name;

    /**
     * 代码
     */
    @DataHistory(name = "代码")
    @Column(name = "code", length = 20, nullable = false, unique = true)
    private String code;

    /**
     * 备注
     */
    @DataHistory(name = "备注")
    @Column(name = "remark")
    private String remark;


    /**
     * web基地址
     */
    @Column(name = "web_base_address")
    private String webBaseAddress;

    /**
     * api基地址
     */
    @Column(name = "api_base_address")
    private String apiBaseAddress;

    /**
     * 排序号
     */
    @Column(name = "rank", nullable = false)

    private Integer rank;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public void setCode(String code) {
        this.code = code;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getWebBaseAddress() {
        return webBaseAddress;
    }

    public void setWebBaseAddress(String webBaseAddress) {
        this.webBaseAddress = webBaseAddress;
    }

    public String getApiBaseAddress() {
        return apiBaseAddress;
    }

    public void setApiBaseAddress(String apiBaseAddress) {
        this.apiBaseAddress = apiBaseAddress;
    }

    @Override
    public Integer getRank() {
        return rank;
    }

    public void setRank(Integer rank) {
        this.rank = rank;
    }
}
