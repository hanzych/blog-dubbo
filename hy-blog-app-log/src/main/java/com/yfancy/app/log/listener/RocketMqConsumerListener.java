package com.yfancy.app.log.listener;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSONObject;
import com.yfancy.common.base.entity.Notify;
import com.yfancy.service.notify.api.service.NotifyRpcService;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.message.MessageExt;

import java.util.List;

@Slf4j
public class RocketMqConsumerListener implements MessageListenerConcurrently {

    @Reference
    private NotifyRpcService notifyRpcService;

    /**
     * It is not recommend to throw exception,rather than returning ConsumeConcurrentlyStatus.RECONSUME_LATER if
     * consumption failure
     *
     * @param msgs    msgs.size() >= 1<br> DefaultMQPushConsumer.consumeMessageBatchMaxSize=1,you can modify here
     * @param context
     * @return The consume status
     */
    @Override
    public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
        System.out.printf("%s Receive New Messages: %s %n", Thread.currentThread().getName(), msgs);
        for (MessageExt messageExt : msgs){
            String s = new String(messageExt.getBody());
            System.out.printf("sss"  + s);
            try {
                Notify notify = JSONObject.parseObject(s, Notify.class);
                notifyRpcService.saveNotify(notify);
            }catch (Exception e){
                log.error("this is not a notify object message, error");
            }
        }
        return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
    }
}
