package com.changhong.sei.basic.service;

import com.changhong.sei.basic.connector.HRMSConnector;
import com.changhong.sei.basic.dao.SysUserDao;
import com.changhong.sei.basic.dto.HrmsEmployeeDto;
import com.changhong.sei.basic.entity.SysUser;
import com.changhong.sei.core.dao.BaseEntityDao;
import com.changhong.sei.core.service.BaseEntityService;
import com.changhong.sei.util.DateUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


/**
 * 系统用户(SysUser)业务逻辑实现类
 *
 * @author sei
 * @since 2022-12-02 14:34:52
 */
@Service
public class SysUserService extends BaseEntityService<SysUser> {
    @Autowired
    private SysUserDao dao;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    protected BaseEntityDao<SysUser> getDao() {
        return dao;
    }

    @Transactional(rollbackFor = Exception.class)
    public void updateEmpTask() {
        List<HrmsEmployeeDto.DataDTO> empList = HRMSConnector.getEmp();
        List<SysUser> userList = new ArrayList<>();
        empList.stream().forEach(emp -> {
            LocalDate updateTime = LocalDate.parse(emp.getUpdatetime(), DateTimeFormatter.ofPattern(DateUtils.DEFAULT_TIME_FORMAT));
            // 有更新的赋id进行update
            if(updateTime.format(DateTimeFormatter.ofPattern(DateUtils.DEFAULT_DATE_FORMAT)).compareTo(LocalDate.now().toString()) >= 0) {
                SysUser sysUser = modelMapper.map(emp, SysUser.class);
                SysUser employeeCode = findByProperty("employeeCode", emp.getEmployeeCode());
                if(employeeCode != null){
                    sysUser.setId(employeeCode.getId());
                }
                userList.add(sysUser);
            }
        });
        save(userList);
    }

    public SysUser findByEmployeeCode(String employeeCode) {
        return dao.findByEmployeeCode(employeeCode);

    }
}
