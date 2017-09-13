package com.zhongqi.dao.impl;

import com.zhongqi.dao.PersonRatingRankDao;
import com.zhongqi.entity.PersonRatingRank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSourceUtils;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ningcs on 2017/7/5.
 */
@Repository
public class PersonRatingRankDaoImpl  implements PersonRatingRankDao{
    @Autowired
    NamedParameterJdbcTemplate jdbcTemplate;

    @Value("${ratingRank}")
    private String ratingRank;

    @Override
    public int[] addPersonRatingRankList(List<PersonRatingRank> list) {
        Map<String,Object> params = new HashMap<String,Object>();
        long startTime = System.currentTimeMillis();    // 获取开始时间 毫秒级
        String sql = "insert into PersonRatingRank(identityCardNumber,playerName,goldenPoint,silverPoint," +
                "heartPoint,createDatetime,bindDateTime,goldenRank,silverRank,heartRank,gradeCode)" +
                " value (:identityCardNumber,:playerName,:goldenPoint,:silverPoint," +
                ":heartPoint,:createDatetime,:bindDateTime,:goldenRank,:silverRank,:heartRank,:gradeCode) ";

        SqlParameterSource[] batch = SqlParameterSourceUtils.createBatch(list.toArray());
        int[] updateCounts = jdbcTemplate.batchUpdate(sql,batch);
        long endTime = System.currentTimeMillis();    // 获取结束时间 毫秒级
        System.out.println("批量添加1000条数据运行时间： " + (endTime - startTime) + "ms");
        return updateCounts;
    }

    @Override
    public List<PersonRatingRank> personRatingRankList(Integer page, Integer page_size,String idNumber) {
        Map<String,Object> params = new HashMap<String,Object>();
        String sql ="select * from PersonRatingRank where goldenRank <=:ratingRank  ";
        params.put("ratingRank",ratingRank);
        if (idNumber!=null && !"".equals(idNumber.trim())){
            sql=sql+" and identityCardNumber=:idNumber";
            params.put("idNumber",idNumber);
        }
        sql=sql+" order by goldenRank asc";
        if (page != null && page_size != null && page != 0 && page_size != 0) {
            sql = sql + " limit :row , :page_size ";
            Integer row = (page - 1) * page_size;
            params.put("row", row);
            params.put("page_size", page_size);
        }
        List<PersonRatingRank> ratingRankList =null;
        try {
            ratingRankList=  jdbcTemplate.query(sql,params,new BeanPropertyRowMapper<PersonRatingRank>(PersonRatingRank.class));

        }catch (Exception e){
            e.printStackTrace();
        }
        return ratingRankList;
    }


    @Override
    public Integer personRatingRankListCount(String idNumber) {
        Map<String,Object> params = new HashMap<String,Object>();

        String sql ="select count(*) from PersonRatingRank where goldenRank <=:ratingRank";
        params.put("ratingRank",ratingRank);
        if (idNumber!=null && !"".equals(idNumber.trim())){
            sql=sql+" and identityCardNumber=:idNumber";
            params.put("idNumber",idNumber);
        }
        Integer total =0;
        try {
            total=jdbcTemplate.queryForObject(sql,params,Integer.class);
        }catch (Exception e){
            e.printStackTrace();
        }
        return total;
    }
}
