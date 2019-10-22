package com.cfg;

import com.gummarajum.automation.restmate.steps.StepsConfig;
import com.gummarajum.automation.restmate.svc.SvcConfig;
import com.gummarajum.automation.restmate.utils.UtilsConfig;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

//@Import({UtilsConfig.class, SvcConfig.class, StepsConfig.class})
@Configuration()
@ComponentScan(basePackages = {"com.gummarajum.automation.restmate"})
public class Config {
}
