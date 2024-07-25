package com.gothaxcity.securedoc_be.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.gothaxcity.securedoc_be.enumeration.Authority;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.*;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_DEFAULT;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@Table(name = "roles")
@JsonInclude(NON_DEFAULT)
public class RoleEntity extends Auditable{

    private String name;
    @Enumerated(value = EnumType.STRING)
    private Authority authorities;
}
