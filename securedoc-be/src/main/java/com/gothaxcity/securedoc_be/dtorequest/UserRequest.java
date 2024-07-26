package com.gothaxcity.securedoc_be.dtorequest;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter @Setter
public class UserRequest {
    @NotEmpty(message = "First name cannot be empty")
    private String firstName;
    @NotEmpty(message = "last name cannot be empty")
    private String lastName;
    @NotEmpty(message = "email name cannot be empty")
    @Email(message = "Invalid email address")
    private String email;
    @NotEmpty(message = "password name cannot be empty")
    private String password;
    private String bio;
    private String phone;
}
