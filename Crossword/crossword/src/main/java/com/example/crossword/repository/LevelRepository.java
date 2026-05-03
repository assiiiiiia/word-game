package com.example.crossword.repository;

import com.example.crossword.model.Level;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LevelRepository extends JpaRepository<Level, Long> {

    Level findByLevelNumber(int levelNumber);
}