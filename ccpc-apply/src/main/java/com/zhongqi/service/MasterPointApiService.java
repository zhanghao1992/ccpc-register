package com.zhongqi.service;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "ccop-edy-api-service")
public interface MasterPointApiService {
    //个人大师分详细信息查询接口
    @RequestMapping(value = "/rating/by_identity_number", method = RequestMethod.GET)
    public String queryPersonRatingHandle(@RequestParam(value = "player_id_number") String player_id_number,
                                          @RequestParam(value = "timestamp") String timestamp);
}
