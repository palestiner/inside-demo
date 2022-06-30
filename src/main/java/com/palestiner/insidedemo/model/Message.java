package com.palestiner.insidedemo.model;

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
    @JsonView(Views.UI.class)
    private String content;
}
