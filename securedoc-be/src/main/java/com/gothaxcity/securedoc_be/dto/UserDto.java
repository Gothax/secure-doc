package com.gothaxcity.securedoc_be.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.gothaxcity.securedoc_be.entity.RoleEntity;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

import java.time.LocalDateTime;

import static jakarta.persistence.FetchType.LAZY;

@Data
public class UserDto {

    private Long id;
    private Long createdBy;
    private Long updatedBy;
    private String userId;
    private String email;
    private String firstName;
    private String lastName;
    private Integer loginAttempts;
    private String lastLogin;
    private String updatedAt;
    private String createdAt;
    private String role;
    private String authorities;
    private String phone;
    private String bio;
    private String imageUrl;
    private String qrCodeSecret;
    private String qrCodeImageUri;
    private boolean accountNonExpired;
    private boolean accountNonLocked;
    private boolean credentialsNonExpired;
    private boolean enabled;
    private boolean mfa;


}
