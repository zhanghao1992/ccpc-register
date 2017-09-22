package com.zhongqi.service;

import com.zhongqi.util.ResponseResult;

/**
 * Created by blackyadong on 17/4/27.
 */
public interface SMSService {

    public ResponseResult sendMobileCode(Integer userId, String mobile);

    public ResponseResult checkMobileCode(String mobile, String checkCode);

    public ResponseResult sendCCPCApplyPass(String mobile, String timeStr, String addressStr);
}
