package dev.rabauer.ai.demo.frontend.db;

import jakarta.persistence.*;

import java.time.Instant;

@Entity
@Table(name = "user_complaint")
public class UserComplaint {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Instant timestamp;
    private String username;
    private String request;
    private int mood;

    public UserComplaint(Instant timestamp, String username, String request, int mood) {
        this.timestamp = timestamp;
        this.username = username;
        this.request = request;
        this.mood = mood;
    }

    public UserComplaint() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRequest() {
        return request;
    }

    public void setRequest(String request) {
        this.request = request;
    }

    public int getMood() {
        return mood;
    }

    public void setMood(int mood) {
        this.mood = mood;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Instant timestamp) {
        this.timestamp = timestamp;
    }
}
