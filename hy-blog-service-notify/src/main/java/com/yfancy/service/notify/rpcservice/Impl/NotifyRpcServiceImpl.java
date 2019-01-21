package com.yfancy.service.notify.rpcservice.Impl;

import com.yfancy.common.base.entity.Notify;
import com.yfancy.common.base.enums.NotifyDestinatonEnum;
import com.yfancy.service.notify.api.service.NotifyRpcService;
import com.yfancy.service.notify.dao.NotifyDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;


public class NotifyRpcServiceImpl implements NotifyRpcService {

    @Autowired
    private JmsTemplate notifyJmsTemplate;

    @Autowired
    private NotifyDao notifyDao;

    @Override
    public void saveNotify(Notify notify) {
        notifyDao.insert(notify);
    }

    @Override
    public void updateNotify(Notify notify) {
        notifyDao.update(notify);
    }


    /**
     * 发送消息
     * @param message
     */
    @Override
    public void sendMessage(final String message) {
        notifyJmsTemplate.send(NotifyDestinatonEnum.LOG_DESTINATION.name(), new MessageCreator() {
            @Override
            public Message createMessage(Session session) throws JMSException {
                return session.createTextMessage(message);
            }
        });
    }


}
