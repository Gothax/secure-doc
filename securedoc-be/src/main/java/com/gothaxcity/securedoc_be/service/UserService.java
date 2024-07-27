package com.gothaxcity.securedoc_be.service;


import com.gothaxcity.securedoc_be.entity.RoleEntity;

public interface UserService {
    void createUser(String firstName, String lastName, String email, String password);
    RoleEntity getRoleName(String name);
    void verifyAccountKey(String key);
}
