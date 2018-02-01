package com.hiveview.schedule.dto;

import com.hiveview.common.api.BaseEntityDto;

/**
 * Created by leo on 2018/1/10.
 * 定时调度
 */
public class ScheduleJobDto extends BaseEntityDto {
    /**
     * 编码
     */
    private String code;

    /**
     * 任务名称.
     */
    private String name;

    /**
     * 任务分组.
     */
    private String jobGroup;

    /**
     * 任务描述.
     */
    private String remark;

    /**
     * 任务状态.
     */
    private Integer status;

    /**
     * cron表达式.
     */
    private String cronExpression;

    /**
     * 类名.
     */
    private String beanClass;

    /**
     * 任务是否有状态.
     */
    private Integer isConcurrent;

    /**
     * spring bean.
     */
    private String springId;

    /**
     * 方法名.
     */
    private String methodName;

    /**
     * 调用方式
     */
    private String scheduleType;

    /**
     * http执行url
     */
    private String httpUrl;


    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getJobGroup() {
        return jobGroup;
    }

    public void setJobGroup(String jobGroup) {
        this.jobGroup = jobGroup;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getCronExpression() {
        return cronExpression;
    }

    public void setCronExpression(String cronExpression) {
        this.cronExpression = cronExpression;
    }

    public String getBeanClass() {
        return beanClass;
    }

    public void setBeanClass(String beanClass) {
        this.beanClass = beanClass;
    }

    public Integer getIsConcurrent() {
        return isConcurrent;
    }

    public void setIsConcurrent(Integer isConcurrent) {
        this.isConcurrent = isConcurrent;
    }

    public String getSpringId() {
        return springId;
    }

    public void setSpringId(String springId) {
        this.springId = springId;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public String getScheduleType() {
        return scheduleType;
    }

    public void setScheduleType(String scheduleType) {
        this.scheduleType = scheduleType;
    }

    public String getHttpUrl() {
        return httpUrl;
    }

    public void setHttpUrl(String httpUrl) {
        this.httpUrl = httpUrl;
    }
}
