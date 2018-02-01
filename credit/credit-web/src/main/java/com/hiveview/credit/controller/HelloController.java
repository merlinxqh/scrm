package com.hiveview.credit.controller;

import com.hiveview.base.util.id.IdWorker;
import com.hiveview.base.util.response.RespMsg;
import com.hiveview.credit.entity.DemoEntity;
import com.hiveview.credit.service.DemoEntityService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Random;

/**
 * Created by leo on 2017/10/20.
 */
@RestController
@RequestMapping("/hello")
public class HelloController {

    private static final Logger log= LoggerFactory.getLogger(HelloController.class);

    @Autowired
    private DemoEntityService demoEntityService;

    @RequestMapping(value = "index")
    public RespMsg<?> index(){
        log.info("test info log text...");
        log.warn("test warn log text...");
        log.error("test error log text...");
        return RespMsg.successResp("嘿嘿测试乘公共");
    }


    @RequestMapping(value = "addData")
    public RespMsg<?> addData(String name){
        Random random=new Random();
        DemoEntity demo=new DemoEntity();
        demo.setName(name);
        demo.setCode(IdWorker.getStringCode());
        demo.setPrice(new BigDecimal(random.nextInt()));
        demo.setCreateDate(new Date());
        demo.setCreateBy("by ".concat(name));
        demo.setLastUpdateDate(new Date());
        demo.setLastUpdateBy("last update by "+name);
        demo.setDescription("hehehe");
        demoEntityService.saveData(demo);
        return RespMsg.successResp();
    }



}
