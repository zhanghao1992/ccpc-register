package com.zhongqi.controller;

import com.alibaba.fastjson.JSONObject;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.zhongqi.dao.PersonRatingRankJpaDao;
import com.zhongqi.dto.ResponseRatingForQueryInfo;
import com.zhongqi.entity.CpSource;
import com.zhongqi.entity.MatchApply;
import com.zhongqi.entity.RelevanceUser;
import com.zhongqi.entity.User;
import com.zhongqi.model.UserModel;
import com.zhongqi.service.*;
import com.zhongqi.util.BaseController;
import com.zhongqi.util.BaseUtils;
import com.zhongqi.util.ResponseResult;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by songrenfei on 2017/7/3.
 */
@RestController
@RequestMapping("apply")
public class ApplyController extends BaseController {

    @Autowired
    private MatchApplyService matchApplyService;

    @Autowired
    private PersonRatingRankJpaDao personRatingRankJpaDao;

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

    @Value("${cut-off-data}")
    private String cutOffDate;



    /**
     * 发送图形验证码
     */
    @ApiOperation(value = "0、发送图形验证码",notes = "0、发送图形验证码")
    @RequestMapping(value = "/createCaptcha", method = {RequestMethod.POST})
    public ResponseResult createCaptcha( HttpServletRequest request, HttpServletResponse response) {
        return fileService.createCaptcha();
    }


    /**
     * 发送手机验证码
     */
    @ApiOperation(value = "1、发送手机验证码",notes = "1、发送手机验证码")
    @RequestMapping(value = "/sendMobileCode", method = {RequestMethod.POST})
    @ApiImplicitParams({
            @ApiImplicitParam(name = "mobile",paramType = "query", value = "手机号", required = true, dataType = "String")
    })
    public ResponseResult sendMobileCode(String mobile, HttpServletRequest request, HttpServletResponse response) {
        Integer userId = 0;
        return smsService.sendMobileCode(userId, mobile);
    }

    /**
     * 获取报名时间列表
     */
    @ApiOperation(value = "2、获取报名时间列表",notes = "2、获取报名时间列表")
    @RequestMapping(value = "/getMatchApplyDayList", method = {RequestMethod.POST})
    public ResponseResult getMatchApplyDayList() {
        return new ResponseResult(ResponseResult.SUCCESS, "success", matchApplyService.getMatchApplyDayList());
    }

    /**
     * 获取报名时间+地点的动态“库存”统计信息
     */
    @ApiOperation(value = "3、获取报名时间+地点的动态“库存”统计信息",notes = "3、获取报名时间+地点的动态“库存”统计信息")
    @RequestMapping(value = "/getMatchApplySKU", method = {RequestMethod.POST})
    @ApiImplicitParams({
            @ApiImplicitParam(name = "matchDayId", paramType = "query",value = "时间id", required = true, dataType = "Integer",defaultValue = "1"),
    })
    public ResponseResult getMatchApplySKU(Integer matchDayId  ) {
        return new ResponseResult(ResponseResult.SUCCESS, "success", matchApplyService.findByMatchDayId(matchDayId));
    }

    /**
     * 报名参赛
     */
    @ApiOperation(value = "4、报名参赛",notes = "4、报名参赛")
    @RequestMapping(value = "/applyMatch", method = {RequestMethod.POST})
    @ApiImplicitParams({
            @ApiImplicitParam(name = "cpId", paramType = "query",value = "厂商id", required = true, dataType = "String",defaultValue = "464c8983f7828ef92883d52"),
            @ApiImplicitParam(name = "matchDayId", paramType = "query",value = "报名时间ID", required = true, dataType = "Integer"),
            @ApiImplicitParam(name = "matchPlaceId", paramType = "query",value = "报名地点ID", required = true, dataType = "Integer"),
            @ApiImplicitParam(name = "idNumber", paramType = "query",value = "身份证号", required = true, dataType = "String"),
    })
    public ResponseResult applyMatch(HttpServletRequest request,
                                     String cpId,
                                     Integer matchDayId,
                                     Integer matchPlaceId,
                                     String idNumber) {
        MatchApply matchApply =null;
        if (cpId==null && "".equals(cpId.trim())){
            return  ResponseResult.errorResult("error");
        }
        CpSource cpSource =matchApplyService.findByCpIdCode(cpId);
        if (cpSource==null){
            return ResponseResult.errorResult("厂商不合法");
        }
        matchApplyService.AddCpHotCount(cpId);
        if (matchDayId != null && matchDayId != 0 && matchPlaceId != null
                && matchPlaceId != 0 && idNumber != null && !"".equals(idNumber.trim())) {
            if(idNumber.length()!=18){
                return  ResponseResult.errorResult("身份证号不合法");
            }
            matchApply =matchApplyService.findByIdNumber(idNumber);
            if (matchApply!=null){
                return ResponseResult.errorResult("该用户已报名");
            }else{
                matchApplyService.addMatchApplySkuCount(matchDayId, matchPlaceId);
                matchApplyService.applyMatch(cpId,matchDayId, matchPlaceId, idNumber);
                return ResponseResult.successResult("报名成功");
            }

        }
        return ResponseResult.errorResult("报名失败");
    }

