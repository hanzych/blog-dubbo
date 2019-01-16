package com.yfancy.service.vip.dao;

import com.yfancy.common.base.entity.Vip;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface VipDao {

    public Vip getVipByUserId(int userId);

    public int vipType(int userId);

    public void insertVip(Vip vip);
}
