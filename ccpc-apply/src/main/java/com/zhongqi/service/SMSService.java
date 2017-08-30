package com.zhongqi.service;

import com.zhongqi.util.ResponseResult;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created by blackyadong on 17/4/27.
 */
@FeignClient(value = "imsa-common-sms",url = "${feign.url.ssm-common-service}")
public interface SMSService {

    // 发送验证码
    @RequestMapping(value = "/sms/sendMobileCode",method = RequestMethod.POST)
    public ResponseResult sendMobileCode(@RequestParam(value = "userId") Integer userId,
                                         @RequestParam(value = "mobile") String mobile);

    // 验证手机验证码
    @RequestMapping(value = "/sms/checkMobileCode",method = RequestMethod.POST)
    public ResponseResult checkMobileCode(@RequestParam(value = "mobile") String mobile,
                                          @RequestParam(value = "checkCode") String checkCode);
    //发送CCPC报名成功短信
    @RequestMapping(value = "/sms/sendCCPCApplyPass",method = RequestMethod.POST)
    public ResponseResult sendCCPCApplyPass(@RequestParam(value = "mobile") String mobile,
                                          @RequestParam(value = "timeStr") String timeStr,
                                          @RequestParam(value = "addressStr") String addressStr);
}
