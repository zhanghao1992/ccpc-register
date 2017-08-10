package com.zhongqi.dao;

import com.zhongqi.entity.MatchApplySku;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by ningcs on 2017/7/4.
 */
@Repository
public interface MatchApplySkuJpaDao extends JpaRepository<MatchApplySku,Integer> {

    @Query(value = "select * from MatchApplySku",nativeQuery = true)
    public List<MatchApplySku> findMatchApplySkuList();

    public List<MatchApplySku> findByMatchDayId(Integer matchDayId);

    public MatchApplySku findByMatchDayIdAndMatchPlaceId(Integer matchDayId,Integer matchPlaceId);
}
