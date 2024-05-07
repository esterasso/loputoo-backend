package com.anagrammimang.repository;

import com.anagrammimang.model.AnagramKeys;
import com.anagrammimang.model.Anagrams;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AnagramRepository extends JpaRepository<Anagrams, Long> {

    List<Anagrams> findByAnagramKey(AnagramKeys anagramKey);
}
