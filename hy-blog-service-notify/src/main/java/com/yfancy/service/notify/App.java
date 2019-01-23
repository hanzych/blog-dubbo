package com.yfancy.service.notify;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Hello world!
 *
 */
public class App {

    private final static Logger log = LoggerFactory.getLogger(App.class);

    public static void main( String[] args ){
        try {
            ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(new String[] { "spring/spring-context.xml" });
            context.start();
            log.info("NotifyService Dubbo Service == context start");
            DefaultMQProducer producer = (DefaultMQProducer) context.getBean("rocketmqProduct");

            producer.setNamesrvAddr("47.111.22.77:9876");

            Message msg = new Message("iZbp17ry8etfcerqmzgqirZ" ,
                    "TagA" /* Tag */,
                    ("你好，社会人").getBytes(RemotingHelper.DEFAULT_CHARSET) /* Message body */
            );
            producer.getDefaultMQProducerImpl().send(msg);
            log.info("success,,,,,,,,,,,,,,");
        } catch (Exception e) {
            log.error("[hy-blog-service-notify] == application start error:", e);
            return;
        }

        synchronized (App.class) {
            while (true) {
                try {
                    App.class.wait();
                } catch (InterruptedException e) {
                    log.error("== synchronized error:", e);
                }
            }
        }
    }
}
