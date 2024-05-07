package com.application.anagrammimang.service;

import com.application.anagrammimang.model.GameSessions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.application.anagrammimang.repository.GameSessionsRepository;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private GameSessionsRepository gameSessionsRepository;

    @Override
    public GameSessions getUser(String userName) {
        return gameSessionsRepository.findByUsername(userName);
    }

    @Override
    public void addUser(GameSessions user) {
        GameSessions existing = gameSessionsRepository.findByUsername(user.getUsername());
        if (existing != null && existing.getGameMode().equals(user.getGameMode())) {
            existing.setScore(user.getScore());
            existing.setTime(user.getTime());
            gameSessionsRepository.save(existing);
        } else {
            gameSessionsRepository.save(user);
        }
    }

    @Override
    public List<GameSessions> getAllUsers() {
        return gameSessionsRepository.findAllByOrderByScoreDesc();
    }
}
