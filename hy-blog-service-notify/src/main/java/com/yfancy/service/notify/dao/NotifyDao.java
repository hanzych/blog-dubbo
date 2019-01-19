package com.yfancy.service.notify.dao;

import com.yfancy.common.base.dao.BaseDao;
import com.yfancy.common.base.entity.Notify;

public interface NotifyDao extends BaseDao<Notify> {

    int insert(Notify notify);

    int update(Notify notify);
}
