package com.yfancy.web.timer.task;


import com.yfancy.web.timer.constants.TaskCronConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component("AllTask")
@Async
public class TaskList {

    @Autowired
    private TaskService taskService;


    @Scheduled(cron = TaskCronConstants.autoGetUserInfo)
    public void autoGetUserInfo(){
        log.info("----定时处理任务------");
        taskService.autoGetUserInfo();

    }
}
