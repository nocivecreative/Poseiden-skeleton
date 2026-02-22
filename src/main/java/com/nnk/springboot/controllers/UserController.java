package com.nnk.springboot.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.service.IUserService;

import jakarta.validation.Valid;

/**
 * Contrôleur CRUD pour les utilisateurs.
 */
@Controller
public class UserController {

    private final IUserService userService;

    public UserController(IUserService userService) {
        this.userService = userService;
    }

    /**
     * Affiche la liste des utilisateurs.
     */
    @RequestMapping("/user/list")
    public String home(Model model) {
        model.addAttribute("users", userService.findAll());
        return "user/list";
    }

    /**
     * Affiche le formulaire de création d'un utilisateur.
     */
    @GetMapping("/user/add")
    public String addUser(User bid) {
        return "user/add";
    }

    /**
     * Valide et enregistre un nouvel utilisateur avec mot de passe chiffré.
     */
    @PostMapping("/user/validate")
    public String validate(@Valid User user, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "user/add";
        }
        userService.save(user);
        return "redirect:/user/list";
    }

    /**
     * Affiche le formulaire de modification d'un utilisateur.
     */
    @GetMapping("/user/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        User user = userService.findById(id);
        user.setPassword("");
        model.addAttribute("user", user);
        return "user/update";
    }

    /**
     * Valide et applique la modification d'un utilisateur.
     */
    @PostMapping("/user/update/{id}")
    public String updateUser(@PathVariable("id") Integer id, @Valid User user,
            BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "user/update";
        }
        userService.update(id, user);
        return "redirect:/user/list";
    }

    /**
     * Supprime un utilisateur par son identifiant.
     */
    @PostMapping("/user/delete/{id}")
    public String deleteUser(@PathVariable("id") Integer id) {
        userService.delete(id);
        return "redirect:/user/list";
    }

    /**
     * Affiche la page d'accès refusé (403).
     */
    @GetMapping("/403")
    public String accessDenied() {
        return "403";
    }
}
