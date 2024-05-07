package com.anagrammimang.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class AnagramKeys {

    @Id
    @Column(name="anagram_key")
    private String anagramKey;

    private int oneAnagramCount;

    private int twoAnagramCount;

    public String getAnagramKey() {
        return anagramKey;
    }

    public int getOneAnagramCount() {
        return oneAnagramCount;
    }

    public int getTwoAnagramCount() {
        return twoAnagramCount;
    }
}
