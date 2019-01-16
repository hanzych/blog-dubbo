package com.yfancy.service.vip.serviceImpl;

import com.alibaba.dubbo.config.annotation.Service;
import com.yfancy.common.base.entity.Vip;
import com.yfancy.service.vip.api.service.VipDubboService;
import com.yfancy.service.vip.api.vo.VipVo;
import com.yfancy.service.vip.dao.VipDao;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

@Service(version = "1.0.0",interfaceClass = VipDubboService.class)
@Transactional(rollbackFor = Exception.class)
public class VipDubboServiceImpl implements VipDubboService {

    @Autowired
    private VipDao vipDao;

    @Override
    public boolean isVip(int userId) {
        Vip vipByUserId = vipDao.getVipByUserId(userId);
        if (vipByUserId == null){
            return false;
        }
        return true;
    }

    @Override
    public int vipType(int userId) {
        return vipDao.vipType(userId);
    }

    @Override
    @Transactional
    public void insertVip(VipVo vipVo) {
        Vip vip = new Vip();
        BeanUtils.copyProperties(vipVo,vip);
        vipDao.insertVip(vip);
    }


}
