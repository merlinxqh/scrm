package com.hiveview.credit.api;

import com.alibaba.fastjson.JSON;
import com.hiveview.base.mybatis.page.Page;
import com.hiveview.base.util.id.IdWorker;
import com.hiveview.credit.entity.DemoEntity;
import com.hiveview.credit.service.DemoEntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Random;

/**
 * Created by leo on 2017/10/20.
 */
@Component
public class DemoServiceImpl implements DemoService {

    @Autowired
    private DemoEntityService demoEntityService;

    @Override
    public String sayHello(String name) {
        return "Hello "+name;
    }

    @Override
    public void addData(String name) {
        Random random=new Random();
        DemoEntity demo=new DemoEntity();
        demo.setName(name);
        demo.setCode(IdWorker.getStringCode());
        demo.setPrice(new BigDecimal(random.nextInt(100000)));
        demo.setCreateDate(new Date());
        demo.setCreateBy("by ".concat(name));
        demo.setLastUpdateDate(new Date());
        demo.setLastUpdateBy("last update by "+name);
        demo.setDescription("hehehe");
        demoEntityService.saveData(demo);
    }

    @Override
    public String getPageList(int pageIndex) {
        Page page=new Page(pageIndex);
        demoEntityService.findByPage(page);
        return JSON.toJSONString(page);
    }
}
