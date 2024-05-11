package com.application.anagrammimang.controllers;

import com.application.anagrammimang.model.GameSessions;
import com.application.anagrammimang.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "https://anagrammimang-8d5b0c7ce58d.herokuapp.com")
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
