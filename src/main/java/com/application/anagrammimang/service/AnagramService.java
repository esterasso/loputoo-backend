package com.application.anagrammimang.service;

import com.application.anagrammimang.dto.InputDTO;
import com.application.anagrammimang.model.Anagrams;

import java.util.List;

public interface AnagramService {
    Anagrams getAnagram(String difficulty);

    List<Anagrams> getAllAnagrams();

    String sortCharacters(String s);

    boolean handleInput(InputDTO input);
}
