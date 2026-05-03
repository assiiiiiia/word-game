package com.example.crossword.service;

import com.example.crossword.model.Level;
import com.example.crossword.repository.LevelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GameService {

    @Autowired
    private LevelRepository levelRepository;

    public Level getLevel(int levelNumber) {
        return levelRepository.findByLevelNumber(levelNumber);
    }
}