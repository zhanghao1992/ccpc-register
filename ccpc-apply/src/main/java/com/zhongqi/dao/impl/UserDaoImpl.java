package com.zhongqi.dao.impl;

import com.zhongqi.dao.UserDao;
import com.zhongqi.entity.User;
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
public class UserDaoImpl implements UserDao {
    @Autowired
    NamedParameterJdbcTemplate jdbcTemplate;

    @Override
    public int[] addUser(List<User> list) {
        Map<String,Object> params = new HashMap<String,Object>();
        String sql = "insert into User(realName,idNumber,mobile,queryTime)" +
                " value (:realName,:idNumber,:mobile,:queryTime) ";

        SqlParameterSource[] batch = SqlParameterSourceUtils.createBatch(list.toArray());
        int[] updateCounts = jdbcTemplate.batchUpdate(sql,batch);
        return updateCounts;
    }
}