    /**
     * 查询资格
     */
    @ApiOperation(value = "5、查询资格",notes = "5、查询资格")
    @RequestMapping(value = "/getCurrentUserInfo", method = {RequestMethod.POST})
    @ApiImplicitParams({
            @ApiImplicitParam(name = "cpId", paramType = "query",value = "厂商id", required = true, dataType = "String",defaultValue = "464c8983f7828ef92883d52"),
            @ApiImplicitParam(name = "realName", paramType = "query",value = "真实姓名", required = true, dataType = "String",defaultValue = "陈亚"),
            @ApiImplicitParam(name = "idNumber", paramType = "query",value = "身份证号", required = true, dataType = "String",defaultValue = "310108196312261637"),
            @ApiImplicitParam(name = "mobile", paramType = "query",value = "手机号", required = true, dataType = "String",defaultValue = "18310456275"),
            @ApiImplicitParam(name = "checkCode", paramType = "query",value = "手机验证码", required = true, dataType = "String",defaultValue = "2323"),
    })
    public ResponseResult getCurrentUserInfo(HttpServletRequest request,
                                             String cpId,
                                             String realName, String idNumber, String mobile, String checkCode) {
        ResponseResult result = ResponseResult.successResult("参数校验成功");

        // TODO: 2017/7/10 验证码注释掉
        if (cpId==null && "".equals(cpId.trim())){
            return  ResponseResult.errorResult("error");
        }
       CpSource cpSource =matchApplyService.findByCpIdCode(cpId);
        if (cpSource==null){
            return ResponseResult.errorResult("厂商不合法");
        }
        matchApplyService.AddCpHotCount(cpId);
        if ("false".equals(loginCheckMobileCode)) {
            result = smsService.checkMobileCode(mobile, checkCode);
        }
        UserModel userModel =null;
        userModel =userService.getCurrentUser(idNumber);
        if (userModel==null) {
            ResponseRatingForQueryInfo responseRatingForQueryInfo = masterPointQueryService.findMasterPointsRank(idNumber);
            if (responseRatingForQueryInfo == null) {
                return ResponseResult.errorResult("没有该用户的大师分数据");
            } else {
                if (!realName.trim().equals(responseRatingForQueryInfo.getPlayer_name().trim())) {
                    return ResponseResult.errorResult("姓名和身份证号不匹配");
                }
            }
        }
        if (userModel!=null){
            if(!realName.trim().equals(userModel.getRealName().trim())){
                return ResponseResult.errorResult("姓名和身份证号不匹配");
            }
        }
        userModel = userService.getCurrentUserInfo(realName, idNumber, mobile);
        return new ResponseResult(ResponseResult.SUCCESS, "获取当前信息成功", userModel);
    }

    /**
     * 获取当前用户报名信息
     */
    @ApiOperation(value = "6、获取当前用户报名信息",notes = "6、获取当前用户报名信息")
    @RequestMapping(value = "/getCurrentUserMatchApply", method = {RequestMethod.POST})
    @ApiImplicitParams({
            @ApiImplicitParam(name = "cpId", paramType = "query",value = "厂商id", required = true, dataType = "String",defaultValue = "464c8983f7828ef92883d52"),
            @ApiImplicitParam(name = "idNumber",paramType = "query", value = "身份证号", required = true, dataType = "String",defaultValue="310108196312261637"),
    })
    public ResponseResult getCurrentUserMatchApply(HttpServletRequest request,String cpId, String idNumber) {
        ResponseResult result = ResponseResult.successResult("参数校验成功");
        if (cpId==null && "".equals(cpId.trim())){
            return  ResponseResult.errorResult("error");
        }
        CpSource cpSource =matchApplyService.findByCpIdCode(cpId);
        if (cpSource==null){
            return ResponseResult.errorResult("厂商不合法");
        }
        matchApplyService.AddCpHotCount(cpId);
        if (result.getCode() == ResponseResult.SUCCESS) {
            UserModel userModel = userService.getCurrentUser(idNumber);
            if (userModel!=null){
                return new ResponseResult(ResponseResult.SUCCESS, "获取当前信息成功", userModel);
            }
        }
        return ResponseResult.errorResult("获取失败");

    }

