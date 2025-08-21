package com.hegazy.accounts.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@Getter @Setter @ToString
public class BaseEntity {

    /*
    * annoatations: @CreatedDate, @LastModifiedDate, ...etc :-
    * tells spring Whenever you see these fields inside a table or an entity, 
    * please make sure you are updating the date time based upon 
    * when the record is being updated or inserted
    
    * marks a field in an entity to be automatically populated with the "current user" (or system/service) that created the record
    *   So instead of manually setting these fields every time, Spring Data takes care of them
    
    * we implement 'AuditAwareImpl' to tell spring who is changing
    *   Spring Data JPA doesn't know who the current user is by default
    *   if we use auth ->> you’d typically fetch the current logged-in username from Spring Security
    */
    
    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @CreatedBy 
    @Column(updatable = false)
    private String createdBy;

    @LastModifiedDate
    @Column(insertable = false)
    private LocalDateTime updatedAt;

    @LastModifiedBy
    @Column(insertable = false)
    private String updatedBy;
}
