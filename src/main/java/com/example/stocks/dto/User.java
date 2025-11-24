package com.example.stocks.dto;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

import java.util.List;

@Entity
public class User {

    @Id
    private Long id;

    private String userName;

    private String email;

    @OneToMany(cascade = CascadeType.ALL,mappedBy = "author")
    protected List<Post> posts;
}
