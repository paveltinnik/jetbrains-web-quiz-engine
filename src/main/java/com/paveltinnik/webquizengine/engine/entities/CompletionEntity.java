package com.paveltinnik.webquizengine.engine.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "completions")
public class CompletionEntity {
    @JsonIgnore
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int completionId;

    @JsonIgnore
    @Column(name = "userEmail")
    private String userEmail;

    @Column(name = "id")
    private int id;

    @Column(name = "completedAt")
    private LocalDateTime completedAt;

    public CompletionEntity() {}

    public CompletionEntity(String userEmail, int id, LocalDateTime completedAt) {
        this.userEmail = userEmail;
        this.id = id;
        this.completedAt = completedAt;
    }

    public int getCompletionId() {
        return completionId;
    }

    public void setCompletionId(int completionId) {
        this.completionId = completionId;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDateTime getCompletedAt() {
        return completedAt;
    }

    public void setCompletedAt(LocalDateTime completedAt) {
        this.completedAt = completedAt;
    }
}
