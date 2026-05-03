package com.example.crossword.repository;

import com.example.crossword.model.Player;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlayerRepository extends JpaRepository<Player, Long> {

    Player findByName(String name);
  
}