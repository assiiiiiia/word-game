package com.example.crossword.model;

import jakarta.persistence.*;

@Entity
@Table(name = "levels")
public class Level {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "level_number") 
    private int levelNumber;
    private String words;
    private String image;

    public Level() {}

    public Long getId() {
        return id;
    }

    public int getLevelNumber() {
        return levelNumber;
    }

    public String getWords() {
        return words;
    }

    public String getImage() {
        return image;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setLevelNumber(int levelNumber) {
        this.levelNumber = levelNumber;
    }

    public void setWords(String words) {
        this.words = words;
    }

    public void setImage(String image) {
        this.image = image;
    }
}