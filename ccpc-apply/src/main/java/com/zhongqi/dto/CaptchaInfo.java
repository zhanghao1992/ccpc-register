package com.zhongqi.dto;

public class CaptchaInfo {

    private String token;
    private String base64String;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getBase64String() {
        return base64String;
    }

    public void setBase64String(String base64String) {
        this.base64String = base64String;
    }
}
