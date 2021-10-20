package com.changhong.sei.basic.service.util;

import com.changhong.sei.basic.entity.Employee;
import com.changhong.sei.core.log.LogUtil;
import com.changhong.sei.notify.api.NotifyApi;
import com.changhong.sei.notify.dto.EmailAccount;
import com.changhong.sei.notify.dto.EmailMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 发送邮件
 */
@Component
public class EmailUtil {
    // 注入发送邮件的API服务
    @Autowired
    private NotifyApi notifyApi;
    /**
     * 构造注册成功后发送的邮件
     *
     * @param employee 注册的员工用户
     * @return 电子邮件的消息
     */
    public EmailMessage constructEmailMessage(Employee employee) {
        EmailMessage message = new EmailMessage();
        message.setSubject("SEI平台账号注册成功");
        List<EmailAccount> receivers = new ArrayList<>();
        receivers.add(new EmailAccount(employee.getUserName(), employee.getEmail()));
        message.setReceivers(receivers);
        // 设置邮件内容模板代码
        message.setContentTemplateCode("EMAIL_TEMPLATE_REGIST");
        // 设置邮件内容模板参数
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
        try {
            // 执行邮件发送
            notifyApi.sendEmail(message);
        } catch (Exception e) {
            LogUtil.error("发送邮件异常.", e);
        }
    }
}
