package com.gummarajum.automation.restmate.steps;

import com.gummarajum.automation.restmate.utils.ScenarioUtils;
import cucumber.api.Scenario;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class HooksSteps {

    private static final Logger LOGGER = LoggerFactory.getLogger(HooksSteps.class);

    @Autowired
    private ScenarioUtils scenarioUtils;

    public void setScenario(Scenario scenario) {
        scenarioUtils.setScenario(scenario);
    }
}
