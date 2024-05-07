package com.anagrammimang.repository;

import com.anagrammimang.model.AnagramKeys;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AnagramKeyRepository extends JpaRepository<AnagramKeys, Long> {

    List<AnagramKeys> findByOneAnagramCountGreaterThanEqualAndTwoAnagramCountGreaterThanEqual(int oneAnagramCount, int twoAnagramCount);

    List<AnagramKeys> findByOneAnagramCountLessThanEqualAndTwoAnagramCountGreaterThanEqual(int oneAnagramCount, int twoAnagramCount);

}
