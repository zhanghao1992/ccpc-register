package com.zhongqi.dao;

import com.zhongqi.entity.PersonRatingRank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Created by ningcs on 2017/7/5.
 */
@Repository
public interface PersonRatingRankJpaDao extends JpaRepository<PersonRatingRank,Integer> {

    public PersonRatingRank findByIdentityCardNumber(String idNumber);

    @Query(value = "select  count(*) from PersonRatingRank ",nativeQuery=true)
    public Integer findByCountPersonRating();

}
