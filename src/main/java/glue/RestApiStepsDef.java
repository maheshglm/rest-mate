package glue;

import com.gummarajum.automation.restmate.Bootstrap;
import com.gummarajum.automation.restmate.steps.RestApiSteps;
import com.gummarajum.automation.restmate.svc.DataTableSvc;
import cucumber.api.java8.En;
import io.cucumber.datatable.DataTable;

public class RestApiStepsDef implements En {

    private RestApiSteps steps = (RestApiSteps) Bootstrap.getBean(RestApiSteps.class);
    private DataTableSvc dataTableSvc = (DataTableSvc) Bootstrap.getBean(DataTableSvc.class);

    public RestApiStepsDef() {

        Given("I have an Api with base uri {string}", (final String baseUri) -> steps.setBaseUri(baseUri));
        Given("I configure endpoint {string}", (final String endPoint) -> steps.setApiEndPoint(endPoint));
        Given("I configure endpoint {string} with below query parameters", (final String endPoint, final DataTable params) -> {
            steps.setApiEndPoint(endPoint);
            steps.setApiEndPointParams(dataTableSvc.getTwoColumnsAsMap(params));
        });

        Given("I configure request with below header parameters", (final DataTable headerParamsTable) ->
                steps.setHeaderParams(dataTableSvc.getTwoColumnsAsMap(headerParamsTable))
        );

        Given("I authenticate api with username {string} and password {string}", (final String username, final String password) ->
                steps.setAuthenticationParams(username, password)
        );

        Given("I authenticate api with oAuth token {string}", (final String oAuthToken) ->
                steps.setAuthenticationWithToken(oAuthToken)
        );

        Given("I configure request body parameter with below", (final String requestBody) ->
                steps.setApiBodyParam(requestBody)
        );

        When("I submit GET request", () ->
                steps.sendGetRequest()
        );

        When("I submit POST request", () ->
                steps.sendPostRequest()
        );

        When("I submit DELETE request", () ->
                steps.sendDeleteRequest()
        );

        When("I submit PUT request", () ->
                steps.sendPutRequest()
        );

        When("I submit PATCH request", () ->
                steps.sendPatchRequest()
        );

        Then("I embed Api response in the report", () ->
                steps.writeResponseToReport()
        );

        Then("I expect Api response status code should be {int}", (final Integer statusCode) ->
                steps.expectStatusCodeAs(statusCode)
        );

        Then("I expect Api response should contains:", (final String expectedString) ->
                steps.expectResponseShouldContains(expectedString)
        );

        Then("I expect Api response should contains below key values", (final DataTable keyValTable) ->
                steps.expectResponseShouldIncludeFollowingJsonKeyValues(dataTableSvc.getTwoColumnsAsMap(keyValTable))
        );

        Then("I extract below values from Api response into variables", (final DataTable keyVarsTable) ->
                steps.extractResponseValuesIntoVars(dataTableSvc.getTwoColumnsAsMap(keyVarsTable))
        );





    }
}
