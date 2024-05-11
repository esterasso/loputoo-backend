package com.application.anagrammimang.controllers;

import com.application.anagrammimang.dto.InputDTO;
import com.application.anagrammimang.model.Anagrams;
import com.application.anagrammimang.service.AnagramService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = {"https://anagrammimang-8d5b0c7ce58d.herokuapp.com"})
public class AnagramContoller {

    private final AnagramService anagramService;

    public AnagramContoller(AnagramService anagramService) {
        this.anagramService = anagramService;
    }

    @GetMapping("/anagrams")
    public Anagrams getAnagram(String difficulty) {
        return anagramService.getAnagram(difficulty);
    }

    @GetMapping("/test")
    public String test() {
        return "it works";
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
