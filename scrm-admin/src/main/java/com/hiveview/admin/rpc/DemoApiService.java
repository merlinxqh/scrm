package com.hiveview.admin.rpc;

import com.hiveview.credit.api.DemoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by leo on 2017/10/23.
 */
@Service("demoApiService")
public class DemoApiService {

    @Autowired
    private DemoService demoService;

    public String sayHello(String name){
        return demoService.sayHello(name);
    }

    public void addData(String name){
        demoService.addData(name);
    }

    public String getPageList(int pageIndex){
        return demoService.getPageList(pageIndex);
    }
}
