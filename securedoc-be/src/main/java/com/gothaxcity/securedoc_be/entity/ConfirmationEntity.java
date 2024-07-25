package com.gothaxcity.securedoc_be.entity;


import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.UUID;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_DEFAULT;
import static jakarta.persistence.FetchType.EAGER;
import static jakarta.persistence.GenerationType.UUID;
import static java.util.UUID.*;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@Table(name = "confirmations")
@JsonInclude(NON_DEFAULT)
public class ConfirmationEntity extends Auditable{
    private String key;

    @OneToOne(targetEntity = UserEntity.class, fetch = EAGER)
    @JoinColumn(name = "user_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
    @JsonIdentityReference(alwaysAsId = true)
    @JsonProperty("user_id")
    private UserEntity userEntity;

    public ConfirmationEntity(UserEntity userEntity) {
        this.key = randomUUID().toString();
        this.userEntity = userEntity;
    }
}
