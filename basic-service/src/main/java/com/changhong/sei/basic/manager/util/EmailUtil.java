package com.changhong.sei.basic.manager.util;

import com.changhong.sei.basic.entity.Employee;
import com.changhong.sei.core.context.ContextUtil;
import com.changhong.sei.notify.api.EmailNotifyService;
import com.changhong.sei.notify.dto.EmailAccount;
import com.changhong.sei.notify.dto.EmailMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 实现功能：发送邮件工具类
 * <p/>
 *
 * @author 秦有宝
 * @version 1.0.00
 */
@EnableAsync
@Component
public class EmailUtil {
    @Autowired
    private EmailNotifyService emailNotifyService;
    /**
     *  构造注册成功后发送的邮件
     * @param employee 注册的员工用户
     * @return 电子邮件的消息
     */
    public EmailMessage constructEmailMessage(Employee employee) {
        EmailMessage message = new EmailMessage();
        message.setSubject("企业云平台账号注册成功");
        List<EmailAccount> receivers = new ArrayList<>();
        receivers.add(new EmailAccount(employee.getUserName(), employee.getEmail()));
        message.setReceivers(receivers);
        message.setContentTemplateCode("EMAIL_TEMPLATE_REGIST");
        Map<String, Object> params = new HashMap<>();
        params.put("userName", employee.getUserName());
        params.put("account", employee.getCode());
        params.put("password", "123456");
        message.setContentTemplateParams(params);
        return message;
    }

    /**
     * 注册成功后发送邮件
     *
     * @param message 注册的员工用户
     */
    @Async
    public void sendEmailNotifyUser(EmailMessage message) {
        emailNotifyService.sendEmail(message);
    }
}
