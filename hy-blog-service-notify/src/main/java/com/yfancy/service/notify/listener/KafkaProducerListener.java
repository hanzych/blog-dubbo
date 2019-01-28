package com.yfancy.service.notify.listener;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.springframework.kafka.support.ProducerListener;

@Slf4j
public class KafkaProducerListener implements ProducerListener {
    @Override
    public void onSuccess(String s, Integer integer, Object o, Object o1, RecordMetadata recordMetadata) {
        log.info("kafka发送数据成功");
    }

    @Override
    public void onError(String s, Integer integer, Object o, Object o1, Exception e) {
        log.info("kafka发送数据失败");
    }

    @Override
    public boolean isInterestedInSuccess() {
        log.info("kafkaProducer监听器启动--");
        return true;
    }
}
