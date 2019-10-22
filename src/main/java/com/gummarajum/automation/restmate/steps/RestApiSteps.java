package com.gummarajum.automation.restmate.steps;

import com.gummarajum.automation.restmate.ApiException;
import com.gummarajum.automation.restmate.ApiExceptionType;
import com.gummarajum.automation.restmate.mdl.RequestType;
import com.gummarajum.automation.restmate.svc.DataTableSvc;
import com.gummarajum.automation.restmate.svc.RestApiSvc;
import com.gummarajum.automation.restmate.svc.StateSvc;
import com.gummarajum.automation.restmate.svc.WorkspaceDirSvc;
import com.gummarajum.automation.restmate.utils.FileDirUtils;
import com.gummarajum.automation.restmate.utils.ScenarioUtils;
import io.restassured.response.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

import static com.gummarajum.automation.restmate.svc.DataTableSvc.FILE_PREFIX;

@Component
public class RestApiSteps {

    private static final Logger LOGGER = LoggerFactory.getLogger(RestApiSteps.class);

    public static final String PARAMETER = "parameter";
    public static final String VALUE = "value";

    @Autowired
    private RestApiSvc restApiSvc;

    @Autowired
    private DataTableSvc dataTableSvc;

    @Autowired
    private StateSvc stateSvc;

    @Autowired
    private FileDirUtils fileDirUtils;

    @Autowired
    private WorkspaceDirSvc workspaceDirSvc;

    @Autowired
    private ScenarioUtils scenarioUtils;

    public void writeResponseToReport() {
        scenarioUtils.write(restApiSvc.getResponseAsString());
    }

    public void setBaseUri(final String baseUri) {
        restApiSvc.setApiBaseUri(stateSvc.expandExpression(baseUri));
    }

    public void setApiEndPoint(final String endPoint) {
        restApiSvc.setApiEndPoint(stateSvc.expandExpression(endPoint));
    }

    public void setApiEndPointParams(final Map<String, String> endPointParams) {
        endPointParams.remove(PARAMETER, VALUE);
        endPointParams.forEach((key, value) -> endPointParams.replace(key, stateSvc.expandExpression(value)));
        restApiSvc.setEndPointParamsVar(endPointParams);
    }

    public void setAuthenticationParams(final String username, final String password) {
        restApiSvc.setBasicAuthentication(stateSvc.expandExpression(username), stateSvc.expandExpression(password));
    }

    public void setAuthenticationWithToken(final String oAuthToken) {
        restApiSvc.setBasicAuthentication(stateSvc.expandExpression(oAuthToken));
    }

    public void setApiBodyParam(final String bodyParam) {
        String expandBodyParam = stateSvc.expandExpression(bodyParam);
        if (expandBodyParam.startsWith(FILE_PREFIX)) {
            expandBodyParam = fileDirUtils.readFileToString(workspaceDirSvc.normalize(expandBodyParam.replaceFirst(FILE_PREFIX, "").trim()));
        }
        restApiSvc.setBodyParam(expandBodyParam);
    }

    public void setHeaderParams(final Map<String, String> headerParams) {
        headerParams.remove(PARAMETER, VALUE);
        headerParams.forEach((key, value) -> headerParams.replace(key, stateSvc.expandExpression(value)));
        restApiSvc.setHeaderParams(headerParams);
    }

    public void sendGetRequest() {
        restApiSvc.sendRequest(RequestType.GET);
    }

    public void sendPostRequest() {
        restApiSvc.sendRequest(RequestType.POST);
    }

    public void sendDeleteRequest() {
        restApiSvc.sendRequest(RequestType.DELETE);
    }

    public void sendPutRequest() {
        restApiSvc.sendRequest(RequestType.PUT);
    }

    public void sendPatchRequest() {
        restApiSvc.sendRequest(RequestType.PATCH);
    }

    public void expectStatusCodeAs(final Integer statusCode) {
        final Integer actualStatusCode = restApiSvc.getStatusCode();
        LOGGER.debug("Actual Status Code is [{}]", actualStatusCode);
        if (!statusCode.equals(actualStatusCode)) {
            LOGGER.error("Expected Status Code [{}], but Actual Status Code is [{}]", statusCode, actualStatusCode);
            throw new ApiException(ApiExceptionType.VERIFICATION_FAILED, "Expected Status Code [{}], but Actual Status Code is [{}]", statusCode, actualStatusCode);
        }
    }

    public void expectResponseShouldContains(final String expectedResp) {
        final String actualResponse = restApiSvc.getResponseAsString();
        LOGGER.debug("Actual Response is [{}]", actualResponse);
        if (!actualResponse.contains(expectedResp)) {
            LOGGER.error("Actual Response Body does not contain [{}]", expectedResp);
            throw new ApiException(ApiExceptionType.VERIFICATION_FAILED, "Actual Response Body does not contain [{}]", expectedResp);
        }
    }

    public void expectResponseShouldIncludeFollowingJsonKeyValues(final Map<String, String> jsonKeyValue) {
        for (String jsonKey : jsonKeyValue.keySet()) {
            String actualValue = restApiSvc.getValueFromResponse(jsonKey);
            String expectedValue = jsonKeyValue.get(jsonKey);
            if (!actualValue.contains(expectedValue)) {
                LOGGER.error("Expected Json Key [{}] value is [{}] and Actual value is [{}]", jsonKey, expectedValue, actualValue);
                throw new ApiException(ApiExceptionType.VERIFICATION_FAILED, "Expected Json Key [{}] value is [{}] and Actual value is [{}]", jsonKey, expectedValue, actualValue);
            }
        }
    }

    public void extractResponseValuesIntoVars(final Map<String, String> jsonKeyVars) {
        try {
            for (Map.Entry<String, String> entry : jsonKeyVars.entrySet()) {
                String key = stateSvc.expandExpression(entry.getKey());
                String value = restApiSvc.getJsonObjectFromResponse(key).toString();
                stateSvc.setStringVar(key, value);
            }
        } catch (Exception e) {
            LOGGER.error("Exception occurred while extracting Api json values", e);
            throw new ApiException(ApiExceptionType.PROCESSING_FAILED, "Exception occurred while extracting Api json values");
        }
    }
}
