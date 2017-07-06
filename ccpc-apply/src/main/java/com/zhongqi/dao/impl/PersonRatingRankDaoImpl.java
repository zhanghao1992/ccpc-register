package com.zhongqi.dao.impl;

import com.zhongqi.dao.PersonRatingRankDao;
import com.zhongqi.entity.PersonRatingRank;
import org.springframework.beans.factory.annotation.Autowired;
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
        System.out.println("批量添加100条数据运行时间： " + (endTime - startTime) + "ms");
        return updateCounts;
    }
}
