package com.gummarajum.automation.restmate.svc;

import com.google.common.base.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class StateSvc {

    private static final Logger LOGGER = LoggerFactory.getLogger(StateSvc.class);

    private Map<String, String> stringMap = new HashMap<>();
    private Map<String, String> envStringMap = new HashMap<>();

    public void useNamedEnvironment(final String envName) {


    }

    public void setStringVar(final String varName, final String value) {
        stringMap.put(varName, value);
        LOGGER.debug("Setting value [{}] in variable [{}]", value, varName);
        if (envStringMap.containsKey(varName)) {
            envStringMap.remove(varName);
        }
    }

    public String getStringVar(final String varName) {
        if (stringMap.containsKey(varName)) {
            return stringMap.get(varName);
        } else if (envStringMap.containsKey(varName)) {
            return envStringMap.get(varName);
        }

        return "";
    }

    public String expandExpression(final String expression) {
        if (Strings.isNullOrEmpty(expression)) {
            return null;
        }

        String originalExpression = expression;
        int varStart = expression.indexOf("${");

        if (varStart >= 0) {
            String varName;
            String expanded = "";
            String value;

            while (varStart >= 0) {
                int varEnd = originalExpression.indexOf('}', varStart + 2);
                if (varEnd > varStart + 1) {
                    varName = originalExpression.substring(varStart + 2, varEnd);
                    value = this.getStringVar(varName);
                    expanded = originalExpression.substring(0, varStart) + value + originalExpression.substring(varEnd + 1);
                }
                originalExpression = expanded;
                varStart =  originalExpression.indexOf("${");
            }
        }
        return originalExpression;
    }


}
