package com.zhongqi.service.impl;

import com.google.gson.Gson;
import com.zhongqi.dto.GlobalParameters;
import com.zhongqi.dto.ResponsePersonRatingRankCollection;
import com.zhongqi.dto.ResponseRatingCollection;
import com.zhongqi.dto.ResponseRatingForQueryInfo;
import com.zhongqi.service.MasterPointApiService;
import com.zhongqi.service.MasterPointQueryService;
import com.zhongqi.util.HttpRequestUtils;
import com.zhongqi.util.SecretUtil;
import net.sf.json.JSONObject;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by ningcs on 2017/7/4.
 */
@Service
public class MasterPointQueryServiceImpl implements MasterPointQueryService {

    Log logger = LogFactory.getLog(getClass());

    @Value("${get_master_point_ip}")
    private String url;

    @Autowired
    private MasterPointApiService masterPointApiService;


    @Override
    public ResponseRatingForQueryInfo findMasterPointsRank(String idNumber) {


        //String params = "";
        ResponseRatingForQueryInfo responseRatingForQueryInfo = null;
        //params = "?" + "player_id_number=" + idNumber + "&timestamp=" + time;
        //JSONObject result = HttpRequestUtils.httpGetJsonObject(url + "/rating/by_identity_number.do" + params);


        String cpId = GlobalParameters.CPID;

        String sign = "";
        String secret = "VS4UH1G42UY1EQRXSBSRC3GRL3YYI40C";
        Map<String,String> map = new HashMap<>();
        map.put("cp_id",cpId);
        map.put("player_id_number",idNumber);
        String timestamp = String.valueOf(System.currentTimeMillis());
        map.put("timestamp", timestamp);
        try {
            String p = SecretUtil.signGetParameters(map, secret);
            sign = p.substring(p.indexOf("sign=")+5);
        } catch (Exception e) {
            e.printStackTrace();
        }

        ResponseRatingCollection response = masterPointApiService.getRatingResultInfoByPlayerIdNumber_String_String(cpId,idNumber);

        logger.info("response:"+String.valueOf(response) );

        //JSONObject result = JSONObject.fromObject(response);

        if (response != null ) {
            //String ranking = result.get("ranking").toString();
            //String level_name = result.get("level_name").toString();
            //String player_name = result.get("player_name").toString();

            String ranking = String.valueOf(response.getRanking());
            String level_name = response.getLevel_name();
            String player_name = response.getPlayer_name();
            String g = response.getG();
            String s = response.getS();
            String r = response.getR();

            responseRatingForQueryInfo = new ResponseRatingForQueryInfo();
            responseRatingForQueryInfo.setLevel_name(level_name);
            responseRatingForQueryInfo.setRanking(Integer.parseInt(ranking));
            responseRatingForQueryInfo.setPlayer_name(player_name);
            responseRatingForQueryInfo.setG(new BigDecimal(g));
            responseRatingForQueryInfo.setS(new BigDecimal(s));
            responseRatingForQueryInfo.setR(new BigDecimal(r));
        }

        return responseRatingForQueryInfo;
    }

    public static void main(String[] args){
        String a = "sign=123456789";
        System.out.println(a.substring(a.indexOf("sign=")+5));

        String secret = "VS4UH1G42UY1EQRXSBSRC3GRL3YYI40C";
        Map<String,String> map = new HashMap<>();
        map.put("cp_id","103");
        map.put("player_id_number","371522199709296816");
        String timestamp = String.valueOf(System.currentTimeMillis());
        map.put("timestamp", timestamp);
        try {
            String p = SecretUtil.signGetParameters(map, secret);
            System.out.println(p);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
