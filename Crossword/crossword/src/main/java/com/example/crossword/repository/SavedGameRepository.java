package com.example.crossword.repository;

import com.example.crossword.model.SavedGame;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SavedGameRepository extends JpaRepository<SavedGame, Long> {
    SavedGame findByPlayerId(Long playerId);
}