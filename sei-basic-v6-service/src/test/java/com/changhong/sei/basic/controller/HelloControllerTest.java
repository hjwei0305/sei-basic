package com.changhong.sei.basic.controller;

import com.changhong.sei.basic.service.SysUserService;
import com.changhong.sei.core.context.ContextUtil;
import com.changhong.sei.core.dto.ResultData;
import com.changhong.sei.core.test.BaseUnitTest;
import com.changhong.sei.core.util.JsonUtils;
import com.changhong.sei.utils.AsyncRunUtil;
import com.changhong.sei.util.thread.ThreadLocalHolder;
import org.apache.commons.lang3.StringUtils;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.concurrent.CompletableFuture;

/**
 * <strong>实现功能:</strong>
 * <p></p>
 *
 * @author 王锦光 wangj
 * @version 1.0.1 2020-01-17 22:27
 */
public class HelloControllerTest extends BaseUnitTest {
    @Autowired
    private HelloController controller;
    @Autowired
    private AsyncRunUtil runAsync;


    @Autowired
    private SysUserService service;
    @Test
    public void sayHello() {
        service.updateEmpTask();
      //  employeePositionController.getParentsFromChildId("57B77366-1D46-11ED-A70D-0242AC14000B");
      //  employeePositionService.initUserPosition();
      /*  for(int i=0;i<2000;i++)
        if(i%100==0){
            System.out.println("同步HRMS人员信息进行中："+i);
        }*/
/*        String name = "王锦光";
        ResultData<String> result = controller.sayHello(name);
        System.out.println(JsonUtils.toJson(result));
        LOG.debug(ContextUtil.getUserId());*/
    }

    @Test
    public void sayAsync() throws InterruptedException {
        CompletableFuture.runAsync( () -> {
            try {
                // 初始化线程变量
                ThreadLocalHolder.begin();
                // 设置当前用户Token
                mockUser.mockUser("global", "sei");
                sayHello();
            } finally {
                // 释放线程变量
                ThreadLocalHolder.end();
            }
        });
        System.out.println("异步方法执行结束！");
        Thread.sleep(10*1000);
    }

    @Test
    public void sayHelloAsync() throws InterruptedException {
        String name = "王锦光";
        runAsync.runAsync(() -> {
            ResultData result = controller.sayHello(name);
            System.out.println(JsonUtils.toJson(result));
        }, "global", "sei");
        System.out.println("异步方法执行结束！");
        Thread.sleep(10*1000);
    }

    /**
     * 测试获取系统应用配置的值
     * MQ Topic
     */
    @Test
    public void getTopic() {
        String topic = ContextUtil.getProperty("sei.datachange.topic");
        Assert.assertTrue(StringUtils.isNotBlank(topic));
    }
}
