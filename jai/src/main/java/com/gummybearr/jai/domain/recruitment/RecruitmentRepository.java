package com.gummybearr.jai.domain.recruitment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RecruitmentRepository extends JpaRepository<Recruitment, Long> {

    List<Recruitment> findByHashedValue(Long hashedValue);

    List<Recruitment> findByHashedValueIn(List<Long> hashValues);

    @Query("select r from Recruitment r where r.content like %:word% or r.company like %:word%")
    List<Recruitment> findRecruitmentLike(@Param("word") String word);
}
