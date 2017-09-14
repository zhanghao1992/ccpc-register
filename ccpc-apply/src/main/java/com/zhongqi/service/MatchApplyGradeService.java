package com.zhongqi.service;

import java.util.List;
import java.util.Map;

/**
 * Created by ningcs on 2017/7/4.
 */
public interface MatchApplyGradeService {

//---------------------------竞技趣味专题-------------------------------------

    //获取排名2016之前的大师分列表
    public Map<String ,Object> personRatingRankList(Integer page, Integer page_size, String idNumber) throws Exception;

    //获取比赛成绩列表
    public Map<String, Object> getMatchApplyGradeList(Integer page, Integer page_size, String idNumber,
                                                      Integer type, String matchTime)throws Exception ;

    //录入比赛成绩
    public Map<String, Object> importMatchApplyGrade(List<List<List<String>>> list);


    }
