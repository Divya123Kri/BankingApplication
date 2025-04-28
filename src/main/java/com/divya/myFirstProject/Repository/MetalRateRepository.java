package com.divya.myFirstProject.Repository;

import com.divya.myFirstProject.entity.MetalRate;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MetalRateRepository extends JpaRepository<MetalRate, Integer> {
    @Query("SELECT m FROM MetalRate m WHERE m.metalType = :metalType ORDER BY m.time DESC")
    List<MetalRate> findLatestMetalRate(@Param("metalType") String metalType, Pageable pageable);

    @Query("SELECT DISTINCT m.metalType FROM MetalRate m")
    List<String> findDistinctMetalTypes();
}
