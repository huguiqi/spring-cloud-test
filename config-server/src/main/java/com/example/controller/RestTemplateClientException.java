package com.example.controller;

public class RestTemplateClientException extends RuntimeException {

    private static final long serialVersionUID = -4899586204551749196L;

    private String code;

    public RestTemplateClientException(String message, String code) {
        super(message);
        this.code = code;
    }

    public RestTemplateClientException(String url, Throwable throwable) {
        super("url:" + url + "\n" +
                throwable.getMessage(), throwable);
    }

    public <T> RestTemplateClientException(String url, Throwable throwable, Object requestData) {
        super("url:" + url + "\n" +
                "requestData:" + requestData + "\n" +
                throwable.getMessage(), throwable);
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

}
