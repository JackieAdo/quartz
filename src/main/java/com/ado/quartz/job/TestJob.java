package com.ado.quartz.job;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.util.Date;

/**
 * 定时任务
 * @author Jackie
 * @date
 **/
public class TestJob implements Job {

    /**
     * 定期获取申请试用用户并发送邮件
     * @param context
     * @throws JobExecutionException
     */
    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {

        System.out.println(new Date());
    }
}
