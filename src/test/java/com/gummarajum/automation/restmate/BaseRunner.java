package com.gummarajum.automation.restmate;

import com.cfg.Config;
import com.cfg.CucumberConfig;
import org.junit.AfterClass;
import org.junit.BeforeClass;

public class BaseRunner {
    static String[] cucumberOptions;

    @BeforeClass
    public static void beforeClass(){
        cucumberOptions = CucumberConfig.getCucumberOptions();
        Bootstrap.setConfigClass(Config.class);
        Bootstrap.init();
    }

    @AfterClass
    public static void afterClass() {
        Bootstrap.done();
    }
}
