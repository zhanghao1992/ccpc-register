package com.zhongqi.service;

import com.zhongqi.dto.ResponsePersonRatingRankCollection;
import com.zhongqi.dto.ResponseRatingForQueryInfo;

/**
 * Created by ningcs on 2017/7/4.
 */
public interface MasterPointQueryService {
//    public JSONObject getRefereeInfoList();
    public ResponseRatingForQueryInfo findMasterPointsRank(String idNumber);


    //获得大师分信息
    public ResponsePersonRatingRankCollection getPersonRatingRankInfo(Integer page,Integer page_size);
}
