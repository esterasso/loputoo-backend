package com.anagrammimang.service;

import com.anagrammimang.model.GameSessions;

import java.util.List;

public interface    UserService {
    GameSessions getUser(String userName);
    void addUser(GameSessions userName) throws Exception;
    List<GameSessions> getAllUsers();
}
