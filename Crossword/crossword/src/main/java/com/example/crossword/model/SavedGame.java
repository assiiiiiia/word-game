package com.example.crossword.model;

import jakarta.persistence.*;

@Entity
@Table(name = "saved_games")
public class SavedGame {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long playerId;
    private int level;
    private int score;
    private String foundWords; 
    @Column(name = "time_left", nullable = true)
    private Integer timeLeft; 



    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getPlayerId() { return playerId; }
    public void setPlayerId(Long playerId) { this.playerId = playerId; }

    public int getLevel() { return level; }
    public void setLevel(int level) { this.level = level; }

    public int getScore() { return score; }
    public void setScore(int score) { this.score = score; }

    public String getFoundWords() { return foundWords; }
    public void setFoundWords(String foundWords) { this.foundWords = foundWords; }
    
    public Integer getTimeLeft() { return timeLeft; }
    public void setTimeLeft(Integer timeLeft) { this.timeLeft = timeLeft; }
}