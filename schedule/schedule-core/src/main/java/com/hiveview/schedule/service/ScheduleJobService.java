package com.hiveview.schedule.service;

import com.hiveview.base.service.BaseCrudService;
import com.hiveview.common.api.ModifyCommonDto;
import com.hiveview.schedule.entity.ScheduleJob;


/**
 * Created by leo on 2018/1/10.
 */
public interface ScheduleJobService extends BaseCrudService<ScheduleJob> {

    void modifyData(ModifyCommonDto dto);
}
