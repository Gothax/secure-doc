package com.gothaxcity.securedoc_be.entity;

import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_DEFAULT;
import static jakarta.persistence.FetchType.*;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@Table(name = "credentials")
@JsonInclude(NON_DEFAULT)
public class CredentialEntity extends Auditable{
    private String password;

    @OneToOne(targetEntity = UserEntity.class, fetch = EAGER)
    @JoinColumn(name = "user_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
    @JsonIdentityReference(alwaysAsId = true)
    @JsonProperty("user_id")
    private UserEntity userEntity;

    public CredentialEntity(String password, UserEntity user) {
        this.password = password;
        this.userEntity = user;
    }
}
