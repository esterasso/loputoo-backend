package com.anagrammimang.controllers;

import com.anagrammimang.dto.InputDTO;
import com.anagrammimang.model.Anagrams;
import com.anagrammimang.service.AnagramService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = {"http://localhost:3000"})
public class AnagramContoller {

    private final AnagramService anagramService;

    public AnagramContoller(AnagramService anagramService) {
        this.anagramService = anagramService;
    }

    @GetMapping("/anagrams")
    public Anagrams getAnagram(String difficulty) {
        return anagramService.getAnagram(difficulty);
    }

    @PostMapping("/input")
    public ResponseEntity<Boolean> handleInput(@RequestBody InputDTO input) {
        boolean isCorrect = anagramService.handleInput(input);
        if (!isCorrect) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(false);
        } else {
            return ResponseEntity.ok(true);
        }
    }

}
