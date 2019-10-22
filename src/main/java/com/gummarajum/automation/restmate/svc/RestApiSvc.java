package com.gummarajum.automation.restmate.svc;

import com.google.common.base.Strings;
import com.gummarajum.automation.restmate.ApiException;
import com.gummarajum.automation.restmate.ApiExceptionType;
import com.gummarajum.automation.restmate.mdl.RequestType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static io.restassured.specification.ProxySpecification.host;

@Service
public class RestApiSvc {

    private static final Logger LOGGER = LoggerFactory.getLogger(RestApiSvc.class);

    private ThreadLocal<Response> response = new ThreadLocal<>();
    private ThreadLocal<RequestSpecification> requestSpec = new ThreadLocal<>();
    private ThreadLocal<String> endPoint = new ThreadLocal<>();
    private ThreadLocal<Map<String, String>> endPointParams = ThreadLocal.withInitial(HashMap::new);
    private ThreadLocal<String> apiBaseUri = new ThreadLocal<>();

    //wrapper method on API interactions
    public Response sendRequest(final RequestType requestType) {
        try {
            if (Strings.isNullOrEmpty(endPoint.get())) {
                LOGGER.error("Endpoint is not set, cannot process request");
                throw new ApiException(ApiExceptionType.UNDEFINED, "Endpoint is not set, cannot process request");
            }

            final String url = apiBaseUri.get() + endPoint.get();
            switch (requestType) {
                case GET:
                    response.set(this.sendGetRequest(url));
                    break;

                case POST:
                    response.set(this.sendPostRequest(url));
                    break;

                case DELETE:
                    response.set(this.sendDeleteRequest(url));
                    break;

                case PUT:
                    response.set(this.sendPutRequest(url));
                    break;

                case PATCH:
                    response.set(this.sendPatchRequest(url));
                    break;
                default:
                    LOGGER.error("Request type [{}] is not implemented", requestType);
                    throw new ApiException(ApiExceptionType.UNDEFINED, "Request type [{}] is not implemented", requestType);
            }

            response.get().then().log().ifError();
            return response.get();
        } finally {
            this.clearRequestSpecifications();
        }
    }


    public void clearRequestSpecifications() {
        LOGGER.debug("Clearing Request Specifications");
        requestSpec = new ThreadLocal<>();
    }

    public RequestSpecification getRequestSpec() {
        return requestSpec.get();
    }

    public Response getResponse() {
        if (null == response) {
            LOGGER.error("Response is Empty");
            throw new ApiException(ApiExceptionType.IO_ERROR, "Response is Empty");
        }
        return response.get();
    }


    public void setApiBaseUri(final String apiBaseUri) {
        LOGGER.debug("Setting Api base url [{}]", apiBaseUri);
        this.apiBaseUri.set(apiBaseUri);
    }

    public void setApiEndPoint(final String apiEndPoint) {
        LOGGER.debug("Setting Api endpoint [{}]", apiEndPoint);
        this.endPoint.set(apiEndPoint);
    }

    public void setEndPointParamsVar(final Map<String, String> params) {
        LOGGER.debug("Setting Api endpoint params [{}]", params);
        this.endPointParams.set(params);
    }

    //Setting basic authentication
    public RequestSpecification setBasicAuthentication(final String username, final String password) {
        LOGGER.debug("Basic authentication using [{}] and [{}]", username, password);
        if (this.getRequestSpec() == null) {
            requestSpec.set(given().auth().basic(username, password));
        } else {
            requestSpec.set(getRequestSpec().auth().basic(username, password));
        }
        return requestSpec.get();
    }


    public RequestSpecification setBasicAuthentication(final String token) {
        LOGGER.debug("Basic authentication with oAuth token [{}]", token);
        if (this.getRequestSpec() == null) {
            requestSpec.set(given().auth().oauth2(token));
        } else {
            requestSpec.set(this.getRequestSpec().auth().oauth2(token));
        }
        return requestSpec.get();
    }


    public RequestSpecification setProxy(final String host, final Integer port, final String username, final String password) {
        if (this.getRequestSpec() == null) {
            requestSpec.set(given());
        } else {
            requestSpec.set(this.getRequestSpec().given());
        }
        requestSpec.set(requestSpec.get().proxy(host(host).and().withPort(port).withAuth(username, password)));
        return requestSpec.get();
    }

    public RequestSpecification enableRequestLogging() {
        if (this.getRequestSpec() == null) {
            requestSpec.set(given());
        } else {
            requestSpec.set(this.getRequestSpec().given());
        }
        return requestSpec.get().log().all();
    }

    //Setting header params
    public RequestSpecification setHeaderParams(final Map<String, String> headerParams) {
        LOGGER.debug("Setting Api header params [{}]", headerParams.toString());
        if (this.getRequestSpec() == null) {
            requestSpec.set(given().headers(headerParams));
        } else {
            requestSpec.set(this.getRequestSpec().given().headers(headerParams));
        }
        return requestSpec.get();
    }

    //Setting body params
    public RequestSpecification setBodyParam(final String bodyContent) {
        LOGGER.debug("Setting body param [{}]", bodyContent);
        if (this.getRequestSpec() == null) {
            requestSpec.set(given().body(bodyContent));
        } else {
            requestSpec.set(this.getRequestSpec().given().body(bodyContent));
        }
        return requestSpec.get();
    }


    private Response sendPostRequest(final String url) {
        if (this.getRequestSpec() == null) {
            return given()
                    .when()
                    .post(url);
        }
        return this.getRequestSpec()
                .when()
                .post(url);
    }

    private Response sendGetRequest(final String url) {
        if (this.getRequestSpec() == null) {
            return given()
                    .params(endPointParams.get())
                    .when()
                    .get(url);
        }
        return this.getRequestSpec()
                .given()
                .params(endPointParams.get())
                .when()
                .get(url);
    }

    private Response sendDeleteRequest(final String url) {
        if (this.getRequestSpec() == null) {
            return given()
                    .when()
                    .delete(url);
        }
        return this.getRequestSpec()
                .when()
                .delete(url);
    }


    private Response sendPutRequest(final String url) {
        if (this.getRequestSpec() == null) {
            return given()
                    .when()
                    .put(url);
        }
        return this.getRequestSpec()
                .when()
                .put(url);
    }

    private Response sendPatchRequest(final String url) {
        if (this.getRequestSpec() == null) {
            return given()
                    .when()
                    .patch(url);
        }
        return this.getRequestSpec()
                .when()
                .patch(url);
    }

    public Integer getStatusCode() {
        return getResponse().getStatusCode();
    }

    public String getResponseAsString() {
        return getResponse().asString();
    }

    public Object getJsonObjectFromResponse(final Response response, final String jsonKey) {
        if (response == null) {
            return null;
        }
        Object object = response.jsonPath().get(jsonKey);
        LOGGER.debug("Response Object for JsonKey [{}] is [{}]", jsonKey, object);
        return object;
    }

    public Object getJsonObjectFromResponse(final String jsonKey) {
        return getJsonObjectFromResponse(getResponse(), jsonKey);
    }

    public String getValueFromResponse(final String jsonKey) {
        String result = getJsonObjectFromResponse(jsonKey).toString();
        LOGGER.debug("JsonKey [{}]:Value [{}]", jsonKey, result);
        return result;
    }

    public <T> T getResponseIntoObject(final Response response, final String jsonKey, final Class<T> clazz) {
        if (response == null) {
            return null;
        }
        return response.jsonPath().getObject(jsonKey, clazz);
    }


}
