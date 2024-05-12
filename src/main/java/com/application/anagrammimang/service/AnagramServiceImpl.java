package com.application.anagrammimang.service;

import com.application.anagrammimang.dto.InputDTO;
import com.application.anagrammimang.model.AnagramKeys;
import com.application.anagrammimang.model.Anagrams;
import com.application.anagrammimang.repository.AnagramKeyRepository;
import com.application.anagrammimang.repository.WordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.application.anagrammimang.repository.AnagramRepository;

import java.util.*;


@Service
public class AnagramServiceImpl implements AnagramService {

    @Autowired
    private AnagramRepository anagramRepository;
    @Autowired
    private WordRepository wordRepository;
    @Autowired
    private AnagramKeyRepository anagramKeyRepository;

    @Override
    public Anagrams getAnagram(String difficulty) {

        Random random = new Random();
        List<AnagramKeys> qualifyingKeys;

        if (Objects.equals(difficulty, "kerge")) {
            qualifyingKeys = anagramKeyRepository.findByOneAnagramCountGreaterThanEqualAndTwoAnagramCountGreaterThanEqual(2, 10);
            int totalAnagrams = qualifyingKeys.size();
            int randomIndex = random.nextInt(totalAnagrams);

            AnagramKeys anagramkey = qualifyingKeys.get(randomIndex);
            System.out.println(anagramkey.getOneAnagramCount() + " " + anagramkey.getTwoAnagramCount());
            Anagrams randomAnagram = anagramRepository.findByAnagramKey(anagramkey).get(0);
            System.out.println(randomAnagram.getAnagram());
            return randomAnagram;
        } else {
            qualifyingKeys = anagramKeyRepository.findByOneAnagramCountLessThanEqualAndTwoAnagramCountGreaterThanEqual(1, 5);
            int totalAnagrams = qualifyingKeys.size();
            int randomIndex = random.nextInt(totalAnagrams);

            AnagramKeys anagramkey = qualifyingKeys.get(randomIndex);
            System.out.println(anagramkey.getOneAnagramCount() + " " + anagramkey.getTwoAnagramCount());

            return anagramRepository.findByAnagramKey(anagramkey).get(0);

        }
    }

    @Override
    public List<Anagrams> getAllAnagrams() {
        return anagramRepository.findAll();
    }


    public String sortCharacters(String s) {
        char[] chars = s.toCharArray();
        Arrays.sort(chars);
        return new String(chars);
    }

    private String mergeWords(String input) {
        return input.replaceAll("\\s+", "");
    }

    public boolean handleInput(InputDTO input) {

        if (input.getAnagramValue().equals(input.getInputValue())) return false;

        String[] words = input.getInputValue().split("\\s+");

        String mergedWords = mergeWords(input.getInputValue());
        String sortedInput = sortCharacters(mergedWords);
        String sortedWord = sortCharacters(input.getAnagramValue());

        System.out.println(sortedInput);
        System.out.println(sortedWord);

        boolean isAWord = false;

        if (words.length == 2) {
            boolean firstWord = wordRepository.existsByWord(words[0]);
            boolean secondWord = wordRepository.existsByWord(words[1]);
            if (firstWord && secondWord) isAWord = true;
        } else {
            isAWord = wordRepository.existsByWord(input.getInputValue());
        }

        return sortedInput.equals(sortedWord) && isAWord;
    }

}
