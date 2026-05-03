package com.example.crossword.controller;

import com.example.crossword.model.Player;
import com.example.crossword.model.SavedGame;
import com.example.crossword.repository.SavedGameRepository;
import com.example.crossword.service.PlayerService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Controller
public class HomeController {

    @Autowired
    private PlayerService playerService;

    @Autowired
    private SavedGameRepository savedGameRepository;

    // ─── WELCOME PAGE ─────────────────────────────────────────────────────────
    @GetMapping("/")
    public String welcome() {
        return "welcome";
    }

    // ─── LOGIN PAGE ───────────────────────────────────────────────────────────
    @GetMapping("/login-page")
    public String loginPage() {
        return "login";
    }

    // ─── REGISTER PAGE ────────────────────────────────────────────────────────
    @GetMapping("/register-page")
    public String registerPage() {
        return "register";
    }

    // ─── LOGIN ────────────────────────────────────────────────────────────────
    @PostMapping("/login")
    public String login(@RequestParam String name,
                        @RequestParam String password,
                        HttpSession session,
                        Model model) {

        Player player = playerService.login(name);

        if (player == null) {
            model.addAttribute("error", "User not found");
            return "login";
        }

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        if (!encoder.matches(password, player.getPassword())) {
            model.addAttribute("error", "Mot de passe incorrect");
            return "login";
        }

        session.setAttribute("player", player);
        return "redirect:/home";
    }

    // ─── REGISTER ─────────────────────────────────────────────────────────────
    @PostMapping("/register")
    public String register(@RequestParam String name,
                           @RequestParam String sexe,
                           @RequestParam String password,
                           HttpSession session,
                           Model model) {

        if (playerService.usernameExists(name)) {
            model.addAttribute("error", "❌ Ce nom est déjà pris, choisissez un autre !");
            return "register";
        }

        Player p = new Player();
        p.setName(name);
        p.setSexe(sexe);
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        p.setPassword(encoder.encode(password));
        p.setScore(0);
        p.setCurrentLevel(1);

        Player saved = playerService.create(p);
        session.setAttribute("player", saved);

        return "redirect:/home";
    }

    // ─── HOME MENU ────────────────────────────────────────────────────────────
    @GetMapping("/home")
    public String home() {
        return "home";
    }

    // ─── LEVELS ───────────────────────────────────────────────────────────────
    @GetMapping("/levels")
    public String levels(HttpSession session, Model model) {
        Player player = (Player) session.getAttribute("player");
        if (player == null) {
            return "redirect:/login-page";
        }

        // Sauvegarde existante → pour afficher le bon niveau avec le badge 💾
        SavedGame save = savedGameRepository.findByPlayerId(player.getId());
        model.addAttribute("savedLevel",  save != null ? save.getLevel() : -1);

        // Score du joueur → pour le système de verrouillage des niveaux
        model.addAttribute("playerScore", player.getScore());

        return "levels";
    }

    // ─── SETTINGS ─────────────────────────────────────────────────────────────
    @GetMapping("/settings")
    public String settings(HttpSession session, Model model) {
        Player player = (Player) session.getAttribute("player");
        if (player == null) {
            return "redirect:/login-page";
        }
        model.addAttribute("player", player);
        return "settings";
    }
}