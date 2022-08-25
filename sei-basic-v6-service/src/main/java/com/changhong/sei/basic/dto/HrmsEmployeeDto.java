package com.changhong.sei.basic.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author Carol
 * @date 2022/8/10 14:54
 * @description
 */
@NoArgsConstructor
@Data
public class HrmsEmployeeDto {

    @JsonProperty("code")
    private String code;
    @JsonProperty("data")
    private List<DataDTO> data;
    @JsonProperty("message")
    private String message;

    @NoArgsConstructor
    @Data
    public static class DataDTO {
        @JsonProperty("employee_code")
        private String employeeCode;
        @JsonProperty("employee_name")
        private String employeeName;
        @JsonProperty("sex")
        private String sex;
        @JsonProperty("hiredday")
        private String hiredday;
        @JsonProperty("ljdate")
        private String ljdate;
        @JsonProperty("empstatid")
        private String empstatid;
        @JsonProperty("orgid")
        private Integer orgid;
        @JsonProperty("orgcode")
        private String orgcode;
        @JsonProperty("orgname")
        private String orgname;
        @JsonProperty("uorgid")
        private String uorgid;
        @JsonProperty("uorgcode")
        private String uorgcode;
        @JsonProperty("uorgname")
        private String uorgname;
        @JsonProperty("hwc_namezl")
        private String hwcNamezl;
        @JsonProperty("lv_id")
        private String lvId;
        @JsonProperty("lv_num")
        private float lvNum;
        @JsonProperty("hg_id")
        private String hgId;
        @JsonProperty("hg_code")
        private String hgCode;
        @JsonProperty("hg_name")
        private String hgName;
        @JsonProperty("ospid")
        private String ospid;
        @JsonProperty("ospcode")
        private String ospcode;
        @JsonProperty("sp_name")
        private String spName;
        @JsonProperty("hwc_namezq")
        private String hwcNamezq;
        @JsonProperty("hwc_namezz")
        private String hwcNamezz;
        @JsonProperty("idpath")
        private String idpath;
        @JsonProperty("creator")
        private String creator;
        @JsonProperty("createtime")
        private String createtime;
        @JsonProperty("updator")
        private String updator;
        @JsonProperty("updatetime")
        private String updatetime;
        @JsonProperty("noclock")
        private String noclock;
        @JsonProperty("id_number")
        private String idNumber;
        @JsonProperty("telphone")
        private String telphone;
    }
}
