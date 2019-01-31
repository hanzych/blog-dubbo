package com.yfancy.common.log.kafka;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.AppenderBase;
import ch.qos.logback.core.Layout;
import lombok.Setter;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by han on 2017/4/24.
 */
@Setter
public class KafkaAppender extends AppenderBase<ILoggingEvent> {
  private String topic;
  private String bootstrapServers;
  private String valueSerializer;
  private Long maxBlockTimeMs;
  private int requestTimeoutMs;
  private boolean failOnStartup;
  Producer<String, String> producer;
  private Layout<ILoggingEvent> layout;
  private List<String> customProps = new ArrayList<>();
  @Override
  public void start() {
    Objects.requireNonNull(topic, "topic must not be null");
    Objects.requireNonNull(bootstrapServers, "bootstrapServers must not be null");
    Objects.requireNonNull(valueSerializer, "valueSerializer must not be null");
    Objects.requireNonNull(layout, "layout must not be null");

    Properties props = new Properties();
    props.put("bootstrap.servers", bootstrapServers);
    props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
    props.put("value.serializer", valueSerializer);
    props.put("request.timeout.ms",requestTimeoutMs);
    props.put("max.block.ms",maxBlockTimeMs);
    parseProperties(props);
    try {
      startProducer(props);
      super.start();
    } catch (Exception e) {
      if (failOnStartup) {
        addError("Unable to start Kafka Producer", e);
      } else {
        addWarn("Unable to start Kafka Producer", e);
      }
    }
  }

  @Override
  public void stop() {
    super.stop();
    if (producer != null)
      producer.close();
  }

  public void addCustomProp(String customProp) {
    customProps.add(customProp);
  }


  @Override
  protected void append(ILoggingEvent event) {
    if (producer != null) {
      try {
          String msg = layout.doLayout(event);
            producer.flush();
            producer.send(new ProducerRecord<>(topic, String.valueOf(Math.random()),
                msg)).get();
      } catch (Exception e) {
        addWarn("Unable to send message to Kafka", e);
      }
    }
  }

  void parseProperties(Properties properties) {
    if (customProps != null) {
      customProps.forEach(property -> {
        String[] p = property.split("\\|");
        if (p.length == 2) {
          properties.put(p[0], p[1]);
        } else {
          if (failOnStartup) {
            addError("Unable to parse property string: " + property);
          } else {
            addWarn("Unable to parse property string: " + property);
          }
        }
      });
    }
  }

  // aka unit test friendly
  void startProducer(Properties props) throws Exception {
    producer = new KafkaProducer<>(props);
  }
}