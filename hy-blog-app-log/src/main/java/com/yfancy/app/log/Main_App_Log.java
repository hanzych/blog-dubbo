package com.yfancy.app.log;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main_App_Log {

    private final static Logger log = LoggerFactory.getLogger(Main_App_Log.class);

    public static void main( String[] args ){
        try {
            ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(new String[] { "spring/spring-context.xml" });
            context.start();
            log.info("[hy-blog-app-log Service] == context start");

        } catch (Exception e) {
            log.error("[hy-blog-app-log Service] == application start error:", e);
            return;
        }

        synchronized (Main_App_Log.class) {
            while (true) {
                try {
                    Main_App_Log.class.wait();
                } catch (InterruptedException e) {
                    log.error("== synchronized error:", e);
                }
            }
        }
    }
}
