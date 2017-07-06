package com.zhongqi.service.impl;

import com.google.gson.Gson;
import com.zhongqi.dto.ResponsePersonRatingRankCollection;
import com.zhongqi.dto.ResponseRatingForQueryInfo;
import com.zhongqi.service.MasterPointQueryService;
import com.zhongqi.util.HttpRequestUtils;
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
        jsonObject.element("idNumber",idNumber);
        String params="";
        params="?"+"&&idNumber="+idNumber;
        //        http://dev202.rating.api.cc.cmsa.cn/rating/rank/list.do?page=1&&page_size=100
        JSONObject result = HttpRequestUtils.httpPostJSONObject("http://172.21.122.113:18088/Referee/getRefereeInfoList.do",jsonObject,false);
        String ranking =result.get("ranking").toString();
        String level_name=result.get("level_name").toString();
        ResponseRatingForQueryInfo responseRatingForQueryInfo =new ResponseRatingForQueryInfo();
        responseRatingForQueryInfo.setLevel_name(level_name);
        responseRatingForQueryInfo.setRanking(Integer.parseInt(ranking));
        return responseRatingForQueryInfo;
    }

    @Override
    public ResponsePersonRatingRankCollection getPersonRatingRankInfo(Integer page,Integer page_size) {
        String params="";
        params ="?"+"page="+page+"&&page_size="+page_size;
        String  str = HttpRequestUtils.httpGet(url+"/rating/rank/list.do"+params);
        Gson gson = new Gson();
        ResponsePersonRatingRankCollection responsePersonRatingRankCollection = gson.fromJson(str, ResponsePersonRatingRankCollection.class);
        return responsePersonRatingRankCollection;
    }
}
