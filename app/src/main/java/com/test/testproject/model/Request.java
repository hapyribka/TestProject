package com.test.testproject.model;

import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable
public class Request {

    private String url;
    private String request;
    private long time;

    public Request() {
    }

    public Request(String url, String request, long time) {
        this.url = url;
        this.request = request;
        this.time = time;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getRequest() {
        return request;
    }

    public void setRequest(String request) {
        this.request = request;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }
}