package com.hiveview.schedule.service.impl;

import com.alibaba.fastjson.JSON;
import com.hiveview.base.dao.CrudMapper;
import com.hiveview.base.exception.ServiceException;
import com.hiveview.base.service.impl.BaseCrudServiceImpl;
import com.hiveview.base.util.id.IdWorker;
import com.hiveview.common.api.ModifyCommonDto;
import com.hiveview.common.api.enums.ModifyTypeEnum;
import com.hiveview.schedule.dao.ScheduleJobMapper;
import com.hiveview.schedule.entity.ScheduleJob;
import com.hiveview.schedule.enums.ScheduleTypeEnum;
import com.hiveview.schedule.job.JobDubboServiceUtils;
import com.hiveview.schedule.service.ScheduleJobService;
import com.hiveview.schedule.service.quartz.QuartzJobService;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by leo on 2018/1/10.
 */
@Service
public class ScheduleJobServiceImpl extends BaseCrudServiceImpl<ScheduleJob> implements ScheduleJobService {

    @Resource
    private ScheduleJobMapper scheduleJobMapper;

    @Autowired
    private QuartzJobService quartzJobService;

    @Override
    public CrudMapper init() {
        return scheduleJobMapper;
    }

    @Override
    @Transactional
    public int saveData(ScheduleJob entity) throws ServiceException {
        Assert.notNull(entity,"参数不能为空");
        if(StringUtils.isEmpty(entity.getId())){
            entity.setStatus(ScheduleJob.JOB_NOT_RUNNING_STATUS);//默认禁用状态
            entity.setCode(IdWorker.getStringCode());
            entity.setIsConcurrent(ScheduleJob.JOB_CONCURRENT_IS);
            return scheduleJobMapper.insert(entity);
        }else{
            ScheduleJob old=this.findById(Long.valueOf(entity.getId()));
            Assert.isTrue(old.getStatus() == ScheduleJob.JOB_NOT_RUNNING_STATUS,"启用状态任务不能编辑");
            return scheduleJobMapper.updateByPrimaryKeySelective(entity);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void modifyData(ModifyCommonDto dto) {
        Assert.isTrue(StringUtils.hasText(dto.getId()) && null != dto.getModifyType(),"缺失关键参数");
        ScheduleJob data=this.findById(Long.valueOf(dto.getId()));
        Assert.notNull(data,"ID无效");
        if(ModifyTypeEnum.enable.equals(dto.getModifyType())){
            Assert.isTrue(data.getStatus() == ScheduleJob.JOB_NOT_RUNNING_STATUS,"数据已经是有效状态");
            data.setStatus(ScheduleJob.JOB_RUNNING_STATUS);
            try {
                quartzJobService.addJob(data);
                JobDubboServiceUtils.setService(data);
            } catch (SchedulerException e) {
                logger.error("",e);
            }
            this.scheduleJobMapper.updateByPrimaryKey(data);
        }else if(ModifyTypeEnum.disable.equals(dto.getModifyType())){
            Assert.isTrue(data.getStatus() == ScheduleJob.JOB_RUNNING_STATUS,"数据已经是失效状态");
            data.setStatus(ScheduleJob.JOB_NOT_RUNNING_STATUS);
            try {
                quartzJobService.deleteJob(data);
                JobDubboServiceUtils.removeService(data);
            } catch (SchedulerException e) {
                logger.error("",e);
            }
            this.scheduleJobMapper.updateByPrimaryKey(data);
        }else if(ModifyTypeEnum.execute.equals(dto.getModifyType())){
            //立即执行
            Assert.isTrue(data.getStatus() == ScheduleJob.JOB_RUNNING_STATUS,"该任务是禁用状态");
            try {
                quartzJobService.runJobNow(data);
            } catch (SchedulerException e) {
                logger.error("",e);
            }
        }else if(ModifyTypeEnum.delete.equals(dto.getModifyType())){
            //删除调度
            Assert.isTrue(data.getStatus() == ScheduleJob.JOB_NOT_RUNNING_STATUS,"启用状态调度任务不能删除!");
            this.scheduleJobMapper.deleteByPrimaryKey(data.getId());
        }
    }

    /**
     * 添加任务到quartz
     * @throws Exception
     */
    @PostConstruct
    public void initJob() throws Exception {

        //获取所有任务数据
        Map<String,Object> param=new HashMap<>();
        param.put("status",ScheduleJob.JOB_RUNNING_STATUS);
        List<ScheduleJob> list=scheduleJobMapper.selectByParams(param);
        if(!CollectionUtils.isEmpty(list)){
            for(ScheduleJob job:list){
                if(ScheduleTypeEnum.RPC.equals(job.getScheduleType())){
                    //处理远程调用 执行方式
                    try {
                        JobDubboServiceUtils.setService(job);
                    }catch (Exception e){
                        logger.error("添加rpc dubbo远程调用任务出错"+ JSON.toJSONString(job),e);
                        continue;
                    }
                }
                quartzJobService.addJob(job);
            }
        }
    }
}
