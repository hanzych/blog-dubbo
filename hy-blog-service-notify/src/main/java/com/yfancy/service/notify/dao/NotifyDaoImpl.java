package com.yfancy.service.notify.dao;

import com.yfancy.common.base.dao.BaseDaoImpl;
import com.yfancy.common.base.entity.Notify;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;


@Repository("notifyDao")
public class NotifyDaoImpl extends BaseDaoImpl<Notify> implements NotifyDao {


}
