package com.zhongqi.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ningcs on 2017/7/4.
 */
public class ss {
    public static void main(String[] args) {
//        SimpleDateFormat dateFormater = new SimpleDateFormat("yyyy-MM-dd");
//        Date date =new Date();
//        String date1 =dateFormater.format(date);
//        date = BaseUtils.formatStrToDateDay(date1);
//        Date date2 =BaseUtils.formatStrToDateDay("2017-9-30");
//        //2017-7-4 早于2017-9-30
//        if (date.before(date2)){
//            System.out.println("ha");
//        }
//
//        int i=2;
//        for (foo('A') ;foo('B') && (i<4) ;foo('C')){
//            i++;
//            foo('D');
//
//        }
//
//
//    }
//
//
//    static  boolean foo(char c){
//        System.out.println(c);
//        return true;
//
//    }

        List list = new ArrayList() ;          //你的list

        for (int i=0;i<210;i++){
            list.add(i);

        }
        Map map = new HashMap();    //用map存起来新的分组后数据
        int k = 0;
        for(int i = 0;i<list.size();i+=100){
            List  newlist = list.subList(i,i+99);
            map.put(k, newlist);
            k++;
        }
    }


}
