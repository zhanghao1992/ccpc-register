package com.zhongqi.service.impl;

import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.request.AlibabaAliqinFcSmsNumSendRequest;
import com.taobao.api.response.AlibabaAliqinFcSmsNumSendResponse;
import com.zhongqi.dao.CheckSMSDao;
import com.zhongqi.entity.CheckSMS;
import com.zhongqi.service.SMSService;
import com.zhongqi.util.BaseUtils;
import com.zhongqi.util.ResponseResult;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;

@Service
public class SMSServiceImpl implements SMSService {
    @Override
    public ResponseResult sendMobileCode(Integer userId, String mobile) {
        ResponseResult result = ResponseResult.errorResult("手机验证码发送失败!");

        //校验手机号码格式是否正确
        if (BaseUtils.isMobile(mobile)) {

            boolean bl = this.sendMobileCodeFunc(userId, mobile);

            if (bl) {
                result = ResponseResult.successResult("手机验证码发送成功!");
            } else {
                result = ResponseResult.errorResult("手机验证码发送失败!");
            }

        } else {
            result = ResponseResult.errorResult("手机格式不正确!");
        }

        return result;
    }

    @Override
    public ResponseResult checkMobileCode(String mobile, String checkCode) {
        ResponseResult result = new ResponseResult();

        Integer checkResult = this.checkMobileCodeFunc(mobile, checkCode);

        if (checkResult == 0) {
            result = ResponseResult.successResult("验证码校验成功!");
        } else if (checkResult == 1) {
            result = ResponseResult.errorResult("验证码校验失败!");
        } else if (checkResult == 2) {
            result = ResponseResult.errorResult("验证码已使用!");
        } else if (checkResult == 3) {
            result = ResponseResult.errorResult("验证码已过期!");
        }

        return result;
    }

    @Override
    public ResponseResult sendCCPCApplyPass(String mobile, String timeStr, String addressStr) {
        ResponseResult result = ResponseResult.errorResult("短信发送失败!");

        //校验手机号码格式是否正确
        if (BaseUtils.isMobile(mobile)) {

            boolean bl = this.sendCCPCApplyPassFunc(mobile, timeStr, addressStr);

            if (bl) {
                result = ResponseResult.successResult("短信发送成功!");
            } else {
                result = ResponseResult.errorResult("短信发送失败!");
            }

        } else {
            result = ResponseResult.errorResult("手机格式不正确!");
        }

        return result;
    }

    @Autowired
    private CheckSMSDao checkSMSDao;

    @Value("${match_manager_checkCode.sms.url}")
    private String url ;

    @Value("${match_manager_checkCode.sms.appKey}")
    private String appKey ;

    @Value("${match_manager_checkCode.sms.appSecret}")
    private String appSecret ;

    @Value("${match_manager_checkCode.sms.signName}")
    private String signName ;

    @Value("${match_manager_checkCode.sms.templateCode}")
    private String templateCode ;


    @Transactional
    public boolean sendMobileCodeFunc(Integer userId, String mobile) {
        boolean bl = false;

        List<CheckSMS> checkSMSList = checkSMSDao.findByMobileOrderBySendTimeDesc(mobile);

        if(checkSMSList!=null && checkSMSList.size()>0 && checkSMSList.get(0)!=null){
            CheckSMS checkSMSed = checkSMSList.get(0);
            Date nowTime = new Date();
            Date sendTime = checkSMSed.getSendTime();
            if((nowTime.getTime()-sendTime.getTime())< 60 * 1000){
                return bl;
            }
        }


        try{

            // save checkSMS
            String checkCode = "";
            Random random = new Random();
            checkCode = checkCode + random.nextInt(9);
            checkCode = checkCode + random.nextInt(9);
            checkCode = checkCode + random.nextInt(9);
            checkCode = checkCode + random.nextInt(9);

            CheckSMS checkSMS = new CheckSMS();
            checkSMS.setUserId(userId);
            checkSMS.setMobile(mobile);
            checkSMS.setCheckCode(checkCode);

            Date now = new Date();
            checkSMS.setSendTime(now);

            Calendar calendar = Calendar.getInstance();
            calendar.setTime(now);
            calendar.add(Calendar.MINUTE, 5);
            Date fail = calendar.getTime();
            checkSMS.setFailureTime(fail);

            checkSMSDao.save(checkSMS);
            // save checkSMS

            // send SMS
            TaobaoClient client = new DefaultTaobaoClient(url, appKey, appSecret);
            AlibabaAliqinFcSmsNumSendRequest req = new AlibabaAliqinFcSmsNumSendRequest();

            req.setSmsType("normal");
            req.setSmsFreeSignName(signName);

            JSONObject parameters = new JSONObject();
            parameters.put("number", checkCode);
            parameters.put("time", "5分钟");

            req.setSmsParamString(parameters.toString());
            req.setRecNum(mobile);
            req.setSmsTemplateCode(templateCode);

            AlibabaAliqinFcSmsNumSendResponse rsp = client.execute(req);

            String body = rsp.getBody();

            System.out.println("短信接口返回:"+body);

            if(body!=null){

                JSONObject bodyJson = JSONObject.fromObject(body);

                bl = bodyJson.getJSONObject("alibaba_aliqin_fc_sms_num_send_response").getJSONObject("result").getBoolean("success");
            }
            // send SMS
        }catch (Exception e){
            e.printStackTrace();
        }

        return bl;
    }

