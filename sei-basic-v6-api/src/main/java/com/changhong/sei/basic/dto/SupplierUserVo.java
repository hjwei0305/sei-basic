package com.changhong.sei.basic.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.List;

/**
 * 供应商用户VO
 * Author:jamson
 * date:2018/3/19
 */
@ApiModel(description = "供应商用户VO")
public class SupplierUserVo implements Serializable {
    @ApiModelProperty(value = "Id标识")
    private String id;
    /**
     * 代码
     */
    @ApiModelProperty(value = "代码(max = 30)", required = true)
    private String code;
    /**
     * 名称
     */
    @ApiModelProperty(value = "名称(max = 100)", required = true)
    private String name;
    /**
     * 供应商id
     */
    @ApiModelProperty(value = "供应商id(max = 36)")
    private String supplierId;
    /**
     * 申请注册供应商id
     */
    @ApiModelProperty(value = "申请注册供应商id(max = 36)")
    private String supplierApplyId;
    /**
     * 供应商代码
     */
    @ApiModelProperty(value = "供应商代码(max = 30)")
    private String supplierCode;
    /**
     * 供应商名称
     */
    @ApiModelProperty(value = "供应商名称(max = 100)")
    private String supplierName;
    /**
     * 邮箱
     */
    @ApiModelProperty(value = "邮箱")
    private String email;
    /**
     * 性别
     */
    @ApiModelProperty(value = "性别")
    private Boolean gender = true;
    /**
     * 手机
     */
    @ApiModelProperty(value = "手机")
    private String mobile;
    /**
     * 电话
     */
    @ApiModelProperty(value = "电话")
    private String telephone;
    /**
     * 地址
     */
    @ApiModelProperty(value = "地址")
    private String mailingAddress;
    /**
     * 证件号码
     */
    @ApiModelProperty(value = "证件号码")
    private String idNumber;
    /**
     * 业务办理委托书附件
     */
    @ApiModelProperty(value = "业务办理委托书附件")
    private List<String> attachments;
    /**
     * 用户密码(加密后)
     */
    @ApiModelProperty(value = "用户密码(加密后)")
    private String password;

    /**
     * 是否冻结
     */
    @ApiModelProperty(value = "是否冻结")
    private boolean frozen;
    /**
     * 是否创建供应商管理员
     */
    private boolean isCreateSupplierManager = false;
    /**
     * 是否编辑供应商管理员
     */
    @ApiModelProperty(value = "是否编辑供应商管理员")
    private boolean isEditSupplier = false;

    public boolean isEditSupplier() {
        return isEditSupplier;
    }

    public void setEditSupplier(boolean editSupplier) {
        isEditSupplier = editSupplier;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(String supplierId) {
        this.supplierId = supplierId;
    }

    public String getSupplierCode() {
        return supplierCode;
    }

    public void setSupplierCode(String supplierCode) {
        this.supplierCode = supplierCode;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public boolean getFrozen() {
        return frozen;
    }

    public void setFrozen(boolean frozen) {
        this.frozen = frozen;
    }

    public boolean isCreateSupplierManager() {
        return isCreateSupplierManager;
    }

    public void setCreateSupplierManager(boolean createSupplierManager) {
        isCreateSupplierManager = createSupplierManager;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Boolean getGender() {
        return gender;
    }

    public void setGender(Boolean gender) {
        this.gender = gender;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getMailingAddress() {
        return mailingAddress;
    }

    public void setMailingAddress(String mailingAddress) {
        this.mailingAddress = mailingAddress;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    public List<String> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<String> attachments) {
        this.attachments = attachments;
    }

    public boolean isFrozen() {
        return frozen;
    }

    public String getSupplierApplyId() {
        return supplierApplyId;
    }

    public void setSupplierApplyId(String supplierApplyId) {
        this.supplierApplyId = supplierApplyId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
