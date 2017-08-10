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
@FeignClient(value = "imsa-common-file",url = "${feign.url.file-common-service}")
public interface FileService {

    // 上传文件
    @RequestMapping(value = "/commonFile/upload",method = RequestMethod.POST)
    public ResponseResult upload(@RequestParam(value = "base64String") String base64String,
                                 @RequestParam(value = "contentType") String contentType,
                                 @RequestParam(value = "fileName") String fileName,
                                 @RequestParam(value = "suffix") String suffix);

    // 获取文件
    @RequestMapping(value = "/commonFile/getFile",method = RequestMethod.POST)
    public ResponseResult getFile(@RequestParam(value = "fileMD5") String fileMD5);


    @RequestMapping(value = "/commonFile/getFileInfo",method = RequestMethod.POST)
    public FileInfo getFileInfo(@RequestParam(value = "fileMD5") String fileMD5);


    // 生成图形验证码
    @RequestMapping(value = "/captcha/create",method = RequestMethod.POST)
    public ResponseResult createCaptcha();
}
