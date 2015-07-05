package com.test.testproject.network;

import android.content.Context;
import java.util.HashMap;

public class ApiContent {

    public static final String ERROR_STATUS = "error";
    public static final String SUCCESS_STATUS = "success";

    private String url;
    private HashMap<String, String> params;
    private String response;
    private String status;
    private String error;
    private Object responseObject;


    public static ApiContent createApiContentToGetOrderList(Context context, String orderId) {
        HashMap<String, String> params = initParams(context);
        params.put(ApiService.JSON_FIELD_LAST_ORDER_ID, orderId);
        params.put(ApiService.JSON_FIELD_TOKEN, "2790eeb25c45aebed5ae93c89b259530");   //token should be from preferences
        return new ApiContent("http://depo.sendsms-addon.ru/api/1.0/orders",  params); // url should be from resources
    }

    private ApiContent(String url, HashMap<String, String> params) {
        this.url = url;
        this.params = params;
    }

    private static HashMap<String, String> initParams(Context context) {
        HashMap<String, String> params = new HashMap<String, String>();
        return  params;
    }

    public String getUrl() {
        return url;
    }

    public HashMap<String, String> getParams() {
        return params;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public String getStatus() {
        return status;
    }

    public String getError() {
        return error;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setError(String errors) {
        this.error = errors;
    }

    public boolean hasError() {
        return status.equals(ApiService.JSON_FIELD_ERROR);
    }

    public Object getResponseObject() {
        return responseObject;
    }

    public void setResponseObject(Object responseObject) {
        this.responseObject = responseObject;
    }
}