package com.zhongqi.dao.impl;

import com.zhongqi.dao.MatchApplyGradeDao;
import com.zhongqi.entity.MatchApplyGrade;
import com.zhongqi.entity.constant.MatchApplyGradeConstant;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by ningcs on 2017/9/13.
 */
@Repository
public class MatchApplyGradeDaoImpl implements MatchApplyGradeDao{

    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    @Value("${match_half_final_goldenRank_}")
    private String  match_half_final_goldenRank_;

    @Override
    public List<MatchApplyGrade> getMatchApplyGradeList(Integer page, Integer page_size, String idNumber,
                                                        Integer type,String matchTime) {
        Map<String,Object>  params =new HashedMap();

        String sql ="select * from MatchApplyGrade where matchType=:matchType ";
        params.put("matchType",type);
        if (idNumber==null || "".equals(idNumber.trim()) && type!=null && type==MatchApplyGradeConstant.MATCH_HALF_FINAL){
            sql=sql+"  and goldenRank <= :goldenRank";
            params.put("goldenRank",match_half_final_goldenRank_);
        }
        if(matchTime!=null && !"".equals(matchTime.trim())){
            sql=sql+"  and matchTime=:matchTime";
            params.put("matchTime",matchTime);
        }
        if(idNumber!=null && !"".equals(idNumber.trim())){
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
        List<MatchApplyGrade> matchApplyGradeList =null;
        try {
            matchApplyGradeList=  jdbcTemplate.query(sql,params,new BeanPropertyRowMapper<MatchApplyGrade>(MatchApplyGrade.class));

        }catch (Exception e){
            e.printStackTrace();
        }

        return matchApplyGradeList;
    }

    @Override
    public Integer getMatchApplyGradeListCount(String idNumber, Integer type, String matchTime) {
        Map<String,Object>  params =new HashedMap();
        String sql ="select count(*) from MatchApplyGrade where matchType=:matchType ";
        params.put("matchType",type);
        if (idNumber==null && type!=null && type==MatchApplyGradeConstant.MATCH_HALF_FINAL){
            sql=sql+"  and goldenRank <= :goldenRank";
            params.put("goldenRank",match_half_final_goldenRank_);
        }
        if(matchTime!=null && !"".equals(matchTime.trim())){
            sql=sql+"  and matchTime=:matchTime";
            params.put("matchTime",matchTime);
        }
        if(idNumber!=null && !"".equals(idNumber.trim())){
            sql=sql+" and identityCardNumber=:idNumber";
            params.put("idNumber",idNumber);
        }
        Integer total=0;
        try {
            total=  jdbcTemplate.queryForObject(sql,params,Integer.class);
        }catch (Exception e){
            e.printStackTrace();
        }
        return total;
    }

    @Override
    public MatchApplyGrade getMatchApplyGradeByIdNumberAndMatchType(String idNumber,Integer type) {
        Map<String,Object>  params =new HashedMap();
        String sql ="select * from MatchApplyGrade where matchType=:matchType and identityCardNumber=:idNumber";
        params.put("matchType",type);
        params.put("idNumber",idNumber);
        MatchApplyGrade  matchApplyGrade=null;
        try {
            matchApplyGrade= jdbcTemplate.queryForObject(sql,params,new BeanPropertyRowMapper<MatchApplyGrade>(MatchApplyGrade.class));
        }catch (Exception e){
            e.printStackTrace();
        }
        return matchApplyGrade;
    }

    @Override
    public void addMatchApplyGrade(String identityCardNumber, String playerName, BigDecimal goldenPoint,
                                   BigDecimal silverPoint, BigDecimal heartPoint,Integer goldenRank,
                                   String matchTime, Integer matchType,
                                   double bonus) {

        Map<String,Object>  params =new HashedMap();
        String sql ="insert into MatchApplyGrade( identityCardNumber,playerName, goldenPoint,silverPoint," +
                "heartPoint,goldenRank,matchTime,matchType,bonus,createDatetime)" +
                "values (:identityCardNumber,:playerName,:goldenPoint,:silverPoint,:heartPoint," +
                ":goldenRank,:matchTime,:matchType,:bonus,:createDatetime)";
        params.put("identityCardNumber",identityCardNumber);
        params.put("playerName",playerName);
        params.put("goldenPoint",goldenPoint);
        params.put("silverPoint",silverPoint);
        params.put("heartPoint",heartPoint);
        params.put("goldenRank",goldenRank);
        params.put("matchTime",matchTime);
        params.put("matchType",matchType);
        params.put("bonus",bonus);
        params.put("createDatetime",new Date());
        jdbcTemplate.update(sql,params);

    }

    @Override
    public MatchApplyGrade getMatchApplyGradeByIdNumberAndMatchTypeAndMatchTime(String idNumber, Integer type, String matchTime) {
        Map<String,Object>  params =new HashedMap();
        String sql ="select * from MatchApplyGrade where identityCardNumber=:idNumber and matchType=:matchType ";
        if (type== MatchApplyGradeConstant.MATCH_HALF_FINAL &&matchTime !=null && !"".equals(matchTime) ){
            sql=sql+" and matchTime=:matchTime";
            params.put("matchTime",matchTime);
        }
        params.put("matchType",type);
        params.put("idNumber",idNumber);
        MatchApplyGrade  matchApplyGrade=null;
        try {
            matchApplyGrade= jdbcTemplate.queryForObject(sql,params,new BeanPropertyRowMapper<MatchApplyGrade>(MatchApplyGrade.class));
        }catch (Exception e){
            e.printStackTrace();
        }
        return matchApplyGrade;
    }
}
