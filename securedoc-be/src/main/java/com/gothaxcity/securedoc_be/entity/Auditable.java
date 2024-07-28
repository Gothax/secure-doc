package com.gothaxcity.securedoc_be.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.gothaxcity.securedoc_be.domain.RequestContext;
import com.gothaxcity.securedoc_be.exception.ApiException;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Generated;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.util.AlternativeJdkIdGenerator;

import java.time.LocalDateTime;

import static java.time.LocalDateTime.*;

@MappedSuperclass
@Getter @Setter
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties(value = {"createdAt", "updatedAt"}, allowGetters = true)
public abstract class Auditable {
    @Id
    @Column(name = "id", updatable = false)
    @SequenceGenerator(
            name = "primary_key_seq",
            sequenceName = "primary_key_seq",
            allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "primary_key_seq")
    private Long id;
    private String referenceId = new AlternativeJdkIdGenerator().generateId().toString();


    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
    @LastModifiedDate
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;
    @CreatedBy
    @Column(name = "created_by", nullable = false, updatable = false)
    private Long createdBy;
    @LastModifiedBy
    @Column(name = "updated_by", nullable = false)
    private Long updatedBy;


    @PrePersist
    public void beforePersist() {
//        var userId = RequestContext.getUserId();
//        if(userId == null) {
//            throw new ApiException("Cannot persist entity without userId in Request Context for this thread");
//        }
        var userId = 0L;
        setCreatedAt(now());
        setUpdatedAt(now());
        setCreatedBy(userId);
        setUpdatedBy(userId);
    }

    @PreUpdate
    public void beforeUpdate() {
//        var userId = RequestContext.getUserId();
//        if(userId == null) {
//            throw new ApiException("Cannot update entity without userId in Request Context for this thread");
//        }
        var userId = 0L;
        setUpdatedAt(now());
        setUpdatedBy(userId);
    }
}
