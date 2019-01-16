package com.yfancy.web.boss.controller;


import com.yfancy.common.base.Result;
import com.yfancy.common.base.url.AdminUrlMapping;
import com.yfancy.common.service.UserService;
import com.yfancy.service.user.api.vo.UserVo;
import com.yfancy.service.vip.api.vo.VipVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@Slf4j
@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = AdminUrlMapping.USER_GET_ALL_USER, method = RequestMethod.GET)
    public Result getAllUser(){
        List<UserVo> allUser = userService.getAllUser();
        return Result.SUCCESS(allUser);
    }

    @RequestMapping(value = AdminUrlMapping.USER_GET_USER_BY_ID, method = RequestMethod.GET)
    public Result getUserById(@RequestParam("id") int id){
        UserVo allUser = userService.getUserById(id);
        int i = userService.vipType(id);
        log.info("vipType = {}", i);
        return Result.SUCCESS(allUser);
    }

    @RequestMapping(value = AdminUrlMapping.VIP_ADD_VIP, method = RequestMethod.POST)
    public Result addVipByUserId(@RequestParam("userId") int userId){
        VipVo vipVo = new VipVo();
        vipVo.setUserId(userId);
        vipVo.setCreateTime(new Date());
        vipVo.setEndTime(new Date());
        vipVo.setType(2);
        userService.addVip(vipVo);
        return Result.SUCCESS();
    }

    @RequestMapping(value = AdminUrlMapping.USER_ADD_UER, method = RequestMethod.POST)
    public Result addUser(@RequestParam("mobile") String mobile){
        UserVo userVo = new UserVo();
        userVo.setAge(12);
        userVo.setName("zhangsan");
        userVo.setNickName("czdasdasd");
        userVo.setSex(-1);
        userService.addUser(userVo);
        return Result.SUCCESS();
    }


}
