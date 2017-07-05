package com.zhongqi.service.impl;

import com.zhongqi.dto.PersonRatingRankInfo;
import com.zhongqi.dto.ResponseRatingForQueryInfo;
import com.zhongqi.service.MasterPointQueryService;
import com.zhongqi.util.HttpRequestUtils;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * Created by ningcs on 2017/7/4.
 */
@Service
public class MasterPointQueryServiceImpl implements MasterPointQueryService {

    @Value("${get_master_point_ip}")
    private String url;

//    @Override
//    public JSONObject getRefereeInfoList() {
//        JSONObject jsonObject=new JSONObject();
//        jsonObject.element("page",1);
//        jsonObject.element("page_size",10);
//        JSONObject result = HttpRequestUtils.httpPost(url+"/Referee/getRefereeInfoList.do?&page=1&page_size=10",jsonObject,false);
//        return result;
//    }


    @Override
    public ResponseRatingForQueryInfo findMasterPointsRank( String idNumber) {
        JSONObject jsonObject=new JSONObject();
        String params="";
        params="?"+"&&idNumber="+idNumber;
        JSONObject result = HttpRequestUtils.httpPost(url+"/Referee/getRefereeInfoList.do"+params,jsonObject,false);
        String ranking =result.get("ranking").toString();
        String level_name=result.get("level_name").toString();
        ResponseRatingForQueryInfo responseRatingForQueryInfo =new ResponseRatingForQueryInfo();
        responseRatingForQueryInfo.setLevel_name(level_name);
        responseRatingForQueryInfo.setRanking(Integer.parseInt(ranking));
        return responseRatingForQueryInfo;
    }

    @Override
    public PersonRatingRankInfo getPersonRatingRankInfo() {
        String params="";
        JSONObject result = HttpRequestUtils.httpPost(url+"/Referee/getRefereeInfoList.do",null,false);
        return null;
    }
}
