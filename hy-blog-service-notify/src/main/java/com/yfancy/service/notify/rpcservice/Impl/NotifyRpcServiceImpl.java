package com.yfancy.service.notify.rpcservice.Impl;

import com.yfancy.common.base.entity.Notify;
import com.yfancy.service.notify.api.service.NotifyRpcService;
import com.yfancy.service.notify.dao.NotifyDao;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;

@Service("notifyRpcService")
public class NotifyRpcServiceImpl implements NotifyRpcService {


    @Autowired
    private DefaultMQProducer rocketmqProduct;

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
        System.out.print("发送消息-------");
        try {
            Message msg = new Message("iZbp17ry8etfcerqmzgqirZ" ,
                    "TagA" /* Tag */,
                    (message).getBytes(RemotingHelper.DEFAULT_CHARSET) /* Message body */
            );
            rocketmqProduct.send(msg);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (RemotingException e) {
            e.printStackTrace();
        } catch (MQClientException e) {
            e.printStackTrace();
        } catch (MQBrokerException e) {
            e.printStackTrace();
        }
    }


}
