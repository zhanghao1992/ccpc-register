package com.zhongqi.dao;

import com.zhongqi.entity.MatchDay;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by ningcs on 2017/7/4.
 */
@Repository
public interface MatchDayJpaDao extends JpaRepository<MatchDay,Integer> {

    //获取报名时间列表
    public List<MatchDay> findByStatus(Integer status);

    public MatchDay findByMatchDayId(Integer matchDayId);
}
