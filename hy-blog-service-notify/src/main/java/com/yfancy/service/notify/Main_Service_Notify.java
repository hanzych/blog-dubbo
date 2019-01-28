package com.yfancy.service.notify;

import com.yfancy.service.notify.rpcservice.Impl.NotifyRpcServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Hello world!
 *
 */
public class Main_Service_Notify {

    private final static Logger log = LoggerFactory.getLogger(Main_Service_Notify.class);

    public static void main( String[] args ){
        try {
            ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(new String[] { "spring/spring-context.xml" });
            context.start();
            log.info("NotifyService Dubbo Service == context start");
            NotifyRpcServiceImpl notifyRpcService = (NotifyRpcServiceImpl) context.getBean("notifyRpcService");
            notifyRpcService.sendKafkaMsg("社会人");
            log.info("success,,,,,,,,,,,,,,");
        } catch (Exception e) {
            log.error("[hy-blog-service-notify] == application start error:", e);
            return;
        }

        synchronized (Main_Service_Notify.class) {
            while (true) {
                try {
                    Main_Service_Notify.class.wait();
                } catch (InterruptedException e) {
                    log.error("== synchronized error:", e);
                }
            }
        }
    }
}
