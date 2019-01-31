package com.yfancy.web.timer.kafka_log;

import com.alibaba.dubbo.config.annotation.Reference;
import com.yfancy.service.notify.api.service.NotifyRpcService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;


import ch.qos.logback.core.AppenderBase;
import ch.qos.logback.core.Layout;

public class KafkaAppender<E> extends AppenderBase<E> {
    private static final Logger log = LoggerFactory.getLogger("local");
    protected Layout<E> layout;


    @Reference
    private NotifyRpcService notifyRpcService;


    @Override
    public void start() {
        Assert.notNull(layout, "you don't set the layout of KafkaAppender");
        super.start();
    }

    @Override
    public void stop() {
        super.stop();
        System.out.println("[Stopping KafkaAppender !!!]");
    }

    @Override
    protected void append(E event) {
        String msg = layout.doLayout(event);
        notifyRpcService.sendKafkaMsg(msg);
        System.out.println("[推送数据]:" + msg + "_hehe");
        // Future<RecordMetadata> future = producer.send(producerRecord);
        // try {
        // // Object obj = future.get(0, TimeUnit.MILLISECONDS);//1ms内没有任何响应就直接写入文本文件
        // Object obj = future.get();
        // if (future.isDone()) {
        // System.out.println("响应结果:" + obj.toString());
        // System.out.println("推送数据到kafka成功");
        // }
        // } catch (Exception e) {
        // log.info("推送异常:{}", e.getMessage());
        // }
    }

    public Layout<E> getLayout() {
        return layout;
    }

    public void setLayout(Layout<E> layout) {
        this.layout = layout;
    }

}