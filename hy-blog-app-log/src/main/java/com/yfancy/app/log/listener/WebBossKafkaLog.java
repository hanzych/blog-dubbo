package com.yfancy.app.log.listener;

import com.yfancy.common.log.kafka.KafkaLogNameEnum;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.listener.MessageListener;

public class WebBossKafkaLog implements MessageListener {

    private final static Logger log = LoggerFactory.getLogger(KafkaLogNameEnum.web_boss.name());

    /**
     * Executes when a {@link ConsumerRecord} is received.
     *
     * @param record the ConsumerRecord to be processed.
     */
    @Override
    public void onMessage(ConsumerRecord record) {
        log.info("{}",record.value());
    }
}
