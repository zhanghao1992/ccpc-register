package com.zhongqi.service.impl;

import com.zhongqi.dto.CaptchaInfo;
import com.zhongqi.service.CaptchaService;
import com.zhongqi.service.FileService;
import com.zhongqi.util.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FileServiceImpl implements FileService {

    @Autowired
    private CaptchaService captchaService;

    @Override
    public ResponseResult createCaptcha() {
        ResponseResult result = ResponseResult.errorResult("生成验证码失败！");
        try {
            CaptchaInfo captchaInfo = captchaService.createCaptcha();

            result = new ResponseResult(ResponseResult.SUCCESS, "生成验证码成功！", captchaInfo);
        }catch (Exception e){

            e.printStackTrace();
        }
        return result;
    }
}
