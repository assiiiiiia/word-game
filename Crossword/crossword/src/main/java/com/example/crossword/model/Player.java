package com.example.crossword.model;

import jakarta.persistence.*;

@Entity
@Table(name = "players")
public class Player {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String password;
    private String sexe;

    @Column(name = "score")
    private int score = 0;

    @Column(name = "current_level")
    private int currentLevel = 1;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    
    public String getPassword() {return password;}
    public void setPassword(String password) {this.password = password;}

    public String getSexe() { return sexe; }
    public void setSexe(String sexe) { this.sexe = sexe; }

    public int getScore() { return score; }
    public void setScore(int score) { this.score = score; }

    public int getCurrentLevel() { return currentLevel; }
    public void setCurrentLevel(int currentLevel) { this.currentLevel = currentLevel; }
}