package com.gothaxcity.securedoc_be.repository;

import com.gothaxcity.securedoc_be.entity.ConfirmationEntity;
import com.gothaxcity.securedoc_be.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ConfirmationRepository extends JpaRepository<ConfirmationEntity, Long> {
    Optional<ConfirmationEntity> findByKey(String key);
    Optional<ConfirmationEntity> findByUserEntity(UserEntity userEntity);
}
