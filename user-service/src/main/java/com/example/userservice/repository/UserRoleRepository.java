package com.example.userservice.repository;

import com.example.userservice.entity.UserRoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRoleEntity, String> {
    List<UserRoleEntity> findByUserId(String userId);
}
