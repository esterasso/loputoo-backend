package com.application.anagrammimang.dto;


import lombok.Data;

@Data
public class GameSessionsDTO {

    private String username;
    private int score;
    private String gameMode;

    // Add constructors, getters, and setters as needed

    public GameSessionsDTO() {
        // Default constructor
    }

    public GameSessionsDTO(String username, int score, String gameMode) {
        this.username = username;
        this.score = score;
        this.gameMode = gameMode;
    }
}

