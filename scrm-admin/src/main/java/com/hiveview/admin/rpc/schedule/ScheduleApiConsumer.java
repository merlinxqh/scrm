package com.hiveview.admin.rpc.schedule;

import com.alibaba.dubbo.config.annotation.Reference;
import com.hiveview.common.api.ModifyCommonDto;
import com.hiveview.common.api.PageDto;
import com.hiveview.config.dubbo.DubboConfiguration;
import com.hiveview.schedule.api.ScheduleJobApiService;
import com.hiveview.schedule.dto.ScheduleJobDto;
import org.springframework.stereotype.Component;

/**
 * Created by leo on 2018/1/15.
 */
@Component
public class ScheduleApiConsumer {

    @Reference(registry = DubboConfiguration.ZOOKEEPER_CLIENT)
    private ScheduleJobApiService scheduleJobApiService;


    public PageDto findPage(PageDto page, ScheduleJobDto dto){
        return scheduleJobApiService.findPage(page, dto);
    }

    public ScheduleJobDto findById(Long id){
        return scheduleJobApiService.findById(id);
    }

    public void saveData(ScheduleJobDto dto){
        scheduleJobApiService.saveData(dto);
    }

    public void modifyData(ModifyCommonDto dto){
        scheduleJobApiService.modifyData(dto);
    }
}
