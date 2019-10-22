package com.gummarajum.automation.restmate;

import com.cfg.Config;
import com.google.common.base.Strings;
import org.apache.log4j.xml.DOMConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import java.net.MalformedURLException;
import java.net.URL;

public class Bootstrap {

    private static final Logger LOGGER = LoggerFactory.getLogger(Bootstrap.class);

    private static ConfigurableApplicationContext context;
    private static boolean initialized = false;
    private static Class configClass = Config.class;

    private Bootstrap(){
    }

    public static void setConfigClass(Class configClass){
        Bootstrap.configClass = configClass;
    }

    public static void configureContainer(){
        if(context == null){
            context = new AnnotationConfigApplicationContext(configClass);
        }
    }

    public static void init(){
        if(!initialized){
            configureContainer();
            //configureLogging();
            initialized = true;
            LOGGER.info("bootstrap: initialized");
        }
    }

    public static final void done(){
        if(context != null){
            try{
                context.close();
            }finally {
                context = null;
            }
        }
    }

    public static ConfigurableApplicationContext getContext(){
        init();
        return context;
    }

    public static synchronized Object getBean(Class class1) {
        init();
        return context.getBean(class1);
    }

    public static synchronized Object getBean(String name) {
        init();
        return context.getBean(name);
    }

    public static void configureLogging(){
        String logConfigLocation = getLogConfigLocation();
        try {
            DOMConfigurator.configure(new URL(logConfigLocation));
        } catch (MalformedURLException e) {
            System.out.println("FATAL: failed to configure Logging." + e.getMessage());
            System.exit(3);
        }
    }

    private static String getLogConfigLocation() {
        String logConfigLocation = System.getProperty("cart.log4j.config");
        if (Strings.isNullOrEmpty(logConfigLocation)) {
            logConfigLocation = "file:src/main/resources/log4j.properties";
        }
        return logConfigLocation;
    }
}
