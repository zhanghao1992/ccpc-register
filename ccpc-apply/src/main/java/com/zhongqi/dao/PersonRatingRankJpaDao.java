package com.zhongqi.dao;

import com.zhongqi.entity.PersonRatingRank;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by ningcs on 2017/7/5.
 */
public interface PersonRatingRankJpaDao extends JpaRepository<PersonRatingRank,Integer> {
    public PersonRatingRank findByIdentityCardNumber(String idNumber);
}
