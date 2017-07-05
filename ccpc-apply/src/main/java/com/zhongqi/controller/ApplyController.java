package com.zhongqi.controller;

import com.zhongqi.dao.UserDao;
import com.zhongqi.entity.User;
import com.zhongqi.model.UserModel;
import com.zhongqi.service.MatchApplyService;
import com.zhongqi.service.SMSService;
import com.zhongqi.service.UserService;
import com.zhongqi.util.BaseController;
import com.zhongqi.util.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by songrenfei on 2017/7/3.
 */
@RestController
@RequestMapping("apply")
public class ApplyController extends BaseController {

    @Autowired
    private MatchApplyService matchApplyService;

    @Autowired
    private UserService userService;

    @Value("${loginCheckMobileCode}")
    private String loginCheckMobileCode;

//    @Autowired
    private SMSService smsService;

    @Autowired
    UserDao userDao;


    //测试
    @RequestMapping(value = "/addUser", method = {RequestMethod.POST, RequestMethod.GET})
    public void  addUser(){
        List<User> list =new ArrayList<>();
        for (int i =0;i<288 ;i++){
            User user =new User();
            user.setMobile("18310456275");
            user.setRealName("宁春笋");
            user.setIdNumber("411381199401101711");
            user.setQueryTime(new Date());
            list.add(user);
        }
        List<User>  newlist =null;
        for(int i = 0;i<list.size();i+=100){
           newlist = list.subList(i,i+99);
            userDao.addUser(newlist);
        }
    }


    /**
     * 发送手机验证码
     */
    @RequestMapping(value = "/sendMobileCode", method = {RequestMethod.POST, RequestMethod.GET})
    public ResponseResult sendMobileCode(String mobile, HttpServletRequest request, HttpServletResponse response) {
        Integer userId = 0;
        return smsService.sendMobileCode(userId, mobile);
    }

//    /**
//     * 查询大师分排名
//     * > 报名截止日期前-查询实时排名
//     * > 报名截止日期后-查询截止日期时的固定快照排名
//     */
//    public void findMasterPointsRank(String idNumber,String mobile,String checkCode){
//        //// TODO: 2017/7/5 效验手机号
//        if (BaseUtils.compareCurrentTime(cutOffDate)){
//            masterPointQueryService.findMasterPointsRank(idNumber);
//        }else{
//            matchApplyService.findMasterPointsRank(idNumber);
//        }
//    }

    /**
     * 获取报名时间列表
     */
    public ResponseResult getMatchApplyDayList(){
        return  new ResponseResult(ResponseResult.SUCCESS,"success",matchApplyService.getMatchApplyDayList());
    }

    /**
     * 获取报名时间+地点的动态“库存”统计信息
     */
    public ResponseResult getMatchApplySKU(Integer  matchDayId){
        return  new ResponseResult(ResponseResult.SUCCESS,"success",matchApplyService.findByMatchDayId(matchDayId));
    }

    /**
     * 报名参赛
     */
    public ResponseResult applyMatch(HttpServletRequest request, Integer matchDayId, Integer matchPlaceId){
        UserModel user=(UserModel) request.getSession().getAttribute("user");
        String idNumber="";
        if(user!=null){
            idNumber=user.getIdNumber();
        }
        if (matchDayId!=null &&  matchDayId!=0 &&  matchPlaceId!=null
                && matchPlaceId!=0 &&idNumber!=null && "".equals(idNumber.trim())){
            matchApplyService.applyMatch(matchDayId,matchPlaceId,idNumber);
            return ResponseResult.successResult("报名成功");
        }
        return ResponseResult.errorResult("报名失败");
    }

    /**
     * 获取当前用户信息
     */
    public ResponseResult getCurrentUserInfo(HttpServletRequest request ,String realName,String idNumber,String mobile,String checkCode){
        ResponseResult result = ResponseResult.successResult("参数校验成功");
        if ("true".equals(loginCheckMobileCode)) {
            result = smsService.checkMobileCode(mobile, checkCode);
        }
        if (result.getCode() == ResponseResult.SUCCESS) {
            UserModel userModel = userService.getCurrentUserInfo(realName, idNumber, mobile);
            return new ResponseResult(ResponseResult.SUCCESS,"获取当前信息成功",userModel);
        }
        return ResponseResult.errorResult("获取失败");

    }

}
