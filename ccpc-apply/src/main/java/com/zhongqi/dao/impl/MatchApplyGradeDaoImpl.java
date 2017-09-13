package com.zhongqi.dao.impl;

import com.zhongqi.dao.MatchApplyGradeDao;
import com.zhongqi.entity.MatchApplyGrade;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Created by ningcs on 2017/9/13.
 */
@Repository
public class MatchApplyGradeDaoImpl implements MatchApplyGradeDao{

    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    @Override
    public List<MatchApplyGrade> getMatchApplyGradeList(Integer page, Integer page_size, String idNumber,
                                                        Integer type,String matchDate) {
        Map<String,Object>  params =new HashedMap();
        String sql ="select * from MatchApplyGrade where matchType=:type and matchDate=:matchDate ";
        if(idNumber!=null && !"".equals(idNumber.trim())){
            sql=sql+" and ";
        }
        return null;
    }
}
