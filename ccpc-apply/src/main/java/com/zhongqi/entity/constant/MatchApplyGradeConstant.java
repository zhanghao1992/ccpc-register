package com.zhongqi.entity.constant;

/**
 * Created by ningcs on 2017/9/14.
 */
public class MatchApplyGradeConstant {

    /**
     * 半决赛
     */
    public static final Integer MATCH_HALF_FINAL = 1;

    /**
     *决赛
     */
    public static final Integer MATCH_FINAL= 2;


    public static String getMatchName(Integer status){

        String statusName="";

        switch (status){
            case 1:
                statusName="半决赛";
                break;
            case 2:
                statusName="决赛";
                break;
            default:
                break;
        }
        return statusName;
    }

    public static Integer getMatchType(String matchName){

        Integer status=0;

        switch (matchName){
            case "半决赛":
                status=1;
                break;
            case "决赛":
                status=2;
                break;
            default:
                break;
        }
        return status;
    }

}
