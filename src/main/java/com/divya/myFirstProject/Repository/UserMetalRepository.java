package com.divya.myFirstProject.Repository;

import com.divya.myFirstProject.entity.UserMetal;
import com.divya.myFirstProject.entity.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserMetalRepository extends JpaRepository<UserMetal, Integer> {
    List<UserMetal> findAllByuserid(int userId);

    UserMetal findByUseridAndMetalType(int userid, String metalType);

}
