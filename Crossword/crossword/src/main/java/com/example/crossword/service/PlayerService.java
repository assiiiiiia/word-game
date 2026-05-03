package com.example.crossword.service;

import com.example.crossword.model.Player;
import com.example.crossword.repository.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Service
public class PlayerService {

    @Autowired
    private PlayerRepository playerRepository;

    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    // LOGIN
    public Player login(String username) {
        return playerRepository.findByName(username);
    }

    public Player create(Player player) {
        if (usernameExists(player.getName())) {
            throw new RuntimeException("Username déjà utilisé !");
        }
        // Ne pas re-hasher, le password est déjà hashé dans le controller
        return playerRepository.save(player);
    }
    // USERNAME EXISTS
    public boolean usernameExists(String name) {
        return playerRepository.findByName(name) != null;
    }
}