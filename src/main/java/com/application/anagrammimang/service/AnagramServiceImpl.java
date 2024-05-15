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

        // kontrollitakse, et kerge raskustaseme puhul antaks mängijale anagrammi, millel on vähemalt 2 ühesõnalist ning 10 kahesõnalist anagrammi
        if (Objects.equals(difficulty, "kerge")) {
            qualifyingKeys = anagramKeyRepository.findByOneAnagramCountGreaterThanEqualAndTwoAnagramCountGreaterThanEqual(2, 5);
            int totalAnagrams = qualifyingKeys.size();
            int randomIndex = random.nextInt(totalAnagrams);

            AnagramKeys anagramkey = qualifyingKeys.get(randomIndex);
            System.out.println(anagramkey.getOneAnagramCount() + " " + anagramkey.getTwoAnagramCount());
            Anagrams randomAnagram = anagramRepository.findByAnagramKey(anagramkey).get(0);
            System.out.println(randomAnagram.getAnagram());
            return randomAnagram;
        } else {
            // raske raskustaseme puhul kontrollitakse, et ette antaval sõnal oleks vähemalt 1 ühesõnaline ja 5 kahesõnalist
            qualifyingKeys = anagramKeyRepository.findByOneAnagramCountLessThanEqualAndTwoAnagramCountGreaterThanEqual(1, 5);
            int totalAnagrams = qualifyingKeys.size();
            int randomIndex = random.nextInt(totalAnagrams);

            // lõpuks valitakse suvaline tingimusi täitnud sõna anagram key järgi
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

        // esiteks kontrollitakse ega mängija ei sisestanud sama sõna, mis ette anti
        if (input.getAnagramValue().equals(input.getInputValue())) return false;

        String[] words = input.getInputValue().split("\\s+");

        // nii mängijale ette antud sõna kui ka mängija sisestatud sõna(de) tähed sorteeritakse tähestikuliselt ehk luuakse anagram key
        String mergedWords = mergeWords(input.getInputValue());
        String sortedInput = sortCharacters(mergedWords);
        String sortedWord = sortCharacters(input.getAnagramValue());

        // printimised on vahepealseks testimiseks
        System.out.println(sortedInput);
        System.out.println(sortedWord);

        // hakatakse kontrollima kas loodud sõna(d) on eestikeelsed tähenduslikud sõnad
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
