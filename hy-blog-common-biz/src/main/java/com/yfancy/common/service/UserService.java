package com.yfancy.common.service;


import com.yfancy.service.user.api.vo.UserVo;
import com.yfancy.service.vip.api.vo.VipVo;

import java.util.List;

public interface UserService {

    public List<UserVo> getAllUser();

    public UserVo getUserById(int id);

    public boolean isVipUser(int userId);

    public int vipType(int userId);

    public void addVip(VipVo vipVo);

    public void addUser(UserVo userVo);
}
