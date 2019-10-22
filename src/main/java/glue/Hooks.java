package glue;

import com.gummarajum.automation.restmate.Bootstrap;
import com.gummarajum.automation.restmate.steps.HooksSteps;
import cucumber.api.Scenario;
import cucumber.api.java8.En;

public class Hooks implements En {

    private HooksSteps steps = (HooksSteps) Bootstrap.getBean(HooksSteps.class);

    public Hooks(){
        Before(0,(Scenario scenario) -> steps.setScenario(scenario));
    }

}
