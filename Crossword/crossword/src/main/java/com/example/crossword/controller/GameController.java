package com.example.crossword.controller;

import com.example.crossword.model.Level;
import com.example.crossword.model.Player;
import com.example.crossword.model.SavedGame;
import com.example.crossword.repository.PlayerRepository;
import com.example.crossword.repository.SavedGameRepository;
import com.example.crossword.service.GameService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Controller
public class GameController {

    @Autowired
    private GameService gameService;

    @Autowired
    private PlayerRepository playerRepository;

    @Autowired
    private SavedGameRepository savedGameRepository;

    // ─── REPRENDRE LA PARTIE ──────────────────────────────────────────────────
    @GetMapping("/game/resume")
    public String resumeGame(HttpSession session) {
        Player player = (Player) session.getAttribute("player");
        if (player == null) return "redirect:/login-page";

        SavedGame save = savedGameRepository.findByPlayerId(player.getId());
        if (save == null) return "redirect:/levels";

        String url = "redirect:/game/" + save.getLevel()
                   + "?score="      + save.getScore()
                   + "&foundWords=" + (save.getFoundWords() != null ? save.getFoundWords() : "");

        // Ajouter le timer sauvegardé s'il existe (levels 2 et 3)
        if (save.getTimeLeft() != null) {
            url += "&savedTimeLeft=" + save.getTimeLeft();
        }

        return url;
    }

    // ─── AFFICHER UN NIVEAU ───────────────────────────────────────────────────
    @GetMapping("/game/{level:[0-9]+}")
    public String game(@PathVariable int level,
                       @RequestParam(required = false, defaultValue = "0")  int score,
                       @RequestParam(required = false, defaultValue = "")   String foundWords,
                       @RequestParam(required = false)                      Integer savedTimeLeft,
                       HttpSession session,
                       Model model) {

        System.out.println("=== LEVEL REQUESTED = " + level + " ===");

        Player player = (Player) session.getAttribute("player");
        if (player == null) return "redirect:/login-page";

        // Bloquer les niveaux 4 et 5 (supprimés)
        if (level < 1 || level > 3) {
            System.out.println("=== LEVEL OUT OF RANGE → redirect /levels ===");
            return "redirect:/levels";
        }

        Level lvl = gameService.getLevel(level);
        System.out.println("=== LEVEL FOUND IN DB = " + lvl + " ===");

        if (lvl == null) {
            System.out.println("=== LEVEL NOT FOUND → redirect /levels ===");
            return "redirect:/levels";
        }

        Level nextLvl = gameService.getLevel(level + 1);

        model.addAttribute("level",           lvl.getLevelNumber());
        model.addAttribute("words",           lvl.getWords().split(","));
        model.addAttribute("image",           lvl.getImage());
        model.addAttribute("isLastLevel",     nextLvl == null);
        model.addAttribute("savedScore",      score);
        model.addAttribute("savedFoundWords", foundWords);
        model.addAttribute("savedTimeLeft",   savedTimeLeft); // null si pas de timer (level 1)

        // Lettres mélangées aléatoirement
        List<String> letterList = new ArrayList<>(
            Arrays.asList(lvl.getWords().replace(",", "").split(""))
        );
        Collections.shuffle(letterList);
        model.addAttribute("letters", letterList);

        // Retourner la bonne page HTML selon le niveau
        if (level == 1) {
            System.out.println("=== RETURNING → game.html ===");
            return "game";
        }
        if (level == 2) {
            System.out.println("=== RETURNING → game2.html ===");
            return "game2";
        }
        if (level == 3) {
            System.out.println("=== RETURNING → game3.html ===");
            return "game3";
        }

        return "game";
    }

    // ─── TERMINER UN NIVEAU ───────────────────────────────────────────────────
    @PostMapping("/game/complete")
    @ResponseBody
    public String completeLevel(@RequestParam int level,
                                @RequestParam int score,
                                HttpSession session) {

        Player player = (Player) session.getAttribute("player");
        if (player != null) {
            player.setScore(player.getScore() + score);
            player.setCurrentLevel(level + 1);
            playerRepository.save(player);
            session.setAttribute("player", player);
        }
        return "ok";
    }

    // ─── SAUVEGARDER LA PARTIE ────────────────────────────────────────────────
    @PostMapping("/game/save")
    @ResponseBody
    public String saveGame(@RequestParam int    level,
                           @RequestParam int    score,
                           @RequestParam String foundWords,
                           @RequestParam(required = false) Integer timeLeft, // null pour level 1 (pas de timer)
                           HttpSession session) {

        Player player = (Player) session.getAttribute("player");
        if (player == null) return "error";

        SavedGame save = savedGameRepository.findByPlayerId(player.getId());
        if (save == null) {
            save = new SavedGame();
            save.setPlayerId(player.getId());
        }

        save.setLevel(level);
        save.setScore(score);
        save.setFoundWords(foundWords);
        save.setTimeLeft(timeLeft); // stocke null si level 1
        savedGameRepository.save(save);

        return "ok";
    }
}