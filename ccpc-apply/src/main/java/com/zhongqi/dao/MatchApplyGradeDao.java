package com.zhongqi.dao;

import com.zhongqi.entity.MatchApplyGrade;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by ningcs on 2017/9/13.
 */
public interface MatchApplyGradeDao {

    //获取比赛成绩列表
    public List<MatchApplyGrade> getMatchApplyGradeList(Integer page,Integer page_size,String idNumber,
                                                        Integer type,String matchTime);

    //获取比赛成绩列表数量
    public Integer getMatchApplyGradeListCount(String idNumber,Integer type,String matchTime);

    //通过身份证号判断用户是否录入
    public MatchApplyGrade getMatchApplyGradeByIdNumberAndMatchType(String idNumber,Integer type);

    //添加用户比赛成绩
    public void addMatchApplyGrade(String identityCardNumber,
                                   String playerName,
                                   BigDecimal goldenPoint,
                                   BigDecimal silverPoint,
                                   BigDecimal heartPoint,
                                   Integer goldenRank,
                                   String matchTime,
                                   Integer matchType,
                                   double bonus);


//    //获取个人成绩信息
//    public MatchApplyGrade getMatchApplyGradeByIdNumber(String idNumber);


    //通过身份证号和时间判断用户是否存在
    public MatchApplyGrade getMatchApplyGradeByIdNumberAndMatchTypeAndMatchTime(String idNumber,Integer type,String matchTime);


}
