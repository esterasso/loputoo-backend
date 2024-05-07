package com.application.anagrammimang.repository;

import com.application.anagrammimang.model.Words;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WordRepository extends JpaRepository<Words, Long> {

    boolean existsByWord(String word);
}
