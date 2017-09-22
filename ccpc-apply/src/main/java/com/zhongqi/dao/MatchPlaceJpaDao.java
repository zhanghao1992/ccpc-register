package com.zhongqi.dao;

import com.zhongqi.entity.MatchPlace;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by ningcs on 2017/7/4.
 */
@Repository
public interface MatchPlaceJpaDao extends JpaRepository<MatchPlace,Integer> {
    public MatchPlace findByMatchPlaceId(Integer matchPlaceId);
}
