package com.zhongqi.service;

import com.zhongqi.dto.ResponsePersonRatingRankCollection;
import com.zhongqi.dto.ResponseRatingForQueryInfo;

/**
 * Created by ningcs on 2017/7/4.
 */
public interface MasterPointQueryService {
    //获取个人大师分信息 ok
    public ResponseRatingForQueryInfo findMasterPointsRank(String idNumber);

    //获得大师分信息列表 ok
    public ResponsePersonRatingRankCollection getPersonRatingRankInfo(Integer page,Integer page_size);
}
