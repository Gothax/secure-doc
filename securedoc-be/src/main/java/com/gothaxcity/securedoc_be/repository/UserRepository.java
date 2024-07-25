package com.gothaxcity.securedoc_be.repository;

import com.gothaxcity.securedoc_be.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByEmailIgnoreCase(String email);
    Optional<UserEntity> findUserEntityByUserId(String userId);
}