    @Transactional
    public Integer checkMobileCodeFunc(String mobile, String checkCode) {

        Integer result = 1;

        List<CheckSMS> checkSMSList = checkSMSDao.findByMobileAndCheckCodeOrderBySendTimeDesc(mobile,checkCode);

        if(checkSMSList!=null && checkSMSList.size()>0 && checkSMSList.get(0)!=null){

            CheckSMS checkSMS = checkSMSList.get(0);

            Date now = new Date();

            if(checkSMS==null){//判断CheckSMS对象状态
                result = 1;//校验错误
            }else if(checkSMS.isChecked()){
                result = 2;// 校验码已使用
            }else if(checkSMS.getFailureTime().getTime() < now.getTime()){
                result = 3;// 校验码过期
            }else{
                //更新CheckSMS对象校验状态
                checkSMS.setChecked(true);
                checkSMS.setCheckedTime(new Date());

                checkSMSDao.saveAndFlush(checkSMS);

                result = 0;
            }
        }else{
            result = 1;
        }

        return result;
    }

    @Value("${ccpc_apply_pass.sms.url}")
    private String ccpc_apply_pass_sms_url ;

    @Value("${ccpc_apply_pass.sms.appKey}")
    private String ccpc_apply_pass_sms_appKey ;

    @Value("${ccpc_apply_pass.sms.appSecret}")
    private String ccpc_apply_pass_sms_appSecret ;

    @Value("${ccpc_apply_pass.sms.signName}")
    private String ccpc_apply_pass_sms_signName ;

    @Value("${ccpc_apply_pass.sms.templateCode}")
    private String ccpc_apply_pass_sms_templateCode ;


    public boolean sendCCPCApplyPassFunc(String mobile, String timeStr, String addressStr) {
        boolean bl = false;

        try{

            // send SMS
            TaobaoClient client = new DefaultTaobaoClient(ccpc_apply_pass_sms_url, ccpc_apply_pass_sms_appKey, ccpc_apply_pass_sms_appSecret);
            AlibabaAliqinFcSmsNumSendRequest req = new AlibabaAliqinFcSmsNumSendRequest();

            req.setSmsType("normal");
            req.setSmsFreeSignName(ccpc_apply_pass_sms_signName);

            JSONObject parameters = new JSONObject();
            parameters.put("time", timeStr);
            parameters.put("address", addressStr);

            req.setSmsParamString(parameters.toString());
            req.setRecNum(mobile);
            req.setSmsTemplateCode(ccpc_apply_pass_sms_templateCode);

            AlibabaAliqinFcSmsNumSendResponse rsp = client.execute(req);

            String body = rsp.getBody();

            System.out.println("短信接口返回:"+body);

            if(body!=null){

                JSONObject bodyJson = JSONObject.fromObject(body);

                bl = bodyJson.getJSONObject("alibaba_aliqin_fc_sms_num_send_response").getJSONObject("result").getBoolean("success");
            }
            // send SMS
        }catch (Exception e){
            e.printStackTrace();
        }

        return bl;
    }
}
