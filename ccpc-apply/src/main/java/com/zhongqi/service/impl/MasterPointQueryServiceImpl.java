package com.zhongqi.service.impl;

import com.google.gson.Gson;
import com.zhongqi.dto.ResponsePersonRatingRankCollection;
import com.zhongqi.dto.ResponseRatingForQueryInfo;
import com.zhongqi.service.MasterPointQueryService;
import com.zhongqi.util.HttpRequestUtils;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;

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
        Long time =new Date().getTime();
        String params="";
        params="?"+"player_id_number="+idNumber+"&&timestamp="+time;
        JSONObject result = HttpRequestUtils.httpGetJsonObject(url+"/rating/by_identity_number.do"+params);
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
