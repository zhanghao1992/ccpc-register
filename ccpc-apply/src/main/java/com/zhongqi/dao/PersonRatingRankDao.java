package com.zhongqi.dao;

import com.zhongqi.entity.PersonRatingRank;

import java.util.List;

/**
 * Created by ningcs on 2017/7/5.
 */
public interface PersonRatingRankDao {
    //批量导入大师分信息
    public int[] addPersonRatingRankList(List<PersonRatingRank> list );
}
