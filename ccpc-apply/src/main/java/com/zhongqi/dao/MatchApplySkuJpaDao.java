package com.zhongqi.dao;

import com.zhongqi.entity.MatchApplySku;
import com.zhongqi.entity.MatchPlace;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by ningcs on 2017/7/4.
 */
@Repository
public interface MatchApplySkuJpaDao extends JpaRepository<MatchApplySku,Integer> {
    public List<MatchApplySku> findByMatchDayId(Integer matchDayId);
}
