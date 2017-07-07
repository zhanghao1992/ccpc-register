package com.zhongqi.controller;

import com.zhongqi.dao.PersonRatingRankJpaDao;
import com.zhongqi.dto.ResponsePersonRatingRankCollection;
import com.zhongqi.dto.ResponsePersonRatingRankInfo;
import com.zhongqi.dto.ResponseRatingForQueryInfo;
import com.zhongqi.entity.PersonRatingRank;
import com.zhongqi.entity.User;
import com.zhongqi.model.UserModel;
import com.zhongqi.service.MasterPointQueryService;
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
    private PersonRatingRankJpaDao personRatingRankJpaDao;

    @Autowired
    private UserService userService;

    @Value("${loginCheckMobileCode}")
    private String loginCheckMobileCode;

    //    @Autowired
    private SMSService smsService;

    @Autowired
    private MasterPointQueryService masterPointQueryService;


    //测试
    @RequestMapping(value = "/addUser", method = {RequestMethod.POST, RequestMethod.GET})
    public void addUser() {
        List<User> list = new ArrayList<>();

        for (int i = 0; i < 1000; i++) {
            User user = new User();
            user.setMobile("" + i);
            user.setRealName("宁春笋");
            user.setIdNumber("411381199401101711");
            user.setQueryTime(new Date());
            list.add(user);
        }

        List<User> newlist = null;
        Integer count = 1000 / 100;
        Integer remain = 1000 % 100;
        Integer j = 0;
        long startTime = System.currentTimeMillis();    // 获取开始时间 毫秒级
        for (int i = 0; i < count; i++) {

            newlist = list.subList(j, j + 100);
            j = j + 100;

            userService.addUser(newlist);
        }
        long endTime = System.currentTimeMillis();    // 获取结束时间 毫秒级
        newlist = list.subList(list.size() - remain, list.size());
        userService.addUser(newlist);
        System.out.println("批量添加数据运行时间： " + (endTime - startTime) + "ms");

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
    @RequestMapping(value = "/getMatchApplyDayList", method = {RequestMethod.POST, RequestMethod.GET})
    public ResponseResult getMatchApplyDayList() {
        return new ResponseResult(ResponseResult.SUCCESS, "success", matchApplyService.getMatchApplyDayList());
    }

    /**
     * 获取报名时间+地点的动态“库存”统计信息
     */
    @RequestMapping(value = "/getMatchApplySKU", method = {RequestMethod.POST, RequestMethod.GET})
    public ResponseResult getMatchApplySKU(Integer matchDayId) {
        return new ResponseResult(ResponseResult.SUCCESS, "success", matchApplyService.findByMatchDayId(matchDayId));
    }

    /**
     * 报名参赛
     */
    @RequestMapping(value = "/applyMatch", method = {RequestMethod.POST, RequestMethod.GET})
    public ResponseResult applyMatch(HttpServletRequest request, Integer matchDayId, Integer matchPlaceId, String idNumber) {
//        UserModel user=(UserModel) request.getSession().getAttribute("user");
//        String idNumber="";
//        if(user!=null){
//            idNumber=user.getIdNumber();
//        }
        if (matchDayId != null && matchDayId != 0 && matchPlaceId != null
                && matchPlaceId != 0 && idNumber != null && "".equals(idNumber.trim())) {
            matchApplyService.applyMatch(matchDayId, matchPlaceId, idNumber);
            return ResponseResult.successResult("报名成功");
        }
        return ResponseResult.errorResult("报名失败");
    }

    /**
     * 获取当前用户信息
     */
    @RequestMapping(value = "/getCurrentUserInfo", method = {RequestMethod.POST, RequestMethod.GET})
    public ResponseResult getCurrentUserInfo(HttpServletRequest request, String realName, String idNumber, String mobile, String checkCode) {
        ResponseResult result = ResponseResult.successResult("参数校验成功");
        if ("true".equals(loginCheckMobileCode)) {
            result = smsService.checkMobileCode(mobile, checkCode);
        }
        if (result.getCode() == ResponseResult.SUCCESS) {
            UserModel userModel = userService.getCurrentUserInfo(realName, idNumber, mobile);
            return new ResponseResult(ResponseResult.SUCCESS, "获取当前信息成功", userModel);
        }
        return ResponseResult.errorResult("获取失败");

    }


    //测试

    /**
     * 获取报名时间列表
     */
    @RequestMapping(value = "/getratingRankInfoList", method = {RequestMethod.POST, RequestMethod.GET})
    public void getratingRankInfoList() {
         Integer ss =matchApplyService.findByCountPersonRating();
        ResponsePersonRatingRankCollection ratingRankInfoList = masterPointQueryService.getPersonRatingRankInfo(1, 200);
        Integer total = ratingRankInfoList.getTotal();
        Integer page =0;
        Integer count =total/1000 ;
        if (count!=0){
            count=count+1;
        }
        Integer page_size =1000;

        for (int i=1 ;i<=count ;i++){
            ResponsePersonRatingRankCollection ratingRankInfo = masterPointQueryService.getPersonRatingRankInfo(i, page_size);
            List<ResponsePersonRatingRankInfo> responsePersonRatingRankInfos = ratingRankInfo.getList();
            List<PersonRatingRank> personRatingRanks = new ArrayList<>();
            for (ResponsePersonRatingRankInfo responsePersonRatingRankInfo : responsePersonRatingRankInfos) {
                PersonRatingRank ratingRank1 =null;
                ratingRank1 =personRatingRankJpaDao.findByIdentityCardNumber(responsePersonRatingRankInfo.getIdentityCardNumber());
                if (ratingRank1 == null) {
                    PersonRatingRank ratingRank = this.setResponsePersonRatingRankInfo(responsePersonRatingRankInfo);
                    personRatingRanks.add(ratingRank);
                }

            }
            if (!responsePersonRatingRankInfos.isEmpty()) {
                matchApplyService.addPersonRatingRankList(personRatingRanks);
            }
        }
    }

    //测试

    /**
     * 获取报名时间列表
     */
    @RequestMapping(value = "/getRefereeList", method = {RequestMethod.POST, RequestMethod.GET})
    public ResponseRatingForQueryInfo getRefereeList() {
        return masterPointQueryService.findMasterPointsRank(null);

    }
    private  PersonRatingRank setResponsePersonRatingRankInfo(ResponsePersonRatingRankInfo rankInfo){
        PersonRatingRank ratingRank = new PersonRatingRank();
        ratingRank.setId(rankInfo.getGoldenRank());
        ratingRank.setIdentityCardNumber(rankInfo.getIdentityCardNumber());
        ratingRank.setPlayerName(rankInfo.getPlayerName());
        ratingRank.setGoldenPoint(rankInfo.getGoldenPoint());
        ratingRank.setSilverPoint(rankInfo.getSilverPoint());
        ratingRank.setHeartPoint(rankInfo.getHeartPoint());
        ratingRank.setCreateDatetime(new Date(rankInfo.getCreateDatetime()));
        ratingRank.setBindDateTime(new Date(rankInfo.getBindDateTime()));
        ratingRank.setBindDateTime(new Date(rankInfo.getBindDateTime()));
        ratingRank.setGoldenRank(rankInfo.getGoldenRank());
        ratingRank.setSilverRank(rankInfo.getSilverRank());
        ratingRank.setHeartRank(rankInfo.getHeartRank());
        ratingRank.setGradeCode(rankInfo.getGradeCode());
        return ratingRank;

    }
}