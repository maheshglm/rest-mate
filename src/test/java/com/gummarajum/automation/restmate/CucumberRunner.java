package com.gummarajum.automation.restmate;

import org.junit.Test;

public class CucumberRunner extends BaseRunner{
    @Test
    public void testCucumber(){
        cucumber.api.cli.Main.run(cucumberOptions,Thread.currentThread().getContextClassLoader());
    }
}
