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

//    //录入比赛成绩
//    public Map<String, Object> importMatchApplyGrade(List<List<List<String>>> list);

    //通过身份证号判断用户是否录入
    public MatchApplyGrade getMatchApplyGradeByIdNumber(String idNumber,Integer type);

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


}
