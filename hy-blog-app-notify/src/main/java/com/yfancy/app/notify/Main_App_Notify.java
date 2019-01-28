package com.yfancy.app.notify;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main_App_Notify {

    private final static Logger log = LoggerFactory.getLogger(Main_App_Notify.class);

    public static void main( String[] args ){
        try {
            ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(new String[] { "spring/spring-context.xml" });
            context.start();
            log.info("NotifyService Dubbo Service == context start");

        } catch (Exception e) {
            log.error("[hy-blog-service-notify] == application start error:", e);
            return;
        }

        synchronized (Main_App_Notify.class) {
            while (true) {
                try {
                    Main_App_Notify.class.wait();
                } catch (InterruptedException e) {
                    log.error("== synchronized error:", e);
                }
            }
        }
    }
}
