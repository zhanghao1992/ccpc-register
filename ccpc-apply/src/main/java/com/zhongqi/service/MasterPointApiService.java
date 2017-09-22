package com.zhongqi.service;

import com.zhongqi.dto.ResponseRatingCollection;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "ccop-edy-service")
public interface MasterPointApiService {

    //个人大师分详细信息查询接口
    /*
    @RequestMapping(value = "/rating/by_identity_number", method = RequestMethod.GET)
    public String queryPersonRatingHandle(@RequestParam(value = "player_id_number") String player_id_number,
                                          @RequestParam(value = "timestamp") String timestamp,
                                          @RequestParam(value = "cp_id") String cp_id,
                                          @RequestParam(value = "sign") String sign);
    */

    @RequestMapping(value = "/personLastestRatingApplicationgService/getRatingResultInfoByPlayerIdNumber_String_String", method = RequestMethod.POST)
    public ResponseRatingCollection getRatingResultInfoByPlayerIdNumber_String_String(@RequestParam(value = "cp_id")String cp_id,
                                                                                      @RequestParam(value = "player_id_number")String player_id_number);
}
