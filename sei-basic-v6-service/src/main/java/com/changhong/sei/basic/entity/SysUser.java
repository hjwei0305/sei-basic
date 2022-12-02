package com.changhong.sei.basic.entity;

import com.changhong.sei.core.entity.BaseAuditableEntity;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * 系统用户(SysUser)实体类
 *
 * @author sei
 * @since 2022-12-02 14:34:51
 */
@Entity
@Table(name = "sys_user")
@DynamicInsert
@DynamicUpdate
public class SysUser extends BaseAuditableEntity implements Serializable {
    private static final long serialVersionUID = 331366587595980987L;
    /**
     * 用户名
     */
    @Column(name = "employee_code")
    private String employeeCode;
    /**
     * 邮箱
     */
    @Column(name = "email")
    private String email;
    /**
     * 手机号
     */
    @Column(name = "telphone")
    private String telphone;
    /**
     * 状态  0：禁用   1：正常
     */
    @Column(name = "usable")
    private String usable;
    /**
     * 创建者ID
     */
    @Column(name = "creator")
    private String creator;
    /**
     * 创建时间
     */
    @Column(name = "createtime")
    private Date createtime;
    /**
     * 姓名
     */
    @Column(name = "employee_name")
    private String employeeName;
    /**
     * 公司编码
     */
    @Column(name = "orgcode")
    private String orgcode;
    /**
     * 出生日期
     */
    @Column(name = "birthday")
    private Date birthday;
    /**
     * 性别
     */
    @Column(name = "sex")
    private Integer sex;
    /**
     * 入职日期
     */
    @Column(name = "hiredday")
    private Date hiredday;
    /**
     * 离职日期
     */
    @Column(name = "ljdate")
    private Date ljdate;
    /**
     * 人员状态
     */
    @Column(name = "empstatid")
    private Integer empstatid;
    /**
     * 部门名称
     */
    @Column(name = "orgname")
    private String orgname;
    /**
     * ID
     */
    @Column(name = "er_id")
    private Integer erId;
    /**
     * 职务
     */
    @Column(name = "sp_name")
    private String spName;
    /**
     * 职级
     */
    @Column(name = "lv_num")
    private Double lvNum;
    /**
     * 身份证号码
     */
    @Column(name = "id_number")
    private String idNumber;

    @Column(name = "idpath")
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

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public String getOrgcode() {
        return orgcode;
    }

    public void setOrgcode(String orgcode) {
        this.orgcode = orgcode;
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

    public String getIdpath() {
        return idpath;
    }

    public void setIdpath(String idpath) {
        this.idpath = idpath;
    }
}
