package com.changhong.sei.basic.dto;

import com.changhong.sei.core.dto.BaseEntityDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

/**
 * 系统用户(SysUser)DTO类
 *
 * @author sei
 * @since 2022-12-02 14:35:15
 */
@ApiModel(description = "系统用户DTO")
public class SysUserDto extends BaseEntityDto {
    private static final long serialVersionUID = -74200551188432199L;
    /**
     * 用户名
     */
    @ApiModelProperty(value = "用户名")
    private String employeeCode;
    /**
     * 邮箱
     */
    @ApiModelProperty(value = "邮箱")
    private String email;
    /**
     * 手机号
     */
    @ApiModelProperty(value = "手机号")
    private String telphone;
    /**
     * 状态  0：禁用   1：正常
     */
    @ApiModelProperty(value = "状态0：禁用1：正常")
    private String usable;
    /**
     * 创建者ID
     */
    @ApiModelProperty(value = "创建者ID")
    private String creator;
    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    private Date createtime;
    /**
     * 姓名
     */
    @ApiModelProperty(value = "姓名")
    private String employeeName;
    /**
     * 公司编码
     */
    @ApiModelProperty(value = "公司编码")
    private String orgcode;
    /**
     * 出生日期
     */
    @ApiModelProperty(value = "出生日期")
    private Date birthday;
    /**
     * 性别
     */
    @ApiModelProperty(value = "性别")
    private Integer sex;
    /**
     * 入职日期
     */
    @ApiModelProperty(value = "入职日期")
    private Date hiredday;
    /**
     * 离职日期
     */
    @ApiModelProperty(value = "离职日期")
    private Date ljdate;
    /**
     * 人员状态
     */
    @ApiModelProperty(value = "人员状态")
    private Integer empstatid;
    /**
     * 部门名称
     */
    @ApiModelProperty(value = "部门名称")
    private String orgname;
    /**
     * ID
     */
    @ApiModelProperty(value = "ID")
    private Integer erId;
    /**
     * 职务
     */
    @ApiModelProperty(value = "职务")
    private String spName;
    /**
     * 职级
     */
    @ApiModelProperty(value = "职级")
    private Double lvNum;
    /**
     * 身份证号码
     */
    @ApiModelProperty(value = "身份证号码")
    private String idNumber;


    private String idpath;


    public String getEmployeeCode() {
        return employeeCode;
    }

    public void setEmployeeCode(String employeeCode) {
        this.employeeCode = employeeCode;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public Date getHiredday() {
        return hiredday;
    }

    public void setHiredday(Date hiredday) {
        this.hiredday = hiredday;
    }

    public Date getLjdate() {
        return ljdate;
    }

    public void setLjdate(Date ljdate) {
        this.ljdate = ljdate;
    }

    public Integer getEmpstatid() {
        return empstatid;
    }

    public void setEmpstatid(Integer empstatid) {
        this.empstatid = empstatid;
    }

    public String getOrgname() {
        return orgname;
    }

    public void setOrgname(String orgname) {
        this.orgname = orgname;
    }

    public Integer getErId() {
        return erId;
    }

    public void setErId(Integer erId) {
        this.erId = erId;
    }

    public String getSpName() {
        return spName;
    }

    public void setSpName(String spName) {
        this.spName = spName;
    }

    public Double getLvNum() {
        return lvNum;
    }

    public void setLvNum(Double lvNum) {
        this.lvNum = lvNum;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    public String getTelphone() {
        return telphone;
    }

    public void setTelphone(String telphone) {
        this.telphone = telphone;
    }

    public String getUsable() {
        return usable;
    }

    public void setUsable(String usable) {
        this.usable = usable;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public String getOrgcode() {
        return orgcode;
    }

    public void setOrgcode(String orgcode) {
        this.orgcode = orgcode;
    }

    public String getIdpath() {
        return idpath;
    }

    public void setIdpath(String idpath) {
        this.idpath = idpath;
    }
}
