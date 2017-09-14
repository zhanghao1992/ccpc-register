package com.zhongqi.service;

import com.zhongqi.dto.ResponseRatingForQueryInfo;
import com.zhongqi.entity.*;
import com.zhongqi.model.MatchAddresssDayDetail;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * Created by ningcs on 2017/7/4.
 */
public interface MatchApplyService {

    //获取截止日期时的固定快照排名
    public ResponseRatingForQueryInfo findMasterPointsRank(String idNumber);

    //获取报名时间列表
    public Map<String,Object> getMatchApplyDayList();

    //获取报名时间+地点的动态“库存”统计信息
    public Map<String,Object> findByMatchDayId(Integer matchDayId );

    //报名参赛
    public MatchAddresssDayDetail applyMatch(String cpId, Integer matchDayId, Integer matchPlaceId, String idNumber);

    //获取等级
    public String getStandardName(BigDecimal g, BigDecimal s, BigDecimal r);

    //批量导入大师分信息
    public void addPersonRatingRankList(List<PersonRatingRank> list);

    //通过身份证号获取大师分信息
    public PersonRatingRank findByIdentityCardNumber(String idNumber);

    //计算大师分总人数
    public Integer findByCountPersonRating();

    //通过身份证号获取报名信息
    public MatchApply findByIdNumber(String idNumber);

    //添加厂商访问记录
    public String addCpSource(String cpId);

    //生成关联userIdCode
    public RelevanceUser createRelevanceUserId(Integer userId);

    //效验userIdCode
    public RelevanceUser findByUserIdCode(String userIdCode);

    //效验userId
    public RelevanceUser findByUserId(Integer  userId);

    //添加厂商访问记录
    public void AddCpHotCount(String cpId);

    //添加报名人数
    public void  addMatchApplySkuCount(Integer matchDayId, Integer matchPlaceId);

    //效验厂商
    public CpSource findByCpIdCode(String cpIdCode);

//---------------------------竞技趣味专题-------------------------------------
    //获取排名2016之前的大师分列表
    public Map<String ,Object> personRatingRankList(Integer page, Integer page_size,String idNumber) throws Exception;

    //获取比赛成绩列表
    public Map<String, Object> getMatchApplyGradeList(Integer page, Integer page_size, String idNumber,
                                                        Integer type, String matchTime)throws Exception ;


    }
