package com.application.anagrammimang.model;

import jakarta.persistence.*;

@Entity
public class Anagrams {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="anagram_Id")
    private Long id;

    private String anagram;


    @ManyToOne
    @JoinColumn(name = "anagram_key", referencedColumnName = "anagram_key")
    private AnagramKeys anagramKey;

    public String getAnagram() {
        return anagram;
    }

    public AnagramKeys getAnagramKey() {
        return anagramKey;
    }
}
