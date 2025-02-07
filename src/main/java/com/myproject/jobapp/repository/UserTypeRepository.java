package com.myproject.jobapp.repository;

import com.myproject.jobapp.entity.UserType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserTypeRepository extends JpaRepository<UserType, Integer> {

    @Query(value = "SELECT u FROM UserType u WHERE u.id = :theId")
    UserType findUserTypeById(@Param("theId") Integer theId);
}
