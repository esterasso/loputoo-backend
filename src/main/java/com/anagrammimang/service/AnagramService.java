package com.anagrammimang.service;

import com.anagrammimang.dto.InputDTO;
import com.anagrammimang.model.Anagrams;

import java.util.List;

public interface AnagramService {
    Anagrams getAnagram(String difficulty);

    List<Anagrams> getAllAnagrams();

    String sortCharacters(String s);

    boolean handleInput(InputDTO input);
}
