package com.yfancy.app.notify.listener;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.listener.MessageListener;

@Slf4j
public class KafkaConsumerListener implements MessageListener {

    /**
     * Executes when a {@link ConsumerRecord} is received.
     *
     * @param record the ConsumerRecord to be processed.
     */
    @Override
    public void onMessage(ConsumerRecord record) {
        log.info("kafka开始消费,kafkaMessage={}",record);
    }
}
