package com.zhongqi.service;

import com.zhongqi.dto.ResponseRatingForQueryInfo;
import com.zhongqi.entity.MatchDay;
import com.zhongqi.entity.PersonRatingRank;
import com.zhongqi.model.MatchApplySkuInfo;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by ningcs on 2017/7/4.
 */
public interface MatchApplyService {

    //获取截止日期时的固定快照排名
    public ResponseRatingForQueryInfo findMasterPointsRank(String idNumber);

    //获取报名时间列表
    public List<MatchDay> getMatchApplyDayList();

    //获取报名时间+地点的动态“库存”统计信息
    public List<MatchApplySkuInfo> findByMatchDayId(Integer matchDayId);

    //报名参赛
    public void applyMatch(Integer matchDayId, Integer matchPlaceId,String idNumber);

    //获取等级
    public String getStandardName(BigDecimal g, BigDecimal s, BigDecimal r);

    //批量导入大师分信息
    public void addPersonRatingRankList(List<PersonRatingRank> list);
//
//    //批量导入裁判员信息 测试
//    public void addRefereeList(List<Referee> list);

    }
