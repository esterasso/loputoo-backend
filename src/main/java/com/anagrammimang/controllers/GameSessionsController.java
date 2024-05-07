package com.anagrammimang.controllers;

import com.anagrammimang.model.GameSessions;
import com.anagrammimang.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class GameSessionsController {

    @Autowired
    private UserService userService;

    @PostMapping("/addScore")
    public String add(@RequestBody GameSessions gamesession) throws Exception {
        userService.addUser(gamesession);
        return "Uus skoor on salvestatud";
    }

    @GetMapping("/getAll")
    public List<GameSessions> list() {
        return userService.getAllUsers();
    }



}
