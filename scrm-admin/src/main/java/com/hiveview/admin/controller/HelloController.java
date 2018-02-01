package com.hiveview.admin.controller;

import com.hiveview.admin.rpc.DemoApiService;
import com.hiveview.base.util.response.RespMsg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by leo on 2017/10/23.
 */
@RestController
public class HelloController {

    @Autowired
    private DemoApiService demoApiService;

    @RequestMapping(value = "hello/{name}")
    public RespMsg<String> hello(@PathVariable("name") String name){
        return RespMsg.successResp(demoApiService.sayHello(name));
    }

    @RequestMapping(value = "addData/{name}")
    public RespMsg<?> addData(@PathVariable("name") String name){
        demoApiService.addData(name);
        return RespMsg.successResp();
    }

    @RequestMapping(value = "getPage/{pageIndex}")
    public RespMsg<?> getPage(@PathVariable int pageIndex){
        return RespMsg.successResp("",demoApiService.getPageList(pageIndex));
    }
}
