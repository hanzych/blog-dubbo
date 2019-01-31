package com.yfancy.app.notify.listener;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.listener.MessageListener;

public class KafkaConsumerListener implements MessageListener {

    private final static Logger log = LoggerFactory.getLogger("web-timer");

    /**
     * Executes when a {@link ConsumerRecord} is received.
     *
     * @param record the ConsumerRecord to be processed.
     */
    @Override
    public void onMessage(ConsumerRecord record) {
        log.info("kafka={}",record.value());
    }
}
