package com.ado.quartz.configuration;


import com.ado.quartz.job.TestJob;
import org.quartz.*;
import org.quartz.spi.TriggerFiredBundle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.scheduling.quartz.AdaptableJobFactory;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Component;

/**
 * 定时任务配置
 * @author Jackie
 * @date 2018年3月08日
 **/
@Component
public class QuartzFactory extends AdaptableJobFactory {

    @Autowired
    private AutowireCapableBeanFactory capableBeanFactory;
    @Autowired
    private SchedulerFactoryBean schedulerFactoryBean;

    public void scheduleJobs() throws SchedulerException {
        Scheduler scheduler = schedulerFactoryBean.getScheduler();
        startJob1(scheduler);
//        startJob2(scheduler);
    }

    /**
     * 配置定时任务
     * @param scheduler
     * @throws SchedulerException
     */
    private void startJob1(Scheduler scheduler) throws SchedulerException {
        JobDetail jobDetail = JobBuilder.newJob(TestJob.class) .withIdentity("job1", "group1").build();
        //每天凌晨两点启动
        /**
         * 0 0 12 * * ? 每天12点触发
         0 15 10 ? * * 每天10点15分触发
         0 15 10 * * ? 每天10点15分触发
         0 15 10 * * ? * 每天10点15分触发
         0 15 10 * * ? 2005 2005年每天10点15分触发
         0 * 14 * * ? 每天下午的 2点到2点59分每分触发
         0 0/5 14 * * ? 每天下午的 2点到2点59分(整点开始，每隔5分触发)
         0 0/5 14,18 * * ? 每天下午的 2点到2点59分(整点开始，每隔5分触发)
         每天下午的 18点到18点59分(整点开始，每隔5分触发)
         0 0-5 14 * * ? 每天下午的 2点到2点05分每分触发
         0 10,44 14 ? 3 WED 3月分每周三下午的 2点10分和2点44分触发 （特殊情况，在一个时间设置里，执行两次或                                                             两次以上的情况）
         0 59 2 ? * FRI 每周5凌晨2点59分触发
         0 15 10 ? * MON-FRI 从周一到周五每天上午的10点15分触发
         0 15 10 15 * ? 每月15号上午10点15分触发
         0 15 10 L * ? 每月最后一天的10点15分触发
         0 15 10 ? * 6L 每月最后一周的星期五的10点15分触发
         0 15 10 ? * 6L 2002-2005 从2002年到2005年每月最后一周的星期五的10点15分触发
         0 15 10 ? * 6#3 每月的第三周的星期五开始触发
         0 0 12 1/5 * ? 每月的第一个中午开始每隔5天触发一次
         */
        CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule("* * * * * ?");
        CronTrigger cronTrigger = TriggerBuilder.newTrigger().withIdentity("trigger1", "group1") .withSchedule(scheduleBuilder).build();
        scheduler.scheduleJob(jobDetail,cronTrigger);
    }

//    private void startJob2(Scheduler scheduler) throws SchedulerException{
//        JobDetail jobDetail = JobBuilder.newJob(BillPayJob.class) .withIdentity("job2", "group2").build();
//        CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule("0 * * * * ?");
//        CronTrigger cronTrigger = TriggerBuilder.newTrigger().withIdentity("trigger2", "group2") .withSchedule(scheduleBuilder).build();
//        scheduler.scheduleJob(jobDetail,cronTrigger);
//    }

    /**
     * 初始化定时任务
     * @param bundle
     * @return
     * @throws Exception
     */
    @Override
    protected Object createJobInstance(TriggerFiredBundle bundle) throws Exception {
        // 调用父类的方法
        Object jobInstance = super.createJobInstance(bundle);
        // 进行注入
        capableBeanFactory.autowireBean(jobInstance);
        return jobInstance;
    }
}
