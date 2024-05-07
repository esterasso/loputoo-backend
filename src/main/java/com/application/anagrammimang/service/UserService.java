package com.application.anagrammimang.service;

import com.application.anagrammimang.model.GameSessions;

import java.util.List;

public interface    UserService {
    GameSessions getUser(String userName);
    void addUser(GameSessions userName) throws Exception;
    List<GameSessions> getAllUsers();
}
