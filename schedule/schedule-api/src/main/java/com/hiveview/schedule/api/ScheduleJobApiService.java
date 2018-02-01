package com.hiveview.schedule.api;

import com.hiveview.common.api.CrudApiService;
import com.hiveview.common.api.ModifyCommonDto;
import com.hiveview.schedule.dto.ScheduleJobDto;

/**
 * Created by leo on 2018/1/10.
 */
public interface ScheduleJobApiService  extends CrudApiService<ScheduleJobDto> {

    void modifyData(ModifyCommonDto dto);
}
