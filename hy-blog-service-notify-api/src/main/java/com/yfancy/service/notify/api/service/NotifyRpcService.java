package com.yfancy.service.notify.api.service;

import com.yfancy.common.base.entity.Notify;

public interface NotifyRpcService {

    public void saveNotify(Notify notify);

    public void updateNotify(Notify notify);

    public void sendMessage(String message);

    public void sendKafkaMsg(Object message);


}
