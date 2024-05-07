package com.application.anagrammimang.repository;

import com.application.anagrammimang.model.GameSessions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GameSessionsRepository extends JpaRepository<GameSessions, Long> {
    GameSessions findByUsername(String username);

    List<GameSessions> findAllByOrderByScoreDesc();
}
