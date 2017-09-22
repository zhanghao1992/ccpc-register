package com.zhongqi.dto;

import java.io.Serializable;

/**
 * Created by leo on 16/4/15.
 */
public class ApiResponseResult implements Serializable {
    public String getResp_code() {
        return resp_code;
    }

    public ApiResponseResult setResp_code(String resp_code) {
        this.resp_code = resp_code;
        return this;
    }

    public String getResp_msg() {
        return resp_msg;
    }


    public ApiResponseResult setResp_msg(String resp_msg) {
        this.resp_msg = resp_msg;
        return this;
    }

    private String resp_code;
    private String resp_msg;


    public static ApiResponseResult CreateSuccessResult() {
        return new ApiResponseResult().setResp_code(ResultInfo.SUCCESSCODE).setResp_msg(ResultInfo.SUCCESSMESSAGE);
    }

    public static ApiResponseResult CreateErrorResult(String errCode, String respMessage) {
        return new ApiResponseResult().setResp_code(errCode).setResp_msg(respMessage);
    }


}
