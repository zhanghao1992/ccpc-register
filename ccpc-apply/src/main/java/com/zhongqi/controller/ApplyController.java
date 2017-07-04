package com.zhongqi.controller;

import com.zhongqi.service.MasterPointQueryService;
import com.zhongqi.service.MatchApplyService;
import com.zhongqi.util.BaseController;
import com.zhongqi.util.BaseUtils;
import com.zhongqi.util.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by songrenfei on 2017/7/3.
 */
@RestController
@RequestMapping("apply")
public class ApplyController extends BaseController {

    @Value("${cut-off-data}")
    private String cutOffDate;
    @Autowired
    private MasterPointQueryService masterPointQueryService;

    @Autowired
    private MatchApplyService matchApplyService;

    /**
     * 发送手机验证码
     */
    public void sendMoibleCode(String mobile){

    }

    /**
     * 查询大师分排名
     * > 报名截止日期前-查询实时排名
     * > 报名截止日期后-查询截止日期时的固定快照排名
     */
    public void findMasterPointsRank(String realName,String idNumber,String mobile,String checkCode){
        if (BaseUtils.compareCurrentTime(cutOffDate)){
            masterPointQueryService.findMasterPointsRank(realName,idNumber);
        }else{
            matchApplyService.findMasterPointsRank(realName,idNumber);
        }
    }

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
    public void applyMatch(String matchDayId,String matchPlaceId){

    }

    /**
     * 获取当前用户信息
     */
    public void getCurrentUserInfo(){

    }

//    @ResponseBody
//    @RequestMapping(value = "/getRefereeInfoList",method = {RequestMethod.GET,RequestMethod.POST})
//    public void getRefereeInfoList(HttpServletResponse response){
//        JSONObject jsonObject =httpService.getRefereeInfoList();
//        response (jsonObject.toString(),response);
//    }



}
