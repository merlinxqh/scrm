package com.hiveview.schedule.entity;

import com.hiveview.base.common.BaseEntity;
import com.hiveview.schedule.enums.ScheduleTypeEnum;
import lombok.Data;
import org.springframework.util.StringUtils;

/**
 * 
 * @author hiveview
 * @date 2017-06-09 09:18:58
 * @version 1.0.0
 * @copyright www.hiveview.com
 */
@Data
public class ScheduleJob extends BaseEntity {

    private static final long serialVersionUID = 1L;

    public static final int JOB_RUNNING_STATUS=1;//启用

    public static final int JOB_NOT_RUNNING_STATUS=2;//禁用

    public static final int JOB_CONCURRENT_IS=1;

    public static final int JOB_CONCURRENT_NOT=0;

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
    private ScheduleTypeEnum scheduleType;

    /**
     * http执行url
     */
    private String httpUrl;

    /**
     * 获取泛化调用ID
     * @return
     */
    public String getGenericServiceId(){
        if(StringUtils.hasText(this.beanClass) && StringUtils.hasText(this.methodName)){
            return this.beanClass.concat(".").concat(this.methodName);
        }
        return null;
    }

}