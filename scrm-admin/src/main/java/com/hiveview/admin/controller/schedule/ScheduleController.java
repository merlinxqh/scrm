package com.hiveview.admin.controller.schedule;

import com.hiveview.admin.commom.BaseController;
import com.hiveview.admin.rpc.schedule.ScheduleApiConsumer;
import com.hiveview.base.util.response.RespMsg;
import com.hiveview.common.api.ModifyCommonDto;
import com.hiveview.common.api.PageDto;
import com.hiveview.schedule.dto.ScheduleJobDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by leo on 2018/1/10.
 * 定时调度
 */
@Controller
@RequestMapping(value = "/schedule")
public class ScheduleController extends BaseController{

    @Autowired
    private ScheduleApiConsumer scheduleApiConsumer;

    /**
     * 列表页
     * @return
     */
    @RequestMapping(value = "list",method = RequestMethod.GET)
    public ModelAndView list(){
        return new ModelAndView("schedule/schedule_list");
    }

    @RequestMapping(value = "listData")
    public @ResponseBody
    PageDto listData(PageDto page, ScheduleJobDto dto){
        return scheduleApiConsumer.findPage(page,dto);
    }

    /**
     * 新增 & 编辑
     * @return
     */
    @RequestMapping(value = "edit",method = RequestMethod.GET)
    public ModelAndView edit(String id, ModelMap model){
        if(StringUtils.hasText(id)){
            ScheduleJobDto data=scheduleApiConsumer.findById(Long.valueOf(id));
            if(null != data){
                model.put("data",data);
            }
        }
        return new ModelAndView("schedule/schedule_edit");
    }


    @RequestMapping(value = "saveData",method = RequestMethod.POST)
    public @ResponseBody
    RespMsg<?> saveData(ScheduleJobDto dto){
        try {
            putOperatorInfo(dto);
            scheduleApiConsumer.saveData(dto);
        }catch (Exception e){
            return RespMsg.failResp(e.getMessage());
        }
        return RespMsg.successResp();
    }

    @RequestMapping(value = "modifyData",method = RequestMethod.POST)
    public @ResponseBody RespMsg<?> modifyData(ModifyCommonDto dto){
        try {
            scheduleApiConsumer.modifyData(dto);
        }catch (Exception e){
            return RespMsg.failResp(e.getMessage());
        }
        return RespMsg.successResp();
    }
}
