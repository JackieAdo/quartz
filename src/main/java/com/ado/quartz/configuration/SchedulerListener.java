package com.ado.quartz.configuration;

import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

/**
 * 定时任务配置
 * @author Jackie
 * @date 2018年3月08日
 **/
@Configuration
public class SchedulerListener implements ApplicationListener<ContextRefreshedEvent> {
    @Autowired
    public QuartzFactory quartzFactory;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        try {
            quartzFactory.scheduleJobs();
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

    @Bean
    public SchedulerFactoryBean schedulerFactoryBean(){
        SchedulerFactoryBean schedulerFactoryBean = new SchedulerFactoryBean();
        schedulerFactoryBean.setJobFactory(quartzFactory);
        return schedulerFactoryBean;
    }
}
