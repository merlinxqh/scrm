package com.hiveview.admin;

import com.hiveview.credit.api.DemoService;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by leo on 2017/10/20.
 */
public class Consumer {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext context=new ClassPathXmlApplicationContext(new String[]{"dubbo-demo-consumer.xml"});
        context.start();
        DemoService domo= (DemoService) context.getBean("demoService");
        String name = domo.sayHello("world");
        System.out.println(name);
    }
}
