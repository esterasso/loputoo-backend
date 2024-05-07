package com.application.anagrammimang.model;

import jakarta.persistence.*;

@Entity
public class Words {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="word_Id")
    private Long id;

    private String word;
}