    /**
     * 加密厂商cpId
     */
    @ApiOperation(value = "7、加密厂商cpId",notes = "7、加密厂商cpId")
    @RequestMapping(value = "/addCpSource", method = {RequestMethod.POST})
    @ApiImplicitParams({
            @ApiImplicitParam(name = "cpId",paramType = "query", value = "厂商标识id", required = true, dataType = "String"),
    })
    public ResponseResult addCpSource(HttpServletRequest request, String  cpId) {
        ResponseResult result = ResponseResult.successResult("参数校验成功");
        if (cpId==null && "".equals(cpId.trim())){
            return  ResponseResult.errorResult("error");
        }
        String cpIdCode =matchApplyService.addCpSource(cpId);
        return new ResponseResult(ResponseResult.SUCCESS,"获取当前信息成功",cpIdCode);
    }

    /**
     * 获取分享用户信息
     */
    @ApiOperation(value = "9、获取分享用户信息",notes = "9、获取分享用户信息")
    @RequestMapping(value = "/getRelevanceUserId", method = {RequestMethod.POST})
    @ApiImplicitParams({
            @ApiImplicitParam(name = "cpId", paramType = "query",value = "厂商id", required = true, dataType = "String",defaultValue = "464c8983f7828ef92883d52"),
            @ApiImplicitParam(name = "userIdCode",paramType = "query", value = "用户ID", required = true, dataType = "String",defaultValue = "6244c1aa2eefca4f86cbf31"),
    })
    public ResponseResult getRelevanceUserId(HttpServletRequest request,String cpId, String userIdCode) {
        RelevanceUser relevanceUser =matchApplyService.findByUserIdCode(userIdCode);
        Integer userId=0;
        if (cpId==null && "".equals(cpId.trim())){
            return  ResponseResult.errorResult("error");
        }
        CpSource cpSource =matchApplyService.findByCpIdCode(cpId);
        if (cpSource==null){
            return ResponseResult.errorResult("厂商不合法");
        }
        matchApplyService.AddCpHotCount(cpId);
        if (relevanceUser !=null){
            userId= relevanceUser.getUserId();
            User user =userService.findByUserId(userId);
            if (user!=null){
                UserModel userModel = userService.getCurrentUser(user.getIdNumber());
                return new ResponseResult(ResponseResult.SUCCESS, "success", userModel);
            }
        }
        return ResponseResult.errorResult("获取失败");
    }
    @ApiOperation(value = "10、获取服务器时间",notes = "10、获取服务器时间")
    @RequestMapping(value = "/getCurrentTime", method = {RequestMethod.POST})
    public ResponseResult getCurrentTime() {
        Map<String,Object> map =new HashedMap();
        Date date =new Date();
        Date date1 =BaseUtils.formatStrToDateDayHourMin(cutOffDate+" 08:00:00");
        Boolean checkDate =true;
        if (!date.before(date1)){
            checkDate =false;
        }
        return new ResponseResult(ResponseResult.SUCCESS,"sucess",checkDate);


    }
    @ApiOperation(value = "9、获取二维码",notes = "9、获取二维码")
    @RequestMapping(value = "/getErWeiMa", method = {RequestMethod.POST,RequestMethod.GET})
    public String    getErWeiMa(HttpServletRequest request)throws Exception {
        String filePath = "D://";
        String fileName = "zxing.jpg";
        JSONObject json = new JSONObject();
//        json.put(
//                "zxing",
//                "https://github.com/zxing/zxing/tree/zxing-3.0.0/javase/src/main/java/com/google/zxing");
        json.put("author", "ningcs");
        String content = json.toJSONString();// 内容
        int width = 200; // 图像宽度
        int height = 200; // 图像高度
        String format = "jpg";// 图像类型
        Map<EncodeHintType, Object> hints = new HashMap<EncodeHintType, Object>();
        hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
        BitMatrix bitMatrix = new MultiFormatWriter().encode(content,
                BarcodeFormat.QR_CODE, width, height, hints);// 生成矩阵
        Path path = FileSystems.getDefault().getPath(filePath, fileName);
        MatrixToImageWriter.writeToPath(bitMatrix, format, path);// 输出图像
        System.out.println("输出成功.");

        return filePath+fileName;
    }

}