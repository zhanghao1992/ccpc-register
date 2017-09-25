package com.zhongqi.controller;

import com.zhongqi.dto.GlobalParameters;
import com.zhongqi.dto.ResponseRatingForQueryInfo;
import com.zhongqi.entity.CpSource;
import com.zhongqi.entity.MatchApply;
import com.zhongqi.entity.RelevanceUser;
import com.zhongqi.entity.User;
import com.zhongqi.model.MatchAddresssDayDetail;
import com.zhongqi.model.UserModel;
import com.zhongqi.service.*;
import com.zhongqi.util.BaseController;
import com.zhongqi.util.BaseUtils;
import com.zhongqi.util.ResponseResult;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

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

    @Autowired
    private SMSService smsService;

    @Autowired
    private FileService fileService;

    @Autowired
    private MasterPointQueryService masterPointQueryService;

    // 参赛资格大师分最后截止日期
    @Value("${cut-off-data}")
    private String cutOffDate;

    // 有参赛资格玩家排名
    @Value("${ratingRank}")
    private String ratingRank;


    /**
     * 发送图形验证码
     */
    @ApiOperation(value = "0、发送图形验证码", notes = "0、发送图形验证码")
    @RequestMapping(value = "/createCaptcha", method = {RequestMethod.POST, RequestMethod.GET})
    public ResponseResult createCaptcha(HttpServletRequest request, HttpServletResponse response) {
        return fileService.createCaptcha();
    }


    /**
     * 发送手机验证码
     */
    @ApiOperation(value = "1、发送手机验证码", notes = "1、发送手机验证码")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "mobile", paramType = "query", value = "手机号", required = true, dataType = "String")
    })
    @RequestMapping(value = "/sendMobileCode", method = {RequestMethod.POST, RequestMethod.GET})
    public ResponseResult sendMobileCode(String mobile, HttpServletRequest request, HttpServletResponse response) {
        Integer userId = 0;
        return smsService.sendMobileCode(userId, mobile);
    }

    /**
     * 获取报名时间列表
     */
    @ApiOperation(value = "2、获取报名时间列表", notes = "2、获取报名时间列表")
    @RequestMapping(value = "/getMatchApplyDayList", method = {RequestMethod.POST, RequestMethod.GET})
    public ResponseResult getMatchApplyDayList() {
        return new ResponseResult(ResponseResult.SUCCESS, "success", matchApplyService.getMatchApplyDayList());
    }

    /**
     * 获取报名时间+地点的动态“库存”统计信息
     */
    @ApiOperation(value = "3、获取报名时间+地点的动态“库存”统计信息", notes = "3、获取报名时间+地点的动态“库存”统计信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "matchDayId", paramType = "query", value = "时间id", required = true, dataType = "Integer", defaultValue = "1"),
    })
    @RequestMapping(value = "/getMatchApplySKU", method = {RequestMethod.POST, RequestMethod.GET})
    public ResponseResult getMatchApplySKU(Integer matchDayId) {
        return new ResponseResult(ResponseResult.SUCCESS, "success", matchApplyService.findByMatchDayId(matchDayId));
    }

    /**
     * 报名参赛
     */
    @ApiOperation(value = "4、报名参赛", notes = "4、报名参赛")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "cpId", paramType = "query", value = "厂商id", required = true, dataType = "String", defaultValue = "e44ab68b1c7bb15fc7e014103"),
            @ApiImplicitParam(name = "matchDayId", paramType = "query", value = "报名时间ID", required = true, dataType = "Integer"),
            @ApiImplicitParam(name = "matchPlaceId", paramType = "query", value = "报名地点ID", required = true, dataType = "Integer"),
            @ApiImplicitParam(name = "idNumber", paramType = "query", value = "身份证号", required = true, dataType = "String"),
    })
    @RequestMapping(value = "/applyMatch", method = {RequestMethod.POST, RequestMethod.GET})
    public ResponseResult applyMatch(HttpServletRequest request,
                                     String cpId,
                                     Integer matchDayId,
                                     Integer matchPlaceId,
                                     String idNumber) {
        MatchApply matchApply = null;
        if (cpId == null || "".equals(cpId.trim())) {
            return ResponseResult.errorResult("error");
        }
        CpSource cpSource = matchApplyService.findByCpIdCode(cpId);
        if (cpSource == null) {
            return ResponseResult.errorResult("厂商不合法");
        }else{
            GlobalParameters.CPID = cpSource.getCpId();
        }
        if (BaseUtils.compareCurrentTime(cutOffDate)) {
            return ResponseResult.errorResult("报名尚未开启");
        }

        matchApplyService.AddCpHotCount(cpId);

        if (matchDayId != null && matchDayId != 0 && matchPlaceId != null
                && matchPlaceId != 0 && idNumber != null && !"".equals(idNumber.trim())) {

            if (idNumber.length() != 18) {
                return ResponseResult.errorResult("身份证号不合法");
            }

            ResponseRatingForQueryInfo responseRatingForQueryInfo = matchApplyService.findMasterPointsRank(idNumber);

            if (responseRatingForQueryInfo == null) {
                return ResponseResult.errorResult("没有该用户的大师分数据");
            }
            if (responseRatingForQueryInfo != null && responseRatingForQueryInfo.getRanking() > Integer.parseInt(ratingRank)) {
                return ResponseResult.errorResult("大师分排名没有达到2017之前");
            }

            matchApply = matchApplyService.findByIdNumber(idNumber);

            if (matchApply != null) {
                return ResponseResult.errorResult("该用户已报名");
            } else {
                try {
                    MatchAddresssDayDetail matchAddresssDayDetail = matchApplyService.doMatchApply(cpId, idNumber, matchDayId, matchPlaceId);

                    UserModel userModel = userService.getCurrentUser(idNumber);

                    if (matchAddresssDayDetail != null && userModel != null) {
                        smsService.sendCCPCApplyPass(userModel.getMobile(), matchAddresssDayDetail.getDayDetail(), matchAddresssDayDetail.getPlaceDetail());
                    }

                    return ResponseResult.successResult("报名成功");
                } catch (Exception e) {

                }

            }

        }
        return ResponseResult.errorResult("报名失败");
    }

    /**
     * 查询资格
     */
    @ApiOperation(value = "5、查询资格", notes = "5、查询资格")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "cpId", paramType = "query", value = "厂商id", required = true, dataType = "String", defaultValue = "e44ab68b1c7bb15fc7e014103"),
            @ApiImplicitParam(name = "realName", paramType = "query", value = "真实姓名", required = true, dataType = "String", defaultValue = "陈亚"),
            @ApiImplicitParam(name = "idNumber", paramType = "query", value = "身份证号", required = true, dataType = "String", defaultValue = "310108196312261637"),
            @ApiImplicitParam(name = "mobile", paramType = "query", value = "手机号", required = true, dataType = "String", defaultValue = "18310456275"),
            @ApiImplicitParam(name = "checkCode", paramType = "query", value = "手机验证码", required = true, dataType = "String", defaultValue = "2323"),
    })
    @RequestMapping(value = "/getCurrentUserInfo", method = {RequestMethod.POST, RequestMethod.GET})
    public ResponseResult getCurrentUserInfo(HttpServletRequest request,
                                             String cpId,
                                             String realName, String idNumber, String mobile, String checkCode) {
        ResponseResult result = ResponseResult.successResult("参数校验成功");

        if (cpId == null || "".equals(cpId.trim())) {
            return ResponseResult.errorResult("error");
        }
        CpSource cpSource = matchApplyService.findByCpIdCode(cpId);
        if (cpSource == null) {
            return ResponseResult.errorResult("厂商不合法");
        }else{
            GlobalParameters.CPID = cpSource.getCpId();
        }

        matchApplyService.AddCpHotCount(cpId);

        //手机验证码
        if ("true".equals(loginCheckMobileCode)) {
            result = smsService.checkMobileCode(mobile, checkCode);
            if (result.getCode() != 0) {
                return result;
            }
        }
        Date now = new Date();
        Date cutOff = BaseUtils.formatStrToDate(cutOffDate + " 12:30:00");

        // true 查询资格
        // false 开始报名
        Boolean cutOffStatus = true;
        if (cutOff.before(now)) {
            cutOffStatus = false;
            User user = userService.findByMobile(mobile);
            if (user != null) {
                return ResponseResult.errorResult("手机号已存在");
            }
        }
        UserModel userModel = null;
        userModel = userService.getCurrentUser(idNumber);
        if (userModel == null) {
            ResponseRatingForQueryInfo responseRatingForQueryInfo = masterPointQueryService.findMasterPointsRank(idNumber);
            if (responseRatingForQueryInfo == null) {
                return ResponseResult.successResult("没有该用户的大师分数据");
            } else {
                if (!realName.trim().equals(responseRatingForQueryInfo.getPlayer_name().trim())) {
                    return ResponseResult.errorResult("姓名和身份证号不匹配");
                }
            }
        }

        if (userModel != null) {
            if (!realName.trim().equals(userModel.getRealName().trim())) {
                return ResponseResult.errorResult("姓名和身份证号不匹配");
            }
        }
        userModel = userService.getCurrentUserInfo(realName, idNumber, mobile,cutOffStatus);
        return new ResponseResult(ResponseResult.SUCCESS, "获取当前信息成功", userModel);
    }

    /**
     * 获取当前用户报名信息
     */
    @ApiOperation(value = "6、获取当前用户报名信息", notes = "6、获取当前用户报名信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "cpId", paramType = "query", value = "厂商id", required = true, dataType = "String", defaultValue = "e44ab68b1c7bb15fc7e014103"),
            @ApiImplicitParam(name = "idNumber", paramType = "query", value = "身份证号", required = true, dataType = "String", defaultValue = "310108196312261637"),
    })
    @RequestMapping(value = "/getCurrentUserMatchApply", method = {RequestMethod.POST, RequestMethod.GET})
    public ResponseResult getCurrentUserMatchApply(HttpServletRequest request, String cpId, String idNumber) {
        ResponseResult result = ResponseResult.successResult("参数校验成功");
        if (cpId == null || "".equals(cpId.trim())) {
            return ResponseResult.errorResult("error");
        }
        CpSource cpSource = matchApplyService.findByCpIdCode(cpId);
        if (cpSource == null) {
            return ResponseResult.errorResult("厂商不合法");
        }else{
            GlobalParameters.CPID = cpSource.getCpId();
        }

        matchApplyService.AddCpHotCount(cpId);

        if (result.getCode().equals(ResponseResult.SUCCESS)) {
            UserModel userModel = userService.getCurrentUser(idNumber);
            if (userModel != null) {
                return new ResponseResult(ResponseResult.SUCCESS, "获取当前信息成功", userModel);
            }
        }
        return ResponseResult.errorResult("获取失败");

    }

    /**
     * 加密厂商cpId
     */
    @ApiOperation(value = "7、加密厂商cpId", notes = "7、加密厂商cpId")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "cpId", paramType = "query", value = "厂商标识id", required = true, dataType = "String"),
    })
    @RequestMapping(value = "/addCpSource", method = {RequestMethod.POST, RequestMethod.GET})
    public ResponseResult addCpSource(HttpServletRequest request, String cpId) {
        ResponseResult result = ResponseResult.successResult("参数校验成功");
        if (cpId == null || "".equals(cpId.trim())) {
            return ResponseResult.errorResult("error");
        }
        String cpIdCode = matchApplyService.addCpSource(cpId);
        return new ResponseResult(ResponseResult.SUCCESS, "获取当前信息成功", cpIdCode);
    }

    /**
     * 获取分享用户信息
     */
    @ApiOperation(value = "9、获取分享用户信息", notes = "9、获取分享用户信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "cpId", paramType = "query", value = "厂商id", required = true, dataType = "String", defaultValue = "464c8983f7828ef92883d52"),
            @ApiImplicitParam(name = "userIdCode", paramType = "query", value = "用户ID", required = true, dataType = "String", defaultValue = "6244c1aa2eefca4f86cbf31"),
    })
    @RequestMapping(value = "/getRelevanceUserId", method = {RequestMethod.POST, RequestMethod.GET})
    public ResponseResult getRelevanceUserId(HttpServletRequest request, String cpId, String userIdCode) {
        RelevanceUser relevanceUser = matchApplyService.findByUserIdCode(userIdCode);
        Integer userId = 0;
        if (cpId == null || "".equals(cpId.trim())) {
            return ResponseResult.errorResult("error");
        }
        CpSource cpSource = matchApplyService.findByCpIdCode(cpId);
        if (cpSource == null) {
            return ResponseResult.errorResult("厂商不合法");
        } else {
            GlobalParameters.CPID = cpSource.getCpId();
        }

        matchApplyService.AddCpHotCount(cpId);

        if (relevanceUser != null) {
            userId = relevanceUser.getUserId();
            User user = userService.findByUserId(userId);
            if (user != null) {
                UserModel userModel = userService.getCurrentUser(user.getIdNumber());
                return new ResponseResult(ResponseResult.SUCCESS, "success", userModel);
            }
        }
        return ResponseResult.errorResult("获取失败");
    }

    // true 查询资格
    // false 开始报名
    @ApiOperation(value = "10、获取服务器时间", notes = "10、获取服务器时间")
    @RequestMapping(value = "/getCurrentTime", method = {RequestMethod.POST, RequestMethod.GET})
    public ResponseResult getCurrentTime() {

        Date now = new Date();
        Date cutOff = BaseUtils.formatStrToDate(cutOffDate + " 12:30:00");

        Boolean checkDate = true;

        if (cutOff.before(now)) {
            checkDate = false;
        }
        return new ResponseResult(ResponseResult.SUCCESS, "sucess", checkDate);


    }

}