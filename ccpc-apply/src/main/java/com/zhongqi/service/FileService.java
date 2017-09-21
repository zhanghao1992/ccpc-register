package com.zhongqi.service;

import com.zhongqi.util.ResponseResult;
import org.apache.tomcat.jni.FileInfo;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created by songrenfei on 2017/2/17.
 */
public interface FileService {

    // 生成图形验证码
    public ResponseResult createCaptcha();
}
