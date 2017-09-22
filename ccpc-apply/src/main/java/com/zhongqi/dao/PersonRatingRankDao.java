package com.zhongqi.dao;

import com.zhongqi.entity.PersonRatingRank;

import java.util.List;

/**
 * Created by ningcs on 2017/7/5.
 */
public interface PersonRatingRankDao {
    //批量导入大师分信息
    public int[] addPersonRatingRankList(List<PersonRatingRank> list );

    //获取排名2016大师分信息
    public List<PersonRatingRank> personRatingRankList(Integer page, Integer page_size,String idNumber);

    public Integer personRatingRankListCount(String idNumber);


}
