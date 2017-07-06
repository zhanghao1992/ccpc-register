package com.zhongqi.service;

import com.zhongqi.dto.ResponseRatingForQueryInfo;
import com.zhongqi.entity.PersonRatingRank;

import java.util.List;

/**
 * Created by ningcs on 2017/7/4.
 */
public interface MasterPointQueryService {
//    public JSONObject getRefereeInfoList();
    public ResponseRatingForQueryInfo findMasterPointsRank(String idNumber);


    //获得大师分信息
    public List<PersonRatingRank> getPersonRatingRankInfo();
}
