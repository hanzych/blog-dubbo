package com.yfancy.web.timer.task;


import com.yfancy.common.biz.UserService;
import com.yfancy.service.user.api.vo.UserVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class TaskService {

    @Autowired
    private UserService userService;




    public void autoGetUserInfo(){
        List<UserVo> allUser = userService.getAllUser();
        log.info("allUser={}", allUser);
    }

}
