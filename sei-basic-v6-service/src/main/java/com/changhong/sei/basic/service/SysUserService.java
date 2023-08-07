package com.changhong.sei.basic.service;

import com.changhong.sei.basic.connector.HRMSConnector;
import com.changhong.sei.basic.dao.SysUserDao;
import com.changhong.sei.basic.dto.HrmsEmployeeDto;
import com.changhong.sei.basic.entity.SysUser;
import com.changhong.sei.basic.entity.User;
import com.changhong.sei.core.dao.BaseEntityDao;
import com.changhong.sei.core.service.BaseEntityService;
import com.changhong.sei.util.DateUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


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
    @Autowired
    private UserService userService;

    @Override
    protected BaseEntityDao<SysUser> getDao() {
        return dao;
    }

    @Transactional(rollbackFor = Exception.class)
    public void updateEmpTask() {
        List<HrmsEmployeeDto.DataDTO> empList = HRMSConnector.getEmp();
        List<User> userList = userService.findAllUnfrozen();

        empList.stream().forEach(emp -> {
                 LocalDateTime updateTime = emp.getUpdatetime();
               // 有更新的赋id进行update
               if(updateTime.isBefore(LocalDateTime.now().plusDays(-3))) {
                   SysUser sysUser = modelMapper.map(emp, SysUser.class);
                   userList.stream().filter(a -> a.getAccount() != null && a.getAccount().equals(emp.getEmployeeCode())).findFirst().ifPresent(user -> {
                       SysUser byEmployeeCode = dao.findByEmployeeCode(user.getAccount());
                       if(byEmployeeCode==null){
                           sysUser.setId(user.getId());
                           dao.save(sysUser, true);
                       }else{
                            sysUser.setId(byEmployeeCode.getId());
                            dao.save(sysUser, false);
                       }
                   });
               }
        });
    }

    public SysUser findByEmployeeCode(String employeeCode) {
        return dao.findByEmployeeCode(employeeCode);

    }
}
