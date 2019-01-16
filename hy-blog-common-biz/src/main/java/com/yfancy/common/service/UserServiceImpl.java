package com.yfancy.common.service;


import com.alibaba.dubbo.config.annotation.Reference;
import com.yfancy.service.user.api.service.UserDubboService;
import com.yfancy.service.user.api.vo.UserVo;
import com.yfancy.service.vip.api.service.VipDubboService;
import com.yfancy.service.vip.api.vo.VipVo;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class UserServiceImpl implements UserService {

    @Reference(version = "1.0.0")
    private UserDubboService userDubboService;

    @Reference(version = "1.0.0")
    private VipDubboService vipDubboService;

    @Override
    public List<UserVo> getAllUser() {
        return userDubboService.getAllUser();
    }

    @Override
    public UserVo getUserById(int id) {
        return userDubboService.getUserInfoById(id);
    }

    @Override
    public boolean isVipUser(int userId) {
        return vipDubboService.isVip(userId);
    }

    @Override
    public int vipType(int userId) {
        return vipDubboService.vipType(userId);
    }

    @Override
    public void addVip(VipVo vipVo) {
        vipDubboService.insertVip(vipVo);
    }

    @Override
    public void addUser(UserVo userVo) {
        userDubboService.addUser(userVo);
    }
}
