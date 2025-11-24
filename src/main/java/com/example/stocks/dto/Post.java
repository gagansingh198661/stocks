package com.example.stocks.dto;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;

@Entity
public class Post {

    private String title;


    @ManyToOne
    private User author;

}
