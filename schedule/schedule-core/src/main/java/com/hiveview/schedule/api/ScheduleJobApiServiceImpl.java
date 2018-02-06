package com.hiveview.schedule.api;

import com.alibaba.dubbo.config.annotation.Service;
import com.hiveview.base.util.serializer.ObjectUtils;
import com.hiveview.common.api.ModifyCommonDto;
import com.hiveview.common.api.PageDto;
import com.hiveview.schedule.common.WrapperApiService;
import com.hiveview.schedule.dto.ScheduleJobDto;
import com.hiveview.schedule.entity.ScheduleJob;
import com.hiveview.schedule.service.ScheduleJobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by leo on 2018/1/10.
 */
@Service(registry = "zookeeperService")
public class ScheduleJobApiServiceImpl implements ScheduleJobApiService {

    @Autowired
    private ScheduleJobService scheduleJobService;

    @Override
    @Transactional
    public int saveData(ScheduleJobDto data) {
        return scheduleJobService.saveData(ObjectUtils.copyObject(data, ScheduleJob.class));
    }

    @Override
    public int deleteData(ScheduleJobDto data) {
        return 0;
    }

    @Override
    @Transactional
    public void modifyData(ModifyCommonDto dto) {
       scheduleJobService.modifyData(dto);
    }

    @Override
    public List<ScheduleJobDto> findList(ScheduleJobDto params){
        List<ScheduleJob> list=scheduleJobService.findByBiz(ObjectUtils.changeToMap(params));
        return ObjectUtils.copyListObject(list,ScheduleJobDto.class);
    }

    @Override
    public ScheduleJobDto findById(Long id) {
        ScheduleJob job=scheduleJobService.findById(id);
        if(null != job){
            return ObjectUtils.copyObject(job,ScheduleJobDto.class);
        }
        return null;
    }

    @Override
    public PageDto<ScheduleJobDto> findPage(PageDto<ScheduleJobDto> page, ScheduleJobDto params) {
        return WrapperApiService.findByPage(page,params,scheduleJobService,ScheduleJobDto.class);
    }
}
