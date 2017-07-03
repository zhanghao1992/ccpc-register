package com.zhongqi.controller;

import org.springframework.beans.factory.annotation.Value;

/**
 * Created by songrenfei on 2017/7/3.
 */
public class ApplyController {

    @Value("${cut-off-data}")
    private String cutOffDate;

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

    }

    /**
     * 获取报名时间列表
     */
    public void getMatchApplyDayList(){

    }

    /**
     * 获取报名时间+地点的动态“库存”统计信息
     */
    public void getMatchApplySKU(String matchDayId){

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

}
