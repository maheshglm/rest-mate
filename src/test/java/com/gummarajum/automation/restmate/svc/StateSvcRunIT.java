package com.gummarajum.automation.restmate.svc;

import com.cfg.Config;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = Config.class)
public class StateSvcRunIT {

    @Autowired
    private StateSvc stateSvc;

    @Test
    public void testSetStringVar() {
        stateSvc.setStringVar("var", "value");
        Assert.assertEquals("value", stateSvc.getStringVar("var"));
    }

    @Test
    public void testExpandVar() {
        stateSvc.setStringVar("var1", "value1");
        stateSvc.setStringVar("var2", "value2");
        String expression = "This is test to var expansion for ${var1} and ${var2}";
        String expandExpression = "This is test to var expansion for value1 and value2";

        String expand = stateSvc.expandExpression(expression);
        Assert.assertEquals(expandExpression, expand);
    }


}
