package com.gothaxcity.securedoc_be.service;

public interface EmailService {
    void sendNewAccountEmail(String name, String email, String token);
    void sendPasswordResetEmail(String name, String email, String token);
}
