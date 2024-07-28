package com.gothaxcity.securedoc_be.service;


import com.gothaxcity.securedoc_be.entity.RoleEntity;
import com.gothaxcity.securedoc_be.enumeration.LoginType;

public interface UserService {
    void createUser(String firstName, String lastName, String email, String password);
    RoleEntity getRoleName(String name);
    void verifyAccountKey(String key);
    void updateLoginAttempt(String email, LoginType loginType);
}
