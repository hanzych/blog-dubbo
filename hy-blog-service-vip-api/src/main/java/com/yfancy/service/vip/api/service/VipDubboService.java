package com.yfancy.service.vip.api.service;

import com.yfancy.service.vip.api.vo.VipVo;

public interface VipDubboService {

    public boolean isVip(int userId);

    public int vipType(int userId);

    public void insertVip(VipVo vip);
}
