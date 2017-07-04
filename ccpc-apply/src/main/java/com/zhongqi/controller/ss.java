package com.zhongqi.controller;

import com.zhongqi.util.BaseUtils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by ningcs on 2017/7/4.
 */
public class ss {
    public static void main(String[] args){
        SimpleDateFormat dateFormater = new SimpleDateFormat("yyyy-MM-dd");
        Date date =new Date();
        String date1 =dateFormater.format(date);
        date = BaseUtils.formatStrToDateDay(date1);
        Date date2 =BaseUtils.formatStrToDateDay("2017-9-30");
        //2017-7-4 早于2017-9-30
        if (date.before(date2)){
            System.out.println("ha");
        }


    }
}
