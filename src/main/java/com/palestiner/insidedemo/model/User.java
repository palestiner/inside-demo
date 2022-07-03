package com.palestiner.insidedemo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonView;
import com.palestiner.insidedemo.model.view.Views;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Data
@Entity
@Table(name = "APP_USER")
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID", nullable = false)
    @JsonIgnore
    private Long id;

    @Column(name = "USERNAME", nullable = false, unique = true)
    @JsonView(Views.OnlyUsername.class)
    private String username;

    @Column(name = "PASSWORD", nullable = false)
    @JsonView(Views.Password.class)
    private String password;

    @OneToMany(mappedBy = "user")
    @JsonManagedReference
    private List<Message> messages;

    public User(String username) {
        this.username = username;
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public User() {}
}
