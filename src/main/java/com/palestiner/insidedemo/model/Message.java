package com.palestiner.insidedemo.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import com.palestiner.insidedemo.model.view.Views;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "MESSAGE")
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID", nullable = false)
    @JsonView(Views.Disabled.class)
    private Long id;

    @Column(name = "CONTENT", nullable = false)
    @JsonView(Views.Message.class)
    @JsonProperty("message")
    private String content;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonView(Views.Message.class)
    @JsonProperty(value = "username")
    @JsonBackReference
    private User user;
}
