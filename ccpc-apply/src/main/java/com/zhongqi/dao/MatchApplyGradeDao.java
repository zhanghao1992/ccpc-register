package com.zhongqi.dao;

import com.zhongqi.entity.MatchApplyGrade;

import java.util.List;

/**
 * Created by ningcs on 2017/9/13.
 */
public interface MatchApplyGradeDao {

    //获取比赛成绩列表
    public List<MatchApplyGrade> getMatchApplyGradeList(Integer page,Integer page_size,String idNumber,
                                                        Integer type,String matchDate);
}
