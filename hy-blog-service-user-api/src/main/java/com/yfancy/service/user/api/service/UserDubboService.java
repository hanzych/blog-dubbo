package com.yfancy.service.user.api.service;

import com.yfancy.service.user.api.vo.UserVo;

import java.util.List;

public interface UserDubboService {

    public UserVo getUserInfoById(int id);

    public List<UserVo> getAllUser();

    public void addUser(UserVo userVo);

    public UserVo getUserInfoByName(String name);
}
