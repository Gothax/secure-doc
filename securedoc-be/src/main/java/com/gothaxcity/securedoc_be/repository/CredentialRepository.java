package com.gothaxcity.securedoc_be.repository;

import com.gothaxcity.securedoc_be.entity.CredentialEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CredentialRepository extends JpaRepository<CredentialEntity, Long> {
    Optional<CredentialEntity> getCredentialEntityByUserEntityId(Long userId);
}
