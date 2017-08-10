package com.zhongqi.dto;


import com.zhongqi.entity.RatingPersonLeverDetail;

import java.util.List;

/**
 * Created by dell on 2016/4/14.
 */
//@Bean
public class ParameterResult {

    private static List<RatingPersonLeverDetail> personLeverCallEntityList;

    public static List<RatingPersonLeverDetail> getPersonLeverCallEntityList() {
        return personLeverCallEntityList;
    }

    public static void setPersonLeverCallEntityList(List<RatingPersonLeverDetail> personLeverCallEntityList) {
        ParameterResult.personLeverCallEntityList = personLeverCallEntityList;
    }


}
