package ru.clevertec.clevertecTaskRest.dao.entity;

import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@MappedSuperclass
public abstract class BaseEntity {
    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)// CANT BE USED DURING TESTS IN H2 DB
    private Long id;
    @CreatedDate
    private LocalDateTime createdDate;
    @Version
    private LocalDateTime updatedDate;

    public Long getId() {
        return id;
    }

    public BaseEntity setId(Long id) {
        this.id = id;
        return this;
    }

    public BaseEntity setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
        return this;
    }

    public BaseEntity setUpdatedDate(LocalDateTime updatedDate) {
        this.updatedDate = updatedDate;
        return this;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public LocalDateTime getUpdatedDate() {
        return updatedDate;
    }
}
