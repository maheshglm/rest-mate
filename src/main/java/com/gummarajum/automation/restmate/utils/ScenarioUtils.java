package com.gummarajum.automation.restmate.utils;

import cucumber.api.Scenario;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ScenarioUtils {

    private InheritableThreadLocal<Scenario> scenario = new InheritableThreadLocal<>();

    public Scenario getScenario() {
        return scenario.get();
    }

    public void setScenario(Scenario scenario) {
        this.scenario.set(scenario);
    }

    public void write(final String data) {
        try {
            if (getScenario() != null) {
                getScenario().write(data);
            }
        } catch (Exception e) {
            //ignore
        }
    }

    public void embed(final byte[] bytes, final String var) {
        if (getScenario() != null) {
            getScenario().embed(bytes, var);
        }
    }

    public List<String> getTagNames(){
        if(this.getScenario() != null){
            return (List<String>) this.getScenario().getSourceTagNames();
        }
        return new ArrayList<>();
    }

    public boolean isScenarioFailed(){
        return this.getScenario() != null && this.getScenario().isFailed();
    }




}
