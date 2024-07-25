package com.gothaxcity.securedoc_be.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.*;
import static jakarta.persistence.FetchType.*;

@Entity
@Getter @Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
@JsonInclude(NON_DEFAULT)
public class UserEntity extends Auditable{
    @Column(unique = true, nullable = false, updatable = false)
    private String userId;

    @Column(unique = true, nullable = false, updatable = false)
    private String email;

    private String firstName;
    private String lastName;
    private Integer loginAttempts;
    private LocalDateTime lastLogin;
    private String phone;
    private String bio;
    private String imageUrl;
    private boolean accountNonExpired;
    private boolean accountNonLocked;
    private boolean enabled;
    private boolean mfa;

    @JsonIgnore
    private String qrCodeSecret;

    @Column(columnDefinition = "text")
    private String qrCodeImageUri;

//    @ManyToOne(fetch = EAGER)
//    @JoinTable(
//            name = "user_roles",
//            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
//            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id")
//    )
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "role_id")
    private RoleEntity role;

}
