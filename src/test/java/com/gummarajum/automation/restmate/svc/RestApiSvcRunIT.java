package com.gummarajum.automation.restmate.svc;

import com.cfg.Config;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import com.github.tomakehurst.wiremock.junit.WireMockRule;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = Config.class)
public class RestApiSvcRunIT {

    private static final String HTTP_LOCALHOST = "http://localhost";

    @Autowired
    private RestApiSvc restApiSvc;

    @Rule
    public WireMockRule wireMockRule =  new WireMockRule(WireMockConfiguration.wireMockConfig().dynamicPort());

    @Before
    public void before(){
        restApiSvc.setApiBaseUri(HTTP_LOCALHOST + wireMockRule.port());
    }

    @Test
    public void testGetMethod(){}{

    }


}
